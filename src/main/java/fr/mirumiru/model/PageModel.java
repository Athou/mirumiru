package fr.mirumiru.model;

import java.io.Serializable;

import org.apache.wicket.markup.html.WebPage;

public class PageModel implements Serializable {

	private static final long serialVersionUID = 514476461016069051L;

	private String name;
	private int menuOrder;
	private Class<? extends WebPage> pageClass;

	public PageModel(String name, int menuOrder,
			Class<? extends WebPage> pageClass) {
		this.name = name;
		this.menuOrder = menuOrder;
		this.pageClass = pageClass;
	}

	public Class<? extends WebPage> getPageClass() {
		return pageClass;
	}

	public String getName() {
		return name;
	}

	public int getMenuOrder() {
		return menuOrder;
	}
}
