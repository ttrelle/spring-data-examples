package de.codecentric.driverclient;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

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
		MongoClient mongo;
		
		// Default: localhost:27017
		mongo = new MongoClient();
		
		MongoDatabase db = mongo.getDatabase("test");
		MongoCollection<Document> collection = db.getCollection("order");
		
		insert(collection);
		find(mongo);
	}
	
	private static void insert(MongoCollection<Document> collection) {
		Document order;
		List<Document> items = new ArrayList<Document>();
		Document item;
		
		// order
		order = new Document();
		order.put("date", new Date());
		order.put("custInfo" , "Tobias Trelle");
		order.put("items", items);
		// items
		item = new Document();
		item.put("quantity", 1);
		item.put("price", 47.11);
		item.put("desc", "Item #1");
		items.add(item);
		item = new Document();
		item.put("quantity", 2);
		item.put("price", 42.0);
		item.put("desc", "Item #2");
		items.add(item);
		
		collection.insertOne(order);
	}
	
	private static void find(MongoClient mongo) {
		MongoDatabase db = mongo.getDatabase("test");
		MongoCollection<Document> collection = db.getCollection("order");
		Bson query;
		FindIterable<Document> cursor;
		
		query = new BasicDBObject("items.quantity", 2);
		cursor = collection.find(query);

		for(Document document: cursor ) {
			println(document);
		}
	}
	
	private static final void println(Object o) {
		System.out.println(o);
	}
	
}
