package de.codecentric.transaction;

import org.bson.Document;

import com.mongodb.MongoException;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import de.codecentric.Connection;

/**
 * Simple transaction example.
 */
public class SimpleTransaction {

	/**
	 * CLI call.
	 *
	 * @param argv command line arguments
	 * @throws MongoException
	 */
	public static void main(String[] argv) throws MongoException {
		try (MongoClient client = MongoClients.create(Connection.URI)) {
			// use database "test"
			MongoDatabase db = client.getDatabase("test");
			// collection must be created beforehand *outside* the transaction!
			MongoCollection<Document> collection = db.getCollection("foo");

			try (ClientSession clientSession = client.startSession()) {
				clientSession.startTransaction();
				collection.insertOne(clientSession, new Document("i", 1));
				collection.insertOne(clientSession, new Document("i", 2));
				clientSession.commitTransaction();
				println("Transaction committed successfully.");
			}
		}
	}

	private static final void println(Object o) {
		System.out.println(o);
	}

}
