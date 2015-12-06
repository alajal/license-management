import ee.cyber.licensing.entity.License;
import ee.cyber.licensing.entity.Customer;
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
    public void runnableMethod() throws SQLException{
    }
  /*  @Test
    public void licenseAddedTest() throws SQLException {
        Response beforeResp = target("licenses").request("application/json").get();
        String beforeRespInString = beforeResp.readEntity(String.class);
        Assert.assertFalse(beforeRespInString.contains("MindShare"));
        Assert.assertFalse(beforeRespInString.contains("Example University"));
        Assert.assertFalse(beforeRespInString.contains("test@mail.com"));

        License license = new License();
        license.setProduct(new Product(1, "i-Voting", "11.2"));
        license.setCustomer(new Customer(1, "Example University", "example area", "123 Fake Street", "www.example.com", "1A2B", "+372 555555", "E100101", "+372 555555", "Example Science"));
        Entity<License> licenseEntity = Entity.entity(license, "application/json");
        target("licenses").request("application/json").post(licenseEntity);

        Response response = target("licenses").request("application/json").get();
        String responseInString = response.readEntity(String.class);
        Assert.assertTrue(responseInString.contains("test@mail.ee"));
        Assert.assertTrue(responseInString.contains("i-Voting"));
        Assert.assertTrue(responseInString.contains("Example University"));
    }*/

   /* @Test
    public void licenseEditTest() throws SQLException {
        License license = new License();
        license.setCustomer(new Customer(1, "Example University", "example area", "123 Fake Street", "www.example.com", "1A2B", "+372 555555", "E100101", "+372 555555", "Example Science"));
        license.setProduct(new Product(1, "MindShare", "11.2"));

        Entity<License> licenseEntity = Entity.entity(license, "application/json");
        License ls = target("licenses").request("application/json").post(licenseEntity).readEntity(License.class);
        Assert.assertFalse(ls.getEmail().contains("cactus@mail.ee"));

        ls.setEmail("cactus@mail.ee");
        Entity<License> licenseEntityR = Entity.entity(ls, "application/json");
        Response response = target("licenses/" + ls.getId()).request("application/json").put(licenseEntityR);
        License s = response.readEntity(License.class);
        Assert.assertFalse(s.getEmail().contains("test@mail.ee"));
        Assert.assertTrue(s.getEmail().contains("cactus@mail.ee"));

    }*/
}
