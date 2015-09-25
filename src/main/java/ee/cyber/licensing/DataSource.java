package ee.cyber.licensing;

import java.nio.file.Files;
import java.sql.*;

public class DataSource {
    private final String  DB_CONNECTION;
    private final String DB_USER;
    private final String DB_PASSWORD;

    public DataSource(String db_connection, String db_user, String db_password) {
        DB_CONNECTION = db_connection;
        DB_USER = db_user;
        DB_PASSWORD = db_password;
    }

    public Connection getDBConnection() throws SQLException {
        return DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
    }

}
