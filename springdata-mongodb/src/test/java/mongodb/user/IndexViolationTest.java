package mongodb.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import mongodb.config.LocalhostMongoConfiguration;

/**
 * This test shows that violation on indexes are ignored.
 * <p/>
 * To run this test, a local mongod instance is required using the standard port.
 *
 * @author Tobias Trelle
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = LocalhostMongoConfiguration.class)
public class IndexViolationTest {

	private static final String COLLECTION_NAME = "user";

	@Autowired UserRepository repo;

	@Autowired MongoOperations template;

	/**
	 * Create a target collection.
	 */
	@BeforeEach
	public void setUp() {
		template.dropCollection(COLLECTION_NAME);
		template.createCollection(COLLECTION_NAME);
		template.indexOps(COLLECTION_NAME).ensureIndex(new Index().on("fullName", Direction.ASC).unique());
	}

	@Test
	public void does_not_detect_index_violation_on_id() {
		// given
		repo.save(new User("0", "User 0")); // 1st param = _id field, 2nd = unique secondary index

		// when
		repo.save(new User("0", "User 1"));

		// then
	}
}
