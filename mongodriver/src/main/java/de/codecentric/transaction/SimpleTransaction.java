package de.codecentric.transaction;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.Date;

import org.bson.BSON;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

/**
 * Simple transaction example.
 */
public class SimpleTransaction {

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
//					new MongoClientURI("mongodb://localhost:27001,localhost:27002,localhost:27003/replicaSet=demo-dev")
//					);
			
			// use database "test"
			MongoDatabase db = mongo.getDatabase("test");

			// TODO implement example for multi-document transaction
			
			} finally {
				if (mongo != null) {
					mongo.close();
				}
			}
	}
	
	private static final void println(Object o) {
		System.out.println(o);
	}
	
}
