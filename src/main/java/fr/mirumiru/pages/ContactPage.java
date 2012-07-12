package fr.mirumiru.pages;

import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.StringValidator;

import fr.mirumiru.services.MailService;
import fr.mirumiru.utils.BootstrapFeedbackPanel;

@SuppressWarnings("serial")
public class ContactPage extends ContentPage {

	private String name;
	private String email;
	private String message;

	public ContactPage() {

		final FeedbackPanel feedback = new BootstrapFeedbackPanel("feedback");
		add(feedback);

		Form<Void> form = new StatelessForm<Void>("form") {
			@Override
			protected void onSubmit() {
				try {
					getBean(MailService.class).sendMail(name, email, message);
					info("Message sent successfully !");
				} catch (Exception e) {
					getLog().error("Could not send email", e);
					error("Could not send email");
				}
			}
		};
		add(form);

		form.add(new RequiredTextField<String>("name",
				new PropertyModel<String>(this, "name")));
		form.add(new EmailTextField("email", new PropertyModel<String>(this,
				"email")).setRequired(true));
		form.add(new TextArea<String>("message", new PropertyModel<String>(
				this, "message")).setRequired(true).add(
				StringValidator.maximumLength(1000)));
	}

	@Override
	protected String getTitle() {
		return "Contact";
	}

}
