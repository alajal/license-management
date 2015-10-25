package ee.cyber.licensing.api;
import ee.cyber.licensing.dao.AuthorisedUserRepository;
import ee.cyber.licensing.entity.AuthorisedUser;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MultivaluedMap;
import java.util.List;

@Path("authorisedUser")
public class AuthorisedUserResource {

    @Inject
    private AuthorisedUserRepository authorisedUserRepository;

    @GET
    @Path("bylicense/{id}")
    @Produces("application/json")
    public List<AuthorisedUser> getAuthorisedUsers(@PathParam("id") int id) throws Exception {
        return authorisedUserRepository.findAll(id);
    }

    @POST
    @Path ("bylicense/{id}")
    public AuthorisedUser saveAuthorisedUser(AuthorisedUser au, @PathParam("id") int licenseId) throws Exception {
        return authorisedUserRepository.save(au, licenseId);
    }

    @DELETE
    @Path("bylicense/{licenseId}/{userId}")
    //@Consumes("application/json")
    @Consumes("text/plain")
    public AuthorisedUser deleteUserByEmail(@PathParam("licenseId") int licenseId, @PathParam("userId") int userId) throws Exception {
        return authorisedUserRepository.deleteAuthorisedUser(licenseId, authorisedUserRepository.findById(licenseId, userId));

    }

    @PUT
    @Path("bylicense/{licenseId}")
    public AuthorisedUser editAuthorisedUser(@PathParam("licenseId") int licenseId, AuthorisedUser au) throws Exception {
        return authorisedUserRepository.editAuthorisedUser(licenseId, au);
    }

}
