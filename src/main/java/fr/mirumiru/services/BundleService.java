package fr.mirumiru.services;

import java.io.InputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

@Singleton
public class BundleService {

	@Inject
	Logger log;

	Properties props;

	@PostConstruct
	public void init() {
		props = new Properties();
		InputStream is = null;
		try {
			is = getClass().getResourceAsStream("/config.properties");
			props.load(is);
		} catch (Exception e) {
			log.fatal("Could not load config file", e);
		} finally {
			IOUtils.closeQuietly(is);
		}
	}

}
