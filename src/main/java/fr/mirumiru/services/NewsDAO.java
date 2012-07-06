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

	public List<News> findAllOrderByNewest() {

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<News> query = builder.createQuery(getType());
		Root<News> root = query.from(getType());
		query.orderBy(builder.desc(root.get("created")));
		TypedQuery<News> q = em.createQuery(query);
		return q.getResultList();

	}

	public News getLatest() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<News> query = builder.createQuery(getType());
		Root<News> root = query.from(getType());
		query.orderBy(builder.desc(root.get("created")));
		TypedQuery<News> q = em.createQuery(query);
		q.setMaxResults(1);
		return q.getSingleResult();
	}

	public NextAndPrevious getNextAndPrevious(Long id) {
		NextAndPrevious result = new NextAndPrevious();

		News news = findById(id);
		List<News> list = findAllOrderByNewest();

		int index = list.indexOf(news);
		if (index > 0) {
			result.previous = list.get(index - 1).getId();
		}
		if (index < list.size() - 1) {
			result.next = list.get(index + 1).getId();
		}
		return result;
	}

	public class NextAndPrevious {
		public Long previous;
		public Long next;
	}

	@Override
	protected Class<News> getType() {
		return News.class;
	}

}
