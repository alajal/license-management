package ee.cyber.licensing.dao;

import java.sql.*;

public class DataSource {
    private final String connectionString;
    private final String dbUser;
    private final String dbPassword;

    public DataSource(String db_connection, String db_user, String db_password) {
        connectionString = db_connection;
        dbUser = db_user;
        dbPassword = db_password;
    }

    public Connection getDBConnection() throws SQLException {
        return DriverManager.getConnection(connectionString, dbUser, dbPassword);
    }

}
