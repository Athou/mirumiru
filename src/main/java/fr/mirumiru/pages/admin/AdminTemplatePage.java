package fr.mirumiru.pages.admin;

import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;

import fr.mirumiru.pages.TemplatePage;

@SuppressWarnings("serial")
@AuthorizeInstantiation(Roles.ADMIN)
public abstract class AdminTemplatePage extends TemplatePage {

}
