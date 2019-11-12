package jack.skellington.pumpkin.dao;

import java.time.MonthDay;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jack.skellington.pumpkin.model.Celebration;

@Component
public class CelebrationDao {

	private List<Celebration> celebrations = new ArrayList<>();

	@Autowired
	public CelebrationDao(UserDao userDao) {
		celebrations.add(
				new Celebration("Halloween", userDao.getUser("pumpkinking"), MonthDay.of(10, 31), "Se faire peur"));
		celebrations
				.add(new Celebration("Christmas", userDao.getUser("ohohoh"), MonthDay.of(12, 25), "Offrir des jouets"));
		celebrations
				.add(new Celebration("Easter", userDao.getUser("rabbit"), MonthDay.of(4, 12), "Chercher les oeufs"));
	}

	public List<Celebration> getCelebrations() {
		return celebrations;
	}

	public Celebration getCelebration(String name) {
		return celebrations.stream().filter(c -> c.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
	}

}
