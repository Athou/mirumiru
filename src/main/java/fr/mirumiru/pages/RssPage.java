package fr.mirumiru.pages;

import java.io.StringWriter;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.handler.TextRequestHandler;

import com.google.common.collect.Lists;
import com.restfb.types.Post;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.SyndFeedOutput;

import fr.mirumiru.services.BundleService;
import fr.mirumiru.services.FacebookService;

public class RssPage extends WebPage {

	private static final long serialVersionUID = 1L;

	public static final String RSS_PATH = "rss";

	@Inject
	BundleService bundleService;

	@Inject
	FacebookService facebookService;

	private Logger log = Logger.getLogger(getClass());

	public RssPage() {

		List<Post> list = facebookService.getPosts();

		SyndFeed feed = new SyndFeedImpl();
		feed.setFeedType("rss_2.0");
		feed.setTitle("Miru Miru");

		String path = bundleService.getWebServerRootPath();
		feed.setLink(path == null ? "" : path);
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

		StringWriter writer = new StringWriter();
		try {
			output.output(feed, writer);
		} catch (Exception e) {
			writer.write("Could not get feed information");
			log.error(e.getMessage(), e);
		}

		getRequestCycle().scheduleRequestHandlerAfterCurrent(
				new TextRequestHandler("text/xml", "UTF-8", writer.toString()));

	}

}
