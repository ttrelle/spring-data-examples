package mongodb;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class OrderRepositoryTest {

	@Autowired OrderRepository repo;	
	
	@Before public void setUp() {
		repo.deleteAll();
	}
	
	@Test public void shouldFindByItemsQuantity() {
		// given
		Order order = new Order("Tobias Trelle, gold customer");
		List<Item> items = new ArrayList<Item>();
		items.add( new Item(1, 47.11, "Item #1") );
		items.add( new Item(2, 42.0, "Item #2") );
		order.setItems(items);
		repo.save(order);
		
		// when
		List<Order> orders = repo.findByItemsQuantity(2);
		
		// then
		Assert.assertNotNull(orders);
		Assert.assertEquals(1, orders.size());
	}

	@Test public void shouldFindByAnnotatedQuery() {
		// given
		Order order = new Order("Tobias Trelle, gold customer");
		List<Item> items = new ArrayList<Item>();
		items.add( new Item(1, 47.11, "Item #1") );
		items.add( new Item(2, 42.0, "Item #2") );
		order.setItems(items);
		repo.save(order);
		
		// when
		List<Order> orders = repo.findWithQuery(2);
		
		// then
		Assert.assertNotNull(orders);
		Assert.assertEquals(1, orders.size());
	}
	
}
