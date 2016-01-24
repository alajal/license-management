import ee.cyber.licensing.dao.LicenseRepository;
import ee.cyber.licensing.entity.Contact;
import ee.cyber.licensing.entity.License;
import ee.cyber.licensing.entity.Customer;
import ee.cyber.licensing.entity.LicenseType;
import ee.cyber.licensing.entity.Product;
import ee.cyber.licensing.entity.Release;
import ee.cyber.licensing.entity.State;
import ee.cyber.licensing.setup.RepositoryAndServiceBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import javax.inject.Inject;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LicenseResourceTest extends JerseyTest {

    @Inject
    LicenseRepository licenseRepository;

    @Override
    protected Application configure() {
        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.packages("ee.cyber.licensing");
        resourceConfig.register(new RepositoryAndServiceBinder());
        resourceConfig.register(new ObjConf());
        return resourceConfig;
    }

    @Ignore
    @Test
    public void licenseAddedTest() throws SQLException {
        Response beforeResp = target("licenses").request("application/json").get();
        String beforeRespInString = beforeResp.readEntity(String.class);
        Assert.assertFalse(beforeRespInString.contains("i-Product1"));
        Assert.assertFalse(beforeRespInString.contains("University1"));
        Assert.assertFalse(beforeRespInString.contains("testmail@hotmail.com"));

        License license = new License();
        List<Release> releases = new ArrayList<>();
        releases.add(new Release(1, "1.0", new Date(), "admin"));
        license.setProduct(new Product(1, "i-Product1", releases));
        List<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact(1, "Myfirst", "Mylast", "testmail@hotmail.com", "iioo.66", "545435435"));
        license.setCustomer(new Customer(1, "University1", "example area", "123 Fake Street", "www.example.com", "1A2B",
                "+372 555555", "E100101", "+372 555555", "Example Science", contacts));
        license.setState(State.NEGOTIATED);
        license.setContractNumber("AA-LL-99");
        license.setApplicationSubmitDate(new Date());
        license.setLatestDeliveryDate(new Date());
        license.setType(new LicenseType(2, "2 aasta litsents", 2, 200));

        Entity<License> licenseEntity = Entity.entity(license, "application/json");
        target("licenses").request("application/json").post(licenseEntity);

        Response response = target("licenses").request("application/json").get();
        String responseInString = response.readEntity(String.class);
        Assert.assertTrue(responseInString.contains("testmail@hotmail.com"));
        Assert.assertTrue(responseInString.contains("i-Product1"));
        Assert.assertTrue(responseInString.contains("University1"));
    }

    @Ignore
    @Test
    public void licenseEditTest() throws SQLException {
        License license = new License();
        List<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact(3, "Jane", "Doe", "anu654@hotmail.com", "iioo.66", "545435435"));
        license.setCustomer(new Customer(1, "University1", "example area", "123 Fake Street", "www.example.com", "1A2B",
                "+372 555555", "E100101", "+372 555555", "Example Science", contacts));
        List<Release> releases = new ArrayList<>();
        releases.add(new Release(0, "1.0", new Date(), "admin"));
        license.setProduct(new Product(0, "i-Product2", releases));
        license.setState(State.WAITING_FOR_SIGNATURE);
        license.setContractNumber("PP-LL-78");
        license.setApplicationSubmitDate(new Date());
        license.setLatestDeliveryDate(new Date());
        license.setType(new LicenseType(0, "2 aasta litsents", 2, 200));

        Entity<License> licenseEntity = Entity.entity(license, "application/json");
        License ls = target("licenses").request("application/json").post(licenseEntity).readEntity(License.class);
        Assert.assertFalse(ls.getCustomer().getContacts().get(1).getEmail().contains("cactus@mail.ee"));

        ls.getCustomer().getContacts().get(1).setEmail("cactus@mail.ee");
        Entity<License> licenseEntityR = Entity.entity(ls, "application/json");
        Response response = target("licenses/" + ls.getId()).request("application/json").put(licenseEntityR);
        License s = response.readEntity(License.class);
        Assert.assertFalse(s.getCustomer().getContacts().get(0).getEmail().contains("test@mail.ee"));
        Assert.assertTrue(s.getCustomer().getContacts().get(0).getEmail().contains("cactus@mail.ee"));

    }
}
