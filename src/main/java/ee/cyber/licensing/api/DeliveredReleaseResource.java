package ee.cyber.licensing.api;

import ee.cyber.licensing.dao.DeliveredReleaseRepository;
import ee.cyber.licensing.dao.LicenseRepository;
import ee.cyber.licensing.entity.DeliveredRelease;
import ee.cyber.licensing.entity.Release;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Path("deliveredReleases")
public class DeliveredReleaseResource {

    @Inject
    private DeliveredReleaseRepository deliveredReleaseRepository;

    @Inject
    private LicenseRepository licenseRepository;

    @GET
    @Path("bylicense/{id}")
    @Produces("application/json")
    public List<DeliveredRelease> getDeliveredReleasesByLicense(@PathParam("id") int id) throws Exception {
        return deliveredReleaseRepository.getAllDeliveredReleases(id);
    }

    @POST
    @Path("bylicense/{id}")
    public DeliveredRelease addReleaseToDeliveredReleases(@PathParam("id") int licenseId, Release release) throws Exception {
        System.out.println("Vaata siia:");
        System.out.println(release.getUser());
        DeliveredRelease dr = new DeliveredRelease(licenseRepository.findById(licenseId), release, Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()), release.getUser());
        return deliveredReleaseRepository.save(dr);
    }


}
