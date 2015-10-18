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
    @Path("bylicense/{licenseId}/{email}")
    //@Consumes("application/json")
    @Consumes("text/plain")
    public AuthorisedUser deleteUserByEmail(@PathParam("licenseId") int licenseId, @PathParam("email") String email) throws Exception {
        return authorisedUserRepository.deleteAuthorisedUser(licenseId, authorisedUserRepository.findByEmail(licenseId, email));

    }
}
