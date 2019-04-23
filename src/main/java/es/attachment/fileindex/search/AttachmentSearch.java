package es.attachment.fileindex.search;

import java.io.IOException;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttachmentSearch {

	@Autowired
	private RestHighLevelClient restHighLevelClient;

	public void search() {
		SearchRequest searchRequest = new SearchRequest("my_index");
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(new MatchQueryBuilder("attachment.content", "哈哈哈"));
		searchRequest.source(searchSourceBuilder);
		try {
			SearchResponse searchResponse = restHighLevelClient.search(searchRequest);
			SearchHits searchHits = searchResponse.getHits();
			if (searchHits.totalHits > 0) {

				SearchHit first = searchHits.getAt(0);
				Object attachment = first.getSource().get("attachment");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
