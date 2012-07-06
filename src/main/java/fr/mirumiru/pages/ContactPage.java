package fr.mirumiru.pages;

import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.validator.StringValidator;

@SuppressWarnings("serial")
public class ContactPage extends TemplatePage {

	public ContactPage() {

		Form<Contact> form = new Form<Contact>("form", new Contact()) {
			@Override
			protected void onSubmit() {
				Contact contact = getModelObject();
				System.out.println(contact.getName());
				// TODO send mail
			}
		};
		add(form);

		form.add(new TextField<Contact>("name").setRequired(true));
		form.add(new EmailTextField("email"));
		form.add(new TextArea<Contact>("message").setRequired(true).add(
				StringValidator.maximumLength(1000)));
	}

	@Override
	protected String getTitle() {
		return "Contact";
	}

	protected class Contact extends Model<Contact> {
		private String name;
		private String email;
		private String message;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

	}

}
