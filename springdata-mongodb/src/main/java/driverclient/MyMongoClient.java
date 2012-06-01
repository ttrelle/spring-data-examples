package driverclient;

import java.net.UnknownHostException;
import java.util.Date;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

/**
 * Simple MongoDB client based on the MongoDB Java driver API.
 */
public class MyMongoClient {

	/**
	 * CLI call.
	 * @param argv command line arguments
	 * @throws MongoException 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] argv) throws UnknownHostException, MongoException {
		Mongo m = new Mongo("localhost"); // default port 27017
		DB db = m.getDB( "test" );
		
		// get collection names
		Set<String> colls = db.getCollectionNames();
		for (String s : colls) {
		    out(s);
		}
		
		// insert a simple doc
		DBCollection coll = db.getCollection("foo");
		DBObject doc = new BasicDBObject();
		doc.put("date", new Date());
		coll.save(doc);
	}

	private static final void out(Object o) {
		System.out.println(o);
	}
	
}
