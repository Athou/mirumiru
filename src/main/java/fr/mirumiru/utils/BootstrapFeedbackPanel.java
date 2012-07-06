package fr.mirumiru.utils;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.AbstractReadOnlyModel;

public class BootstrapFeedbackPanel extends FeedbackPanel {

	private static final long serialVersionUID = -4454548249075569147L;

	public BootstrapFeedbackPanel(String id) {
		super(id);
		init();
	}

	public BootstrapFeedbackPanel(String id, IFeedbackMessageFilter filter) {
		super(id, filter);
		init();
	}

	private void init() {
		add(new AttributeModifier("class", new AbstractReadOnlyModel<String>() {
			private static final long serialVersionUID = -2728496420265562466L;

			@Override
			public String getObject() {
				return anyErrorMessage() ? "alert alert-error"
						: "alert alert-success";
			}
		}));
	}

	@Override
	protected void onBeforeRender() {
		setVisible(anyMessage());
		super.onBeforeRender();
	}

}
