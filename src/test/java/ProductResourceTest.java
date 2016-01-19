import ee.cyber.licensing.entity.Product;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ProductResourceTest extends JerseyTest {

    ObjConf component;

    @Test
    public void testName() throws Exception {
        System.out.println(Instant.now().atOffset(ZoneOffset.UTC).plus(3, ChronoUnit.YEARS).toInstant());
    }

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
    //@Test
    /*public void productAddedTest() throws SQLException {

        Product testProduct = new Product(1,"MindShare", "6.6.6");
        List<Product> before = target("products").request("application/json").get(new GenericType<List<Product>>(){});
        Assert.assertFalse(before.contains(testProduct));

        Entity<Product> productEntity = Entity.entity(testProduct, "application/json");
        target("products").request("application/json").post(productEntity);

        List<Product> after = target("products").request("application/json").get(new GenericType<List<Product>>(){});
        Assert.assertTrue(after.contains(testProduct));

    }*/
}
