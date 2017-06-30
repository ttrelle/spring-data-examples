package mongodb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

/**
 * Test configuration using localhost:27017
 * @author Tobias Trelle
 */
@Configuration
@EnableMongoRepositories("mongodb")
public class LocalhostMongoConfiguration extends AbstractMongoConfiguration {
	
	@Bean
	public OrderBeforeSaveListener beforeSaveListener() {
		return new OrderBeforeSaveListener();
	}

	@Override
	protected String getDatabaseName() {
		return "odm_springdata";
	}

	@Override
	public Mongo mongo() throws Exception {
		return new MongoClient();
	}
	
}
