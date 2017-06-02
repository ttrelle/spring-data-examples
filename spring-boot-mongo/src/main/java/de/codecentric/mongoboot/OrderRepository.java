package de.codecentric.mongoboot;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource()
public interface OrderRepository extends MongoRepository<Order, String> {

}
