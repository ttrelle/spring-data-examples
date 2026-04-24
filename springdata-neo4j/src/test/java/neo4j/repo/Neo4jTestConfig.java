package neo4j.repo;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.AbstractNeo4jConfig;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@Configuration
@EnableNeo4jRepositories("neo4j.repo")
public class Neo4jTestConfig extends AbstractNeo4jConfig {

	@Bean
	@Override
	public Driver driver() {
		return GraphDatabase.driver("bolt://localhost:7687", AuthTokens.none());
	}

}
