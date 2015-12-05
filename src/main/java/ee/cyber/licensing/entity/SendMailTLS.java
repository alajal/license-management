package ee.cyber.licensing.entity;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import ee.cyber.licensing.entity.MailBody;

import java.util.List;

public class SendMailTLS {

	static Properties mailServerProperties;
	static Session getMailSession;
	static MimeMessage generateMailMessage;

	public static void generateAndSendEmailWithoutFile(MailBody mailbody, List<String> receivers) throws AddressException, MessagingException {

		String subject = mailbody.getSubject();
		String body = mailbody.getBody();


		// Step1
		System.out.println("\n 1st ===> setup Mail Server Properties..");
		mailServerProperties = System.getProperties();
		mailServerProperties.put("mail.smtp.port", "587");
		mailServerProperties.put("mail.smtp.auth", "true");
		mailServerProperties.put("mail.smtp.starttls.enable", "true");
		System.out.println("Mail Server Properties have been setup successfully..");

		// Step2
		System.out.println("\n\n 2nd ===> get Mail Session..");
		getMailSession = Session.getDefaultInstance(mailServerProperties, null);
		generateMailMessage = new MimeMessage(getMailSession);
		for(String receiver : receivers) {
			generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
		}
		generateMailMessage.setSubject(subject);
		String emailBody = body + "<br><br> Regards, <br>Jens-Stefan";
		generateMailMessage.setContent(emailBody, "text/html");
		System.out.println("Mail Session has been created successfully..");

		// Step3
		System.out.println("\n\n 3rd ===> Get Session and Send mail");
		Transport transport = getMailSession.getTransport("smtp");

		// Enter your correct gmail UserID and Password
		// if you have 2FA enabled then provide App Specific Password
		transport.connect("smtp.gmail.com", "ametliktest@gmail.com", "TugevPar00l.");
		transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
		transport.close();
	}

	public static void generateAndSendEmailWithFile(MailBody mailbody, List<String> receivers, String file_location) throws AddressException, MessagingException {

		String subject = mailbody.getSubject();
		String body = mailbody.getBody();


		// Step1
		System.out.println("\n 1st ===> setup Mail Server Properties..");

		final String sourceEmail = "ametliktest@gmail.com"; // requires valid Gmail id
		final String password = "TugevPar00l."; // correct password for Gmail id
		String host = "smtp.gmail.com";


		Properties props = new Properties();
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
		System.out.println("Mail Server Properties have been setup successfully..");

		// Step2
		System.out.println("\n2nd ===> create Authenticator object to pass in Session.getInstance argument..");

			Authenticator authentication = new Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(sourceEmail, password);
					}
			};

		// Step3
		System.out.println("\n\n 3rd ===> get Mail Session..");
		getMailSession = Session.getInstance(props, authentication);

		try {
            System.out.println("\n4th ===> generateAndSendEmail() starts..");

            MimeMessage crunchifyMessage = new MimeMessage(getMailSession);
            crunchifyMessage.addHeader("Content-type", "text/HTML; charset=UTF-8");
            crunchifyMessage.addHeader("format", "flowed");
            crunchifyMessage.addHeader("Content-Transfer-Encoding", "8bit");

            crunchifyMessage.setFrom(new InternetAddress("ametliktest@gmail.com",
                    "NoReply-Crunchify"));
            crunchifyMessage.setReplyTo(InternetAddress.parse("ametliktest@gmail.com", false));
            crunchifyMessage.setSubject(subject, "UTF-8");
            crunchifyMessage.setSentDate(new Date());

            //crunchifyMessage.setRecipients(Message.RecipientType.TO,
            //        InternetAddress.parse(toEmail, false));

						for(String receiver : receivers) {
							crunchifyMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
						}

            // Create the message body part
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(body, "text/html");

            // Create a multipart message for attachment
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            messageBodyPart = new MimeBodyPart();

            // Valid file location
            String filename = file_location;
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            // Trick is to add the content-id header here
            messageBodyPart.setHeader("Content-ID", "image_id");
            multipart.addBodyPart(messageBodyPart);

            System.out.println("\n5th ===> third part for displaying image in the email body..");
            messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent("<br><h3>Find below attached image</h3>"
                    + "<img src='cid:image_id'>", "text/html");
            multipart.addBodyPart(messageBodyPart);
            crunchifyMessage.setContent(multipart);

            System.out.println("\n6th ===> Finally Send message..");

            // Finally Send message
            Transport.send(crunchifyMessage);

            System.out
                    .println("\n7th ===> Email Sent Successfully With Image Attachment. Check your email now..");
            System.out.println("\n8th ===> generateAndSendEmail() ends..");

        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
	}
}
