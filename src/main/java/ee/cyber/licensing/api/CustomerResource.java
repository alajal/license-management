package ee.cyber.licensing.api;

import ee.cyber.licensing.dao.ContactRepository;
import ee.cyber.licensing.dao.CustomerRepository;
import ee.cyber.licensing.entity.Applicant;
import ee.cyber.licensing.entity.Contact;
import ee.cyber.licensing.entity.Customer;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;
import java.util.Objects;

@Path("customers")
public class CustomerResource {

    @Inject
    private CustomerRepository customerRepository;
    @Inject
    private ContactRepository contactRepository;

    @GET
    @Produces("application/json")
    public List<Customer> getCustomers() throws Exception {
        return customerRepository.findAll();
    }

    @Path("/{id}")
    @GET
    @Produces("application/json")
    public Customer getCustomerById(@PathParam("id") Integer id) throws Exception {
        return customerRepository.getCustomerById(id);
    }

    @POST
    public Customer saveCustomer(Applicant applicant) throws Exception {
        //Customer == Organization == Applicant
        // status turns customer into "license owner"
        Customer customer = new Customer(applicant.getOrganizationName(), applicant.getApplicationArea());
        Customer savedCustomer = customerRepository.save(customer);
        //Every contact is connected at least with one customer
        Contact contact = new Contact(savedCustomer.getId(), applicant.getContactName(), applicant.getEmail(),
                applicant.getSkype(), applicant.getPhone());
        contactRepository.save(contact);
        return savedCustomer;
    }

    @Path("/{id}")
    @PUT
    public Customer editCustomer(@PathParam("id") Integer id, Customer customer) throws Exception {
        if (Objects.equals(customer.getId(), id)) {
            return customerRepository.update(customer);
        } else {
            throw new Exception("The customer that needs update is not the one requested by browser.");
        }
    }
}
