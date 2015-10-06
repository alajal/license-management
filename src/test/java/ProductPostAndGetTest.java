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

public class ProductPostAndGetTest extends JerseyTest {

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
        component.onEvent(new ApplicationEventImpl(
                ApplicationEvent.Type.DESTROY_FINISHED,
                null, null, null, null, null));
    }

    @Test
    public void licenseAddedTest() throws SQLException {

        Response before = target("products").request("application/json").get();
        String beforeInString = before.readEntity(String.class);
        Assert.assertFalse(beforeInString.contains("6.6.6"));

        Product testProduct = new Product(1, "MindShare", "6.6.6");
            Entity<Product> productEntity = Entity.entity(testProduct, "application/json");
        target("products").request("application/json").post(productEntity);

        Response response = target("products").request("application/json").get();
        String responseInString = response.readEntity(String.class);
        Assert.assertTrue(responseInString.contains("6.6.6"));
        Assert.assertTrue(responseInString.contains("MindShare"));
    }
}
