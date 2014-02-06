package redis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class UserRepositoryTest {
	
	@Autowired UserRepository repo;
	
	@Before
	public void setUp() {
		repo.add( new User("root", "Superuser") );
	}

	@Test
	public void shouldFindValue() {
		User user = repo.get("root");
		
		assertNotNull("Value is <null>", user);
		assertEquals( "login mismatch" , "root", user.getLogin());
		assertEquals( "login mismatch" , "Superuser", user.getFullName());
	}
	
}
