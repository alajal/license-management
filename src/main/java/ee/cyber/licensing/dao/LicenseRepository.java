package ee.cyber.licensing.dao;


import ee.cyber.licensing.entity.License;
import ee.cyber.licensing.entity.Product;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LicenseRepository {

    @Inject
    private DataSource ds;

    public License save(License license) throws SQLException {
        try (Connection conn = ds.getDBConnection()) {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO License (productName, name, " +
                    "organization, email, skype, phone, applicationArea) VALUES (?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, license.getProductName());
            statement.setString(2, license.getName());
            statement.setString(3, license.getOrganization());
            statement.setString(4, license.getEmail());
            statement.setString(5, license.getSkype());
            statement.setString(6, license.getPhone());
            statement.setString(7, license.getApplicationArea());
            statement.execute();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    license.setId(generatedKeys.getInt(1));
                }
            }
        }
        return license;
    }

    public License findById(int id) throws SQLException {
        try (Connection connection = ds.getDBConnection()){
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM License WHERE id = ?;")){
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()){
                    if (!resultSet.next()){
                        throw new SQLException("Ei leitud ühtegi rida id-ga " + id);
                    }
                    return getLicense(resultSet);
                }
            }
        }
    }

    public List<License> findAll() throws SQLException {
        try (Connection conn = ds.getDBConnection()){
            try(PreparedStatement statement = conn.prepareStatement("SELECT * FROM License")){
                try (ResultSet resultSet = statement.executeQuery()){
                    List<License> licenses = new ArrayList<>();
                    while (resultSet.next()){
                        licenses.add(getLicense(resultSet));
                    }
                    return licenses;
                }
            }
        }
    }

    private License getLicense(ResultSet resultSet) throws SQLException {
        return new License(
                resultSet.getInt("id"),
                resultSet.getString("productName"),
                resultSet.getString("name"),
                resultSet.getString("organization"),
                resultSet.getString("email"),
                resultSet.getString("skype"),
                resultSet.getString("phone"),
                resultSet.getString("applicationArea"));
    }

}
