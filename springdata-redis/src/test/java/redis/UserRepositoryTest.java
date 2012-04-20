package redis;

import junit.framework.Assert;

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
		
		Assert.assertNotNull("Value is <null>", user);
		Assert.assertEquals( "login mismatch" , "root", user.getLogin());
		Assert.assertEquals( "login mismatch" , "Superuser", user.getFullName());
	}
	
}
