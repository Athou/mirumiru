package fr.mirumiru.utils;

import java.util.Locale;

import org.apache.wicket.request.mapper.parameter.PageParameters;

public class WicketUtils {

	public enum Language {
		ENGLISH(Locale.ENGLISH, "flag-gb"), FRENCH(Locale.FRENCH, "flag-fr");

		private Locale locale;
		private String className;

		private Language(Locale locale, String className) {
			this.locale = locale;
			this.className = className;
		}

		public String getClassName() {
			return className;
		}

		public Locale getLocale() {
			return locale;
		}
	}

	public static PageParameters buildParams(String key, Object value) {
		PageParameters params = new PageParameters();
		if (value != null) {
			params.add(key, value);
		}
		return params;
	}
}
