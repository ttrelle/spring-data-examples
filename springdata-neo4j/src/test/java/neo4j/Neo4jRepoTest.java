package neo4j;

import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class Neo4jRepoTest {

	private static final int USER_COUNT = 4;
	
	@Autowired UserRepository repo;
	
	private long rootId;
	
	@Before public void setUp() {
		User root = new User("root", "Superuser");
		User[] user = new User[USER_COUNT];
		
		for ( int i = 0; i < user.length; i++ ) {
			user[i]  = new User(String.format("user%02d", i), "User" + i);
		}

		// build graph
		for ( int i = 0; i < user.length; i++ ) {
			root.knows(user[i]);
			for ( int j = i; j < user.length; j++ ) {
				user[i].knows(user[j]);
			}
		}

		// save nodes
		for ( int i = 0; i < user.length; i++ ) {
			repo.save(user[i]);
		}
		repo.save( root );
		rootId = root.getId();
		out("Root id: " + rootId);
		
	}
	
	@Test public void shouldFindAll() {
		// when
		long n = repo.count();
		
		// then
		Assert.assertEquals("User count mismatch", USER_COUNT + 1, n);
	}
	
	@Test public void shouldFindRootUserById() {
		// when
		User root = repo.findOne(rootId);
		
		// then
		Assert.assertNotNull( "Root user not found", root );
	}
	
	@Test public void shouldFindRootUserByLogin() {
		// when
		User root = repo.findByLogin("root");
		
		// then
		Assert.assertNotNull( "Root user not found", root );
	}
	
	@Test public void shouldFindFriendsOfRoot() {
		// when
		List<User> users = repo.findFriendsOfRoot();
		
		// then
		Assert.assertNotNull("result is <null>", users);
		Assert.assertEquals( "mismatch @ friend count", USER_COUNT, users.size() );
	}
	
	@After
	public void tearDown() {
		repo.deleteAll();
	}
	
	private static void out(Object o) {
		System.out.println(o);
	}
	
}
