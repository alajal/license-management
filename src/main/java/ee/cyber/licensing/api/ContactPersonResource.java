package ee.cyber.licensing.api;

import ee.cyber.licensing.dao.AuthorisedUserRepository;
import ee.cyber.licensing.dao.ContactRepository;
import ee.cyber.licensing.entity.AuthorisedUser;
import ee.cyber.licensing.entity.Contact;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

@Path("contactPerson")
public class ContactPersonResource {
/*
    @Inject
    private ContactRepository contactRepository;

    @GET
    @Path("bylicense/{id}")
    @Produces("application/json")
    public List<Contact> getContactPerson(@PathParam("id") int id) throws Exception {
        return contactRepository.findAll(id);
    }

    @POST
    @Path ("bylicense/{id}")
    public Contact saveContactPerson(Contact cp, @PathParam("id") int licenseId) throws Exception {
        return contactRepository.save(cp, licenseId);
    }

    @DELETE
    @Path("bylicense/{licenseId}/{userId}")
    @Consumes("text/plain")
    public Contact deleteContactPerson(@PathParam("licenseId") int licenseId, @PathParam("contactPersonId") int contactPersonId) throws Exception {
        return contactRepository.deleteContactPerson(licenseId, contactRepository.findById(licenseId, contactPersonId));

    }

    @PUT
    @Path("bylicense/{licenseId}")
    public AuthorisedUser editAuthorisedUser(@PathParam("licenseId") int licenseId, AuthorisedUser au) throws Exception {
        return authorisedUserRepository.editAuthorisedUser(licenseId, au);
    }*/
}
