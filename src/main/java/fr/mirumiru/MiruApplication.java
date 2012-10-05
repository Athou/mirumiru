package fr.mirumiru;

import javax.enterprise.inject.spi.BeanManager;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.wicket.Application;
import org.apache.wicket.Page;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.cdi.CdiConfiguration;
import org.apache.wicket.cdi.ConversationPropagation;
import org.apache.wicket.markup.html.WebPage;

import fr.mirumiru.auth.LoginPage;
import fr.mirumiru.auth.LogoutPage;
import fr.mirumiru.auth.MiruSession;
import fr.mirumiru.pages.AboutPage;
import fr.mirumiru.pages.ContactPage;
import fr.mirumiru.pages.CrochetInfoPage;
import fr.mirumiru.pages.Error404Page;
import fr.mirumiru.pages.FAQPage;
import fr.mirumiru.pages.FacebookNewsPage;
import fr.mirumiru.pages.GalleryListPage;
import fr.mirumiru.pages.HomePage;
import fr.mirumiru.pages.PortfolioPage;
import fr.mirumiru.pages.RssPage;
import fr.mirumiru.pages.SitemapPage;
import fr.mirumiru.pages.UpdatePage;
import fr.mirumiru.utils.LocaleFirstMapper;

public class MiruApplication extends AuthenticatedWebApplication {

	@Override
	protected void init() {
		super.init();
		setupCDI();
		mountPages();
		setRootRequestMapper(new LocaleFirstMapper(
				getRootRequestMapperAsCompound()));

	}

	protected void setupCDI() {
		try {
			BeanManager beanManager = (BeanManager) new InitialContext()
					.lookup("java:comp/BeanManager");
			new CdiConfiguration(beanManager).setPropagation(
					ConversationPropagation.NONE).configure(this);
		} catch (NamingException e) {
			throw new IllegalStateException("Unable to obtain CDI BeanManager",
					e);
		}
	}

	private void mountPages() {

		mountPage("login", LoginPage.class);
		mountPage("logout", LogoutPage.class);
		mountPage("404", Error404Page.class);
		mountPage("sitemap.xml", SitemapPage.class);
		mountPage("rss", RssPage.class);
		mountPage("update", UpdatePage.class);

		mountPage("about", AboutPage.class);
		mountPage("news/#{page}", FacebookNewsPage.class);
		mountPage("portfolio", PortfolioPage.class);
		mountPage("crochet", CrochetInfoPage.class);
		mountPage("albums", GalleryListPage.class);
		mountPage("faq", FAQPage.class);
		mountPage("contact", ContactPage.class);

	}

	public static MiruApplication get() {
		return (MiruApplication) Application.get();
	}

	@Override
	public Class<? extends Page> getHomePage() {
		return HomePage.class;
	}

	@Override
	protected Class<? extends WebPage> getSignInPageClass() {
		return LoginPage.class;
	}

	@Override
	protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
		return MiruSession.class;
	}
}
