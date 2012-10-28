package fr.mirumiru.services;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

@ApplicationScoped
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
			log.fatal("Could not load config file");
		} finally {
			IOUtils.closeQuietly(is);
		}
	}

	public String getFacebookAppId() {
		return getProperty("facebook.appid");
	}

	public String getFacebookAppSecret() {
		return getProperty("facebook.appsecret");
	}

	public String getSmtpGmailUserName() {
		return getProperty("mail.smtp.gmail.username");
	}

	public String getSmtpGmailPassword() {
		return getProperty("mail.smtp.gmail.password");
	}

	public String getMailDest() {
		return getProperty("mail.dest");
	}

	public String getWebServerRootPath() {
		return getProperty("web");
	}

	private String getProperty(String name) {
		if (props == null) {
			return null;
		}
		return props.getProperty(name);
	}

}
