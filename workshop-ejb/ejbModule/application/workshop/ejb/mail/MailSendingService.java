package application.workshop.ejb.mail;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Stateless
public class MailSendingService {

	@Resource(name = "java:jboss/mail/gmail")
	private Session session;
	  
    public void sendEmail(String recipient, String subject, String body) {
    	try {
    		Message message = new MimeMessage(session);
    		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
    		message.setSubject(subject);
    		message.setText(body);
    		Transport.send(message);
    	} catch (MessagingException e) {
    		e.printStackTrace();
    	}
    }
    
    public void sendEmailWithAttachment(String recipient, String subject, Multipart multipart) {
    	try {
    		Message message = new MimeMessage(session);
    		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
    		message.setSubject(subject);
    		message.setContent(multipart);
    		Transport.send(message);
    	} catch (MessagingException e) {
    		e.printStackTrace();
    	}
    }
}
