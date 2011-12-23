package jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import jpa.User;
import jpa.UserRepository;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class JpaRepoTest {
	
	 @Autowired UserRepository repo;

	 @PersistenceContext EntityManager em;

	 @Autowired ClassicUserRepository classicRepo;
	 
	 @Before 
	 public void setUp() {
		 for ( int i = 0; i < 6; i++ ) {
			 repo.save( new User( String.format("user%02d", i), "User " + i ) );
		 }
	 }
	 
//	 @Transactional
//	 @Test public void shouldInsertEntity() {
//		 User u = new User("foo", "bar");
//		 em.persist(u);
//		 em.flush();
//	 }
	 
	 @Test public void shouldUseClassicRepository() {
		 List<User> users;

		 // when
		 users = classicRepo.findByFullName("User 1");
		 
		 // then
		 assertUserByFullName(users, "User 1");
	 }
	 
	 @Test public void shouldPageUsers() {
		 List<User> users;
		 
		 // when
		 Page<User> page = repo.findAll( new PageRequest(2, 2 ) );
		 users = page.getContent();
	 
		 // then
		 assertUserCount(users, 2);
	 }

	 @Test public void shouldFindByFullnameQuery() {
		 List<User> users;
		 
		 // when
		 users = repo.findByFullName("User 5");
		 
		 // then
		 assertUserByFullName(users, "User 5");
	 }

	 @Test public void shouldFindByFullnameQueryWithSort() {
		 List<User> users;
		 
		 // when
		 users = repo.findByFullName("User 5", new Sort( new Sort.Order(Sort.Direction.DESC,"fullName")));
		 
		 // then
		 assertUserByFullName(users, "User 5");
	 }
	 
	 @Test public void shouldUseExistingNamedQuery() {
		 List<User> users;
		 
		 // when
		 users = repo.findByUser5();
		 
		 // then
		 assertUserByFullName(users, "User 5");
	 }
	 
	 @Test public void shouldUseXmlNamedQuery() {
		 List<User> users;
		 
		 // when
		 users = repo.findByOrm();
		 
		 // then
		 assertUserByFullName(users, "User 2");
	 }	 

	 @Test public void shouldUseSpringDataQuery() {
		 List<User> users;
		 
		 // when
		 users = repo.findByGivenQuery();
		 
		 // then
		 assertUserByFullName(users, "User 3");
	 }	 
	 
	 @Test public void shouldIgnoreNullQueryParameters() {
		 List<User> usersById, usersByFullName;

		 // when
		 usersById = repo.findByIdAndFullName("user01", null);
		 usersByFullName = repo.findByIdAndFullName(null, "User 01");
		 
		 // then
		 assertUserCount(usersById, 0);
		 assertUserCount(usersByFullName, 0);
	 }
	 
	 @Test public void shouldSortByTwoCriteria() {
		 List<User> users;
		 
		 // when
		 users = repo.findAll( new Sort(
				 new Sort.Order(Sort.Direction.ASC, "id"),
				 new Sort.Order(Sort.Direction.DESC, "fullName")
				 )
		 );
		 
		 // then
		 assertUserCount(users, 6);
	 }
	 
	 private static void assertUserByFullName(List<User> users, String fullName)  {
		 assertUserCount(users, 1);
		 Assert.assertEquals( "Mismatch full name" , fullName, users.get(0).getFullName());
	 }

	 private static void assertUserCount(List<User> users, int expected) {
		 Assert.assertNotNull( users );
		 Assert.assertEquals( "Mismatch user count" , expected, users.size());
		 
	 }
	 
}
