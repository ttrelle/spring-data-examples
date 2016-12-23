package driverclient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.BulkWriteOptions;
import com.mongodb.client.model.InsertOneModel;
import com.mongodb.client.model.WriteModel;

public class ReplicaSetTimeout {

	private static final WriteConcern WRITE_CONCERN = new WriteConcern()
			.withW("majority")
			.withWTimeout(200, TimeUnit.MILLISECONDS)
			.withJournal(true)
			;

	private static final int BULK_SIZE = 10000;
	
	public static void main(String[] argv) {
		new ReplicaSetTimeout().execute();
	}

	public void execute() {
		MongoClient mongo = null;

		mongo = new MongoClient(Arrays.asList( //
				new ServerAddress("localhost", 27001), //
				new ServerAddress("localhost", 27002), //
				new ServerAddress("localhost", 27003)));

		try {
			
			MongoDatabase db = mongo.getDatabase("test");
			MongoCollection<Document> collection = db.getCollection("foo");
			collection = collection.withWriteConcern(WRITE_CONCERN);
			System.out.println("WriteConcern: " + collection.getWriteConcern());
						
			// remove
			collection.deleteMany(new Document());
			
			// bulk insert
			BulkWriteOptions opts = new BulkWriteOptions();
			opts.ordered(true);
			
			long time = System.currentTimeMillis();
			collection.bulkWrite( createBulk(), opts );
			time = System.currentTimeMillis() - time;
			System.out.println("*** runtime [ms]: " + time);
			
			
		} catch (MongoException e) {
			e.printStackTrace();
		} finally {
			if (mongo != null) {
				mongo.close();
			}
		}
		
	}

	private List<WriteModel<Document>> createBulk() {
		final List<WriteModel<Document>> ops = new ArrayList<WriteModel<Document>>();
		Document payload;
		
		for(int i=0; i<BULK_SIZE; i++) {
			payload = new Document();
			payload.put("i", i);
			payload.put("t", new Date());
			
			ops.add( new InsertOneModel<Document>(payload) );
		}
		
		return ops;
	}
}
