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
import com.restfb.types.Post;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedOutput;

import fr.mirumiru.MiruApplication;
import fr.mirumiru.services.FacebookService;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/rss")
public class RssServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.setContentType("text/xml");

		MiruApplication app = (MiruApplication) Application.get();
		FacebookService facebookService = app.getBean(FacebookService.class);
		List<Post> list = facebookService.getPosts();

		SyndFeed feed = new SyndFeedImpl();
		feed.setFeedType("rss_2.0");
		feed.setTitle("Miru Miru");
		String path = req.getScheme()
				+ "://"
				+ req.getServerName()
				+ req.getRequestURI().substring(0,
						req.getRequestURI().length() - 4);
		feed.setLink(path);
		feed.setDescription("Miru Miru");

		List<SyndEntry> entries = Lists.newArrayList();
		for (Post post : list) {
			SyndEntry entry = new SyndEntryImpl();
			entry.setUri("fr.mirumiru.post:" + post.getId());
			entry.setTitle(post.getMessage().substring(0,
					Math.min(20, post.getMessage().length())));
			entry.setLink("http://www.facebook.com/" + post.getId());
			entry.setPublishedDate(post.getUpdatedTime());

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
