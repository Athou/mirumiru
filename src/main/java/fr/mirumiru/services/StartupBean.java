package fr.mirumiru.services;

import java.util.Calendar;
import java.util.logging.Filter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import com.restfb.DefaultFacebookClient;
import com.restfb.DefaultWebRequestor;

@Startup
@Singleton
public class StartupBean {

	private Calendar startupTime;

	@PostConstruct
	public void init() {
		startupTime = Calendar.getInstance();

		final Filter disableLog = new Filter() {
			@Override
			public boolean isLoggable(LogRecord record) {
				return false;
			}
		};
		Logger.getLogger(DefaultFacebookClient.class.getName()).setFilter(
				disableLog);
		Logger.getLogger(DefaultWebRequestor.class.getName()).setFilter(
				disableLog);
	}

	public Calendar getStartupTime() {
		return startupTime;
	};
}
