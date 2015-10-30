package ee.cyber.licensing.api;

import ee.cyber.licensing.dao.LicenseRepository;
import ee.cyber.licensing.entity.License;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;
import java.util.Objects;

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
    public License getLicenseById(@PathParam("id") Integer id) throws Exception {
        //TODO return Response.ok(License objekt)
        return licenseRepository.findById(id);
    }

    @POST
    public License saveLicense(License license) throws Exception {
        return licenseRepository.save(license);
    }

    @Path("/{id}")
    @PUT
    public License editLicense(@PathParam("id")Integer id, License license) throws Exception{
        if(Objects.equals(license.getId(), id)){
            return licenseRepository.update(license);
        } else {
            throw new Exception("The license that needs update is not the one requested by client.");
        }
    }

}
