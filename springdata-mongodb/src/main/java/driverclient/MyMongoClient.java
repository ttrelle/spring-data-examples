package driverclient;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Set;

import org.bson.BSON;

import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;

/**
 * Simple MongoDB client based on the MongoDB Java driver API.
 */
public class MyMongoClient {

	/**
	 * CLI call.
	 * @param argv command line arguments
	 * @throws MongoException 
	 * @throws UnknownHostException 
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] argv) throws UnknownHostException, MongoException, UnsupportedEncodingException {
		Mongo mongo;
		
		// Default: localhost:27017
		mongo = new Mongo();
		
//		// Sharding: mongos server
//		mongo = new Mongo("mongo-1", 4711);
//		
//		// Replica set
//		mongo = new Mongo(Arrays.asList(
//				new ServerAddress("replicant01", 10001),
//				new ServerAddress("replicant02", 10002),
//				new ServerAddress("replicant03", 10003)
//				));
		
		
		DB db = mongo.getDB("test");
		
		// get collection names
		Set<String> colls = db.getCollectionNames();
		for (String s : colls) {
		    println(s);
		}
		//getLastError(db);
		
		DBCollection collection = db.getCollection("foo");
		
		insert(collection);
		find(collection);
		
		// remove(collection);
		// bsonize();
	}
	
	private static void getLastError(DB db) {
		CommandResult cr;
		
		cr = db.getLastError(WriteConcern.NORMAL);
		System.out.println(cr);
	}
	
	private static void update(DBCollection collection) {
		
	}

	private static void remove(DBCollection collection) {
		// alle Dokuemente mit {i: 42}
		DBObject criteria 
			= new BasicDBObject("i", 42);
		collection.remove(criteria);
		
		// alle Dokumente
		collection.remove( new BasicDBObject() );
		
		// schneller:
		collection.drop();
	}
	
	
	private static void insert(DBCollection collection) {
		// Document speichern
		DBObject doc = new BasicDBObject();
		doc.put("date", new Date());
		doc.put("i", 42);
		
		collection.insert(doc);
	}
	
	private static void find(DBCollection collection) {
		DBObject document;
		DBCursor cursor;
		
		// alle Dokumente
		cursor = collection.find();

		while ( cursor.hasNext() ) {
			document = cursor.next();
			println(document);
		}
	}

	private static void find(DBCollection collection, DBObject query) {
		DBObject document;
		DBCursor cursor;
		
		// alle Dokumente
		cursor = collection.find();
		
		// Dokumente mit {i: 42}
		cursor = collection.find( query ); 
		
		while ( cursor.hasNext() ) {
			document = cursor.next();
			println(document);
		}
	}
	
	
	private static void bsonize() throws UnsupportedEncodingException {
		final String key = "hello";
		final String value = "MongoDB";
		
		bsonize( new BasicDBObject(key, value) );
		println( toString(key.getBytes("UTF-8")) );
		println( toString(value.getBytes("UTF-8")) );
	}

	
	private static void bsonize(DBObject doc) {
		final byte[] buff = BSON.encode(doc);
		
		println( toString(buff) );
	}
	
	private static String toString(byte[] buff) {
		final StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < buff.length; i++) {
			sb.append("\\x");
			
			String hex = Integer.toHexString(buff[i]);
			if ( hex.length() < 2 ) {
				sb.append("0");
			}
				
			sb.append(hex);
			;
		}

		return sb.toString();
	}
	
	private static final void println(Object o) {
		System.out.println(o);
	}
	
}
