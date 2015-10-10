package ee.cyber.licensing.api;
import ee.cyber.licensing.dao.AuthorisedUserRepository;
import ee.cyber.licensing.dao.ProductRepository;
import ee.cyber.licensing.entity.AuthorisedUser;
import ee.cyber.licensing.entity.Product;
import javax.inject.Inject;
import javax.ws.rs.*;
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
}
