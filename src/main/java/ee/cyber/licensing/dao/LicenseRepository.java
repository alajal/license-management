package ee.cyber.licensing.dao;


import ee.cyber.licensing.license.License;

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

    public License save(License license) throws SQLException {
        try (Connection conn = ds.getDBConnection()) {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO License (product, name, " +
                    "organization, email, skype, phone, applicationArea) VALUES (?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, license.getProduct());
            statement.setString(2, license.getName());
            statement.setString(3, license.getOrganization());
            statement.setString(4, license.getEmail());
            statement.setString(5, license.getSkype());
            statement.setString(6, license.getPhone());
            statement.setString(7, license.getApplicationArea());
            statement.execute();
        }
        //TODO lisa litsentsile id;

        //TODO tagastada sama litsents, aga nüüd juba id-ga
        return license;
    }

    public List<License> findAll() throws SQLException {
        try (Connection conn = ds.getDBConnection()){
            try(PreparedStatement statement = conn.prepareStatement("SELECT * FROM License")){
                try (ResultSet resultSet = statement.executeQuery()){
                    List<License> licenses = new ArrayList<>();
                    while (resultSet.next()){
                        licenses.add(new License(
                                resultSet.getString("product"),
                                resultSet.getString("name"),
                                resultSet.getString("organization"),
                                resultSet.getString("email"),
                                resultSet.getString("skype"),
                                resultSet.getString("phone"),
                                resultSet.getString("applicationArea")));
                    }
                    return licenses;
                }
            }
        }
    }

}
