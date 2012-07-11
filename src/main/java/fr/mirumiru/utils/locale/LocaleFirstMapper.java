package fr.mirumiru.utils.locale;

import java.util.List;
import java.util.Locale;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.wicket.Session;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.IRequestMapper;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Url;

public class LocaleFirstMapper implements IRequestMapper {

	private final IRequestMapper chain;

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
				Locale locale = LocaleHelper.parseLocale(localeAsString);
				if (locale != null) {
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
			if (locale == null) {
				locale = Locale.ENGLISH;
			}
			url.getSegments().add(0, locale.getLanguage());
		}
		return url;
	}

	private Request cleanRequest(Request req) {
		Url url = req.getUrl();
		url.getSegments().remove(0);
		return req.cloneWithUrl(url);
	}

}