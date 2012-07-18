package fr.mirumiru.auth;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.Session;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;

import com.google.common.collect.Iterables;

import fr.mirumiru.MiruApplication;
import fr.mirumiru.model.User;
import fr.mirumiru.services.UserDAO;

@SuppressWarnings("serial")
public class MiruSession extends AuthenticatedWebSession {

	private User user;

	public MiruSession(Request request) {
		super(request);
	}

	@Override
	public Roles getRoles() {
		Roles roles = new Roles();
		if (isSignedIn()) {
			roles.add(Roles.ADMIN);
		}
		return roles;
	}

	@Override
	public boolean authenticate(String username, String password) {
		String adminPassword = "admin";

		MiruApplication app = MiruApplication.get();
		List<User> users = app.getBean(UserDAO.class).findByField("name",
				username);

		if (!users.isEmpty() && StringUtils.equals(password, adminPassword)) {
			user = Iterables.getOnlyElement(users);
			return true;
		} else {
			return false;
		}
	}

	public User getUser() {
		return user;
	}

	public static MiruSession get() {
		return (MiruSession) Session.get();
	}

}