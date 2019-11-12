package jack.skellington.pumpkin.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import jack.skellington.pumpkin.model.User;

@Component
public class UserDao {

	private List<User> users = new ArrayList<>();

	public UserDao() {
		users.add(new User("pumpkinking", "Jack Skellington"));
		users.add(new User("badguy", "Oogie Boogie"));
		users.add(new User("ohohoh", "Santa Claus"));
		users.add(new User("rabbit", "Easter Bunny"));
	}

	public List<User> getUsers() {
		return users;
	}

	public User getUser(String id) {
		return users.stream().filter(u -> u.getId().equalsIgnoreCase(id)).findFirst().orElse(null);
	}

	public void addUser(User user) {
		users.add(user);
	}

}
