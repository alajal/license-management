package ee.cyber.licensing.api;

import ee.cyber.licensing.dao.ContactRepository;
import ee.cyber.licensing.dao.CustomerRepository;
import ee.cyber.licensing.dao.LicenseRepository;
import ee.cyber.licensing.entity.AuthorisedUser;
import ee.cyber.licensing.entity.Contact;
import ee.cyber.licensing.entity.Customer;
import ee.cyber.licensing.entity.License;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

@Path("contactPersons")
public class ContactResource {

    @Inject
    private ContactRepository contactRepository;

    @Inject
    private LicenseRepository licenseRepository;

    @GET
    @Path("bylicense/{id}")
    @Produces("application/json")
    public List<Contact> getContactsByLicenseId(@PathParam("id") int id) throws Exception {
        License license = licenseRepository.findById(id);
        Customer customer = license.getCustomer();
        return contactRepository.findAll(customer);
    }

    @POST
    @Path("bylicense/{id}")
    public Contact saveContactPerson(Contact contactPerson, @PathParam("id") int licenseId) throws Exception {
        return contactRepository.save(contactPerson, licenseId);
    }

    @PUT
    @Path("bylicense/{licenseId}")
    public Contact updateContactPerson(Contact contactPerson, @PathParam("licenseId") int licenseId) throws Exception {
        return contactRepository.updateContactPerson(contactPerson, licenseId);
    }

}
