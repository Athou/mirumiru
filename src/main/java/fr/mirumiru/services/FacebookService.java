package fr.mirumiru.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.Album;
import com.restfb.types.Post;

@Startup
@Singleton
public class FacebookService {

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

	// @Schedule(hour = "*", persistent = false)
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

		try {
			log.info("Refreshing news list from Facebook");

			String token = bundle.getFacebookAuthToken();
			if (StringUtils.isNotBlank(token)) {
				FacebookClient client = new DefaultFacebookClient(token);

				Connection<Post> postConnection = client.fetchConnection(
						"127903530580737/statuses&limit=1000", Post.class);
				posts = new ArrayList<Post>(postConnection.getData());
			}
		} catch (Exception e) {
			log.fatal("Could not refresh facebook news", e);
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
