import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;

import ee.cyber.licensing.entity.LicenseOwner;

public class LicenseOwnerResourceTest extends JerseyTest {

    ObjConf component;

    @Override
    protected Application configure() {
        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.packages("ee.cyber.licensing");
        component = new ObjConf();
        resourceConfig.register(component);
        return resourceConfig;
    }

    @Test
    public void licenseOwnerAddedTest() throws SQLException {
        LicenseOwner testLicenseOwner = new LicenseOwner(2, "Test University", "123 Test", "www.test.com", "1TEST", "+372 55TEST", "E10TEST", "+372 55TEST", "Test Science");
        List<LicenseOwner> before = target("licenseOwners").request("application/json").get(new GenericType<List<LicenseOwner>>(){});
        Assert.assertFalse(before.contains(testLicenseOwner));

        Entity<LicenseOwner> licenseOwnerEntity = Entity.entity(testLicenseOwner, "application/json");
        target("licenseOwners").request("application/json").post(licenseOwnerEntity);

        List<LicenseOwner> after = target("licenseOwners").request("application/json").get(new GenericType<List<LicenseOwner>>(){});
        Assert.assertTrue(after.contains(testLicenseOwner));
    }

    @Test
    public void licenseOwnerEditTest() throws SQLException {
        LicenseOwner licenseOwner = new LicenseOwner();
        licenseOwner.setname("Test University");
        licenseOwner.setAddress("123 Test");
        licenseOwner.setWebpage("www.test.com");
        licenseOwner.setRegistrationCode("1TEST");

        Entity<LicenseOwner> licenseOwnerEntity = Entity.entity(licenseOwner, "application/json");
        LicenseOwner lo = target("licenseOwners").request("application/json").post(licenseOwnerEntity).readEntity(LicenseOwner.class);
        Assert.assertFalse(lo.getRegistrationCode().contains("2test"));

        lo.setRegistrationCode("2test");
        Entity<LicenseOwner> licenseOwnerEntityR = Entity.entity(lo, "application/json");
        Response response = target("licenseOwners/" + lo.getId()).request("application/json").put(licenseOwnerEntityR);
        LicenseOwner responseLicenseOwner = response.readEntity(LicenseOwner.class);
        System.out.println(response);
        System.out.println(responseLicenseOwner);
        Assert.assertFalse(responseLicenseOwner.getRegistrationCode().contains("1TEST"));
        Assert.assertTrue(responseLicenseOwner.getRegistrationCode().contains("2test"));

    }
}
