package fr.mirumiru.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.log4j.Logger;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.Album;

@Singleton
public class FacebookService {

	@Inject
	BundleService bundle;

	@Inject
	Logger log;

	private List<Album> albums;
	private long albumValidity = 0;

	public List<Album> getAlbums() {
		long now = Calendar.getInstance().getTimeInMillis();
		if (albums == null || albumValidity < now) {
			log.info("Refreshing album list from Facebook");
			FacebookClient client = new DefaultFacebookClient();
			Connection<Album> connection = client.fetchConnection(
					"127903530580737/albums", Album.class);
			List<Album> albums = new ArrayList<Album>(connection.getData());
			Collections.sort(albums, new Comparator<Album>() {
				@Override
				public int compare(Album o1, Album o2) {
					return o1.getCreatedTime().compareTo(o2.getCreatedTime());
				}
			});
			this.albums = albums;
			this.albumValidity = now + TimeUnit.HOURS.toMillis(1);
		}
		return albums;
	}
}
