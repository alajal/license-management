package ee.cyber.licensing.api;

import ee.cyber.licensing.dao.LicenseRepository;
import ee.cyber.licensing.entity.License;
import ee.cyber.licensing.entity.StateHelper;
import ee.cyber.licensing.entity.LicenseType;
import ee.cyber.licensing.entity.SendMailTLS;
import ee.cyber.licensing.entity.Release;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.Date;
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

    @Path("/expiring")
    @GET
    @Produces("application/json")
    public List<License> getExpiringLicenses() throws Exception {
        return licenseRepository.findExpiringLicenses();
    }

    @POST
    public License saveLicense(License license) throws Exception {
        return licenseRepository.save(license);
    }

    @Path("/licensetype")
    @POST
    public LicenseType saveLicenseType(LicenseType type) throws Exception {
        return licenseRepository.saveType(type);
    }

    @Path("/type")
    @GET
    @Produces("application/json")
    public List<LicenseType> getLicenseTypes() throws Exception{
        return licenseRepository.findLicenseTypes();
    }

    @Path("/{id}")
    @PUT
    public License editLicense(@PathParam("id") Integer id, License license) throws Exception {
        if (Objects.equals(id, license.getId())) {
            return licenseRepository.updateLicense(license);
        } else {
            throw new Exception("The license that needs update is not the one requested by client. Id: " + id);
        }
    }

    @PUT //this method is reserved for delivery notification - it sets Date when the delivery date was sent
    @Path("bylicense/{licenseId}")
    public License updateRelease(@PathParam("licenseId") Integer licenseId, Release release) throws Exception{
        License license = licenseRepository.findById(licenseId);
        java.util.Date todaysDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(todaysDate.getTime());
        license.setLatestDeliveryDate(sqlDate);
        license.setRelease(release);
        return licenseRepository.updateLicense(license);
    }

    @Path("/search/{keyword}")
    @PUT
    public List<License> getLicensesSearch(@PathParam("keyword") String keyword, StateHelper sh) throws Exception {
        return licenseRepository.findByKeyword(keyword, sh);
    }
}
