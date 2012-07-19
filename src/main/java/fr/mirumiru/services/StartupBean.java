package fr.mirumiru.services;

import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import fr.mirumiru.model.User;

@Startup
@Singleton
public class StartupBean {

	@Inject
	UserDAO userDAO;

	private Calendar startupTime;

	@PostConstruct
	public void init() {
		startupTime = Calendar.getInstance();
		populateDatabase();
	}

	private void populateDatabase() {
		User user = new User();
		user.setName("admin");
		userDAO.save(user);

	}

	public Calendar getStartupTime() {
		return startupTime;
	};
}
