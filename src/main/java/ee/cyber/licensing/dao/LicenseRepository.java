package ee.cyber.licensing.dao;


import ee.cyber.licensing.entity.Customer;
import ee.cyber.licensing.entity.License;
import ee.cyber.licensing.entity.Product;
import ee.cyber.licensing.entity.State;

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
    private CustomerRepository customerRepository;

    public License save(License license) throws SQLException {
        try (Connection conn = ds.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(
                    "INSERT INTO License (productId, customerId, contractNumber, state, predecessorLicenseId, " +
                            "validFrom, validTill, applicationSubmitDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            statement.setInt(1, license.getProduct().getId());
            statement.setInt(2, license.getCustomer().getId());
            statement.setString(3, license.getContractNumber());
            statement.setInt(4, license.getState().getStateNumber());
            statement.setString(5, license.getPredecessorLicenseId());
            statement.setDate(6, license.getValidFrom());
            statement.setDate(7, license.getValidTill());
            statement.setDate(8, license.getApplicationSubmitDate());
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
                            customerRepository.getCustomerById(resultSet.getInt("customerId")));
                }
            }
        }
    }

    public List<License> findAll() throws SQLException {
        try (Connection conn = ds.getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM License")) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    List<License> licenses = new ArrayList<>();
                    while (resultSet.next()) {
                        int productId = resultSet.getInt("productId");
                        Product productById = productRepository.getProductById(productId);
                        int customerId = resultSet.getInt("customerId");
                        Customer customerById = customerRepository.getCustomerById(customerId);

                        License license = getLicense(resultSet, productById, customerById);
                        licenses.add(license);
                    }
                    return licenses;
                }
            }
        }
    }

    private License getLicense(ResultSet resultSet, Product product, Customer customer) throws SQLException {
        Integer state = resultSet.getInt("state");

        return new License(
                resultSet.getInt("id"),
                product,
                customer,
                resultSet.getString("contractNumber"),
                State.getByStateNumber(state),
                resultSet.getString("predecessorLicenseId"),
                resultSet.getDate("validFrom"),
                resultSet.getDate("validTill"),
                resultSet.getDate("applicationSubmitDate"));
    }

    public License update(License license) throws SQLException {
        try (Connection conn = ds.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("UPDATE License SET " +
                    "licenseState = ?, validFrom = ?, validTill = ? WHERE id = ?");

            State licenseState = license.getState();
            statement.setInt(1, licenseState.getStateNumber());
            statement.setDate(2, license.getValidFrom());
            statement.setDate(3, license.getValidTill());
            statement.setInt(4, license.getId());

            int rowCount = statement.executeUpdate();
            if (rowCount == 0) {
                throw new RuntimeException("No license with id: " + license.getId());
            }
        }
        return license;
    }

    public List<License> findExpiringLicenses() throws SQLException {
        try(Connection connection = ds.getConnection()) {
            try(PreparedStatement stmnt = connection.prepareStatement("Select *, DATEDIFF('DAY', CURRENT_DATE, validTill),CURRENT_DATE from License where DATEDIFF('DAY', CURRENT_DATE, validTill) < 31 AND DATEDIFF('DAY', CURRENT_DATE, validTill) > 0")) {
                try (ResultSet resultSet = stmnt.executeQuery()) {
                    List<License> expiringLicenses = new ArrayList<>();
                    while (resultSet.next()) {
                        int productId = resultSet.getInt("productId");
                        Product productById = productRepository.getProductById(productId);
                        int customerId = resultSet.getInt("customerId");
                        Customer customerById = customerRepository.getCustomerById(customerId);

                        License license = getLicense(resultSet, productById, customerById);
                        expiringLicenses.add(license);
                    }
                    return expiringLicenses;
                }
            }
        }

    }
}
