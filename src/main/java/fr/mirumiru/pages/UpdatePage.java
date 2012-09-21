package fr.mirumiru.pages;

import javax.inject.Inject;

import org.apache.wicket.markup.html.WebPage;

import fr.mirumiru.services.FacebookService;

public class UpdatePage extends WebPage {

	private static final long serialVersionUID = 1L;

	@Inject
	FacebookService facebookService;

	public UpdatePage() {
		facebookService.refresh();
		setResponsePage(getApplication().getHomePage());
	}

}
