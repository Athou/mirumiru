package fr.mirumiru.pages;

import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.apache.wicket.Application;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Session;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.bootstrap.Bootstrap;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.PriorityHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

import com.google.common.collect.Lists;
import com.restfb.types.Post;

import fr.mirumiru.MiruApplication;
import fr.mirumiru.components.FacebookPost;
import fr.mirumiru.model.PageModel;
import fr.mirumiru.services.FacebookService;
import fr.mirumiru.utils.WicketUtils;
import fr.mirumiru.utils.WicketUtils.Language;

@SuppressWarnings("serial")
public abstract class TemplatePage extends WebPage {

	protected WebMarkupContainer html;

	public TemplatePage() {
		html = new WebMarkupContainer("html");
		html.add(new AttributeModifier("lang",
				new AbstractReadOnlyModel<String>() {
					@Override
					public String getObject() {
						return getSession().getLocale().getLanguage();
					}
				}));
		html.add(new AttributeModifier("xmlns:wicket",
				new AbstractReadOnlyModel<String>() {
					@Override
					public String getObject() {
						return AttributeModifier.VALUELESS_ATTRIBUTE_REMOVE;
					}
				}));
		add(html);
		html.add(new Label("title", getLocalizedString(getTitle(), getTitle())));
		html.add(new BookmarkablePageLink<HomePage>("logo-link", HomePage.class));
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
				Link<Void> link = new BookmarkablePageLink<Void>("link",
						getPageClass(), getPageParameters()) {
					@Override
					protected CharSequence getURL() {
						Session session = Session.get();
						Locale oldLocale = session.getLocale();
						session.setLocale(lang.getLocale());
						CharSequence url = super.getURL();
						session.setLocale(oldLocale);
						return url;
					}
				};
				link.add(new AttributeAppender("class", " "
						+ lang.getClassName()));
				item.add(link);
			}
		};

		html.add(flags);

		html.add(new BookmarkablePageLink<TemplatePage>("faq", FAQPage.class));
		html.add(new BookmarkablePageLink<TemplatePage>("contactpage",
				ContactPage.class));

	}

	protected List<PageModel> getMenuPages() {
		return WicketUtils.getMenuPages();
	}

	private void addNavigationMenu() {

		IModel<List<PageModel>> model = new LoadableDetachableModel<List<PageModel>>() {
			protected java.util.List<PageModel> load() {
				return getMenuPages();
			}
		};

		ListView<PageModel> entries = new ListView<PageModel>("menu-entry",
				model) {
			@Override
			protected void populateItem(ListItem<PageModel> item) {
				PageModel model = item.getModelObject();
				BookmarkablePageLink<TemplatePage> link = new BookmarkablePageLink<TemplatePage>(
						"link", model.getPageClass());
				item.add(link);
				link.add(new Label("name", getLocalizedString(model.getName(),
						model.getName())));
				final Class<? extends WebPage> pageClass = model.getPageClass();
				link.add(new AttributeModifier("class",
						new AbstractReadOnlyModel<String>() {
							public String getObject() {
								return getPage().getClass().equals(pageClass) ? "active"
										: AttributeModifier.VALUELESS_ATTRIBUTE_REMOVE;
							}
						}));
			}
		};
		html.add(entries);
	}

	private void addFacebookPosts() {

		LoadableDetachableModel<List<Post>> model = new LoadableDetachableModel<List<Post>>() {
			@Override
			protected List<Post> load() {
				List<Post> posts = getBean(FacebookService.class).getPosts();
				if (posts.size() > 2) {
					posts = posts.subList(0, 2);
				}
				return posts;
			}
		};

		ListView<Post> posts = new ListView<Post>("fb-posts", model) {
			@Override
			protected void populateItem(ListItem<Post> item) {
				Post post = item.getModelObject();
				item.add(new FacebookPost("post", post, 85));
			}
		};
		html.add(posts);

		html.add(new BookmarkablePageLink<TemplatePage>("more-news",
				FacebookNewsPage.class));
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		response.render(new PriorityHeaderItem(JavaScriptHeaderItem
				.forReference(Bootstrap.get())));
	}

	protected abstract String getTitle();

	public <T> T getBean(Class<? extends T> klass) {
		return MiruApplication.get().getBean(klass);
	}

	public Logger getLog() {
		return Logger.getLogger(getClass());
	}

	public String getLocalizedString(String key) {
		return getLocalizedString(key, null);
	}

	public String getLocalizedString(String key, String defaultValue) {
		return Application.get().getResourceSettings().getLocalizer()
				.getString(key, this, defaultValue);
	}
}
