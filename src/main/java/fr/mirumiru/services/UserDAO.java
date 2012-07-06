package fr.mirumiru.services;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import fr.mirumiru.model.User;

@LocalBean
@Stateless
public class UserDAO extends GenericDAO<User> {

	@Override
	protected Class<User> getType() {
		return User.class;
	}

}
