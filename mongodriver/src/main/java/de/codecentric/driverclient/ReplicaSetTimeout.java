package de.codecentric.driverclient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.bson.Document;

import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.BulkWriteOptions;
import com.mongodb.client.model.InsertOneModel;
import com.mongodb.client.model.WriteModel;

public class ReplicaSetTimeout {

	private static final WriteConcern WRITE_CONCERN = WriteConcern.ACKNOWLEDGED
			.withW("majority")
			.withWTimeout(200, TimeUnit.MILLISECONDS)
			.withJournal(true)
			;

	private static final int BULK_SIZE = 10000;

	public static void main(String[] argv) {
		new ReplicaSetTimeout().execute();
	}

	public void execute() {
		try (MongoClient mongo = MongoClients.create(
				"mongodb://localhost:27001,localhost:27002,localhost:27003/?replicaSet=dev0")) {

			MongoDatabase db = mongo.getDatabase("test");
			MongoCollection<Document> collection = db.getCollection("foo").withWriteConcern(WRITE_CONCERN);
			System.out.println("WriteConcern: " + collection.getWriteConcern());

			// remove
			collection.deleteMany(new Document());

			// bulk insert
			BulkWriteOptions opts = new BulkWriteOptions();
			opts.ordered(true);

			long time = System.currentTimeMillis();
			collection.bulkWrite(createBulk(), opts);
			time = System.currentTimeMillis() - time;
			System.out.println("*** runtime [ms]: " + time);

		} catch (MongoException e) {
			e.printStackTrace();
		}
	}

	private List<WriteModel<Document>> createBulk() {
		final List<WriteModel<Document>> ops = new ArrayList<WriteModel<Document>>();
		Document payload;

		for (int i = 0; i < BULK_SIZE; i++) {
			payload = new Document();
			payload.put("i", i);
			payload.put("t", new Date());

			ops.add(new InsertOneModel<Document>(payload));
		}

		return ops;
	}
}
