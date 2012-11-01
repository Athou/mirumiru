package fr.mirumiru.utils;

import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.markup.head.CssReferenceHeaderItem;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.util.io.IOUtils;
import org.apache.wicket.util.template.PackageTextTemplate;
import org.apache.wicket.util.template.TextTemplate;

public class WicketUtils {

	public enum Language {
		ENGLISH(Locale.ENGLISH, "flag-gb", "English"), FRENCH(Locale.FRENCH,
				"flag-fr", "Fran\u00E7ais");

		private Locale locale;
		private String className;
		private String displayName;

		private Language(Locale locale, String className, String displayName) {
			this.locale = locale;
			this.className = className;
			this.displayName = displayName;
		}

		public String getClassName() {
			return className;
		}

		public Locale getLocale() {
			return locale;
		}

		public String getDisplayName() {
			return displayName;
		}

		public static boolean isLocaleSupported(Locale locale) {
			return locale == null ? false : isLanguageSupported(locale
					.getLanguage());
		}

		public static boolean isLanguageSupported(String lang) {
			boolean supported = false;
			for (Language l : values()) {
				if (StringUtils.equals(lang, l.getLocale().getLanguage())) {
					supported = true;
					break;
				}
			}
			return supported;
		}
	}

	public static PageParameters buildParams(String key, Object value) {
		PageParameters params = new PageParameters();
		if (value != null) {
			params.add(key, value);
		}
		return params;
	}

	public static JavaScriptReferenceHeaderItem loadJS(Class<?> klass) {
		return JavaScriptReferenceHeaderItem
				.forReference(new JavaScriptResourceReference(klass, klass
						.getSimpleName() + ".js"));
	}

	public static HeaderItem loadJS(Class<?> klass, Map<String, Object> map) {
		TextTemplate template = new PackageTextTemplate(klass,
				klass.getSimpleName() + ".js");
		String script = template.asString(map);
		IOUtils.closeQuietly(template);
		return OnDomReadyHeaderItem.forScript(script);
	}

	public static CssReferenceHeaderItem loadCSS(Class<?> klass) {
		return CssReferenceHeaderItem.forReference(new CssResourceReference(
				klass, klass.getSimpleName() + ".css"));
	}
}
