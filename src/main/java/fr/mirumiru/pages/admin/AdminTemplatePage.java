package fr.mirumiru.pages.admin;

import java.util.List;

import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;

import com.google.common.collect.Lists;

import fr.mirumiru.model.PageModel;
import fr.mirumiru.pages.TemplatePage;

@SuppressWarnings("serial")
@AuthorizeInstantiation(Roles.ADMIN)
public abstract class AdminTemplatePage extends TemplatePage {

	@Override
	protected List<PageModel> getMenuPages() {
		List<PageModel> pages = Lists.newArrayList();
		pages.add(new PageModel("Admin Home", 0, AdminHomePage.class));
		return pages;
	}

}
