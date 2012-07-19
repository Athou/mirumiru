package fr.mirumiru;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;

import com.google.common.collect.Lists;

import fr.mirumiru.auth.LoginPage;
import fr.mirumiru.auth.LogoutPage;
import fr.mirumiru.model.PageModel;
import fr.mirumiru.pages.AboutPage;
import fr.mirumiru.pages.ContactPage;
import fr.mirumiru.pages.CrochetInfoPage;
import fr.mirumiru.pages.Error404Page;
import fr.mirumiru.pages.FAQPage;
import fr.mirumiru.pages.FacebookNewsPage;
import fr.mirumiru.pages.GalleryListPage;
import fr.mirumiru.pages.HomePage;
import fr.mirumiru.pages.PortfolioPage;
import fr.mirumiru.pages.SitemapPage;
import fr.mirumiru.pages.admin.AdminHomePage;

@Singleton
public class MiruPages {

	private List<PageModel> mountPoints;
	private List<PageModel> menuPages;

	@PostConstruct
	public void init() {
		// mounts
		mountPoints = Lists.newArrayList();
		mountPoints.add(new PageModel("/about", AboutPage.class));
		mountPoints.add(new PageModel("/news/#{page}", FacebookNewsPage.class));
		mountPoints.add(new PageModel("/portfolio", PortfolioPage.class));
		mountPoints.add(new PageModel("/crochet", CrochetInfoPage.class));
		mountPoints.add(new PageModel("/albums", GalleryListPage.class));
		mountPoints.add(new PageModel("/faq", FAQPage.class));
		mountPoints.add(new PageModel("/contact", ContactPage.class));

		mountPoints.add(new PageModel("/login", LoginPage.class));
		mountPoints.add(new PageModel("/logout", LogoutPage.class));

		mountPoints.add(new PageModel("/admin", AdminHomePage.class));

		mountPoints.add(new PageModel("/404", Error404Page.class));
		mountPoints.add(new PageModel("/sitemap.xml", SitemapPage.class));

		// menu
		menuPages = Lists.newArrayList();
		menuPages.add(new PageModel("home", HomePage.class));
		menuPages.add(new PageModel("about", AboutPage.class));
		menuPages.add(new PageModel("news", FacebookNewsPage.class));
		menuPages.add(new PageModel("portfolio", PortfolioPage.class));
		menuPages.add(new PageModel("crochet", CrochetInfoPage.class));
		menuPages.add(new PageModel("albums", GalleryListPage.class));
		menuPages.add(new PageModel("faq", FAQPage.class));
		menuPages.add(new PageModel("contact", ContactPage.class));
	}

	public List<PageModel> getMenuPages() {
		return menuPages;
	}

	public List<PageModel> getMountPoints() {
		return mountPoints;
	}

}
