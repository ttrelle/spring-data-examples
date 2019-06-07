package de.codecentric.notifications;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;
import static java.util.Arrays.asList;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.ChangeStreamIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Aggregates;

import de.codecentric.Connection;

public class EventListener  {
	
    public static void main(String[] args) throws Exception {
		try (MongoClient client = new MongoClient(Connection.URI)) {
    		MongoCollection<Document> eventCollection = 
    				client.getDatabase("test").getCollection("events");
	
    		ChangeStreamIterable<Document> changes = eventCollection.watch(asList( 
    				Aggregates.match( and( asList( 
    					in("operationType", asList("insert")),
    					eq("fullDocument.even", 1L)))
    	    )));
    		
    		changes.iterator().forEachRemaining(
    				change -> System.out.println("received: " + change.getFullDocument())
    		);
		}
    }
}
