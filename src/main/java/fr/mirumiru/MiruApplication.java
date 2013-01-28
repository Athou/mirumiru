package fr.mirumiru;

import javax.enterprise.inject.spi.BeanManager;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.wicket.Application;
import org.apache.wicket.DefaultMapperContext;
import org.apache.wicket.Page;
import org.apache.wicket.cdi.CdiConfiguration;
import org.apache.wicket.cdi.ConversationPropagation;
import org.apache.wicket.core.request.mapper.IMapperContext;
import org.apache.wicket.protocol.http.WebApplication;

import fr.mirumiru.pages.ContactPage;
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

public class MiruApplication extends WebApplication {

	public static final String RESOURCES_PREFIX_URL = "static";

	@Override
	protected void init() {
		super.init();
		setupCDI();

		getMarkupSettings().setStripWicketTags(true);
		getMarkupSettings().setCompressWhitespace(true);

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

		mountPage("404", Error404Page.class);
		mountPage(SitemapPage.SITEMAP_PATH, SitemapPage.class);
		mountPage(RssPage.RSS_PATH, RssPage.class);
		mountPage("update", UpdatePage.class);

		mountPage("news/#{page}", FacebookNewsPage.class);
		mountPage("portfolio", PortfolioPage.class);
		mountPage("albums", GalleryListPage.class);
		mountPage("faq", FAQPage.class);
		mountPage("contact", ContactPage.class);

	}

	public static MiruApplication get() {
		return (MiruApplication) Application.get();
	}

	@Override
	protected IMapperContext newMapperContext() {
		return new DefaultMapperContext(this) {
			@Override
			public String getNamespace() {
				return RESOURCES_PREFIX_URL;
			}
		};
	}

	@Override
	public Class<? extends Page> getHomePage() {
		return HomePage.class;
	}

}
