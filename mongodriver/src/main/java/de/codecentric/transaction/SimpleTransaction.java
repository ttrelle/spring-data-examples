package de.codecentric.transaction;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * Simple transaction example.
 */
public class SimpleTransaction {

	/**
	 * CLI call.
	 * 
	 * @param argv
	 *            command line arguments
	 * @throws MongoException
	 * @throws UnknownHostException
	 * @throws UnsupportedEncodingException
	 */
	public static void main(String[] argv) throws UnknownHostException, MongoException, UnsupportedEncodingException {
		MongoClient client = null;

		try {
			// Replica set
			 client = new MongoClient(
			 new
			 MongoClientURI("mongodb://localhost:27001,localhost:27002,localhost:27003/replicaSet=demo-dev")
			 );

			// use database "test"
			MongoDatabase db = client.getDatabase("test");
			MongoCollection<Document> collection = db.getCollection("foo");

			try (ClientSession clientSession = client.startSession()) {
				clientSession.startTransaction();
				collection.insertOne(clientSession, new Document("i", 1));
				collection.insertOne(clientSession, new Document("i", 2));
				clientSession.commitTransaction();
			}

		} finally {
			if (client != null) {
				client.close();
			}
		}
	}

	private static final void println(Object o) {
		System.out.println(o);
	}

}
