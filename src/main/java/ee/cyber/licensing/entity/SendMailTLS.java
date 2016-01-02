package ee.cyber.licensing.entity;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

public abstract class SendMailTLS {

    public static void generateAndSendEmailWithoutFile(MailBody mailbody, List<String> receivers) throws MessagingException, IOException {
        String subject = mailbody.getSubject();
        String body = mailbody.getBody();

        // Step1
        System.out.println("\n 1st ===> setup Mail Server Properties..");

        InputStream input = SendMailTLS.class.getClassLoader().getResourceAsStream("config.properties");
        if (input == null) {
            throw new RuntimeException();
        }
        Properties mailServerProperties = new Properties(System.getProperties());
        mailServerProperties.load(input);
        input.close();

        // Step2
        final String email = mailServerProperties.getProperty("fromEmail");
        final String password = mailServerProperties.getProperty("password");
        final String host = mailServerProperties.getProperty("mail.smtp.host");

        System.out.println("\n2nd ===> create Authenticator object to pass in Session.getInstance argument..");

        Authenticator authentication = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        };

        System.out.println("Mail Server Properties have been setup successfully..");

        // Step3
        System.out.println("\n\n 3rd ===> get Mail Session..");
        Session getMailSession = Session.getDefaultInstance(mailServerProperties, authentication);
        MimeMessage generateMailMessage = new MimeMessage(getMailSession);
        for (String receiver : receivers) {
            generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
        }
        generateMailMessage.setSubject(subject);
        String emailBody = body + "<br><br> Regards, <br>Jens-Stefan";
        generateMailMessage.setContent(emailBody, "text/html");
        System.out.println("Mail Session has been created successfully..");

        // Step4
        System.out.println("\n\n 4th ===> Get Session and Send mail");
        Transport transport = getMailSession.getTransport("smtp");

        // Enter your correct gmail UserID and Password
        // if you have 2FA enabled then provide App Specific Password
        transport.connect(host, email, password);
        transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
        transport.close();
    }

    public static void generateAndSendEmailWithFile(MailBody mailbody, List<String> receivers, String file_name, byte[] file_bytes) throws MessagingException, IOException {
        String subject = mailbody.getSubject();
        String body = mailbody.getBody();

        // Step0
        InputStream input = SendMailTLS.class.getClassLoader().getResourceAsStream("config.properties");
        if (input == null) {
            throw new RuntimeException();
        }
        Properties props = new Properties(System.getProperties());
        props.load(input);
        input.close();

        // Step1
        System.out.println("\n 1st ===> setup Mail Server Properties..");

        final String email = props.getProperty("fromEmail");
        final String password = props.getProperty("password");

        System.out.println("Mail Server Properties have been setup successfully..");

        // Step2
        System.out.println("\n2nd ===> create Authenticator object to pass in Session.getInstance argument..");

        Authenticator authentication = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        };

        // Step3
        System.out.println("\n\n 3rd ===> get Mail Session..");
        Session getMailSession = Session.getInstance(props, authentication);
        System.out.println(getMailSession);

        System.out.println("\n4th ===> generateAndSendEmail() starts..");
        System.out.println(getMailSession);
        MimeMessage crunchifyMessage = new MimeMessage(getMailSession);
        crunchifyMessage.addHeader("Content-type", "text/html; charset=UTF-8");
        crunchifyMessage.addHeader("format", "flowed");
        crunchifyMessage.addHeader("Content-Transfer-Encoding", "8bit");
        crunchifyMessage.setFrom(new InternetAddress(email, "License dude"));
        //crunchifyMessage.setReplyTo(InternetAddress.parse(email, false));
        crunchifyMessage.setSubject(subject, "UTF-8");
        crunchifyMessage.setSentDate(new Date());

        //crunchifyMessage.setRecipients(Message.RecipientType.TO,
        //        InternetAddress.parse(toEmail, false));

        for (String receiver : receivers) {
            crunchifyMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
        }

        // Create a multipart message for attachment
        Multipart multipart = new MimeMultipart();

        // Create the message body part
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(body, "text/html");

        // Set text message part
        multipart.addBodyPart(messageBodyPart);

        //Attachment part
        messageBodyPart = new MimeBodyPart();

        // Valid file location
        ByteArrayDataSource source = new ByteArrayDataSource(file_bytes, "application/octet-stream");
        System.out.println(source);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(file_name);
        multipart.addBodyPart(messageBodyPart);

        System.out.println("\n5th ===> Finally Send message..");

        // Finally Send message
        //Transport.send(crunchifyMessage);
        System.out.println(getMailSession);
        crunchifyMessage.setContent(multipart);
        Transport transport = getMailSession.getTransport("smtp");
        final String host = props.getProperty("mail.smtp.host");
        // Enter your correct gmail UserID and Password
        // if you have 2FA enabled then provide App Specific Password
        transport.connect(host, email, password);
        transport.sendMessage(crunchifyMessage, crunchifyMessage.getAllRecipients());
        transport.close();

        System.out.println("\n6th ===> Email Sent Successfully With Image Attachment. Check your email now..");
        System.out.println("\n7th ===> generateAndSendEmail() ends..");
    }

}

