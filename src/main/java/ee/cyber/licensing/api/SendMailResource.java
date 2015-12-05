package ee.cyber.licensing.api;

import ee.cyber.licensing.dao.AuthorisedUserRepository;
import ee.cyber.licensing.dao.LicenseRepository;
import ee.cyber.licensing.dao.ContactRepository;

import ee.cyber.licensing.entity.SendMailTLS;
import ee.cyber.licensing.entity.MailBody;
import ee.cyber.licensing.entity.License;
import ee.cyber.licensing.entity.AuthorisedUser;
import ee.cyber.licensing.entity.Contact;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;
import java.util.ArrayList;

@Path("sendMail")
public class SendMailResource {

    @Inject
    private LicenseRepository licenseRepository;
    @Inject
    private AuthorisedUserRepository authorisedUserRepository;
    @Inject
    private ContactRepository contactRepository;


    @Path("/{file_id}/{license_id}")
    @PUT
    public void sendMailWithoutFile(@PathParam("file_id")Integer file_id, @PathParam("license_id") Integer license_id, MailBody mailbody) throws Exception {
      //List<AuthorisedUser> au_users = authorisedUserRepository.findAll(license_id);
      License license = licenseRepository.findById(license_id);
      List<Contact> contacts = contactRepository.findAll(license.getCustomer());

      List<String> receivers = new ArrayList<String>();
      /* Kui soov on ka authorised useritele saata, siis uncommenti see.
      for(AuthorisedUser au : au_users) {
        System.out.println(au);
        receivers.add(au.getEmail());
      }
      */
      for(Contact contact : contacts) {
        System.out.println(contact);
        receivers.add(contact.getEmail());
      }

      String file_location = "aa";

      SendMailTLS sml = new SendMailTLS();
      if(file_id==0 && receivers.size()>0) {
        sml.generateAndSendEmailWithoutFile(mailbody, receivers);
      }
      else if(!(file_id==0) && receivers.size()>0 && file_location!=null) {
        sml.generateAndSendEmailWithFile(mailbody, receivers, file_location);
      }
    }
}
