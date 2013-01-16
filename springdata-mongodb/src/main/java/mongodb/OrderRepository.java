package mongodb;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> {

	List<Order> findByItemsQuantity(int quantity);
	
	List<Order> findByItemsPriceGreaterThan(double price);
	
}
