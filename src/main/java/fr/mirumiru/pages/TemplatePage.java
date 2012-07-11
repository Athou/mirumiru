package fr.mirumiru.pages;

import java.text.DateFormat;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.link.StatelessLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.ResourceModel;

import com.google.common.collect.Lists;
import com.restfb.types.Post;

import fr.mirumiru.MiruApplication;
import fr.mirumiru.auth.MiruSession;
import fr.mirumiru.services.FacebookService;
import fr.mirumiru.utils.WicketUtils;
import fr.mirumiru.utils.WicketUtils.Language;

@SuppressWarnings("serial")
public abstract class TemplatePage extends WebPage {

	public TemplatePage() {

		add(new Label("title", getTitle()));
		add(new BookmarkablePageLink<HomePage>("logo-link", HomePage.class));
		addNavigationMenu();
		addFacebookPosts();

		IModel<List<Language>> model = new LoadableDetachableModel<List<Language>>() {
			@Override
			protected List<Language> load() {
				return Lists.reverse(Lists.newArrayList(WicketUtils.Language
						.values()));
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
		List<PageModel> pages = Lists.newArrayList();
		pages.add(new PageModel("home", HomePage.class));
		pages.add(new PageModel("about", AboutPage.class));
		pages.add(new PageModel("news", NewsPage.class));
		pages.add(new PageModel("pictures", GalleryListPage.class));
		pages.add(new PageModel("contact", ContactPage.class));

		ListView<PageModel> entries = new ListView<PageModel>("menu-entry",
				pages) {
			@Override
			protected void populateItem(ListItem<PageModel> item) {
				PageModel model = item.getModelObject();
				BookmarkablePageLink<TemplatePage> link = new BookmarkablePageLink<TemplatePage>(
						"link", model.getPageClass());
				item.add(link);
				link.add(new Label("name", new ResourceModel(model.getName())));
				final Class<? extends TemplatePage> pageClass = model
						.getPageClass();
				link.add(new AttributeModifier("class",
						new AbstractReadOnlyModel<String>() {
							public String getObject() {
								return getPage().getClass().equals(pageClass) ? "active"
										: AttributeModifier.VALUELESS_ATTRIBUTE_REMOVE;
							}
						}));
			}
		};
		add(entries);
	}

	private void addFacebookPosts() {

		LoadableDetachableModel<List<Post>> model = new LoadableDetachableModel<List<Post>>() {
			@Override
			protected List<Post> load() {
				return getBean(FacebookService.class).getPosts();
			}
		};

		ListView<Post> posts = new ListView<Post>("fb-post", model) {
			@Override
			protected void populateItem(ListItem<Post> item) {
				Post post = item.getModelObject();

				String message = post.getMessage();
				int max = 100;
				if (message.length() > max) {
					message = message.substring(0, max - 1) + "...";
				}
				DateFormat dateFormat = DateFormat.getDateInstance(
						DateFormat.SHORT, getSession().getLocale());

				item.add(new Label("fb-content", message));
				ExternalLink link = new ExternalLink("fb-link", post.getLink());
				item.add(link);
				link.add(new Label("fb-postdate", dateFormat.format(post
						.getCreatedTime())));
			}
		};
		add(posts);
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
}
