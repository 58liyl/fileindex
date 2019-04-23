package es.attachment.fileindex.index;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.StatusLine;
import org.apache.http.message.BasicHeader;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.rest.RestRequest.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@RestController
public class FileIndex {

	@Autowired
	private RestTemplate restTemplate;

	public void indexFile() {

		RestClient client = null;
		ResponseEntity<byte[]> responseEntity = restTemplate
				.getForEntity("http://localhost:8080/filedownload/linuxcmd.pdf", byte[].class);
//		try {
//			System.out.println(new String(responseEntity.getBody(), "GBK"));
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}

		try {
			HttpHost[] hosts = { new HttpHost("192.168.174.20", 9200), new HttpHost("192.168.174.21", 9200),
					new HttpHost("192.168.174.22", 9200) };
			client = RestClient.builder(hosts).build();
			Map<String, String> map = Collections.singletonMap("data",
					Base64Utils.encodeToString(responseEntity.getBody()));

			HttpEntity entity = new NStringEntity(new ObjectMapper().writeValueAsString(map));
			Header header = new BasicHeader("Content-Type", "application/vnd.ms-excel");
			Response resp = client.performRequest(Method.PUT.name(), "my_index/my_type/my_id",
					Collections.singletonMap("pipeline", "attachment"), entity, header);
			StatusLine status = resp.getStatusLine();

			System.out.println(status.toString());

			System.out.println(EntityUtils.toString(resp.getEntity()));
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (client != null) {
				try {
					client.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	public void indexAttachment() {
		RestHighLevelClient client = null;

		try {
			ResponseEntity<byte[]> responseEntity = restTemplate.getForEntity("http://localhost:8080/filedownload/linuxcmd.pdf",
					byte[].class);
			HttpHost[] hosts = { new HttpHost("192.168.174.20", 9200), new HttpHost("192.168.174.21", 9200),
					new HttpHost("192.168.174.22", 9200) };
			RestClient restClient = RestClient.builder(hosts).build();

			XContentBuilder builder = XContentFactory.cborBuilder();
			builder.startObject();
//			builder.field("data", new String(responseEntity.getBody(), "GBK").getBytes());
			builder.field("data", responseEntity.getBody());
			builder.endObject();
			IndexRequest indexRequest = new IndexRequest("my_index", "my_type", "my_id");
			indexRequest.setPipeline("attachment");
			indexRequest.source(builder);

			client = new RestHighLevelClient(restClient);
			IndexResponse response = client.index(indexRequest);

			if (response.getResult() == org.elasticsearch.action.DocWriteResponse.Result.UPDATED) {
				System.out.println(response.getResult().name());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}

	}

	public void getDocs() {
		try {
			HttpHost[] hosts = { new HttpHost("192.168.174.20", 9200), new HttpHost("192.168.174.21", 9200),
					new HttpHost("192.168.174.22", 9200) };
			RestClient client = RestClient.builder(hosts).build();
			Response resp = client.performRequest(Method.GET.name(), "my_index/my_type/my_id");
			StatusLine status = resp.getStatusLine();
			System.out.println(status.toString());

			System.out.println(EntityUtils.toString(resp.getEntity()));
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
