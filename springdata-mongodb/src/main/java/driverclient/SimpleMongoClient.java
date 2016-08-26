package driverclient;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Date;

import org.bson.BSON;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.MongoException;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

/**
 * Simple MongoDB client based on the MongoDB Java driver API.
 */
public class SimpleMongoClient {

	/**
	 * CLI call.
	 * @param argv command line arguments
	 * @throws MongoException 
	 * @throws UnknownHostException 
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] argv) throws UnknownHostException, MongoException, UnsupportedEncodingException {
		MongoClient mongo = null;
		
		try {
			// Default: localhost:27017
			mongo = new MongoClient();
			
			// Sharding: mongos server
			// mongo = new MongoClient("mongos-1", 4711);
			
			// Replica set
//			mongo = new MongoClient(
//				Arrays.asList(
//					new ServerAddress("localhost", 27001),
//					new ServerAddress("localhost", 27002),
//					new ServerAddress("localhost", 27003)
//				),
//				Arrays.asList(
//						MongoCredential.createCredential("test1", "test", "test1".toCharArray())
//				)
//			);
			
			// use database "test"
			MongoDatabase db = mongo.getDatabase("test");
			
			// get collection names
			MongoIterable<String> colls = db.listCollectionNames();
			for (String s : colls) {
			    println(s);
			}
			
			// use collection "foo"
			MongoCollection<Document> collection = db.getCollection("foo");
			
			insert(collection);
			find(collection);
			
			// remove(collection);
			// bsonize();
			} finally {
				if (mongo != null) {
					mongo.close();
				}
			}
	}
	
	private static void remove(MongoCollection<Document> collection) {
		// alle Dokuemente mit {i: 42}
		Bson criteria = new BasicDBObject("i", 42);
		collection.deleteOne(criteria);
		
		// alle Dokumente
		collection.deleteMany( new BasicDBObject() );
		
		// schneller:
		collection.drop();
	}
	
	
	private static void insert(MongoCollection<Document> collection) {
		// Document speichern
		Document doc = new Document();
		doc.put("date", new Date());
		doc.put("i", 42);
		
		collection.insertOne(doc);
	}
	
	private static void find(MongoCollection<Document> collection) {
		FindIterable<Document> cursor;
		
		// alle Dokumente
		cursor = collection.find();

		for ( Document document: cursor ) {
			println(document);
		}
	}

	private static void find(MongoCollection<Document> collection, Bson query) {
		FindIterable<Document> cursor;
		
		// alle Dokumente
		cursor = collection.find();
		
		// Dokumente mit {i: 42}
		cursor = collection.find( query ); 
		
		for ( Document document: cursor ) {
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
