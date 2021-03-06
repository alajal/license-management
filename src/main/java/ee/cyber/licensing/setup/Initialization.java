package ee.cyber.licensing.setup;

/* Class that sets h2 database */

import ee.cyber.licensing.api.TransactionInterceptionService;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.apache.xpath.operations.Bool;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.InterceptionService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.glassfish.jersey.server.monitoring.ApplicationEvent;
import org.glassfish.jersey.server.monitoring.ApplicationEventListener;
import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.monitoring.RequestEventListener;
import org.h2.tools.RunScript;

import javax.inject.Singleton;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class Initialization extends AbstractBinder implements ApplicationEventListener {

    private DataSource dataSource;

    private Boolean ifUseSampleData;

    @Override
    protected void configure() {
        try {
            dataSource = createAndInitDatasource();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        bindFactory(getConnectionFactory()).to(Connection.class).in(RequestScoped.class).proxy(true);
        bind(TransactionInterceptionService.class).to(InterceptionService.class).in(Singleton.class);
    }

    protected org.apache.tomcat.jdbc.pool.DataSource createAndInitDatasource() throws SQLException, IOException {
        org.apache.tomcat.jdbc.pool.DataSource myDataSource = getPlaceToSaveData();
        try (Connection dbConnection = myDataSource.getConnection()) {
            executeScriptFromClasspath(dbConnection, "dbSchema.sql");
            if (ifUseSampleData) {
                executeScriptFromClasspath(dbConnection, "sampleData.sql");
            }
        }
        return myDataSource;
    }

    private org.apache.tomcat.jdbc.pool.DataSource getPlaceToSaveData() throws IOException {
        String databaseLocation = getDatabaseLocation();
        String url;
        if (databaseLocation.equals("")) {
            Path path = Paths.get(System.getProperty("java.io.tmpdir"), "h2-licence-db");
            //Windows users have "\", linux users have "/" but h2-database needs "/"
            String replace = path.toString().replace("\\", "/");
            url = "jdbc:h2:" + replace;
            ifUseSampleData = true;
        } else {
            Path path2 = Paths.get(databaseLocation, "h2-license-db");
            url = "jdbc:h2:" + path2;
            ifUseSampleData = false;
        }
        System.out.println("DATABASE LOCATION IN: " + url);
        PoolProperties pp = new PoolProperties();
        pp.setUrl(url);
        pp.setDriverClassName("org.h2.Driver");
        return new org.apache.tomcat.jdbc.pool.DataSource(pp);
    }

    private String getDatabaseLocation() throws IOException {
        InputStream input = RestApp.class.getClassLoader().getResourceAsStream("config.properties");
        Properties properties = new Properties();
        properties.load(input);
        input.close();
        return properties.getProperty("databaseLocation");
    }

    protected void executeScriptFromClasspath(Connection conn, String fileName) throws SQLException, IOException {
        try (InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
            RunScript.execute(conn, new InputStreamReader(resourceAsStream));
        }
    }

    private Factory<Connection> getConnectionFactory() {

        return new Factory<Connection>() {
            @Override
            @RequestScoped
            public Connection provide() {
                try {
                    return dataSource.getConnection();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void dispose(Connection instance) {
                try {
                    instance.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    @Override
    public void onEvent(ApplicationEvent event) {
        if (event.getType() == ApplicationEvent.Type.DESTROY_FINISHED) {
            dataSource.close();
        }
    }

    @Override
    public RequestEventListener onRequest(RequestEvent requestEvent) {
        return null;
    }
}
