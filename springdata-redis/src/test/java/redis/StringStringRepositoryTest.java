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
public class StringStringRepositoryTest {

	@Autowired StringStringRepository repo;

	@BeforeEach
	public void setUp() {
		repo.add("foo", "bar");
	}

	@Test
	public void shouldFindValue() {
		String value = repo.getValue("foo");

		assertNotNull(value, "Value is <null>");
		assertEquals("bar", value, "Value mismatch");
	}

}
