package de.codecentric.notifications;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;

public class DocumentProducer  {
    
	public static void main(String[] args) throws Exception {

		MongoCollection<Document> eventCollection = 
				new MongoClient(
						new MongoClientURI("mongodb://localhost:27001,localhost:27002,localhost:27003/test?replicatSet=demo-dev")
				).getDatabase("test").getCollection("events");    		
    		
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
