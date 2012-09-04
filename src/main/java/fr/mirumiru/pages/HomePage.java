package fr.mirumiru.pages;

import org.apache.wicket.markup.head.IHeaderResponse;

import fr.mirumiru.utils.WicketUtils;

@SuppressWarnings("serial")
public class HomePage extends TemplatePage {

	@Override
	protected String getTitle() {
		return "mirumiru";
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		response.render(WicketUtils.loadCSS(HomePage.class));
	}

}
