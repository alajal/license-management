package ee.cyber.licensing.api;

import ee.cyber.licensing.dao.CustomerRepository;
import ee.cyber.licensing.entity.Applicant;
import ee.cyber.licensing.entity.Contact;
import ee.cyber.licensing.entity.Customer;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Path("customers")
public class CustomerResource {

    @Inject
    private CustomerRepository customerRepository;

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

    @Path("/search/{keyword}")
    @GET
    @Produces("application/json")
    public List<Customer> getCustomersSearch(@PathParam("keyword") String keyword) throws Exception {
      return customerRepository.findByKeyword(keyword);
    }

    @POST
    public Customer saveCustomer(Applicant applicant) throws Exception {
        //Customer == Organization == Applicant
        Contact contact = new Contact(applicant.getContactFirstName(), applicant.getContactLastName(), applicant.getEmail(), applicant.getSkype(), applicant.getPhone());
        Customer customer = new Customer(applicant.getOrganizationName(), applicant.getApplicationArea(), Arrays.asList(contact));
        return customerRepository.save(customer);
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
