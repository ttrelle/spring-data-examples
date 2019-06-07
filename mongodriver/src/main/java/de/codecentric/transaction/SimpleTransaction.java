package de.codecentric.transaction;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.ClientSession;
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
	 * @param argv
	 *            command line arguments
	 * @throws MongoException
	 * @throws UnknownHostException
	 * @throws UnsupportedEncodingException
	 */
	public static void main(String[] argv) throws UnknownHostException, MongoException, UnsupportedEncodingException {
		try (MongoClient client = new MongoClient(Connection.URI)) {
			// use database "test"
			MongoDatabase db = client.getDatabase("test");
			// collection must be created beforehand *outside* the transaction!
			MongoCollection<Document> collection = db.getCollection("foo");

			try (ClientSession clientSession = client.startSession()) {
				clientSession.startTransaction();
				collection.insertOne(clientSession, new Document("i", 1));
				collection.insertOne(clientSession, new Document("i", 2));
				clientSession.commitTransaction();
				println("Transaction committed sucessfully.");
			}
		}
	}

	private static final void println(Object o) {
		System.out.println(o);
	}

}
