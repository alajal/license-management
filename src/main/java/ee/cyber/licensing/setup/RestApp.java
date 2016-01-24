package ee.cyber.licensing.setup;

import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("rest")
public class RestApp extends ResourceConfig {

    public RestApp() {
        packages("ee.cyber.licensing");
        register(new Initialization());
        register(StatusChecker.class);
        register(new RepositoryAndServiceBinder());
    }

}
