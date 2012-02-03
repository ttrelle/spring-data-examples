package mongodb;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.Circle;
import org.springframework.data.mongodb.core.index.GeospatialIndex;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Tests for Spring Data MongoDB - Geospatial queries.
 * 
 * @author <a href="http://blog.codecentric.de/en/author/tobias-trelle">Tobias
 *         Trelle</a>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class MongoDBMassTest {

	private static final int N_POINTS = 1000000;

	private static final int N_QUERIES = 100;
	
	@Autowired
	LocationRepository repo;

	@Autowired
	MongoTemplate template;

	@Before public void setUp() {
		final List<Location> locations = new ArrayList<Location>();
		
		 // ensure geospatial index
		 template.indexOps(Location.class).ensureIndex( new GeospatialIndex("position") );
		 
		 // prepare data
		 for ( int i = 0;  i < N_POINTS; i++ ) {
			 locations.add( new Location(String.valueOf(i), 2 * Math.random() - 1, 2 * Math.random() - 1) );
		 }
		 out("Preparing data (" + N_POINTS + " locations) ...");
		 repo.save(locations);
		 out("Done");
	}
	
	@Test
	public void shouldPerformRandomQueries() {
		long time = 0;
		long singleRun = 0;
		Circle c;
		
		out( "Performing " + N_QUERIES + " queries ..." );
		for (int i = 0; i < N_QUERIES; i++) {
			// random search
			c = new Circle(2 * Math.random() - 1, 2 * Math.random() - 1, 0.1);
			
			// perform query
			singleRun = System.currentTimeMillis();
			repo.findByPositionWithin(c);
			singleRun = System.currentTimeMillis() - singleRun;
			
			time += singleRun;
		}
		out( "Done.\n" );
		
		out( "Overall time for " + N_QUERIES + " queries [ms]: " + time );
		out( "Average time per query [ms]: " + (double)time / N_QUERIES );
	}
	
	@After
	public void tearDown() {
		//repo.deleteAll();
	}

	private static void out(Object o) {
		System.out.println(o);
	}

}
