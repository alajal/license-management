package ee.cyber.licensing.api;

import ee.cyber.licensing.dao.DataSource;
import ee.cyber.licensing.dao.LicenseRepository;
import ee.cyber.licensing.dao.ProductRepository;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.h2.tools.RunScript;

import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;

@ApplicationPath("rest")
public class RestApp extends ResourceConfig {

    public RestApp() {
        packages("ee.cyber.licensing");
        register(new InjectableObjectConfig());
    }

    static class InjectableObjectConfig extends AbstractBinder {
        //configute() (jersey spetsiifiline) on võrdväärne contextInitialized() meetodiga
        // RTFM https://jersey.java.net/documentation/latest/ioc.html
        @Override
        protected void configure() {
            //enne Resource'ide tekitamist
            //ühenduste lahtitegemine
            //andmebaasi seadistamine
            //emailide teavituste threadi töölepanemine

            //server->annan web applicationi(war), mille jetty leiab ülesse, seda hakatakse deployma->tekitab RestApp objekt tekitatakse jersey poolt-
            //<listenerid, servletid, nende eventid -nüüd on deploytud

            try {
                //injectitav objekt ->bind()
                //SIIN SAAB INJECTIDA JA LUUA INJECTITAVAT
                DataSource dataSource = createAndInitDatasource();
                //bind(new LicenseRepository(dataSource));    //newga ei saa hk2 injectida
                //bind(new ProductRepository(dataSource));


                bind(dataSource);
                bind(LicenseRepository.class).to(LicenseRepository.class).in(Singleton.class);
                bind(ProductRepository.class).to(ProductRepository.class).in(Singleton.class);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        private DataSource createAndInitDatasource() throws SQLException, IOException {
            DataSource myDataSource = getPlaceToSaveData();
            try (Connection dbConnection = myDataSource.getDBConnection()){
                executeScriptFromClasspath(dbConnection, "dbSchema.sql");
                executeScriptFromClasspath(dbConnection, "sampleData.sql");
            }
            return myDataSource;
        }

        private DataSource getPlaceToSaveData() {
            Path path = Paths.get(System.getProperty("java.io.tmpdir"), "h2-licence-db");
            //Windows users have "\", linux users have "/" but h2-database needs always "/" -> replacement
            String replace = path.toString().replace("\\", "/");
            String url = "jdbc:h2:" + replace;
            System.out.println("ANDMEBAAS ASUB: " + url);
            return new DataSource(url, "", "");
        }

        private void executeScriptFromClasspath(Connection conn, String fileName) throws SQLException, IOException {
            try (InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
                RunScript.execute(conn, new InputStreamReader(resourceAsStream));
            }
        }
    }
}
