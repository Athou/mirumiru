package fr.mirumiru.services;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import fr.mirumiru.model.News;

@LocalBean
@Stateless
public class NewsDAO extends GenericDAO<News> {

	@Override
	protected Class<News> getType() {
		return News.class;
	}

}
