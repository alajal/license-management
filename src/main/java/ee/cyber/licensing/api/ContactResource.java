package ee.cyber.licensing.api;

import ee.cyber.licensing.dao.ContactRepository;
import ee.cyber.licensing.dao.CustomerRepository;
import ee.cyber.licensing.dao.LicenseRepository;
import ee.cyber.licensing.entity.Contact;
import ee.cyber.licensing.entity.Customer;
import ee.cyber.licensing.entity.License;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
    public List<Contact> getContactPersons(@PathParam("id") int id) throws Exception {
        License license = licenseRepository.findById(id);
        Customer customer = license.getCustomer();
        return contactRepository.findAll(customer);
    }
}
