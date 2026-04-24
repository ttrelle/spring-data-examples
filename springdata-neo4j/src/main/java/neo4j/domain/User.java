package neo4j.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node
public class User {

	@Id @GeneratedValue
	private Long id;

	private String login;

	private String fullName;

	private Date lastLogin;

	@Relationship(type = "KNOWS")
	private Set<User> friends;

	public User() {}

	public User(String login, String fullName) {
		this.login = login;
		this.fullName = fullName;
		this.lastLogin = new Date();
		this.friends = new HashSet<>();
	}

	public void knows(User user) {
		friends.add(user);
	}

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public String getLogin() { return login; }
	public void setLogin(String login) { this.login = login; }
	public String getFullName() { return fullName; }
	public void setFullName(String fullName) { this.fullName = fullName; }
	public Date getLastLogin() { return lastLogin; }
	public void setLastLogin(Date lastLogin) { this.lastLogin = lastLogin; }
	public Set<User> getFriends() { return friends; }
	public void setFriends(Set<User> friends) { this.friends = friends; }
}
