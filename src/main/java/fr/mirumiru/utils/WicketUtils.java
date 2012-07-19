package fr.mirumiru.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.google.common.collect.Lists;

import fr.mirumiru.MiruApplication;
import fr.mirumiru.model.PageModel;
import fr.mirumiru.pages.TemplatePage;

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

		public static boolean isLocaleSupported(Locale locale) {
			return locale == null ? null : isLanguageSupported(locale
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

	private static List<PageModel> menuPages;

	public static List<PageModel> getMenuPages() {
		if (menuPages == null) {
			List<PageModel> list = Lists.newArrayList();
			List<Class<TemplatePage>> pageClasses = MiruApplication.get()
					.getBeanClasses(TemplatePage.class, Mount.class);
			for (Class<TemplatePage> klass : pageClasses) {
				Mount mount = klass.getAnnotation(Mount.class);
				String menu = mount.menu();
				int menuOrder = mount.menuOrder();
				if (StringUtils.isNotBlank(menu)) {
					list.add(new PageModel(menu, menuOrder, klass));
				}
			}
			Collections.sort(list, new Comparator<PageModel>() {
				@Override
				public int compare(PageModel o1, PageModel o2) {
					return o1.getMenuOrder() - o2.getMenuOrder();
				}
			});
			menuPages = list;
		}
		return menuPages;
	}

	public static PageParameters buildParams(String key, Object value) {
		PageParameters params = new PageParameters();
		if (value != null) {
			params.add(key, value);
		}
		return params;
	}
}
