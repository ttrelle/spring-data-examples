package driverclient;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

/**
 * Simple MongoDB client based on the MongoDB Java driver API.
 */
public class OrderExample {

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
		
		DB db = mongo.getDB("test");
		DBCollection collection = db.getCollection("order");
		
		insert(collection);
		find(mongo);
	}
	
	private static void insert(DBCollection collection) {
		DBObject order;
		List<DBObject> items = new ArrayList<DBObject>();
		DBObject item;
		
		// order
		order = new BasicDBObject();
		order.put("date", new Date());
		order.put("custInfo" , "Tobias Trelle");
		order.put("items", items);
		// items
		item = new BasicDBObject();
		item.put("quantity", 1);
		item.put("price", 47.11);
		item.put("desc", "Item #1");
		items.add(item);
		item = new BasicDBObject();
		item.put("quantity", 2);
		item.put("price", 42.0);
		item.put("desc", "Item #2");
		items.add(item);
		
		collection.insert(order);
	}
	
	private static void find(Mongo mongo) {
		DB db = mongo.getDB("test");
		DBCollection collection = db.getCollection("order");
		DBObject query;
		DBObject document;
		DBCursor cursor;
		
		query = new BasicDBObject("items.quantity", 2);
		cursor = collection.find(query);

		while ( cursor.hasNext() ) {
			document = cursor.next();
			println(document);
		}
	}
	
	private static final void println(Object o) {
		System.out.println(o);
	}
	
}
