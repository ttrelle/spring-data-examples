package mongodb.user;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import mongodb.config.LocalhostMongoConfiguration;

/**
 * Tests for Spring Data MongoDB.
 *
 * @author <a href="http://blog.codecentric.de/en/author/tobias-trelle">Tobias Trelle</a>
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = LocalhostMongoConfiguration.class)
public class UserRepositoryTest {

	@Autowired UserRepository repo;

	@Autowired MongoTemplate template;

	@BeforeEach
	public void setUp() {
		template.dropCollection("user");
		template.createCollection("user");

		repo.save(new User("root", "Superuser"));
		for (int i = 0; i < 6; i++) {
			repo.save(new User(String.format("user%02d", i), "User " + i));
		}
	}

	@Test
	public void shouldFindByCustomerQuery() {
		List<User> users;

		// when
		users = repo.findByTheUsersFullName("User 0");

		// then
		assertUserByFullName(users, "User 0");
	}

	@Test
	public void shouldFindByFullNameLike() {
		List<User> users;

		// when
		users = repo.findByFullNameLike("User", null);

		// then
		assertUserCount(users, 6);
	}

	@Test
	public void shouldPageUsers() {
		// when
		Page<User> page = repo.findAll(PageRequest.of(2, 2));
		List<User> users = page.getContent();

		// then
		assertUserCount(users, 2);
	}

	@AfterEach
	public void tearDown() {
		repo.deleteAll();
	}

	private static void assertUserByFullName(List<User> users, String fullName) {
		assertUserCount(users, 1);
		assertThat("Mismatch full name", users.get(0).getFullName(), is(fullName));
	}

	private static void assertUserCount(List<User> users, int expected) {
		assertThat(users, notNullValue());
		assertThat("Mismatch user count", users.size(), is(expected));
	}

}
