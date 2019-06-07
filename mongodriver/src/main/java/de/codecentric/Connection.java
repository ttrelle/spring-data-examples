package de.codecentric;

import com.mongodb.MongoClientURI;

abstract public class Connection {

	public static final MongoClientURI URI = new MongoClientURI(
			"mongodb://mongo1:27001,mongo2:27002,mongo3:27003/test?replicatSet=dev0"
			// "mongodb://localhost:27001,localhost:27002,localhost:27003/test?replicatSet=dev0"
			);
	
}
