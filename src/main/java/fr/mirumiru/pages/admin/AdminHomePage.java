package fr.mirumiru.pages.admin;

import fr.mirumiru.utils.Mount;

@SuppressWarnings("serial")
@Mount(path = "admin")
public class AdminHomePage extends AdminTemplatePage {

	public AdminHomePage() {

	}

	@Override
	protected String getTitle() {
		return "Admin";
	}
}
