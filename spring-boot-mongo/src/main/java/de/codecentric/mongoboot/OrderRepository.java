package de.codecentric.mongoboot;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource()
public interface OrderRepository extends MongoRepository<Order, String> {

	List<Order> findByText(@Param("name") String name);
	
}
