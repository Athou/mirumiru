package fr.mirumiru.pages;

import fr.mirumiru.utils.Mount;

@Mount(path = "crochet", menu = "crochet", menuOrder = 40)
public class CrochetInfoPage extends ContentPage {

	private static final long serialVersionUID = -197179143378330195L;

	public CrochetInfoPage() {

	}

	@Override
	protected String getTitle() {
		return "crochet";
	}

}
