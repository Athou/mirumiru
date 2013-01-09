package fr.mirumiru.pages;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import org.apache.wicket.Session;
import org.apache.wicket.markup.MarkupType;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.request.Url;

import com.google.common.collect.Lists;

import fr.mirumiru.MiruPages;
import fr.mirumiru.model.PageModel;
import fr.mirumiru.services.StartupBean;
import fr.mirumiru.utils.WicketUtils;
import fr.mirumiru.utils.WicketUtils.Language;

@SuppressWarnings("serial")
public class SitemapPage extends WebPage {
	
	public static final String SITEMAP_PATH = "sitemap.xml";

	@Inject
	StartupBean startupBean;

	@Inject
	MiruPages miruPages;

	public SitemapPage() {
		List<PageModel> pages = miruPages.getMenuPages();
		List<ExtendedPageModel> model = Lists.newArrayList();
		for (PageModel page : pages) {
			for (Language lang : WicketUtils.Language.values()) {
				model.add(new ExtendedPageModel(page, lang.getLocale()));
			}
		}

		ListView<ExtendedPageModel> view = new ListView<ExtendedPageModel>(
				"url", model) {
			@Override
			protected void populateItem(ListItem<ExtendedPageModel> item) {
				ExtendedPageModel page = item.getModelObject();
				Session.get().setLocale(page.getLocale());
				String url = getRequestCycle().getUrlRenderer().renderFullUrl(
						Url.parse(getRequestCycle().urlFor(page.getPageClass(),
								null).toString()));

				Calendar startupTime = startupBean.getStartupTime();

				item.add(new Label("loc", url));
				item.add(new Label("lastmod",
						new SimpleDateFormat("yyyy-MM-dd").format(startupTime
								.getTime())));
			}
		};
		add(view);
	}

	@Override
	public MarkupType getMarkupType() {
		return new MarkupType("xml", MarkupType.XML_MIME);
	}

	private class ExtendedPageModel extends PageModel {

		private Locale locale;

		public ExtendedPageModel(PageModel model, Locale locale) {
			super(model.getName(), model.getPageClass());
			this.locale = locale;
		}

		public Locale getLocale() {
			return locale;
		}

	}

}
