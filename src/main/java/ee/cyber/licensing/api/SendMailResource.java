package ee.cyber.licensing.api;

import ee.cyber.licensing.dao.AuthorisedUserRepository;
import ee.cyber.licensing.dao.LicenseRepository;
import ee.cyber.licensing.dao.ContactRepository;
import ee.cyber.licensing.dao.FileRepository;

import ee.cyber.licensing.entity.SendMailTLS;
import ee.cyber.licensing.entity.MailBody;
import ee.cyber.licensing.entity.MailAttachment;
import ee.cyber.licensing.entity.License;
import ee.cyber.licensing.entity.AuthorisedUser;
import ee.cyber.licensing.entity.Contact;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;
import java.util.ArrayList;
import org.apache.commons.lang3.ArrayUtils;
import java.util.Base64;

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


    @Path("/{file_id}/{license_id}")
    @PUT
    public void sendMailWithoutFile(@PathParam("file_id")Integer file_id, @PathParam("license_id") Integer license_id, MailBody mailbody) throws Exception {

      License license = licenseRepository.findById(license_id);
      List<Contact> contacts = contactRepository.findAll(license.getCustomer());
      //List<AuthorisedUser> au_users = authorisedUserRepository.findAll(license_id);

      List<String> receivers = new ArrayList<String>();
      /* Kui soov on ka authorised useritele saata, siis uncommenti see.
      for(AuthorisedUser au : au_users) {
        System.out.println(au);
        receivers.add(au.getEmail());
      }
      */
      String mb_contact_ids = mailbody.getContact_ids();

      if(mb_contact_ids!=null && !(mb_contact_ids.equals(""))) {
        String[] mb_contact_ids_split = mb_contact_ids.trim().split("\\s*,\\s*");
        int[] split_ids = new int[mb_contact_ids_split.length];

        for(int i = 0; i < mb_contact_ids_split.length;i++) {
          split_ids[i] = Integer.parseInt(mb_contact_ids_split[i]);
        }

        for(Contact contact : contacts) {
          System.out.println((contact.getId()).intValue());
          if(contains(split_ids, (contact.getId()).intValue())) {
            receivers.add(contact.getEmail());
            System.out.println("LISAS");
          }
        }
      }

      else {
        System.out.println("Saadab kõik");
        for(Contact contact : contacts) {
          receivers.add(contact.getEmail());
        }
      }
      
      System.out.println(file_id);
      
      SendMailTLS sml = new SendMailTLS();
      if(file_id==0 && receivers.size()>0) {
        sml.generateAndSendEmailWithoutFile(mailbody, receivers);
      }
      else if(!(file_id==0) && receivers.size()>0) {
        MailAttachment file = fileRepository.findById(file_id);
        if(file!=null) {
          String file_name = file.getFileName();
          byte[] file_data = file.getData_b();
          System.out.println(file_data);
          if(file_name!=null) {
            sml.generateAndSendEmailWithFile(mailbody, receivers, file_name, file_data);
          }
        }
      }
    }

    public boolean contains(final int[] array, final int key) {
      return ArrayUtils.contains(array, key);
    }
}
