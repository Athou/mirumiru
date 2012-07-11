package fr.mirumiru.pages;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.StatelessLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

import fr.mirumiru.MiruApplication;
import fr.mirumiru.auth.MiruSession;
import fr.mirumiru.nav.NavigationHeader;
import fr.mirumiru.utils.WicketUtils;
import fr.mirumiru.utils.WicketUtils.Language;

@SuppressWarnings("serial")
public abstract class TemplatePage extends WebPage {

	public TemplatePage() {

		add(new Label("title", getTitle()));
		add(new BookmarkablePageLink<HomePage>("logo-link", HomePage.class));
		addNavigationMenu();

		IModel<List<Language>> model = new LoadableDetachableModel<List<Language>>() {
			@Override
			protected List<Language> load() {
				return Lists.reverse(Lists.newArrayList(WicketUtils.Language.values()));
			}
		};

		ListView<Language> flags = new PropertyListView<Language>("locale",
				model) {
			@Override
			protected void populateItem(ListItem<Language> item) {
				final Language lang = item.getModelObject();
				StatelessLink<Void> link = new StatelessLink<Void>("link") {
					@Override
					public void onClick() {
						getSession().setLocale(lang.getLocale());
						setResponsePage(getPage().getClass());
					}
				};
				link.add(new AttributeAppender("class", " "
						+ lang.getClassName()));
				item.add(link);
			}
		};

		add(flags);

	}

	protected void addNavigationMenu() {
		Multimap<String, PageModel> pages = LinkedListMultimap.create();
		pages.put("home", new PageModel("homepage", HomePage.class));
		pages.put("home", new PageModel("news", NewsPage.class));

		pages.put("pictures", new PageModel("albums", GalleryListPage.class));

		pages.put("mirumiru", new PageModel("about", AboutPage.class));
		pages.put("mirumiru", new PageModel("contact", ContactPage.class));

		RepeatingView repeatingView = new RepeatingView("nav-headers");
		for (String category : pages.keySet()) {
			repeatingView.add(new NavigationHeader(repeatingView.newChildId(),
					category, pages.get(category)));
		}
		add(repeatingView);

	}

	protected abstract String getTitle();

	public class PageModel {
		private String name;
		private Class<? extends TemplatePage> pageClass;

		public PageModel(String name, Class<? extends TemplatePage> pageClass) {
			super();
			this.name = name;
			this.pageClass = pageClass;
		}

		public Class<? extends TemplatePage> getPageClass() {
			return pageClass;
		}

		public String getName() {
			return name;
		}

	}

	public MiruSession getAuthSession() {
		return (MiruSession) super.getSession();
	}

	public <T> T getBean(Class<? extends T> klass) {
		MiruApplication application = (MiruApplication) getApplication();
		return application.getBean(klass);
	}

	public Logger getLog() {
		return Logger.getLogger(getClass());
	}

	private static class LocaleWrapper implements Serializable {

		private Locale locale;

		public LocaleWrapper(Locale locale) {
			this.locale = locale;
		}

		public Locale getLocale() {
			return locale;
		}

		@Override
		public String toString() {
			return locale.getDisplayLanguage(locale);
		}
	}
}
