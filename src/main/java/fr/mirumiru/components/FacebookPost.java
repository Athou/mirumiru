package fr.mirumiru.components;

import java.text.DateFormat;
import java.util.Date;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.panel.Panel;

import com.restfb.types.Post;

public class FacebookPost extends Panel {

	private static final long serialVersionUID = 5821159556507035960L;

	public FacebookPost(String id, Post post) {
		this(id, post, -1);
	}

	public FacebookPost(String id, Post post, int messageLengthLimit) {
		super(id);

		String message = post.getMessage();
		if (messageLengthLimit != -1 && message.length() > messageLengthLimit) {
			message = message.substring(0, messageLengthLimit - 1) + "...";
		}
		DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL,
				getSession().getLocale());

		add(new Label("fb-content", message));
		ExternalLink link = new ExternalLink("fb-link",
				"http://www.facebook.com/" + post.getId());
		add(link);
		Date postDate = post.getUpdatedTime();
		link.add(new Label("fb-postdate", postDate == null ? "" : dateFormat
				.format(postDate)));
	}

}
