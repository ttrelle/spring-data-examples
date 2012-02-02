package mongodb;

import java.util.List;

import junit.framework.Assert;

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
 * @author <a href="http://blog.codecentric.de/en/author/tobias-trelle">Tobias Trelle</a>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class MongoDBGeoSpatialTest {
	
	 @Autowired LocationRepository repo;
	  
	 @Autowired MongoTemplate template;
	 
	 @Before public void setUp() {
		 // ensure geospatial index
		 template.indexOps(Location.class).ensureIndex( new GeospatialIndex("position") );
		 // prepare data
		 repo.save( new Location("A", 0.001, -0.002) );
		 repo.save( new Location("B", 1, 1) );
		 repo.save( new Location("C", 0.5, 0.5) );
		 repo.save( new Location("D", -0.5, -0.5) );
	 }

	 @Test public void shouldFindAll() {
		 // when
		 List<Location> locations = repo.findAll();
		 
		 // then
		 assertLocationCount(locations, 4);
	 }

	 @Test public void shouldFindWithinCircle() {
		 // when
		 List<Location> locations = repo.findByPositionWithin( new Circle(0,0, 0.75) );
		 
		 // then
		 assertLocationCount(locations, 3);
	 }

	 @Test public void shouldFindWithinBox() {
		 // when
		 List<Location> locations = repo.findByPositionWithin( new Box( new Point(0.25, 0.25), new Point(1,1)) );
		 
		 // then
		 assertLocationCount(locations, 2);
	 }
	 
	 @After public void tearDown() {
		 repo.deleteAll();
	 }

	 private static void assertLocationCount(List<Location> locations, int expected) {
		 Assert.assertNotNull( locations );
		 out("-----------------------------");
		 for (Location l: locations) {
			 out(l);
		 }
		 Assert.assertEquals( "Mismatch location count" , expected, locations.size());
	 }
	 
	 private static void out(Object o) {
		 System.out.println(o);
	 }
	 
}
