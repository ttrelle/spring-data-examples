package mongodb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

/**
 * Test configuration for GridFS using localhost:27017
 * @author Tobias Trelle
 */
@Configuration
public class LocalhostMongoGridFSConfiguration extends LocalhostMongoConfiguration {

	@Bean 
	public GridFsTemplate gridTemplate() throws Exception {
		return new GridFsTemplate( mongoDbFactory(), mappingMongoConverter() );
	}
	
}
