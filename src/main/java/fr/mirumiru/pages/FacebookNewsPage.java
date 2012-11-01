package fr.mirumiru.pages;

import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.restfb.types.Post;

import fr.mirumiru.components.FacebookPost;
import fr.mirumiru.services.FacebookService;
import fr.mirumiru.utils.WicketUtils;

@SuppressWarnings("serial")
public class FacebookNewsPage extends ContentPage {

	private static final int NEWS_PER_PAGE = 3;

	@Inject
	FacebookService facebookService;

	private int page;

	public FacebookNewsPage() {
		this(new PageParameters());
	}

	public FacebookNewsPage(PageParameters params) {
		page = params.get("page").toInt(-1);

		List<Post> posts = facebookService.getPosts();
		int pageMax = calculatePageMax(posts);

		if (posts.isEmpty()) {
			setResponsePage(Error404Page.class);
			return;
		} else if (page < 1 || page > pageMax) {
			page = pageMax;
			setResponsePage(getPageClass(),
					WicketUtils.buildParams("page", page));
			return;
		}

		IModel<List<Post>> model = new LoadableDetachableModel<List<Post>>() {
			@Override
			protected List<Post> load() {
				List<Post> posts = facebookService.getPosts();
				int pageMax = calculatePageMax(posts);

				int lastIndex = posts.size() - 1;
				int fromIndex = (pageMax - page) * NEWS_PER_PAGE;
				int toIndex = fromIndex + NEWS_PER_PAGE < lastIndex ? fromIndex
						+ NEWS_PER_PAGE : lastIndex;
				posts = posts.subList(fromIndex, toIndex);
				return posts;
			}
		};
		ListView<Post> postsView = new ListView<Post>("posts", model) {
			@Override
			protected void populateItem(ListItem<Post> item) {
				Post post = item.getModelObject();
				item.add(new FacebookPost("post", post));
				item.add(new WebMarkupContainer("separator").setVisible(item
						.getIndex() != NEWS_PER_PAGE - 1));
			}
		};
		add(postsView);

		WebMarkupContainer newer = new WebMarkupContainer("newer");
		newer.setVisible(page < pageMax);
		add(newer);
		newer.add(new BookmarkablePageLink<TemplatePage>("link",
				getPageClass(), WicketUtils.buildParams("page", page + 1)));

		WebMarkupContainer older = new WebMarkupContainer("older");
		older.setVisible(page > 1);
		add(older);
		older.add(new BookmarkablePageLink<TemplatePage>("link",
				getPageClass(), WicketUtils.buildParams("page", page - 1)));

	}

	private int calculatePageMax(List<Post> posts) {
		return (int) Math.ceil(posts.size() / (double) NEWS_PER_PAGE);
	}

	@Override
	protected String getTitle() {
		return "news";
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		response.render(WicketUtils.loadCSS(FacebookNewsPage.class));
	}
}
