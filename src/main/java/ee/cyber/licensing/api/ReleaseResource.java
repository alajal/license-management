package ee.cyber.licensing.api;

import ee.cyber.licensing.dao.ReleaseRepository;
import ee.cyber.licensing.entity.Release;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
}
