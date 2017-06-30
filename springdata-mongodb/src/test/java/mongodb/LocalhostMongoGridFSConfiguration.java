package mongodb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Test configuration for GridFS using localhost:27017
 * @author Tobias Trelle
 */
@Configuration
@EnableMongoRepositories("mongodb")
public class LocalhostMongoGridFSConfiguration extends LocalhostMongoConfiguration {

	@Bean 
	public GridFsTemplate gridTemplate() throws Exception {
		return new GridFsTemplate( mongoDbFactory(), mappingMongoConverter() );
	}
	
}
