package com.java.gmall.search.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jiangli
 * @since 2020/1/14 20:53
 */
@Configuration
public class ElasticConfig {

	@Bean
	public RestHighLevelClient restHighLevelClient() {
		return new RestHighLevelClient(RestClient.builder(HttpHost.create("106.54.238.192:9200")));
	}
}
