package ee.cyber.licensing.dao;


import ee.cyber.licensing.entity.License;
import ee.cyber.licensing.entity.Product;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LicenseRepository {

    @Inject
    private DataSource ds;

    @Inject
    private ProductRepository productRepository;

    public License save(License license) throws SQLException {
        try (Connection conn = ds.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO License (productId, name, " +
                    "organization, email, skype, phone, applicationArea) VALUES (?, ?, ?, ?, ?, ?, ?)");
            statement.setInt(1, license.getProduct().getId());
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
        try (Connection connection = ds.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM License WHERE id = ?;")) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (!resultSet.next()) {
                        throw new SQLException("Ei leitud ühtegi rida id-ga " + id);
                    }
                    return getLicense(resultSet, productRepository.getProductById(resultSet.getInt("productId")));
                }
            }
        }
    }

    public List<License> findAll() throws SQLException {
        try (Connection conn = ds.getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM License")) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    List<License> licenses = new ArrayList<>();
                    // ToDo optimeeri päringute arvu
                    while (resultSet.next()) {
                        int productId = resultSet.getInt("productId");
                        Product productById = productRepository.getProductById(productId);
                        License license = getLicense(resultSet, productById);
                        licenses.add(license);
                    }
                    return licenses;
                }
            }
        }
    }

    private License getLicense(ResultSet resultSet, Product product) throws SQLException {
        return new License(
                resultSet.getInt("id"),
                product,
                resultSet.getString("name"),
                resultSet.getString("organization"),
                resultSet.getString("email"),
                resultSet.getString("skype"),
                resultSet.getString("phone"),
                resultSet.getString("applicationArea"));
    }

    public License edit(License license) throws SQLException {
        try (Connection conn = ds.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("UPDATE License SET name=?, organization=?, " +
                    "email=?, skype=?, phone=?, applicationArea=? WHERE id=?");
            statement.setString(1, license.getName());
            statement.setString(2, license.getOrganization());
            statement.setString(3, license.getEmail());
            statement.setString(4, license.getSkype());
            statement.setString(5, license.getPhone());
            statement.setString(6, license.getApplicationArea());
            statement.setInt(7, license.getId());
            statement.execute();
        }
        return license;
    }
}
