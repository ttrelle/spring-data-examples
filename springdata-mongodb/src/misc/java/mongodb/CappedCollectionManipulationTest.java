package mongodb;

import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * This test shows that deletes and updates to capped collections fail silently.
 * <p/>
 * To run this test, a local mongod instance is required using the standard port.
 * 
 * @author Tobias Trelle
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class CappedCollectionManipulationTest {

	private static final String COLLECTION_NAME = "user";
	
	 @Autowired UserRepository repo;
	  
	 @Autowired MongoOperations template;
	
	 /** 
	  * Create a capped collection.
	  * If you create an uncapped collections the tests go green.
	  */
	 @Before
	 public void setUp() {
		 template.dropCollection(COLLECTION_NAME);
		 
		 // capped
		 template.createCollection(COLLECTION_NAME, new CollectionOptions(1000,5,true));
		 
		 // uncapped
		 //template.createCollection(COLLECTION_NAME);
	 }
	 
	 @Test
	 public void does_not_delete_from_capped_collection() {
		 // given
		 for ( int i = 0; i< 5; i++ ) {
			 repo.save(new User("user"+i, "Herr User Nr. " +i));
		 }
		 
		 // when
		 repo.delete("user0");
		 
		 // then
		 assertNull("User not deleted", repo.findOne("user0"));
	 }

	 @Test
	 public void does_not_update_capped_collection() {
		 // given
		 for ( int i = 0; i< 5; i++ ) {
			 repo.save(new User("user"+i, "Herr User Nr. " +i));
		 }
		 
		 // when
		 User user;
		 user = repo.findOne("user0");
		 user.setFullName( "Something completely different" );
		 repo.save(user);
		 user = repo.findOne("user0");
		 
		 // then
		 assertThat("Fullname mismatch", 
				 user.getFullName(), is("Something completely different") );
	 }
	 
}
