package mongodb;

import java.util.List;

import org.springframework.data.mongodb.core.geo.Box;
import org.springframework.data.mongodb.core.geo.Circle;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LocationRepository extends MongoRepository<Location, String> {

	List<Location> findByPositionWithin(Circle c);

	//List<Location> positionWithinSphere(Circle c);

	List<Location> findByPositionWithin(Box b);
	
}
