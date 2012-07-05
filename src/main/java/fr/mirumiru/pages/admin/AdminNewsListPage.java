package fr.mirumiru.pages.admin;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.LoadableDetachableModel;

import fr.mirumiru.model.News;
import fr.mirumiru.services.NewsDAO;
import fr.mirumiru.utils.WicketUtils;

@SuppressWarnings("serial")
public class AdminNewsListPage extends AdminTemplatePage {

	public AdminNewsListPage() {
		ListView<News> newsView = new PropertyListView<News>("news",
				new NewsModel()) {
			@Override
			protected void populateItem(ListItem<News> item) {
				News news = item.getModelObject();

				BookmarkablePageLink<AdminNewsEditPage> link = new BookmarkablePageLink<AdminNewsEditPage>(
						"link", AdminNewsEditPage.class,
						WicketUtils.buildParams("id", news.getId()));
				item.add(link);
				link.add(new Label("title"));
				item.add(new Label("author.name"));
				item.add(new Label("created",
						new SimpleDateFormat("yyyy-MM-dd").format(news
								.getCreated().getTime())));
			}
		};
		add(newsView);
	}

	private class NewsModel extends LoadableDetachableModel<List<News>> {

		@Override
		protected List<News> load() {
			return getBean(NewsDAO.class).findAllOrderByNewest();
		}
	}

	@Override
	protected String getTitle() {
		return "News List";
	}

}
