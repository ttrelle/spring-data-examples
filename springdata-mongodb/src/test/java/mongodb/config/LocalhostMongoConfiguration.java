package mongodb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import mongodb.order.OrderBeforeSaveListener;

/**
 * Test configuration using localhost:27017
 * @author Tobias Trelle
 */
@Configuration
@EnableMongoRepositories({"mongodb.geo", "mongodb.order", "mongodb.user"})
public class LocalhostMongoConfiguration extends AbstractMongoClientConfiguration {

	@Bean
	public OrderBeforeSaveListener beforeSaveListener() {
		return new OrderBeforeSaveListener();
	}

	@Override
	protected String getDatabaseName() {
		return "odm_springdata";
	}

	@Override
	public MongoClient mongoClient() {
		return MongoClients.create();
	}

}
