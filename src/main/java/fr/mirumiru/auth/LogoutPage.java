package fr.mirumiru.auth;

import fr.mirumiru.pages.TemplatePage;
import fr.mirumiru.utils.Mount;

@SuppressWarnings("serial")
@Mount(path = "logout")
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
