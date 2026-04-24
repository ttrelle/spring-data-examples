package neo4j.repo;

import java.util.List;

import neo4j.domain.User;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

public interface UserRepository extends Neo4jRepository<User, Long> {

	User findByLogin(String login);

	@Query("MATCH (root:User {login: 'root'})-[:KNOWS]->(friends) RETURN friends")
	List<User> findFriendsOfRoot();

}
