package redis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
public class UserRepositoryTest {

	@Autowired UserRepository repo;

	@BeforeEach
	public void setUp() {
		repo.add(new User("root", "Superuser"));
	}

	@Test
	public void shouldFindValue() {
		User user = repo.get("root");

		assertNotNull(user, "Value is <null>");
		assertEquals("root", user.getLogin(), "login mismatch");
		assertEquals("Superuser", user.getFullName(), "fullName mismatch");
	}

}
