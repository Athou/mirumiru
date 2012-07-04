package fr.mirumiru;

import org.apache.wicket.Page;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authroles.authentication.pages.SignInPage;
import org.apache.wicket.markup.html.WebPage;

import fr.mirumiru.auth.MiruSession;
import fr.mirumiru.pages.HomePage;

public class MiruApplication extends AuthenticatedWebApplication{

	@Override
	protected void init() {
		super.init();

		getMarkupSettings().setStripWicketTags(true);
	}

	@Override
	public Class<? extends Page> getHomePage() {
		return HomePage.class;
	}

	@Override
	protected Class<? extends WebPage> getSignInPageClass() {
		return SignInPage.class;
	}

	@Override
	protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
		return MiruSession.class;
	}
}
