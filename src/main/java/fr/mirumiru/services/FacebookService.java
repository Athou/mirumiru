package fr.mirumiru.services;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.wicket.util.io.IOUtils;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.Album;
import com.restfb.types.Post;

@Startup
@Singleton
public class FacebookService {

	private static final String ACCESS_TOKEN_URL = "https://graph.facebook.com/oauth/access_token?type=client_cred&client_id=%s&client_secret=%s";

	@Inject
	BundleService bundle;

	@Inject
	Logger log;

	private FacebookCache cache;

	public List<Album> getAlbums() {
		return cache.getAlbums();
	}

	public List<Post> getPosts() {
		return cache.getPosts();
	}

	@Schedule(hour = "*", persistent = false)
	@PostConstruct
	public void refresh() {
		List<Album> albums = new ArrayList<Album>();
		List<Post> posts = new ArrayList<Post>();
		cache = new FacebookCache(albums, posts);

		try {
			log.info("Refreshing album list from Facebook");
			FacebookClient client = new DefaultFacebookClient();

			Connection<Album> albumConnection = client.fetchConnection(
					"127903530580737/albums", Album.class);
			albums = new ArrayList<Album>(albumConnection.getData());
			Collections.sort(albums, new Comparator<Album>() {
				@Override
				public int compare(Album o1, Album o2) {
					return o2.getCreatedTime().compareTo(o1.getCreatedTime());
				}
			});

		} catch (Exception e) {
			log.fatal("Could not refresh facebook albums", e);
		}

		InputStream is = null;
		try {
			log.info("Refreshing news list from Facebook");

			String appId = bundle.getFacebookAppId();
			String appSecret = bundle.getFacebookAppSecret();
			String url = String.format(ACCESS_TOKEN_URL, appId, appSecret);

			if (StringUtils.isNotBlank(appId)
					&& StringUtils.isNotBlank(appSecret)) {
				String accessTokenKeyValue = IOUtils.toString(new URL(url)
						.openStream());
				String accessToken = accessTokenKeyValue
						.substring("access_token=".length());

				FacebookClient client = new DefaultFacebookClient(accessToken);
				Connection<Post> postConnection = client.fetchConnection(
						"127903530580737/feed", Post.class,
						Parameter.with("limit", 1000),
						Parameter.with("offset", 0));

				posts = new ArrayList<Post>(Collections2.filter(
						postConnection.getData(), new Predicate<Post>() {
							@Override
							public boolean apply(Post post) {
								return "status".equals(post.getType());
							}
						}));
			}
		} catch (Exception e) {
			log.fatal("Could not refresh facebook news", e);
		} finally {
			IOUtils.closeQuietly(is);
		}
		cache = new FacebookCache(albums, posts);

	}

	private class FacebookCache {

		private List<Album> albums;
		private List<Post> posts;

		public FacebookCache(List<Album> albums, List<Post> posts) {
			this.albums = albums;
			this.posts = posts;
		}

		public List<Album> getAlbums() {
			return albums;
		}

		public List<Post> getPosts() {
			return posts;
		}
	}
}
