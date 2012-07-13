package fr.mirumiru.pages;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.LoadableDetachableModel;

import fr.mirumiru.model.Portfolio.PortfolioItem;
import fr.mirumiru.services.PortfolioService;

@SuppressWarnings("serial")
public class PortfolioPage extends ContentPage {

	public PortfolioPage() {

		ListView<PortfolioItem> portfolioView = new PropertyListView<PortfolioItem>(
				"product", new PortfolioModel()) {
			@Override
			protected void populateItem(ListItem<PortfolioItem> item) {
				PortfolioItem portfolioItem = item.getModelObject();
				String imageName = portfolioItem.getName(getSession()
						.getLocale().getLanguage());
				String imagePath = "../images/portfolio/"
						+ portfolioItem.getImage();
				item.add(new Label("name", imageName));
				ExternalLink link = new ExternalLink("link", imagePath);
				link.add(new AttributeModifier("title", imageName));
				item.add(link);
				Component image = new WebMarkupContainer("image");
				image.add(new AttributeModifier("src", imagePath));
				link.add(image);
			}
		};
		add(portfolioView);
	}

	private class PortfolioModel extends
			LoadableDetachableModel<List<PortfolioItem>> {
		@Override
		protected List<PortfolioItem> load() {
			return getBean(PortfolioService.class).getPortfolio().getItems();
		}
	}

	@Override
	protected String getTitle() {
		return "portfolio";
	}

}
