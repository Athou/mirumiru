package fr.mirumiru.services;

import java.io.FileInputStream;
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
			String configPath = "/config.properties";
			String openshiftDataDir = System.getenv("OPENSHIFT_DATA_DIR");
			if (openshiftDataDir != null) {
				is = new FileInputStream(openshiftDataDir + configPath);
			} else {
				is = getClass().getResourceAsStream(configPath);
			}
			props.load(is);
		} catch (Exception e) {
			log.fatal("Could not load config file", e);
		} finally {
			IOUtils.closeQuietly(is);
		}
	}

	public String getSmtpGmailUserName() {
		return props.getProperty("mail.smtp.gmail.username");
	}

	public String getSmtpGmailPassword() {
		return props.getProperty("mail.smtp.gmail.password");
	}

	public String getMailDest() {
		return props.getProperty("mail.dest");
	}

}
