package fr.mirumiru;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import com.google.common.collect.Lists;

import fr.mirumiru.model.PageModel;
import fr.mirumiru.pages.AboutPage;
import fr.mirumiru.pages.ContactPage;
import fr.mirumiru.pages.CrochetInfoPage;
import fr.mirumiru.pages.FAQPage;
import fr.mirumiru.pages.FacebookNewsPage;
import fr.mirumiru.pages.GalleryListPage;
import fr.mirumiru.pages.HomePage;
import fr.mirumiru.pages.PortfolioPage;

@ApplicationScoped
public class MiruPages {
	private List<PageModel> menuPages;

	@PostConstruct
	public void init() {
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
}
