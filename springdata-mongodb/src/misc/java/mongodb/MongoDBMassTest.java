package mongodb;

import java.util.ArrayList;
import java.util.List;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.Box;
import org.springframework.data.mongodb.core.geo.Circle;
import org.springframework.data.mongodb.core.geo.Point;
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

	private static final int N_POINTS = 100000;

	private static final int N_QUERIES = 10000;
	
	/** Radius for circle search. */
	private static final double RADIUS = 0.1;
	
	/** Half the width of a box with the same area as a circle with r = {@link #RADIUS}. */
	private static final double HALF_BOX_WIDTH = RADIUS * Math.sqrt(Math.PI) / 2;
	
	@Autowired
	LocationRepository repo;

	@Autowired
	MongoTemplate template;

	@Before 
	public void setUp() {
		final List<Location> locations = new ArrayList<Location>();
		
		 // ensure geospatial index
		 template.indexOps(Location.class).ensureIndex( new GeospatialIndex("position") );
		 
		 // prepare data
		 for ( int i = 0;  i < N_POINTS; i++ ) {
			 locations.add( new Location(String.valueOf(i), rnd(), rnd()) );
		 }
		 out("Preparing data (" + N_POINTS + " locations) ...");
		 repo.save(locations);
		 out("Done.");
	}
	
	@Test
	public void shouldPerformRandomQueries() {
		long time_circle = 0;
		long time_box = 0;
		long singleRun = 0;
		Circle c;
		Box b;
		long n_circle = 0;
		long n_box = 0;
		List<Location> result;
		
		out( "Performing " + N_QUERIES + " queries ..." );
		for (int i = 0; i < N_QUERIES; i++) {
			
			double x = rnd(), y = rnd();
			
			// circle search
			c = new Circle( x, y, RADIUS);
			// box search
			b = new Box( new Point(x - HALF_BOX_WIDTH, y - HALF_BOX_WIDTH), new Point(x + HALF_BOX_WIDTH, y + HALF_BOX_WIDTH) );
			
			// perform circle query
			singleRun = System.currentTimeMillis();
			result = repo.findByPositionWithin(c);
			singleRun = System.currentTimeMillis() - singleRun;
			n_circle += result.size();
			time_circle += singleRun;
			
			// peform box search
			singleRun = System.currentTimeMillis();
			result = repo.findByPositionWithin(b);
			singleRun = System.currentTimeMillis() - singleRun;
			n_box += result.size();
			time_box += singleRun;
			
		}
		out( "Done.\n" );
		
		out( "Search by circle:" );
		out( "Overall time for " + N_QUERIES + " queries [ms]: " + time_circle );
		out( "Average time per query [ms]: " + (double)time_circle / N_QUERIES );
		out( "Average hits per query: " + n_circle / N_QUERIES );
		out( "" );
		out( "Search by box:" );
		out( "Overall time for " + N_QUERIES + " queries [ms]: " + time_box );
		out( "Average time per query [ms]: " + (double)time_box / N_QUERIES );
		out( "Average hits per query: " + n_box / N_QUERIES );
	}
	
	@After
	public void tearDown() {
		repo.deleteAll();
	}

	private static double rnd() {
		return 2 * Math.random() -1;
	}
	
	private static void out(Object o) {
		System.out.println(o);
	}

}
