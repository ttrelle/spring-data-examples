package mongodb.user;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import mongodb.config.LocalhostMongoConfiguration;
import mongodb.user.User;
import mongodb.user.UserRepository;

/**
 * Tests for Spring Data MongoDB.
 * 
 * @author <a href="http://blog.codecentric.de/en/author/tobias-trelle">Tobias Trelle</a>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=LocalhostMongoConfiguration.class)
public class UserRepositoryTest {
	
	 @Autowired UserRepository repo;
	  
	 @Autowired MongoTemplate template;
	 
	 @Before public void setUp() {
		 
		 template.dropCollection("user");
		 template.createCollection("user");
		 
		 repo.save(new User("root", "Superuser"));
		 for ( int i = 0; i < 6; i++ ) {
			 repo.save( new User( String.format("user%02d", i), "User " + i ) );
		 }
	 }

	 @Test public void shouldFindByCustomerQuery() {
		 List<User> users;
		 
		 // when
		 users = repo.findByTheUsersFullName("User 0");
		 
		 // then
		 assertUserByFullName(users, "User 0");
	 }
	 
	 
	 @Test public void shouldFindByFullNameLike() {
		 List<User> users;
		 
		 // when
		 users = repo.findByFullNameLike("User", null);
		 
		 // then
		 assertUserCount(users, 6);
	 }
	 
	 @Test public void shouldPageUsers() {
		 List<User> users;
		 
		 // when
		 Page<User> page = repo.findAll( new PageRequest(2, 2 ) );
		 users = page.getContent();
	 
		 // then
		 assertUserCount(users, 2);
	 }
	 
	 @After public void tearDown() {
		 repo.deleteAll();
	 }
	 
	 private static void assertUserByFullName(List<User> users, String fullName)  {
		 assertUserCount(users, 1);
		 assertThat( "Mismatch full name", users.get(0).getFullName(), is(fullName));
	 }

	 private static void assertUserCount(List<User> users, int expected) {
		 assertThat( users, notNullValue() );
		 assertThat( "Mismatch user count", users.size(), is(expected));
	 }
	 
}
