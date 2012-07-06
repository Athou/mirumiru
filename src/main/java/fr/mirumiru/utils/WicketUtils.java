package fr.mirumiru.utils;

import org.apache.wicket.request.mapper.parameter.PageParameters;

public class WicketUtils {
	public static PageParameters buildParams(String key, Object value) {
		PageParameters params = new PageParameters();
		if (value != null) {
			params.add(key, value);
		}
		return params;
	}
}
