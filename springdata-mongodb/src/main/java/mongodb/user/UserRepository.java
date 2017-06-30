package mongodb.user;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepository extends MongoRepository<User, String> {

	@Query("{ fullName: ?0 }")
	List<User> findByTheUsersFullName(String fullName);

	List<User> findByFullNameLike(String fullName, Sort sort);
}
