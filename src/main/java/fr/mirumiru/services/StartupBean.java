package fr.mirumiru.services;

import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Startup
@Singleton
public class StartupBean {

	private Calendar startupTime;

	@PostConstruct
	public void init() {
		startupTime = Calendar.getInstance();
	}

	public Calendar getStartupTime() {
		return startupTime;
	};
}
