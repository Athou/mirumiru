package fr.mirumiru.services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import fr.mirumiru.model.News;

@LocalBean
@Stateless
public class NewsDAO extends GenericDAO<News> {

	public List<News> findAllOrderByNewest(int startIndex, int count) {

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<News> query = builder.createQuery(getType());
		Root<News> root = query.from(getType());
		query.orderBy(builder.desc(root.get("created")));
		TypedQuery<News> q = em.createQuery(query);
		q.setMaxResults(count);
		q.setFirstResult(startIndex);
		return q.getResultList();

	}

	@Override
	protected Class<News> getType() {
		return News.class;
	}

}
