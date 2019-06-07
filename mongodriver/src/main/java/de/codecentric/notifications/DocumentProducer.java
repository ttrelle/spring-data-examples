package de.codecentric.notifications;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;

import de.codecentric.Connection;

public class DocumentProducer  {
    
	public static void main(String[] args) throws Exception {

		try (MongoClient client = new MongoClient(Connection.URI)) {
    		MongoCollection<Document> eventCollection = 
    				client.getDatabase("test").getCollection("events");   		
    		
    		long i = 0;
    		while (true) {
    			Document doc = new Document();
    			doc.put("i", i++);
    			doc.put("even", i % 2);
    			eventCollection.insertOne(doc);
    			//System.out.println("inserted: " + doc);
    			Thread.sleep(2000L + (long)(1000*Math.random()));
    		}
		}
    }
}
