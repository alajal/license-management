package ee.cyber.licensing.api;

import ee.cyber.licensing.dao.ReleaseRepository;
import ee.cyber.licensing.entity.AuthorisedUser;
import ee.cyber.licensing.entity.Product;
import ee.cyber.licensing.entity.Release;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

@Path("releases")
public class ReleaseResource {

    @Inject
    private ReleaseRepository releaseRepository;

    @GET
    @Produces("application/json")
    public List<Release> getReleasesByProductId(int id) throws Exception {
        return releaseRepository.findByProductId(id);
    }

    @PUT
    public Release editRelease(Release release) throws Exception {
        return releaseRepository.editRelease(release);
    }
}