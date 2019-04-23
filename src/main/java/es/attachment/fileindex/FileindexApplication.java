package es.attachment.fileindex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import es.attachment.fileindex.config.Utils;
import es.attachment.fileindex.index.FileIndex;
import es.attachment.fileindex.search.AttachmentSearch;

@SpringBootApplication
@Import(Utils.class)
public class FileindexApplication implements CommandLineRunner {
	@Autowired
	public FileIndex fileIndex;
	
	@Autowired
	public AttachmentSearch attachment;

	public static void main(String[] args) {

		SpringApplication.run(FileindexApplication.class, args);
		System.out.println("FileindexApplication");

	}

	@Override
	public void run(String... args) throws Exception {
		attachment.search();
	}

}
