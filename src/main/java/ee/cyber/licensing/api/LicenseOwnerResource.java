package ee.cyber.licensing.api;

import ee.cyber.licensing.dao.LicenseOwnerRepository;
import ee.cyber.licensing.entity.LicenseOwner;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

@Path("licenseOwners")
public class LicenseOwnerResource {

    @Inject
    private LicenseOwnerRepository licenseOwnerRepository;

    @GET
    @Produces("application/json")
    public List<LicenseOwner> getLicenseOwners() throws Exception {
        return licenseOwnerRepository.findAll();
    }

    @Path("/{id}")
    @GET
    @Produces("application/json")
    public LicenseOwner getLicenseOwnerById(@PathParam("id") Integer id) throws Exception {
        //TODO Response.ok(LicenseOwner objekt)
        return licenseOwnerRepository.getLicenseOwnerById(id);
    }

    @POST
    public LicenseOwner saveLicenseOwner(LicenseOwner licenseOwner) throws Exception {
        return licenseOwnerRepository.save(licenseOwner);
    }

}
