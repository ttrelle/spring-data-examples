package mongodb;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test cases for the {@link OrderRepository}.
 * 
 * @author Tobias Trelle
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=LocalhostMongoConfiguration.class)
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
		assertThat(orders, notNullValue());
		assertThat(orders.size(), is(1));
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
		assertThat(orders, notNullValue());
		assertThat(orders.size(), is(1));
	}

	@Test public void use_field_projection() {
		// given
		Order order = new Order("Tobias Trelle, gold customer");
		List<Item> items = new ArrayList<Item>();
		items.add( new Item(1, 47.11, "Item #1") );
		items.add( new Item(2, 42.0, "Item #2") );
		order.setItems(items);
		repo.save(order);
		
		// when
		List<Order> orders = repo.findOnlyItems("Tobias Trelle, gold customer");
		
		// then
		assertThat(orders, notNullValue());
		assertThat(orders.size(), is(1));
		order = orders.get(0);
		assertThat(order.getId(), nullValue());
		assertThat(order.getCustomerInfo(), nullValue());
		assertThat(order.getDate(), nullValue());
		
		items = order.getItems();
		assertThat(items, notNullValue());
		assertThat(items.size(), is(2));
		Item item = items.get(0);
		assertThat( item.getDescription(), notNullValue());
		assertThat( item.getPrice(), notNullValue());
		assertThat( item.getQuantity(), notNullValue());
	}
	
}
