package jpa.repo;

import java.util.List;

import jpa.domain.User;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, String> {

	List<User> findByFullName(String fullName);

	List<User> findByFullName(String fullName, Sort sort);

	List<User> findByFullName(String fullName, Pageable paging);
	
	List<User> findByUser5();

	List<User> findByOrm();
	
	@Transactional(timeout = 2, propagation = Propagation.REQUIRED)
	@Query("SELECT u FROM User u WHERE u.fullName = 'User 3'")
	List<User> findByGivenQuery();
	
	List<User> findByIdAndFullName(@Param("id") String id, @Param("fullName") String fullname);
	
}
