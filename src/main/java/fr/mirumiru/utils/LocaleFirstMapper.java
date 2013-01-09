package fr.mirumiru.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.wicket.Session;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.IRequestMapper;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Url;

import fr.mirumiru.MiruApplication;
import fr.mirumiru.pages.RssPage;
import fr.mirumiru.pages.SitemapPage;

public class LocaleFirstMapper implements IRequestMapper {

	private final IRequestMapper chain;
	private static final List<String> IGNORED_PATHS = Arrays.asList(
			MiruApplication.RESOURCES_PREFIX_URL, SitemapPage.SITEMAP_PATH,
			RssPage.RSS_PATH);

	public LocaleFirstMapper(final IRequestMapper chain) {
		this.chain = chain;
	}

	@Override
	public int getCompatibilityScore(final Request request) {
		return chain.getCompatibilityScore(cleanRequest(request));
	}

	@Override
	public IRequestHandler mapRequest(Request request) {
		List<String> segments = request.getUrl().getSegments();
		if (CollectionUtils.isNotEmpty(segments)) {
			String localeAsString = segments.get(0);
			if (StringUtils.isNotEmpty(localeAsString)) {
				Locale locale = new Locale(localeAsString);
				if (WicketUtils.Language.isLocaleSupported(locale)) {
					Session.get().setLocale(locale);
					request = cleanRequest(request);
				}
			}
		}
		return chain.mapRequest(request);
	}

	@Override
	public Url mapHandler(IRequestHandler handler) {
		Url url = chain.mapHandler(handler);
		if (url != null) {
			Locale locale = Session.get().getLocale();
			if (locale == null
					|| !WicketUtils.Language.isLocaleSupported(locale)) {
				locale = Locale.FRENCH;
			}
			List<String> segments = url.getSegments();
			if (CollectionUtils.isNotEmpty(segments)
					&& IGNORED_PATHS.contains(segments.get(0))) {
				// do nothing
			} else {
				url.getSegments().add(0, locale.getLanguage());
			}
		}
		return url;
	}

	private Request cleanRequest(Request req) {
		Url url = req.getUrl();
		List<String> segments = url.getSegments();
		if (CollectionUtils.isNotEmpty(segments)
				&& WicketUtils.Language.isLanguageSupported(segments.get(0))) {
			url.getSegments().remove(0);
		}
		return req.cloneWithUrl(url);
	}

}