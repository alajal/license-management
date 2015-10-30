import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import ee.cyber.licensing.entity.Customer;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;

public class CustomerResourceTest extends JerseyTest {

    ObjConf component;

    @Override
    protected Application configure() {
        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.packages("ee.cyber.licensing");
        component = new ObjConf();
        resourceConfig.register(component);
        return resourceConfig;
    }

/*    @Test
    public void customerAddedTest() throws SQLException {
        Customer testCustomer = new Customer(2, "Test University", "something area", "123 Test aadress", "www.test.com", "1TEST123", "+372 55TEST", "E10TEST", "+372 55TEST", "Test Science");
        List<Customer> before = target("customers").request("application/json").get(new GenericType<List<Customer>>(){});
        Assert.assertFalse(before.contains(testCustomer));

        Entity<Customer> customerEntity = Entity.entity(testCustomer, "application/json");
        target("customers").request("application/json").post(customerEntity);

        List<Customer> after = target("customers").request("application/json").get(new GenericType<List<Customer>>(){});
        Assert.assertTrue(after.contains(testCustomer));
    }

    @Test
    public void customerEditTest() throws SQLException {
        Customer customer = new Customer();
        customer.setOrganizationName("Test University");
        customer.setAddress("123 Test");
        customer.setWebpage("www.test.com");
        customer.setRegistrationCode("1TEST");

        Entity<Customer> customerEntity = Entity.entity(customer, "application/json");
        Customer lo = target("customers").request("application/json").post(customerEntity).readEntity(Customer.class);
        Assert.assertFalse(lo.getRegistrationCode().contains("2test"));

        lo.setRegistrationCode("2test");
        Entity<Customer> customerEntity1 = Entity.entity(lo, "application/json");
        Response response = target("customers/" + lo.getId()).request("application/json").put(customerEntity1);
        Customer responseCustomer = response.readEntity(Customer.class);
        System.out.println(response);
        System.out.println(responseCustomer);
        Assert.assertFalse(responseCustomer.getRegistrationCode().contains("1TEST"));
        Assert.assertTrue(responseCustomer.getRegistrationCode().contains("2test"));

    }*/
}
