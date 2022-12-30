package com.java.gmall.search.service;

import cn.hutool.json.JSONUtil;
import com.java.core.exception.RRException;
import com.java.gmall.search.entity.Goods;
import com.java.gmall.search.vo.SearchParamVO;
import com.java.gmall.search.vo.SearchResponseAttrVO;
import com.java.gmall.search.vo.SearchResponseVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.nested.ParsedNested;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author jiangli
 * @since 2020/1/13 23:20
 */
@Service
public class SearchService {
	@Autowired
	private RestHighLevelClient restHighLevelClient;

	public SearchResponseVO search(SearchParamVO searchParamVO) throws IOException {

		//构建dsl语句
		SearchRequest searchRequest = this.buildQueryDsl(searchParamVO);
		if (searchRequest == null) {
			throw new RRException("请输入搜索关键字");
		}
		SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		SearchResponseVO searchResponseVO = fetchSearchResponseVO(searchResponse);
		searchResponseVO.setPageSize(searchParamVO.getPageSize());
		searchResponseVO.setPageNum(searchParamVO.getPageNum());
		return searchResponseVO;
	}

	private SearchResponseVO fetchSearchResponseVO(SearchResponse searchResponse) {
		SearchResponseVO searchResponseVO = new SearchResponseVO();
		// 获取总记录数
		SearchHits hits = searchResponse.getHits();
		searchResponseVO.setTotal(hits.totalHits);
		// 获取聚合桶集合
		Map<String, Aggregation> stringAggregationMap = searchResponse.getAggregations().asMap();

		// 解析品牌
		//获取品牌聚合桶
		ParsedLongTerms brandIdAgg = (ParsedLongTerms) stringAggregationMap.get("brandIdAgg");
		List<String> brandValue = brandIdAgg.getBuckets().stream().map(bucket -> {
			Map<String, String> map = new HashMap<>();
			String id = bucket.getKeyAsString();
			map.put("id", id);
			Map<String, Aggregation> subAggregationMap = bucket.getAggregations().asMap();
			ParsedStringTerms brandNameAgg = (ParsedStringTerms) subAggregationMap.get("brandNameAgg");
			String brandName = brandNameAgg.getBuckets().get(0).getKeyAsString();
			map.put("name", brandName);
			return JSONUtil.toJsonStr(map);
		}).collect(Collectors.toList());
		SearchResponseAttrVO brand = new SearchResponseAttrVO();
		brand.setName("品牌");
		brand.setValue(brandValue);

		searchResponseVO.setBrand(brand);

		//解析分类
		//获取分类聚合桶
		ParsedLongTerms categoryIdAgg = (ParsedLongTerms) stringAggregationMap.get("categoryIdAgg");
		List<String> categoryValue = categoryIdAgg.getBuckets().stream().map(bucket -> {
			Map<String, String> map = new HashMap<>();
			String id = bucket.getKeyAsString();
			map.put("id", id);
			Map<String, Aggregation> subAggregationMap = bucket.getAggregations().asMap();
			ParsedStringTerms categoryNameAgg = (ParsedStringTerms) subAggregationMap.get("categoryNameAgg");
			String categoryName = categoryNameAgg.getBuckets().get(0).getKeyAsString();
			map.put("name", categoryName);
			return JSONUtil.toJsonStr(map);
		}).collect(Collectors.toList());
		SearchResponseAttrVO category = new SearchResponseAttrVO();
		category.setName("分类");
		category.setValue(categoryValue);
		searchResponseVO.setCatelog(category);

		// 获取Goods集合
		List<Goods> goods = new ArrayList<>();
		for (SearchHit subHit : hits.getHits()) {
			Goods goodsBean = JSONUtil.toBean(subHit.getSourceAsString(), Goods.class);
			// 设置高亮属性
			Text title = subHit.getHighlightFields().get("title").getFragments()[0];
			goodsBean.setTitle(title.toString());
			goods.add(goodsBean);
		}
		searchResponseVO.setProducts(goods);

		// 规格参数
		// 获取嵌套聚合对象
		ParsedNested attrAgg = (ParsedNested) stringAggregationMap.get("attrAgg");
		// 规格参数id聚合对象
		ParsedLongTerms attrIdAgg = attrAgg.getAggregations().get("attrIdAgg");
		List<SearchResponseAttrVO> attrs = attrIdAgg.getBuckets().stream().map(bucket -> {
			SearchResponseAttrVO searchAttrVO = new SearchResponseAttrVO();
			// 设置规格参数id
			searchAttrVO.setProductAttributeId(bucket.getKeyAsNumber().longValue());
			// 设置规格参数名
			searchAttrVO.setName(((ParsedStringTerms) bucket.getAggregations().get("attrNameAgg")).getBuckets().get(0).getKeyAsString());
			// 设置规格参数值的列表
			List<? extends Terms.Bucket> attrValueBuckets = ((ParsedStringTerms) bucket.getAggregations().get("attrValueAgg")).getBuckets();
			List<String> values = attrValueBuckets.stream().map(Terms.Bucket::getKeyAsString).collect(Collectors.toList());
			searchAttrVO.setValue(values);
			return searchAttrVO;
		}).collect(Collectors.toList());
		searchResponseVO.setAttrs(attrs);

		return searchResponseVO;
	}

