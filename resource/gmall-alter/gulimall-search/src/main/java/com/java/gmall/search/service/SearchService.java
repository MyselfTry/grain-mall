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

		//??????dsl??????
		SearchRequest searchRequest = this.buildQueryDsl(searchParamVO);
		if (searchRequest == null) {
			throw new RRException("????????????????????????");
		}
		SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		SearchResponseVO searchResponseVO = fetchSearchResponseVO(searchResponse);
		searchResponseVO.setPageSize(searchParamVO.getPageSize());
		searchResponseVO.setPageNum(searchParamVO.getPageNum());
		return searchResponseVO;
	}

	private SearchResponseVO fetchSearchResponseVO(SearchResponse searchResponse) {
		SearchResponseVO searchResponseVO = new SearchResponseVO();
		// ??????????????????
		SearchHits hits = searchResponse.getHits();
		searchResponseVO.setTotal(hits.totalHits);
		// ?????????????????????
		Map<String, Aggregation> stringAggregationMap = searchResponse.getAggregations().asMap();

		// ????????????
		//?????????????????????
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
		brand.setName("??????");
		brand.setValue(brandValue);

		searchResponseVO.setBrand(brand);

		//????????????
		//?????????????????????
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
		category.setName("??????");
		category.setValue(categoryValue);
		searchResponseVO.setCatelog(category);

		// ??????Goods??????
		List<Goods> goods = new ArrayList<>();
		for (SearchHit subHit : hits.getHits()) {
			Goods goodsBean = JSONUtil.toBean(subHit.getSourceAsString(), Goods.class);
			// ??????????????????
			Text title = subHit.getHighlightFields().get("title").getFragments()[0];
			goodsBean.setTitle(title.toString());
			goods.add(goodsBean);
		}
		searchResponseVO.setProducts(goods);

		// ????????????
		// ????????????????????????
		ParsedNested attrAgg = (ParsedNested) stringAggregationMap.get("attrAgg");
		// ????????????id????????????
		ParsedLongTerms attrIdAgg = attrAgg.getAggregations().get("attrIdAgg");
		List<SearchResponseAttrVO> attrs = attrIdAgg.getBuckets().stream().map(bucket -> {
			SearchResponseAttrVO searchAttrVO = new SearchResponseAttrVO();
			// ??????????????????id
			searchAttrVO.setProductAttributeId(bucket.getKeyAsNumber().longValue());
			// ?????????????????????
			searchAttrVO.setName(((ParsedStringTerms) bucket.getAggregations().get("attrNameAgg")).getBuckets().get(0).getKeyAsString());
			// ??????????????????????????????
			List<? extends Terms.Bucket> attrValueBuckets = ((ParsedStringTerms) bucket.getAggregations().get("attrValueAgg")).getBuckets();
			List<String> values = attrValueBuckets.stream().map(Terms.Bucket::getKeyAsString).collect(Collectors.toList());
			searchAttrVO.setValue(values);
			return searchAttrVO;
		}).collect(Collectors.toList());
		searchResponseVO.setAttrs(attrs);

		return searchResponseVO;
	}

	/**
	 * ??????dsl.txt
	 */
	private SearchRequest buildQueryDsl(SearchParamVO searchParamVO) {
		// ?????????
		String keyword = searchParamVO.getKeyword();
		if (StringUtils.isEmpty(keyword)) {
			return null;
		}

		// ?????????????????????
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		// ?????????????????????????????????
		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
		// ???????????????????????????
		boolQuery.must(QueryBuilders.matchQuery("title", keyword).operator(Operator.AND));

		// ??????????????????
		String[] brand = searchParamVO.getBrand();
		if (brand != null && brand.length > 0) {
			boolQuery.filter(QueryBuilders.termsQuery("brandId", brand));
		}
		// ????????????
		String[] catelog3 = searchParamVO.getCatelog3();
		if (catelog3 != null && catelog3.length > 0) {
			boolQuery.filter(QueryBuilders.termsQuery("categoryId", catelog3));
		}

		//??????????????????????????????
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

		// ??????????????????
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

		// ??????
		Integer pageNum = searchParamVO.getPageNum();
		Integer pageSize = searchParamVO.getPageSize();
		searchSourceBuilder.from((pageNum - 1) * pageSize);
		searchSourceBuilder.size(pageSize);

		// ??????
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

		// ??????
		searchSourceBuilder.highlighter(new HighlightBuilder().field("title").preTags("<em>").postTags("</em>"));

		// ??????????????????
		// ????????????
		searchSourceBuilder.aggregation(AggregationBuilders.terms("brandIdAgg").field("brandId").subAggregation(AggregationBuilders.terms("brandNameAgg").field("brandName")));
		// ????????????
		searchSourceBuilder.aggregation(AggregationBuilders.terms("categoryIdAgg").field("categoryId").subAggregation(AggregationBuilders.terms("categoryNameAgg").field("categoryName")));
		// ???????????????????????????
		searchSourceBuilder.aggregation(AggregationBuilders.nested("attrAgg", "attrs")
				.subAggregation(AggregationBuilders.terms("attrIdAgg").field("attrs.attrId")
						.subAggregation(AggregationBuilders.terms("attrNameAgg").field("attrs.attrName"))
						.subAggregation(AggregationBuilders.terms("attrValueAgg").field("attrs.attrValue"))));

		System.out.println(searchSourceBuilder.toString());

		// ???????????????
		searchSourceBuilder.fetchSource(new String[]{"skuId", "pic", "title", "price"}, null);

		// ????????????
		SearchRequest searchRequest = new SearchRequest("gmall");
		searchRequest.types("goods");
		searchRequest.source(searchSourceBuilder);
		return searchRequest;
	}
}
