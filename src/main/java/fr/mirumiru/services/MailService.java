package fr.mirumiru.services;

import java.util.Properties;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.SystemUtils;

import fr.mirumiru.model.ContactMail;

@ApplicationScoped
public class MailService {

	@Inject
	BundleService bundle;

	public void sendMail(ContactMail mail) throws Exception {
		final String username = bundle.getSmtpGmailUserName();
		final String password = bundle.getSmtpGmailPassword();
		String dest = bundle.getMailDest();

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(mail.getEmail(), mail.getName()));
		message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(dest));
		message.setSubject("Message from " + mail.getName()
				+ " via mirumiru.fr");
		message.setText(buildContent(mail.getName(), mail.getEmail(),
				mail.getMessage()));

		Transport.send(message);

	}

	private String buildContent(String name, String email, String content) {
		StringBuilder sb = new StringBuilder();
		sb.append("This message has been sent from mirumiru.fr by ");
		sb.append(name);
		sb.append(" (");
		sb.append(email);
		sb.append(") ");
		sb.append(SystemUtils.LINE_SEPARATOR);
		sb.append(SystemUtils.LINE_SEPARATOR);
		sb.append(content);
		return sb.toString();
	}

}
