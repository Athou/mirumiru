package fr.mirumiru.components;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;

import fr.mirumiru.components.references.fancybox.FancyBoxReference;
import fr.mirumiru.utils.WicketUtils;

public class FancyBoxBehavior extends Behavior {

	private static final long serialVersionUID = 1L;

	private String imageTitle;

	public FancyBoxBehavior(String imageTitle) {
		this.imageTitle = imageTitle;
	}

	@Override
	public void onConfigure(Component component) {
		super.onConfigure(component);
		component.add(new AttributeAppender("class", " fancybox"));
		component.add(new AttributeModifier("title", imageTitle));

	}

	@Override
	public void renderHead(Component component, IHeaderResponse response) {
		super.renderHead(component, response);
		FancyBoxReference.render(response);
		response.render(WicketUtils.loadJS(FancyBoxBehavior.class));
	}

}
