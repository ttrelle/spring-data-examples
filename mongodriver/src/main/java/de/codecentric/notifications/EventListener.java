package de.codecentric.notifications;

import static java.util.Arrays.asList;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.ChangeStreamIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Aggregates;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.model.changestream.ChangeStreamDocument;

public class EventListener  {
    public static void main(String[] args) throws Exception {
    	
    		MongoCollection<Document> eventCollection = 
    				new MongoClient(
    						new MongoClientURI("mongodb://localhost:27001,localhost:27002,localhost:27003/test?replicatSet=demo-dev")
    				).getDatabase("test").getCollection("events");
	
    		ChangeStreamIterable<Document> changes = eventCollection.watch(asList( 
    				Aggregates.match( and( asList( 
    					in("operationType", asList("insert")),
    					eq("fullDocument.even", 1L)))
    	    )));
    		
    		changes.forEach(new Block<ChangeStreamDocument<Document>>() {
				@Override
				public void apply(ChangeStreamDocument<Document> t) {
					System.out.println("received: " + t.getFullDocument());
				}
    		});
    }
}
