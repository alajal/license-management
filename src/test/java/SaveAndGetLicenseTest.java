import ee.cyber.licensing.entity.License;
import ee.cyber.licensing.entity.Product;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.internal.monitoring.ApplicationEventImpl;
import org.glassfish.jersey.server.monitoring.ApplicationEvent;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

public class SaveAndGetLicenseTest extends JerseyTest {

    ObjConf component;

    @Override
    protected Application configure() {
        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.packages("ee.cyber.licensing");
        component = new ObjConf();
        resourceConfig.register(component);
        return resourceConfig;
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        //Testkeskonnas in-memory db ei kutsu stop() meetodit, mis paneks kinni datasource'i.
        //Siin öeldakse käsitsi, et kui Jersey jõuab testide jookustamisega lõpule, anna
        //edasi event nimega "DESTROY_FINISHED", mille saamisel pannakse datasource kinni.
        component.onEvent(new ApplicationEventImpl(
                ApplicationEvent.Type.DESTROY_FINISHED,
                null, null, null, null, null));
    }

    @Test
    public void licenseAddedTest() throws SQLException {
        Response beforeResp = target("licenses").request("application/json").get();
        String beforeRespInString = beforeResp.readEntity(String.class);
        Assert.assertFalse(beforeRespInString.contains("MindShare"));
        Assert.assertFalse(beforeRespInString.contains("anu@mail.com"));

        License license = new License();
        license.setProduct(new Product(1, "MindShare", "11.2"));
        license.setName("Anu");
        license.setOrganization("Tuulepiu");
        license.setEmail("anu@mail.ee");
        Entity<License> licenseEntity = Entity.entity(license, "application/json");
        target("licenses").request("application/json").post(licenseEntity);

        Response response = target("licenses").request("application/json").get();
        String responseInString = response.readEntity(String.class);
        Assert.assertTrue(responseInString.contains("anu@mail.ee"));
        Assert.assertTrue(responseInString.contains("MindShare"));
    }
}
