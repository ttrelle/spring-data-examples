package redis;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class RedisTemplateTest {
	
	@Autowired UserRepository repo;
	
	@Before
	public void setUp() {
		repo.add("foo", "bar");
	}

	@Test
	public void shouldFindValue() {
		// nada
	}
	
}
