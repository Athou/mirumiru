package fr.mirumiru.rss;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.wicket.Application;

import com.google.common.collect.Lists;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedOutput;

import fr.mirumiru.MiruApplication;
import fr.mirumiru.model.News;
import fr.mirumiru.services.NewsDAO;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/rss")
public class RssServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		resp.setContentType("text/xml");

		MiruApplication app = (MiruApplication) Application.get();
		NewsDAO dao = app.getBean(NewsDAO.class);
		List<News> list = dao.findAllOrderByNewest();

		SyndFeed feed = new SyndFeedImpl();
		feed.setFeedType("rss_2.0");
		feed.setTitle("Miru Miru");
		feed.setLink("http://www.mirumiru.fr");
		feed.setDescription("Miru Miru");
		

		List<SyndEntry> entries = Lists.newArrayList();
		for (News news : list) {
			SyndEntry entry = new SyndEntryImpl();
			entry.setUri("fr.mirumiru.news:" + news.getId());
			entry.setTitle(news.getTitle());
			entry.setLink("http://www.mirumiru.fr/news/" + news.getId());
			entry.setPublishedDate(news.getCreated().getTime());
			
			entries.add(entry);
		}
		feed.setEntries(entries);
		SyndFeedOutput output = new SyndFeedOutput();
		
		try {
			output.output(feed, resp.getWriter());
		} catch (FeedException e) {
			throw new IOException(e);
		}

	}

}
