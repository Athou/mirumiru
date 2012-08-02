package fr.mirumiru.pages;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

import com.restfb.types.Album;

import fr.mirumiru.services.FacebookService;
import fr.mirumiru.utils.Mount;

@SuppressWarnings("serial")
@Mount(path = "albums", menu = "albums", menuOrder = 50)
public class GalleryListPage extends ContentPage {

	public GalleryListPage() {

		ListView<Album> albumsView = new PropertyListView<Album>("albums",
				new AlbumModel()) {
			@Override
			protected void populateItem(ListItem<Album> item) {
				Album album = item.getModelObject();
				item.add(new Label("name"));
				item.add(new Label("count"));
				// item.add(new Label("description"));
				ExternalLink link = new ExternalLink("link", album.getLink());
				String imageUrl = "http://graph.facebook.com/"
						+ album.getCoverPhoto() + "/picture";
				link.add(new WebMarkupContainer("coverPhoto")
						.add(new AttributeModifier("src", imageUrl)));
				item.add(link);

			}
		};
		html.add(albumsView);
	}

	private class AlbumModel extends LoadableDetachableModel<List<Album>> {
		@Override
		protected List<Album> load() {
			return getBean(FacebookService.class).getAlbums();
		}
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		response.render(JavaScriptReferenceHeaderItem
				.forReference(new JavaScriptResourceReference(
						GalleryListPage.class, GalleryListPage.class
								.getSimpleName() + ".js")));
	}

	@Override
	protected String getTitle() {
		return "albums";
	}

}
