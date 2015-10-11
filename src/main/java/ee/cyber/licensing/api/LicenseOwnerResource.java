package ee.cyber.licensing.api;

import ee.cyber.licensing.dao.LicenseOwnerRepository;
import ee.cyber.licensing.entity.License;
import ee.cyber.licensing.entity.LicenseOwner;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;
import java.util.Objects;

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

    @Path("/{id}")
    @PUT
    public LicenseOwner editLicenseOwner(@PathParam("id")Integer id, LicenseOwner licenseOwner) throws Exception{
        if(Objects.equals(licenseOwner.getId(), id)){
            return licenseOwnerRepository.edit(licenseOwner);
        } else {
            throw new Exception("The licenseOwner that needs update is not the one requested by browser.");
        }
    }
}
