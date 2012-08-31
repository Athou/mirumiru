package fr.mirumiru.components;

import java.util.Arrays;

import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.resource.ResourceReferenceRequestHandler;
import org.apache.wicket.request.resource.ResourceReference;

public class FaviconHeaderItem extends HeaderItem {

	private ResourceReference reference;

	public FaviconHeaderItem(ResourceReference reference) {
		this.reference = reference;
	}

	@Override
	public Iterable<?> getRenderTokens() {
		return Arrays.asList("favicon");
	}

	@Override
	public void render(Response response) {

		IRequestHandler handler = new ResourceReferenceRequestHandler(reference);
		String url = RequestCycle.get().urlFor(handler).toString();

		StringBuilder sb = new StringBuilder();
		sb.append("<link rel=\"shortcut icon\" href=\"");
		sb.append(url);
		sb.append("\" >\n");

		response.write(sb.toString());

	}

}
