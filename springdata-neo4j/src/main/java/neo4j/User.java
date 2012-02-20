package neo4j;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

/** Simple user class. */
@NodeEntity public class User {

	@GraphId Long id;
	
	@Indexed private String login;
	
	private String fullName;
	
	private Date lastLogin;

    @RelatedTo(type = "knows", direction = Direction.OUTGOING)
    Set<User> friends;	
	
	public User() {}
	
	public User(String login, String fullName) {
		this.login = login;
		this.fullName = fullName;
		this.lastLogin = new Date();
		this.friends = new HashSet<User>();
	}	
	
	public void knows(User user) {
		friends.add(user);
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Set<User> getFriends() {
		return friends;
	}

	public void setFriends(Set<User> friends) {
		this.friends = friends;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	
}
