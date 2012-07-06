package fr.mirumiru;

import java.util.Set;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.wicket.Page;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authroles.authentication.pages.SignInPage;
import org.apache.wicket.markup.html.WebPage;

import fr.mirumiru.auth.LoginPage;
import fr.mirumiru.auth.LogoutPage;
import fr.mirumiru.auth.MiruSession;
import fr.mirumiru.pages.GalleryListPage;
import fr.mirumiru.pages.HomePage;
import fr.mirumiru.pages.NewsPage;
import fr.mirumiru.pages.admin.AdminHomePage;
import fr.mirumiru.pages.admin.AdminNewsEditPage;
import fr.mirumiru.pages.admin.AdminNewsListPage;

public class MiruApplication extends AuthenticatedWebApplication {

	private BeanManager beanManager;

	@Override
	protected void init() {
		super.init();
		mountPages();
		getMarkupSettings().setStripWicketTags(true);

		try {
			beanManager = (BeanManager) new InitialContext()
					.lookup("java:comp/BeanManager");
		} catch (NamingException e) {
			throw new IllegalStateException("Unable to obtain CDI BeanManager",
					e);
		}
	}

	private void mountPages() {
		mountPage("/news/#{id}", NewsPage.class);
		mountPage("/gallery", GalleryListPage.class);

		mountPage("/login", LoginPage.class);
		mountPage("/logout", LogoutPage.class);
		
		mountPage("/admin", AdminHomePage.class);
		mountPage("/admin/news", AdminNewsListPage.class);
		mountPage("/admin/news/edit/#{id}", AdminNewsEditPage.class);

	}

	@SuppressWarnings("unchecked")
	public <T> T getBean(Class<? extends T> klass) {
		Set<Bean<?>> beans = beanManager.getBeans(klass);
		Bean<?> bean = beanManager.resolve(beans);
		CreationalContext<?> creationalContext = beanManager
				.createCreationalContext(bean);
		T result = (T) beanManager.getReference(bean, klass, creationalContext);
		return result;
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
