package ee.cyber.licensing.dao;


import ee.cyber.licensing.entity.License;
import ee.cyber.licensing.entity.LicenseOwner;
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

    @Inject
    private LicenseOwnerRepository licenseOwnerRepository;

    public License save(License license) throws SQLException {
        try (Connection conn = ds.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO License (productId, name, " +
                    "licenseOwnerId, email, skype, phone, applicationArea) VALUES (?, ?, ?, ?, ?, ?, ?)");
            statement.setInt(1, license.getProduct().getId());
            statement.setString(2, license.getName());
            statement.setInt(3, license.getLicenseOwner().getId());
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
                    return getLicense(resultSet, productRepository.getProductById(resultSet.getInt("productId")),
                            licenseOwnerRepository.getLicenseOwnerById(resultSet.getInt("licenseOwnerId")));
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
                        int licenseOwnerId = resultSet.getInt("licenseOwnerId");
                        LicenseOwner licenseOwnerById = licenseOwnerRepository.getLicenseOwnerById(licenseOwnerId);

                        License license = getLicense(resultSet, productById, licenseOwnerById);
                        licenses.add(license);
                    }
                    return licenses;
                }
            }
        }
    }

    private License getLicense(ResultSet resultSet, Product product, LicenseOwner licenseOwner) throws SQLException {
        return new License(
                resultSet.getInt("id"),
                product,
                resultSet.getString("name"),
                licenseOwner,
                resultSet.getString("email"),
                resultSet.getString("skype"),
                resultSet.getString("phone"),
                resultSet.getString("applicationArea"));
    }

    public License edit(License license) throws SQLException {
        try (Connection conn = ds.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("UPDATE License SET name=?, " +
                    "email=?, skype=?, phone=?, applicationArea=? WHERE id=?");
            statement.setString(1, license.getName());
            statement.setString(2, license.getEmail());
            statement.setString(3, license.getSkype());
            statement.setString(4, license.getPhone());
            statement.setString(5, license.getApplicationArea());
            statement.setInt(6, license.getId());
            int rowCount = statement.executeUpdate();
            if (rowCount == 0) {
                throw new RuntimeException("No license with id: " + license.getId());
            }
        }
        return license;
    }
}
