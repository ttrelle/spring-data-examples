package driverclient;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.net.UnknownHostException;

import org.junit.Before;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;

/**
 * This test shows that violation on indexes are not ignored.
 * <p/>
 * To run this test, a local mongod instance is required using the standard port.
 * 
 * @author Tobias Trelle
 */
public class IndexViolationTest {

	private static final String COLLECTION_NAME = "user";
	
	private Mongo mongo;
	private DB db;
	private DBCollection collection;	
	
	 @Before
	 public void setUp() throws UnknownHostException {
		mongo = new Mongo("localhost"); // default port 27017
		db = mongo.getDB( "test" );	 
		db.getCollection(COLLECTION_NAME).drop();
		collection = db.createCollection(COLLECTION_NAME,new BasicDBObject());
		collection.ensureIndex(createDocument("{fullName: 1}"), "fullName_1", true );
	}
	
	@Test
	public void should_detect_index_violation_on_id()  {
		// given
		collection.insert( createDocument("{_id:0, fullName: \"User 0\"}") );

		// when
		collection.insert( createDocument("{_id:0, fullName: \"User 1\"}") );
		
		// then: except duplicate key error
		CommandResult cr = db.getLastError();
		assertThat( cr.getInt("code"), is(11000) );
	}

	@Test
	public void should_detect_index_violation_on_fullName()  {
		// given
		collection.insert( createDocument("{_id:100, fullName: \"User 100\"}") );

		// when
		collection.insert( createDocument("{_id:101, fullName: \"User 100\"}") );
		
		// then: except duplicate key error
		CommandResult cr = db.getLastError();
		assertThat( cr.getInt("code"), is(11000) );
	}
	
	private static final DBObject createDocument(String s) {
		return (DBObject)JSON.parse(s);
	}
	
}
