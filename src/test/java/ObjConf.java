import ee.cyber.licensing.api.RestApp;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class ObjConf extends RestApp.InjectableObjectConfig {

    @Override
    protected org.apache.tomcat.jdbc.pool.DataSource createAndInitDatasource() throws SQLException, IOException {
        PoolProperties poolProperties = new PoolProperties();
        poolProperties.setUrl("jdbc:h2:mem:testdb");
        poolProperties.setDriverClassName("org.h2.Driver");
        DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource(poolProperties);
        try (Connection dbConnection = dataSource.getConnection()) {
            executeScriptFromClasspath(dbConnection, "dbSchema.sql");
            executeScriptFromClasspath(dbConnection, "testSampleData.sql");
        }
        return dataSource;
    }


}

