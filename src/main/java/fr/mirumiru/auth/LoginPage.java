package fr.mirumiru.auth;

import fr.mirumiru.pages.TemplatePage;

@SuppressWarnings("serial")
public class LoginPage extends TemplatePage {

	public LoginPage() {
		html.add(new LoginPanel("login"));
	}

	@Override
	protected String getTitle() {
		return "Login";
	}

}
