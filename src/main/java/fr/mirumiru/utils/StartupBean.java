package fr.mirumiru.utils;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import fr.mirumiru.model.User;
import fr.mirumiru.services.UserDAO;

@Startup
@Singleton
public class StartupBean {

	@Inject
	UserDAO userDAO;

	@PostConstruct
	public void init() {
		populateDatabase();
	}

	private void populateDatabase() {
		User user = new User();
		user.setName("admin");
		userDAO.save(user);

	}
}
