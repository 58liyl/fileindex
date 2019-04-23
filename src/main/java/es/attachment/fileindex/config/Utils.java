package es.attachment.fileindex.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Utils {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public RestClient restClient() {
		HttpHost[] hosts = { new HttpHost("hadoop01", 9200), new HttpHost("hadoop02", 9200),
				new HttpHost("hadoop03", 9200) };
		return RestClient.builder(hosts).build();
	}

	@Bean
	public RestHighLevelClient restHighLevelClient() {
		return new RestHighLevelClient(restClient());
	}
}
