package mongodb;

import java.net.UnknownHostException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;

@Configuration
@EnableMongoRepositories("mongodb")
public class OrderRepositoryConfig {

	@Bean 
	public MongoTemplate mongoTemplate() throws UnknownHostException {
		return new MongoTemplate(new SimpleMongoDbFactory(new Mongo(), "odm_springdata"));
	}
	
	@Bean
	public OrderBeforeSaveListener beforeSaveListener() {
		return new OrderBeforeSaveListener();
	}
	
}