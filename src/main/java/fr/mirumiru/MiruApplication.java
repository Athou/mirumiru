package fr.mirumiru;

import java.util.Set;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.wicket.Application;
import org.apache.wicket.Page;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.IRequestMapper;

import fr.mirumiru.auth.LoginPage;
import fr.mirumiru.auth.MiruSession;
import fr.mirumiru.pages.HomePage;
import fr.mirumiru.utils.PageModel;
import fr.mirumiru.utils.locale.LocaleFirstMapper;

public class MiruApplication extends AuthenticatedWebApplication {

	private BeanManager beanManager;

	@Override
	protected void init() {
		super.init();
		try {
			beanManager = (BeanManager) new InitialContext()
					.lookup("java:comp/BeanManager");
		} catch (NamingException e) {
			throw new IllegalStateException("Unable to obtain CDI BeanManager",
					e);
		}

		mountPages();

		IRequestMapper localeFirstMapper = new LocaleFirstMapper(
				getRootRequestMapperAsCompound());
		setRootRequestMapper(localeFirstMapper);

	}

	private void mountPages() {
		MiruPages miruPages = getBean(MiruPages.class);
		for (PageModel page : miruPages.getMountPoints()) {
			mountPage(page.getName(), page.getPageClass());
		}
	}

	public static MiruApplication get() {
		return (MiruApplication) Application.get();
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
		return LoginPage.class;
	}

	@Override
	protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
		return MiruSession.class;
	}
}
