package fr.mirumiru.pages;

import fr.mirumiru.utils.Mount;

@SuppressWarnings("serial")
@Mount(path = "404")
public class Error404Page extends ContentPage {

	@Override
	protected String getTitle() {
		return "404";
	}
}
