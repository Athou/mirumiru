package fr.mirumiru.components.references.fancybox;

import java.util.List;

import org.apache.wicket.bootstrap.Bootstrap;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

import com.google.common.collect.Lists;

public class FancyBoxReference extends JavaScriptResourceReference {

	private static final long serialVersionUID = 1L;

	private static FancyBoxReference instance = new FancyBoxReference();

	public FancyBoxReference() {
		super(FancyBoxReference.class, "jquery.fancybox.pack.js");
	}

	@Override
	public Iterable<? extends HeaderItem> getDependencies() {
		List<HeaderItem> list = Lists.newArrayList();
		list.add(JavaScriptHeaderItem.forReference(Bootstrap.responsive()));
		list.add(CssHeaderItem.forReference(new CssResourceReference(
				FancyBoxReference.class, "jquery.fancybox.css")));
		return list;
	}

	public static void render(IHeaderResponse response) {
		response.render(JavaScriptHeaderItem.forReference(instance));
	}
}
