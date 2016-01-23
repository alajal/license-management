package ee.cyber.licensing.api;

import ee.cyber.licensing.dao.AuthorisedUserRepository;
import ee.cyber.licensing.dao.LicenseRepository;
import ee.cyber.licensing.dao.ContactRepository;
import ee.cyber.licensing.dao.FileRepository;

import ee.cyber.licensing.entity.SendMailTLS;
import ee.cyber.licensing.entity.MailBody;
import ee.cyber.licensing.entity.MailAttachment;
import ee.cyber.licensing.entity.License;
import ee.cyber.licensing.entity.Contact;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;
import java.util.ArrayList;

import org.apache.commons.lang3.ArrayUtils;

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

    @Path("/{fileId}/{licenseId}")
    @PUT
    public void sendMail(@PathParam("fileId") Integer fileId, @PathParam("licenseId") Integer licenseId, MailBody mailbody) throws Exception {

        License license = licenseRepository.findById(licenseId);
        List<Contact> contacts = contactRepository.findAll(license.getCustomer());
        List<String> receivers = getReceivers(mailbody, contacts);

        System.out.println("File id: " + fileId);

        if (fileId == 0 && receivers.size() > 0) {
            SendMailTLS.generateAndSendEmailWithoutFile(mailbody, receivers);
        } else if (!(fileId == 0) && receivers.size() > 0) {
            MailAttachment file = fileRepository.findById(fileId);
            if (file != null) {
                String fileName = file.getFileName();
                byte[] fileData = file.getData_b();
                System.out.println("Faili pikkus on: " + fileData.length);
                if (fileName != null) {
                    SendMailTLS.generateAndSendEmailWithFile(mailbody, receivers, fileName, fileData);
                }
            }
        }
    }

    private List<String> getReceivers(MailBody mailbody, List<Contact> contacts) {
        List<String> receivers = new ArrayList<String>();
        //List<AuthorisedUser> au_users = authorisedUserRepository.findAll(license_id);

      /* Kui soov on ka authorised useritele saata, siis uncommenti see.
      for(AuthorisedUser au : au_users) {
        System.out.println(au);
        receivers.add(au.getEmail());
      }
      */
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
        //saadan ka default iseendale:
        receivers.add("ametliktest@gmail.com");
        return receivers;
    }

    private boolean contains(final int[] array, final int key) {
        return ArrayUtils.contains(array, key);
    }
}
