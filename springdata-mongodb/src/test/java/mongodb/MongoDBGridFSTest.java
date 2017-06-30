package mongodb;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;

import mongodb.config.LocalhostMongoGridFSConfiguration;

/**
 * Tests for Spring Data MongoDB GridFS template.
 * 
 * @author <a href="http://blog.codecentric.de/en/author/tobias-trelle">Tobias Trelle</a>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=LocalhostMongoGridFSConfiguration.class)
public class MongoDBGridFSTest {
	  
	 @Autowired GridFsTemplate template;
	 
	 @Test public void shouldListExistingFiles() {
		 List<GridFSDBFile> files = template.find(null);
		 
		 for (GridFSDBFile file: files) {
			 out(file);
		 }
	 }

	 @Test public void shouldDeleteAllFiles() {
		 // when
		 template.delete(null);
		 
		 // then
		 List<GridFSDBFile> files = template.find(null);
		 assertThat( files.size(), is(0) );
	 }
	 
	 @Test public void shouldStoreFile() throws IOException {
		 // given
		 template.delete(null); // delete all files
		 Resource res = new ClassPathResource("dummy.txt");
		 DBObject metaData = new BasicDBObject();
		 metaData.put("source", "my workspace");
		 
		 // when
		 template.store(res.getInputStream(), "filename", metaData);
		 
		 // then
		 List<GridFSDBFile> files = template.find(null);
		 out( files.get(0) );
		 assertThat(files.size(), is(1));
	 }
	
	 private static final void out(Object o) {
		 System.out.println(o);
	 }
	 
}
