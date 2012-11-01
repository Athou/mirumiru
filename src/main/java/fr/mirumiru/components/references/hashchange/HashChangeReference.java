package fr.mirumiru.components.references.hashchange;

import java.util.List;

import org.apache.wicket.bootstrap.Bootstrap;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

import com.google.common.collect.Lists;

public class HashChangeReference extends JavaScriptResourceReference {

	private static final long serialVersionUID = 1L;

	private static HashChangeReference instance = new HashChangeReference();

	public HashChangeReference() {
		super(HashChangeReference.class, "jquery.ba-hashchange.min.js");
	}

	@Override
	public Iterable<? extends HeaderItem> getDependencies() {
		List<HeaderItem> list = Lists.newArrayList();
		list.add(JavaScriptHeaderItem.forReference(Bootstrap.responsive()));
		return list;
	}

	public static void render(IHeaderResponse response) {
		response.render(JavaScriptHeaderItem.forReference(instance));
	}
}
