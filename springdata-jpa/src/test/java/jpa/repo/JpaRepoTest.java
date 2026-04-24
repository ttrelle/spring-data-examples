package jpa.repo;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import jpa.domain.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Tests for Spring Data JPA.
 *
 * @author Tobias Trelle
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration
public class JpaRepoTest {

	@Autowired UserRepository repo;

	@Autowired ClassicUserRepository classicRepo;

	@BeforeEach
	public void setUp() {
		for (int i = 0; i < 6; i++) {
			repo.save(new User(String.format("user%02d", i), "User " + i));
		}
	}

	@Test
	public void shouldUseClassicRepository() {
		// when
		List<User> users = classicRepo.findByFullName("User 1");

		// then
		assertUserByFullName(users, "User 1");
	}

	@Test
	public void shouldPageUsers() {
		// when
		Page<User> page = repo.findAll(PageRequest.of(2, 2));
		List<User> users = page.getContent();

		// then
		assertUserCount(users, 2);
	}

	@Test
	public void shouldFindByFullnameQuery() {
		// when
		List<User> users = repo.findByFullName("User 5");

		// then
		assertUserByFullName(users, "User 5");
	}

	@Test
	public void shouldFindByFullnameQueryWithSort() {
		// when
		List<User> users = repo.findByFullName("User 5", Sort.by(new Sort.Order(Sort.Direction.DESC, "fullName")));

		// then
		assertUserByFullName(users, "User 5");
	}

	@Test
	public void shouldUseExistingNamedQuery() {
		// when
		List<User> users = repo.findByUser5();

		// then
		assertUserByFullName(users, "User 5");
	}

	@Test
	public void shouldUseXmlNamedQuery() {
		// when
		List<User> users = repo.findByOrm();

		// then
		assertUserByFullName(users, "User 2");
	}

	@Test
	public void shouldUseSpringDataQuery() {
		// when
		List<User> users = repo.findByGivenQuery();

		// then
		assertUserByFullName(users, "User 3");
	}

	@Test
	public void shouldIgnoreNullQueryParameters() {
		// when
		List<User> usersById = repo.findByIdAndFullName("user01", null);
		List<User> usersByFullName = repo.findByIdAndFullName(null, "User 01");

		// then
		assertUserCount(usersById, 0);
		assertUserCount(usersByFullName, 0);
	}

	@Test
	public void shouldSortByTwoCriteria() {
		// when
		List<User> users = repo.findAll(Sort.by(
				Sort.Order.asc("id"),
				Sort.Order.desc("fullName")
		));

		// then
		assertUserCount(users, 6);
	}

	private static void assertUserByFullName(List<User> users, String fullName) {
		assertUserCount(users, 1);
		assertThat("Mismatch full name", users.get(0).getFullName(), is(fullName));
	}

	private static void assertUserCount(List<User> users, int expected) {
		assertThat(users, notNullValue());
		assertThat("Mismatch user count", users.size(), is(expected));
	}

}
