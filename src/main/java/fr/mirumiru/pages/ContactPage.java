package fr.mirumiru.pages;

import javax.inject.Inject;

import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.validator.StringValidator;

import fr.mirumiru.components.BootstrapFeedbackPanel;
import fr.mirumiru.model.ContactMail;
import fr.mirumiru.services.MailService;
import fr.mirumiru.utils.ModelFactory;

@SuppressWarnings("serial")
public class ContactPage extends ContentPage {

	@Inject
	MailService mailService;

	public ContactPage() {

		final FeedbackPanel feedback = new BootstrapFeedbackPanel("feedback");
		add(feedback);
		add(new ContactMailForm("form", Model.of(new ContactMail())));

	}

	public class ContactMailForm extends StatelessForm<ContactMail> {

		public ContactMailForm(String id, IModel<ContactMail> model) {
			super(id, model);
			ContactMail proxy = ModelFactory.proxy(ContactMail.class);
			add(new RequiredTextField<String>("name", ModelFactory.model(
					getModel(), proxy.getName())));
			add(new EmailTextField("email", ModelFactory.model(getModel(),
					proxy.getEmail())).setRequired(true));
			add(new TextArea<String>("message", ModelFactory.model(getModel(),
					proxy.getMessage())).setRequired(true).add(
					StringValidator.maximumLength(1000)));
		}

		@Override
		protected void onSubmit() {
			try {
				mailService.sendMail(getModelObject());
				info("Message sent successfully !");
			} catch (Exception e) {
				getLog().error("Could not send email", e);
				error("Could not send email");
			}
		}

	}

	@Override
	protected String getTitle() {
		return "contact";
	}

}
