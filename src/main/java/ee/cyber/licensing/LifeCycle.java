package ee.cyber.licensing;

import org.h2.tools.RunScript;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;

@WebListener
public class LifeCycle implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //enne Servletide tekitamist
        //ühenduste lahtitegemine
        //andmebaasi seadistamine
        //emailide teavituste threadi töölepanemine

        try {
            DataSource myDataSource = getPlaceToSaveData();
            try (Connection dbConnection = myDataSource.getDBConnection()){
                executeScriptFromClasspath(dbConnection, "dbSchema.sql");
                executeScriptFromClasspath(dbConnection, "sampleData.sql");
            }
            servletContextEvent.getServletContext().setAttribute("dataSource", myDataSource);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private DataSource getPlaceToSaveData() {
        Path path = Paths.get(System.getProperty("java.io.tmpdir"), "h2-licence-db");
        //Windows users have "\", linux users have "/" but h2-database needs always "/" -> replacement
        String replace = path.toString().replace("\\", "/");
        String url = "jdbc:h2:" + replace;
        System.out.println("ANDMEBAAS ASUB: " + url);
        return new DataSource(url, "", "");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        //enne Servletide kinni minekut
        //ühendused katkestada
        //threadide lõpetamine

    }

    private void executeScriptFromClasspath(Connection conn, String fileName) throws SQLException, IOException {
        try (InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
            RunScript.execute(conn, new InputStreamReader(resourceAsStream));
        }
    }
}
