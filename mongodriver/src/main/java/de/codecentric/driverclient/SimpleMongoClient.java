package de.codecentric.driverclient;

import java.util.Date;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

/**
 * Simple MongoDB client based on the MongoDB Java driver API.
 */
public class SimpleMongoClient {

	/**
	 * CLI call.
	 * @param argv command line arguments
	 * @throws MongoException
	 */
	public static void main(String[] argv) throws MongoException {
		try (MongoClient mongo = MongoClients.create()) {

			// Sharding: mongos server
			// MongoClients.create("mongodb://mongos-1:4711")

			// Replica set
			// MongoClients.create("mongodb://localhost:27001,localhost:27002,localhost:27003/?replicaSet=demo-dev")

			// use database "test"
			MongoDatabase db = mongo.getDatabase("test");

			// get collection names
			MongoIterable<String> colls = db.listCollectionNames();
			for (String s : colls) {
			    println(s);
			}

			// use collection "foo"
			MongoCollection<Document> collection = db.getCollection("foo");

			insert(collection);
			find(collection);

			// remove(collection);
		}
	}

	private static void remove(MongoCollection<Document> collection) {
		// alle Dokumente mit {i: 42}
		Bson criteria = new Document("i", 42);
		collection.deleteOne(criteria);

		// alle Dokumente
		collection.deleteMany(new Document());

		// schneller:
		collection.drop();
	}

	private static void insert(MongoCollection<Document> collection) {
		// Document speichern
		Document doc = new Document();
		doc.put("date", new Date());
		doc.put("i", 42);

		collection.insertOne(doc);
	}

	private static void find(MongoCollection<Document> collection) {
		FindIterable<Document> cursor;

		// alle Dokumente
		cursor = collection.find();

		for (Document document : cursor) {
			println(document);
		}
	}

	private static void find(MongoCollection<Document> collection, Bson query) {
		// Dokumente mit {i: 42}
		FindIterable<Document> cursor = collection.find(query);

		for (Document document : cursor) {
			println(document);
		}
	}

	private static final void println(Object o) {
		System.out.println(o);
	}

}
