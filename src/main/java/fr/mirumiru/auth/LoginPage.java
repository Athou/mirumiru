package fr.mirumiru.auth;

import fr.mirumiru.pages.TemplatePage;
import fr.mirumiru.utils.Mount;

@SuppressWarnings("serial")
@Mount(path = "login")
public class LoginPage extends TemplatePage {

	public LoginPage() {
		html.add(new LoginPanel("login"));
	}

	@Override
	protected String getTitle() {
		return "Login";
	}

}
