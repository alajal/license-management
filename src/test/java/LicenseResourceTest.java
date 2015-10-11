import ee.cyber.licensing.entity.License;
import ee.cyber.licensing.entity.Product;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

public class LicenseResourceTest extends JerseyTest {

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
    public void licenseAddedTest() throws SQLException {
        Response beforeResp = target("licenses").request("application/json").get();
        String beforeRespInString = beforeResp.readEntity(String.class);
        Assert.assertFalse(beforeRespInString.contains("MindShare"));
        Assert.assertFalse(beforeRespInString.contains("test@mail.com"));

        License license = new License();
        license.setProduct(new Product(1, "MindShare", "11.2"));
        license.setName("Test");
        license.setOrganization("Test Foundation");
        license.setEmail("test@mail.ee");
        Entity<License> licenseEntity = Entity.entity(license, "application/json");
        target("licenses").request("application/json").post(licenseEntity);

        Response response = target("licenses").request("application/json").get();
        String responseInString = response.readEntity(String.class);
        Assert.assertTrue(responseInString.contains("test@mail.ee"));
        Assert.assertTrue(responseInString.contains("MindShare"));
    }

    @Test
    public void licenseEditTest() throws SQLException {
        License license = new License();
        license.setName("Test");
        license.setOrganization("Test Foundation");
        license.setEmail("test@mail.ee");
        license.setProduct(new Product(1, "MindShare", "11.2"));

        Entity<License> licenseEntity = Entity.entity(license, "application/json");
        License ls = target("licenses").request("application/json").post(licenseEntity).readEntity(License.class);
        Assert.assertFalse(ls.getOrganization().contains("Cactus Foundation"));

        ls.setOrganization("Cactus Foundation");
        Entity<License> licenseEntityR = Entity.entity(ls, "application/json");
        Response response = target("licenses/" + ls.getId()).request("application/json").put(licenseEntityR);
        License s = response.readEntity(License.class);
        Assert.assertFalse(s.getOrganization().contains("Test Foundation"));
        Assert.assertTrue(s.getOrganization().contains("Cactus Foundation"));

    }
}
