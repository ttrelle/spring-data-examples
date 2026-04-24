package neo4j.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import neo4j.domain.User;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Neo4jTestConfig.class)
public class Neo4jRepoTest {

	private static final int USER_COUNT = 4;

	@Autowired
	Neo4jClient neo4jClient;

	@Autowired
	UserRepository repo;

	private Long rootId;

	@BeforeEach
	public void setUp() {
		neo4jClient.query("CREATE INDEX user_login IF NOT EXISTS FOR (u:User) ON (u.login)").run();

		User root = new User("root", "Superuser");
		User[] user = new User[USER_COUNT];

		for (int i = 0; i < user.length; i++) {
			user[i] = new User(String.format("user%02d", i), "User" + i);
		}

		for (int i = 0; i < user.length; i++) {
			root.knows(user[i]);
			for (int j = i; j < user.length; j++) {
				user[i].knows(user[j]);
			}
		}

		for (int i = 0; i < user.length; i++) {
			repo.save(user[i]);
		}
		repo.save(root);
		rootId = root.getId();
	}

	@Test
	public void shouldFindAll() {
		long n = repo.count();
		assertEquals(USER_COUNT + 1, n, "User count mismatch");
	}

	@Test
	public void shouldFindRootUserById() {
		User root = repo.findById(rootId).orElse(null);
		assertNotNull(root, "Root user not found");
	}

	@Test
	public void shouldFindRootUserByLogin() {
		User root = repo.findByLogin("root");
		assertNotNull(root, "Root user not found");
	}

	@Test
	@Disabled
	public void shouldFindFriendsOfRoot() {
		List<User> users = repo.findFriendsOfRoot();
		assertNotNull(users, "result is <null>");
		assertEquals(USER_COUNT, users.size(), "mismatch @ friend count");
	}

	@AfterEach
	public void tearDown() {
		repo.deleteAll();
	}

}
