package fr.mirumiru.pages.admin;

import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.repeater.RepeatingView;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;

import fr.mirumiru.nav.NavigationHeader;
import fr.mirumiru.pages.TemplatePage;

@SuppressWarnings("serial")
@AuthorizeInstantiation(Roles.ADMIN)
public abstract class AdminTemplatePage extends TemplatePage {
	@Override
	protected void addNavigationMenu() {
		Multimap<String, PageModel> pages = LinkedListMultimap.create();
		pages.put("News", new PageModel("News", AdminNewsListPage.class));

		RepeatingView repeatingView = new RepeatingView("nav-headers");

		for (String category : pages.keySet()) {
			repeatingView.add(new NavigationHeader(repeatingView.newChildId(),
					category, pages.get(category)));
		}
		add(repeatingView);
	}
}
