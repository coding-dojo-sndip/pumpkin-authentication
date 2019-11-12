package jack.skellington.pumpkin.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import jack.skellington.pumpkin.controller.ErrorService.WebException;
import jack.skellington.pumpkin.dao.UserDao;
import jack.skellington.pumpkin.model.User;

@Controller
public class UserService {

	@Autowired
	UserDao userDao;

	@GetMapping("/users")
	@ResponseBody
	public List<User> getUsers() {
		return userDao.getUsers();
	}

	@GetMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public User getUser(@PathVariable("id") String id) {
		return userDao.getUser(id);
	}

	@PatchMapping(value = "/user/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void patchUser(@RequestBody User user, HttpServletResponse response, @PathVariable("id") String id)
			throws WebException {
		User userPotentiel = userDao.getUser(id);
		if (userPotentiel == null) {
			throw new WebException(404, "User " + id + " not found");
		} else {
			userPotentiel.setName(user.getName());
			response.setStatus(204);
		}
	}

}
