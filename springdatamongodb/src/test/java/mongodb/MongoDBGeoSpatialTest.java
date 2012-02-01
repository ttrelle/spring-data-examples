package mongodb;

import java.util.List;

import junit.framework.Assert;

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

/**
 * Tests for Spring Data MongoDB.
 * 
 * @author <a href="http://blog.codecentric.de/en/author/tobias-trelle">Tobias Trelle</a>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class MongoDBGeoSpatialTest {
	
	 @Autowired LocationRepository repo;
	  
	 @Autowired MongoTemplate template;
	 
	 @Before public void setUp() {
		 template.executeCommand("db.location.ensureIndex({pos: \"2d\"})");
		 repo.save( new Location("01", 0.0, 0.0, "Location 01") );
	 }
	 
	 @Test public void shouldDoNothing() {
		 
	 }

	 
	 @After public void tearDown() {
		 //repo.deleteAll();
	 }
	 
	 private static void assertUserByFullName(List<User> users, String fullName)  {
		 assertUserCount(users, 1);
		 Assert.assertEquals( "Mismatch full name" , fullName, users.get(0).getFullName());
	 }

	 private static void assertUserCount(List<User> users, int expected) {
		 Assert.assertNotNull( users );
		 Assert.assertEquals( "Mismatch user count" , expected, users.size());
	 }
	 
}
