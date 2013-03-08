package mongodb;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface OrderRepository extends MongoRepository<Order, String> {

	List<Order> findByItemsQuantity(int quantity);
	
	@Query("{ \"items.quantity\": ?0 }")
	List<Order> findWithQuery(int quantity);
}
