package neo4j.repo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.List;

import neo4j.domain.User;
import neo4j.repo.UserRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.ogm.session.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Neo4jTestConfig.class)
public class Neo4jRepoTest {

	private static final int USER_COUNT = 4;

	@Autowired
	Neo4jOperations template;
	
	@Autowired
	UserRepository repo;

	private long rootId;

	@Before
	public void setUp() {
		// create index User(login)
		Result result = template.query("CREATE INDEX ON :User(login)", new HashMap<String, Object>() );
		
		User root = new User("root", "Superuser");
		User[] user = new User[USER_COUNT];

		for (int i = 0; i < user.length; i++) {
			user[i] = new User(String.format("user%02d", i), "User" + i);
		}

		// build graph
		for (int i = 0; i < user.length; i++) {
			root.knows(user[i]);
			for (int j = i; j < user.length; j++) {
				user[i].knows(user[j]);
			}
		}

		// save nodes
		for (int i = 0; i < user.length; i++) {
			repo.save(user[i]);
		}
		repo.save(root);
		rootId = root.getId();
		out("Root id: " + rootId);

	}

	@Test
	public void shouldFindAll() {
		// when
		long n = repo.count();

		// then
		assertEquals("User count mismatch", USER_COUNT + 1, n);
	}

	@Test
	public void shouldFindRootUserById() {
		// when
		User root = repo.findOne(rootId);

		// then
		assertNotNull("Root user not found", root);
	}

	@Test
	public void shouldFindRootUserByLogin() {
		// when
		User root = repo.findByLogin("root");

		// then
		assertNotNull("Root user not found", root);
	}

	// TODO fixme
	// Caused by: org.neo4j.ogm.session.result.ResultProcessingException: "errors":[{"code":"Neo.ClientError.Schema.NoSuchIndex
	// ","message":"Index `User` does not exist"}]}
	@Test
	@Ignore
	public void shouldFindFriendsOfRoot() {
		// when
		List<User> users = repo.findFriendsOfRoot();

		// then
		assertNotNull("result is <null>", users);
		assertEquals("mismatch @ friend count", USER_COUNT, users.size());
	}

	@After
	public void tearDown() {
		repo.deleteAll();
	}

	private static void out(Object o) {
		System.out.println(o);
	}

}