	/**
	 * 详见dsl.txt
	 */
	private SearchRequest buildQueryDsl(SearchParamVO searchParamVO) {
		// 关键字
		String keyword = searchParamVO.getKeyword();
		if (StringUtils.isEmpty(keyword)) {
			return null;
		}

		// 查询条件构建起
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		// 构建查询条件和过滤条件
		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
		// 构建关键字查询条件
		boolQuery.must(QueryBuilders.matchQuery("title", keyword).operator(Operator.AND));

		// 品牌过滤条件
		String[] brand = searchParamVO.getBrand();
		if (brand != null && brand.length > 0) {
			boolQuery.filter(QueryBuilders.termsQuery("brandId", brand));
		}
		// 分类过滤
		String[] catelog3 = searchParamVO.getCatelog3();
		if (catelog3 != null && catelog3.length > 0) {
			boolQuery.filter(QueryBuilders.termsQuery("categoryId", catelog3));
		}

		//构建规格属性嵌套过滤
		String[] props = searchParamVO.getProps();
		if (props != null && props.length > 0) {
			for (String prop : props) {
				BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

				BoolQueryBuilder subBoolQueryBuilder = QueryBuilders.boolQuery();
				String[] split = StringUtils.split(prop, ":");
				if (split == null || split.length != 2) {
					continue;
				}
				subBoolQueryBuilder.must(QueryBuilders.termQuery("attrs.attrId", split[0]));
				String[] attrValues = StringUtils.split(split[1], "-");
				subBoolQueryBuilder.must(QueryBuilders.termsQuery("attrs.attrValue", attrValues));

				boolQueryBuilder.must(QueryBuilders.nestedQuery("attrs", subBoolQueryBuilder, ScoreMode.None));
				boolQuery.filter(boolQueryBuilder);
			}
		}

		// 价格区间过滤
		RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("price");
		Integer priceFrom = searchParamVO.getPriceFrom();
		Integer priceTo = searchParamVO.getPriceTo();
		if (priceFrom != null) {
			rangeQuery.gte(priceFrom);
		}
		if (priceTo != null) {
			rangeQuery.lte(priceTo);
		}
		boolQuery.filter(rangeQuery);

		searchSourceBuilder.query(boolQuery);

		// 分页
		Integer pageNum = searchParamVO.getPageNum();
		Integer pageSize = searchParamVO.getPageSize();
		searchSourceBuilder.from((pageNum - 1) * pageSize);
		searchSourceBuilder.size(pageSize);

		// 排序
		String order = searchParamVO.getOrder();
		if (StringUtils.isNotEmpty(order)) {
			String[] split = StringUtils.split(order, ":");
			if (split != null && split.length == 2) {
				String field = "";
				switch (split[0]) {
					case "1":
						field = "seal";
						break;
					case "2":
						field = "price";
						break;
				}
				searchSourceBuilder.sort(field, StringUtils.equals(split[1], "asc") ? SortOrder.ASC : SortOrder.DESC);
			}
		}

		// 高亮
		searchSourceBuilder.highlighter(new HighlightBuilder().field("title").preTags("<em>").postTags("</em>"));

		// 构建聚合查询
		// 品牌聚合
		searchSourceBuilder.aggregation(AggregationBuilders.terms("brandIdAgg").field("brandId").subAggregation(AggregationBuilders.terms("brandNameAgg").field("brandName")));
		// 分类聚合
		searchSourceBuilder.aggregation(AggregationBuilders.terms("categoryIdAgg").field("categoryId").subAggregation(AggregationBuilders.terms("categoryNameAgg").field("categoryName")));
		// 搜索的规格属性聚合
		searchSourceBuilder.aggregation(AggregationBuilders.nested("attrAgg", "attrs")
				.subAggregation(AggregationBuilders.terms("attrIdAgg").field("attrs.attrId")
						.subAggregation(AggregationBuilders.terms("attrNameAgg").field("attrs.attrName"))
						.subAggregation(AggregationBuilders.terms("attrValueAgg").field("attrs.attrValue"))));

		System.out.println(searchSourceBuilder.toString());

		// 结果集过滤
		searchSourceBuilder.fetchSource(new String[]{"skuId", "pic", "title", "price"}, null);

		// 查询参数
		SearchRequest searchRequest = new SearchRequest("gmall");
		searchRequest.types("goods");
		searchRequest.source(searchSourceBuilder);
		return searchRequest;
	}
}
