package fr.mirumiru.services;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.Album;

@Singleton
public class FacebookService {

	@Inject
	BundleService bundle;

	public List<Album> getAlbums() {
		FacebookClient client = new DefaultFacebookClient();
		Connection<Album> albums = client.fetchConnection(
				"127903530580737/albums", Album.class);
		return albums.getData();

	}
}
