package fr.mirumiru.auth;

import fr.mirumiru.pages.TemplatePage;

@SuppressWarnings("serial")
public class LogoutPage extends TemplatePage {

	public LogoutPage() {
		getSession().invalidate();
		setResponsePage(getApplication().getHomePage());
	}

	@Override
	protected String getTitle() {
		return "Logout";
	}

}
