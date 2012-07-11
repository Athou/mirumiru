package fr.mirumiru.utils;

import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import fr.mirumiru.model.News;
import fr.mirumiru.model.User;
import fr.mirumiru.services.NewsDAO;
import fr.mirumiru.services.UserDAO;

@Startup
@Singleton
public class StartupBean {

	@Inject
	UserDAO userDAO;
	@Inject
	NewsDAO newsDAO;

	@PostConstruct
	public void init() {
		populateDatabase();
	}

	private void populateDatabase() {
		User user = new User();
		user.setName("admin");
		userDAO.save(user);

		String lorem = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus suscipit nisl ac metus porta placerat. Donec semper consectetur neque eget hendrerit.";

		newsDAO.save(new News("News 1", lorem, Calendar.getInstance(), user));
		newsDAO.save(new News("News 2", lorem, Calendar.getInstance(), user));
		newsDAO.save(new News("News 3", lorem, Calendar.getInstance(), user));
		newsDAO.save(new News("News 4", lorem, Calendar.getInstance(), user));
		newsDAO.save(new News("News 5", lorem, Calendar.getInstance(), user));

	}
}
