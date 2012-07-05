package fr.mirumiru.pages;

import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import fr.mirumiru.model.News;
import fr.mirumiru.services.NewsDAO;
import fr.mirumiru.utils.WicketUtils;

@SuppressWarnings("serial")
public class NewsPage extends TemplatePage {

	private static final int NEWS_PER_PAGE = 2;

	private int page = 1;

	public NewsPage(PageParameters params) {

		page = params.get("page").toInt(0);
		if (page < 1) {
			page = 1;
		}

		if (page > 1) {
			Label title = (Label) get("title");
			title.setDefaultModelObject(title.getDefaultModelObjectAsString()
					+ " - Page " + page);
		}

		ListView<News> newsView = new PropertyListView<News>("news",
				new NewsModel(page)) {
			@Override
			protected void populateItem(ListItem<News> item) {
				item.add(new Label("title"));
				item.add(new Label("content"));
				item.add(new Label("author.name"));
			}
		};
		add(newsView);

		int pageCount = (int) Math.ceil(getBean(NewsDAO.class).getCount()
				/ (double) NEWS_PER_PAGE);

		WebMarkupContainer previous = new WebMarkupContainer("previous");
		previous.setVisible(page >= 2);
		add(previous);
		previous.add(new BookmarkablePageLink<NewsPage>("link", NewsPage.class,
				WicketUtils.buildParams("page", page - 1)));

		WebMarkupContainer next = new WebMarkupContainer("next");
		next.setVisible(page < pageCount);
		add(next);
		next.add(new BookmarkablePageLink<NewsPage>("link", NewsPage.class,
				WicketUtils.buildParams("page", page + 1)));

	}

	private class NewsModel extends LoadableDetachableModel<List<News>> {

		private int page;

		public NewsModel(int page) {
			this.page = page;
		}

		@Override
		protected List<News> load() {
			return getBean(NewsDAO.class).findAllOrderByNewest(
					(page - 1) * NEWS_PER_PAGE, NEWS_PER_PAGE);
		}
	}

	@Override
	protected String getTitle() {
		return "News";
	}
}
