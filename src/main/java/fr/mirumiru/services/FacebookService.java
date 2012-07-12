package fr.mirumiru.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.apache.log4j.Logger;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.Album;
import com.restfb.types.Post;

@Singleton
public class FacebookService {

	@Inject
	BundleService bundle;

	@Inject
	Logger log;

	private FacebookCache cache;

	@AroundInvoke
	public Object refreshIfNeeded(InvocationContext ctx) throws Exception {
		Object result = null;
		if (cache == null || !cache.isValid()) {
			refresh();
		}
		result = ctx.proceed();
		return result;
	}

	public List<Album> getAlbums() {
		return cache.getAlbums();
	}

	public List<Post> getPosts() {
		return cache.getPosts();
	}

	private void refresh() {
		synchronized (this) {
			log.info("Refreshing album list from Facebook");

			FacebookClient client = new DefaultFacebookClient(
					bundle.getFacebookAuthToken());

			Connection<Album> albumConnection = client.fetchConnection(
					"127903530580737/albums", Album.class);
			List<Album> albums = new ArrayList<Album>(albumConnection.getData());
			Collections.sort(albums, new Comparator<Album>() {
				@Override
				public int compare(Album o1, Album o2) {
					return o2.getCreatedTime().compareTo(o1.getCreatedTime());
				}
			});

			Connection<Post> postConnection = client.fetchConnection(
					"127903530580737/statuses&limit=1000", Post.class);
			List<Post> posts = new ArrayList<Post>(postConnection.getData());
			cache = new FacebookCache(albums, posts);
		}
	}

	private class FacebookCache {

		private List<Album> albums;
		private long validUntil = 0;
		private List<Post> posts;

		public FacebookCache(List<Album> albums, List<Post> posts) {
			this.albums = albums;
			this.posts = posts;
			validUntil = Calendar.getInstance().getTimeInMillis()
					+ TimeUnit.HOURS.toMillis(1);
		}

		public boolean isValid() {
			return Calendar.getInstance().getTimeInMillis() < validUntil;
		}

		public List<Album> getAlbums() {
			return albums;
		}

		public List<Post> getPosts() {
			return posts;
		}
	}
}
