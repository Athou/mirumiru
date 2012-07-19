package fr.mirumiru.pages;

import fr.mirumiru.utils.Mount;

@SuppressWarnings("serial")
@Mount(path = "", menu = "home", menuOrder = 0)
public class HomePage extends TemplatePage {

	@Override
	protected String getTitle() {
		return "mirumiru";
	}

}
