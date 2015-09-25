package ee.cyber.licensing;

import org.glassfish.jersey.server.ResourceConfig;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextListener;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.awt.*;
import java.util.List;

@Path("licenses")
public class LicenseServlet {

    private LicenseRepository licenseRepository;

    public LicenseServlet(@Context ServletContext servletContext) { //keegi teine () paneb ServletContexti väärtuse (see on "inject")
        DataSource dataSource = (DataSource) servletContext.getAttribute("dataSource");
        licenseRepository = new LicenseRepository(dataSource);
    }

    @GET
    @Produces("application/json")
    public List<License> doGet() throws Exception {
        return licenseRepository.findAll();
    }

    @POST
    public void doPost(License license) throws Exception {
        licenseRepository.save(license);
    }


}
