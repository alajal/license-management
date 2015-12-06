package ee.cyber.licensing.api;

import ee.cyber.licensing.dao.LicenseRepository;
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

    @Inject
    private LicenseRepository licenseRepository;

    @GET
    @Produces("application/json")
    public List<Release> getReleasesByProductId(@PathParam("id")Integer id) throws Exception {
        return releaseRepository.findByProductId(id);
    }

    @GET
    @Path("/bylicense/{licenseId}")
    @Produces("application/json")
    public List<Release> getReleasesByLicenseId(@PathParam("licenseId") Integer licenseId) throws Exception {
        Product product = licenseRepository.findById(licenseId).getProduct();
        return getReleasesByProductId(product.getId());
    }

    @PUT
    public Release editRelease(Release release) throws Exception {
        return releaseRepository.editRelease(release);
    }

    @POST
    public boolean saveRelease(Product product) throws Exception {
        for(int i = 0; i < product.getReleases().size(); i++){
            if(product.getReleases().get(i).getId() == null){
                releaseRepository.saveRelease(product.getId(), product.getReleases().get(i));
            }

        }
        return true;
    }

    @DELETE
    @Path("/{releaseId}")
    public boolean deleteReleaseById(@PathParam("releaseId") int releaseId) throws Exception {
        return releaseRepository.deleteRelease(releaseRepository.getReleaseById(releaseId));
    }
}
