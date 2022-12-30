package com.java.gmall.search.repository;

import com.java.gmall.search.entity.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author jiangli
 * @since 2020/1/13 23:14
 */
public interface GoodsRepository extends ElasticsearchRepository<Goods,Long> {
}
