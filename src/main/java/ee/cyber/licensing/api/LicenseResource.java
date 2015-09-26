package ee.cyber.licensing.api;

import ee.cyber.licensing.license.License;
import ee.cyber.licensing.dao.LicenseRepository;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("licenses")
public class LicenseResource {

    @Inject
    private LicenseRepository licenseRepository;

    @GET
    @Produces("application/json")
    public List<License> getLicenses() throws Exception {
        return licenseRepository.findAll();
    }

    @POST
    public License saveLicense(License license) throws Exception {
        return licenseRepository.save(license);
    }

}
