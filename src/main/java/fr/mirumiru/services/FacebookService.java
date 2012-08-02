package fr.mirumiru.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

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

	@Schedule(hour = "*", persistent = false)
	@PostConstruct
	private void refresh() {
		synchronized (this) {

			cache = new FacebookCache(new ArrayList<Album>(),
					new ArrayList<Post>());
			try {
				log.info("Refreshing album list from Facebook");

				FacebookClient client = new DefaultFacebookClient(
						bundle.getFacebookAuthToken());

				Connection<Album> albumConnection = client.fetchConnection(
						"127903530580737/albums", Album.class);
				List<Album> albums = new ArrayList<Album>(
						albumConnection.getData());
				Collections.sort(albums, new Comparator<Album>() {
					@Override
					public int compare(Album o1, Album o2) {
						return o2.getCreatedTime().compareTo(
								o1.getCreatedTime());
					}
				});

				Connection<Post> postConnection = client.fetchConnection(
						"127903530580737/statuses&limit=1000", Post.class);
				List<Post> posts = new ArrayList<Post>(postConnection.getData());
				cache = new FacebookCache(albums, posts);
			} catch (Exception e) {
				log.fatal("Could not refresh facebook infos", e);
			}
		}
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
