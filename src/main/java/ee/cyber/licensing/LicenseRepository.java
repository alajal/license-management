package ee.cyber.licensing;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LicenseRepository {

    private final DataSource ds;

    public LicenseRepository(DataSource ds) {
        this.ds = ds;
    }

    public void save(License license) throws SQLException {
        try (Connection conn = ds.getDBConnection()) {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO License (product, name, " +
                    "organization, email, skype, phone, applicationArea) VALUES (?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, license.product);
            statement.setString(2, license.name);
            statement.setString(3, license.organization);
            statement.setString(4, license.email);
            statement.setString(5, license.skype);
            statement.setString(6, license.phone);
            statement.setString(7, license.applicationArea);
            statement.execute();
        }
    }

    public List<License> findAll() throws SQLException {
        try (Connection conn = ds.getDBConnection()){
            try(PreparedStatement statement = conn.prepareStatement("SELECT * FROM License")){
                try (ResultSet resultSet = statement.executeQuery()){
                    List<License> licenses = new ArrayList<>();
                    while (resultSet.next()){
                        License license = new License(resultSet.getString("product"), resultSet.getString("name"),resultSet.getString("organization"), resultSet.getString("email"), resultSet.getString("skype"), resultSet.getString("phone"), resultSet.getString("applicationArea"));
                        licenses.add(license);
                    }
                    return licenses;
                }
            }
        }
    }

}
