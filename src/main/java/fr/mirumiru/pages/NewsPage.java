package fr.mirumiru.pages;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import fr.mirumiru.model.News;
import fr.mirumiru.services.NewsDAO;
import fr.mirumiru.services.NewsDAO.NextAndPrevious;
import fr.mirumiru.utils.WicketUtils;

@SuppressWarnings("serial")
public class NewsPage extends TemplatePage {

	private long id;

	public NewsPage(PageParameters params) {

		id = params.get("id").toLong(-1);

		NewsDAO dao = getBean(NewsDAO.class);
		if (id == -1) {
			setResponsePage(getPageClass(),
					WicketUtils.buildParams("id", dao.getLatest().getId()));
		}

		WebMarkupContainer container = new WebMarkupContainer("news",
				new CompoundPropertyModel<News>(new NewsModel(id)));
		add(container);

		container.add(new Label("title"));
		container.add(new Label("content").setEscapeModelStrings(false));
		container.add(new Label("author.name"));

		NextAndPrevious nap = dao.getNextAndPrevious(id);
		WebMarkupContainer previous = new WebMarkupContainer("previous");
		previous.setVisible(nap.previous != null);
		add(previous);
		previous.add(new BookmarkablePageLink<NewsPage>("link", NewsPage.class,
				WicketUtils.buildParams("id", nap.previous)));

		WebMarkupContainer next = new WebMarkupContainer("next");
		next.setVisible(nap.next != null);
		add(next);
		next.add(new BookmarkablePageLink<NewsPage>("link", NewsPage.class,
				WicketUtils.buildParams("id", nap.next)));

	}

	private class NewsModel extends LoadableDetachableModel<News> {

		private long id;

		public NewsModel(long id) {
			this.id = id;
		}

		@Override
		protected News load() {
			return getBean(NewsDAO.class).findById(id);
		}
	}

	@Override
	protected String getTitle() {
		return "News";
	}
}
