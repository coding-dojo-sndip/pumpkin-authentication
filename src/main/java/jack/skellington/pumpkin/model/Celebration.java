package jack.skellington.pumpkin.model;

import java.time.MonthDay;

public class Celebration {

	private String name;
	private User master;
	private MonthDay date;
	private String programme;

	public Celebration(String name, User master, MonthDay date, String programme) {
		this.name = name;
		this.master = master;
		this.date = date;
		this.programme = programme;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getMaster() {
		return master;
	}

	public void setMaster(User master) {
		this.master = master;
	}

	public MonthDay getDate() {
		return date;
	}

	public void setDate(MonthDay date) {
		this.date = date;
	}

	public String getProgramme() {
		return programme;
	}

	public void setProgramme(String programme) {
		this.programme = programme;
	}

}
