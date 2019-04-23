package es.attachment.fileindex.index;

import java.io.IOException;
import java.lang.reflect.Field;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class IndexAPI {

	public void practice() throws IOException {
		IndexRequest indexRequest = new IndexRequest("my_index", "my_type", "my_id");
		XContentBuilder builder = XContentFactory.jsonBuilder();
		builder.startObject();
		{
			builder.field("", "");
		}
	}
}
