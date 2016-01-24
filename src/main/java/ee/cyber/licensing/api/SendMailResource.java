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

import ee.cyber.licensing.service.MailService;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("sendMail")
public class SendMailResource {

    @Inject
    private MailService mailService;

    @Path("/{fileId}/{licenseId}")
    @PUT
    public void sendMail(@PathParam("fileId") int fileId, @PathParam("licenseId") int licenseId, MailBody mailbody) throws Exception {

        mailService.generateAndSendMail(mailbody, licenseId, fileId);

    }

}
