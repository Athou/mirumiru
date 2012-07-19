package fr.mirumiru.pages;

import fr.mirumiru.utils.Mount;

@Mount(path = "about", menu = "about", menuOrder = 10)
public class AboutPage extends ContentPage {

	private static final long serialVersionUID = -2392878202873146276L;

	@Override
	protected String getTitle() {
		return "about";
	}

}
