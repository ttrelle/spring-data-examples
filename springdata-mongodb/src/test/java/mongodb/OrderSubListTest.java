package mongodb;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = OrderRepositoryConfig.class)
public class OrderSubListTest {

	@Autowired OrderRepository repo;	
	
	@Autowired MongoOperations template;
	
	@Before public void setUp() {
		repo.deleteAll();
	}
	
	@Test public void projection_by_aggregation() {
		// given
		Order order = new Order("Tobias Trelle, gold customer");
		List<Item> items = new ArrayList<Item>();
		items.add( new Item(1, 47.11, "Item #1") );
		items.add( new Item(2, 42.0, "Item #2") );
		order.setItems(items);
		repo.save(order);

		// when
		Aggregation agg =  newAggregation(
				project("items").andExclude("_id")
				);
		AggregationResults<Order> result =  template.aggregate(agg, Order.class, Order.class);
		
		
		// then
		assertNotNull(result);
		List<Order> orders = result.getMappedResults();
		assertNotNull(orders);
		assertEquals(1, orders.size());
		items = orders.get(0).getItems();
		assertNotNull(items);
		assertEquals( 2, items.size() );
	}

	@Test public void projection_by_query() {
		// given
		Order order = new Order("Tobias Trelle, gold customer");
		List<Item> items = new ArrayList<Item>();
		items.add( new Item(1, 47.11, "Item #1") );
		items.add( new Item(2, 42.0, "Item #2") );
		order.setItems(items);
		repo.save(order);

		// when
		Aggregation agg =  newAggregation(
				project("items").andExclude("_id")
				);
		AggregationResults<Order> result =  template.aggregate(agg, Order.class, Order.class);
		
		
		// then
		assertNotNull(result);
		List<Order> orders = result.getMappedResults();
		assertNotNull(orders);
		assertEquals(1, orders.size());
		items = orders.get(0).getItems();
		assertNotNull(items);
		assertEquals( 2, items.size() );
	}

	
}
