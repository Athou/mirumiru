package fr.mirumiru.auth;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;

@SuppressWarnings("serial")
public class MiruSession extends AuthenticatedWebSession {

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
		if (StringUtils.equals(password, adminPassword)) {
			setAttribute("name", username);
			return true; 
		} else {
			return false;
		}
	}

}