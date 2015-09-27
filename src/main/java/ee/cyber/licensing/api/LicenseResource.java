package ee.cyber.licensing.api;

import ee.cyber.licensing.entity.License;
import ee.cyber.licensing.dao.LicenseRepository;
import ee.cyber.licensing.entity.Product;

import javax.inject.Inject;
import javax.ws.rs.*;
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

    @Path("/{id}")
    @GET
    @Produces("application/json")
    public License getLicenseById(@PathParam("id") Integer id) throws Exception{
        //TODO Response.ok(License objekt)
        return licenseRepository.findById(id);
    }

    @POST
    public License saveLicense(License license) throws Exception {
        return licenseRepository.save(license);
    }

}
