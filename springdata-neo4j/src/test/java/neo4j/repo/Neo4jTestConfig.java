package neo4j.repo;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.server.InProcessServer;
import org.springframework.data.neo4j.server.Neo4jServer;
import org.springframework.data.neo4j.template.Neo4jTemplate;

@Configuration
@EnableNeo4jRepositories("neo4j.repo")
public class Neo4jTestConfig extends Neo4jConfiguration {

	@Override
	public Neo4jServer neo4jServer() {
		return new InProcessServer();
	}

	@Override
	public SessionFactory getSessionFactory() {
		return new SessionFactory("neo4j.domain");
	}

}
