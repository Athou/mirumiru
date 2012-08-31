package fr.mirumiru.components.references.lightbox;

import java.util.List;

import org.apache.wicket.bootstrap.Bootstrap;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

import com.google.common.collect.Lists;

public class LightBoxReference extends JavaScriptResourceReference {

	private static final long serialVersionUID = 1L;

	private static LightBoxReference instance = new LightBoxReference();

	public LightBoxReference() {
		super(LightBoxReference.class, "jquery.lightbox-0.5.pack.js");
	}

	@Override
	public Iterable<? extends HeaderItem> getDependencies() {
		List<HeaderItem> list = Lists.newArrayList();
		list.add(JavaScriptHeaderItem.forReference(Bootstrap.get()));
		list.add(CssHeaderItem.forReference(new CssResourceReference(
				LightBoxReference.class, "jquery.lightbox-0.5.css")));
		return list;
	}

	public static void render(IHeaderResponse response) {
		response.render(JavaScriptHeaderItem.forReference(instance));
	}
}
