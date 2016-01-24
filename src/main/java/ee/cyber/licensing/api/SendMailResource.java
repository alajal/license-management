package ee.cyber.licensing.api;

import ee.cyber.licensing.dao.AuthorisedUserRepository;
import ee.cyber.licensing.dao.LicenseRepository;
import ee.cyber.licensing.dao.ContactRepository;
import ee.cyber.licensing.dao.FileRepository;

import ee.cyber.licensing.entity.MailAttachment;
import ee.cyber.licensing.entity.MailBody;
import ee.cyber.licensing.entity.License;
import ee.cyber.licensing.entity.Contact;

import javax.activation.DataHandler;
import javax.inject.Inject;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.ws.rs.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("sendMail")
public class SendMailResource {

    @Inject
    private LicenseRepository licenseRepository;
    @Inject
    private AuthorisedUserRepository authorisedUserRepository;
    @Inject
    private ContactRepository contactRepository;
    @Inject
    private FileRepository fileRepository;

    final static Logger logger = LoggerFactory.getLogger(SendMailResource.class);


    @Path("/{fileId}/{licenseId}")
    @PUT
    public void sendMail(@PathParam("fileId") Integer fileId, @PathParam("licenseId") Integer licenseId, MailBody mailbody) throws Exception {

        License license = licenseRepository.findById(licenseId);
        List<Contact> contacts = contactRepository.findAll(license.getCustomer());
        List<String> receivers = getReceivers(mailbody, contacts);

        if (receivers.size() > 0) {
            generateAndSendMail(mailbody, receivers, fileId);
        }
    }

    private void generateAndSendMail(MailBody mailbody, List<String> receivers, Integer fileId) throws MessagingException, IOException, SQLException {
        logger.info("1st ===> setup Mail Server Properties");
        Properties mailServerProperties = getProperties();

        final String email = mailServerProperties.getProperty("fromEmail");
        final String password = mailServerProperties.getProperty("password");
        final String host = mailServerProperties.getProperty("mail.smtp.host");

        logger.info("2nd ===> create Authenticator object to pass in Session.getInstance argument");

        Authenticator authentication = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        };
        logger.info("Mail Server Properties have been setup successfully");

        logger.info("3rd ===> get Mail Session..");
        Session getMailSession = Session.getInstance(mailServerProperties, authentication);

        logger.info("4th ===> generateAndSendEmail() starts");
        MimeMessage mailMessage = new MimeMessage(getMailSession);

        mailMessage.addHeader("Content-type", "text/html; charset=UTF-8");
        mailMessage.addHeader("format", "flowed");
        mailMessage.addHeader("Content-Transfer-Encoding", "8bit");

        mailMessage.setFrom(new InternetAddress(email, "License dude"));
        //mailMessage.setReplyTo(InternetAddress.parse(email, false));
        mailMessage.setSubject(mailbody.getSubject());
        //String emailBody = body + "<br><br> Regards, <br>Cybernetica team";
        mailMessage.setSentDate(new Date());

        for (String receiver : receivers) {
            mailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
        }

        if (fileId != 0) {
            MailAttachment file = fileRepository.findById(fileId);
            if (file != null) {
                String fileName = file.getFileName();
                byte[] fileData = file.getData_b();
                if (fileName != null) {
                    // Create a multipart message for attachment
                    Multipart multipart = new MimeMultipart();
                    // Body part
                    BodyPart messageBodyPart = new MimeBodyPart();
                    messageBodyPart.setContent(mailbody.getBody(), "text/html");
                    multipart.addBodyPart(messageBodyPart);

                    //Attachment part
                    messageBodyPart = new MimeBodyPart();
                    ByteArrayDataSource source = new ByteArrayDataSource(fileData, "application/octet-stream");
                    messageBodyPart.setDataHandler(new DataHandler(source));
                    messageBodyPart.setFileName(fileName);
                    multipart.addBodyPart(messageBodyPart);

                    mailMessage.setContent(multipart);
                }
            }
        } else {
            mailMessage.setContent(mailbody.getBody(), "text/html");
        }

        logger.info("5th ===> Get Session");
        sendMail(email, password, host, getMailSession, mailMessage);
    }

    private static Properties getProperties() throws IOException {
        InputStream input = SendMailResource.class.getClassLoader().getResourceAsStream("config.properties");
        if (input == null) {
            throw new RuntimeException();
        }
        Properties props = new Properties(System.getProperties());
        props.load(input);
        input.close();
        return props;
    }

    private static void sendMail(String email, String password, String host, Session getMailSession, MimeMessage mailMessage) throws MessagingException {
        Transport transport = getMailSession.getTransport("smtp");

        // if you have 2FA enabled then provide App Specific Password
        transport.connect(host, email, password);
        transport.sendMessage(mailMessage, mailMessage.getAllRecipients());
        transport.close();
        logger.info("6th ===> Email Sent Successfully With Image Attachment");
        logger.info("7th ===> generateAndSendEmail() ended");
    }

    private List<String> getReceivers(MailBody mailbody, List<Contact> contacts) {
        List<String> receivers = new ArrayList<>();

        //Possibility to send notifications to authorised users
//        List<AuthorisedUser> au_users = authorisedUserRepository.findAll(license_id);
//        for (AuthorisedUser au : au_users) {
//            System.out.println(au);
//            receivers.add(au.getEmail());
//        }

        String contactIds = mailbody.getContactIds();

        if (contactIds != null && !(contactIds.equals(""))) {
            String[] contactIdsSplitted = contactIds.trim().split("\\s*,\\s*");
            int[] splitIds = new int[contactIdsSplitted.length];

            for (int i = 0; i < contactIdsSplitted.length; i++) {
                splitIds[i] = Integer.parseInt(contactIdsSplitted[i]);
            }

            for (Contact contact : contacts) {
                System.out.println((contact.getId()).intValue());
                if (contains(splitIds, contact.getId())) {
                    receivers.add(contact.getEmail());
                }
            }
        } else {
            for (Contact contact : contacts) {
                receivers.add(contact.getEmail());
            }
        }
        //Send letter to myself as well
        receivers.add("ametliktest@gmail.com");
        return receivers;
    }

    private boolean contains(final int[] array, final int key) {
        return ArrayUtils.contains(array, key);
    }
}
