package ee.cyber.licensing.dao;


import ee.cyber.licensing.entity.*;

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

    @Inject
    private ReleaseRepository releaseRepository;

    public License save(License license) throws SQLException {
        try (Connection conn = ds.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(
                    "INSERT INTO License (productId, releaseId, customerId, contractNumber, state, predecessorLicenseId, " +
                            "validFrom, validTill, applicationSubmitDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            statement.setInt(1, license.getProduct().getId());
            statement.setInt(2, license.getRelease().getId());
            statement.setInt(3, license.getCustomer().getId());
            statement.setString(4, license.getContractNumber());
            statement.setInt(5, license.getState().getStateNumber());
            statement.setString(6, license.getPredecessorLicenseId());
            statement.setDate(7, license.getValidFrom());
            statement.setDate(8, license.getValidTill());
            statement.setDate(9, license.getApplicationSubmitDate());
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
        System.out.println(id);
        try (Connection connection = ds.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM License WHERE id = ?;")) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (!resultSet.next()) {
                        throw new SQLException("Ei leitud ühtegi rida id-ga " + id);
                    }
                    return getLicense(resultSet, productRepository.getProductById(resultSet.getInt("productId")), releaseRepository.getReleaseById(resultSet.getInt("releaseId")),
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
                        int releaseId = resultSet.getInt("releaseId");
                        Release release = releaseRepository.getReleaseById(releaseId);
                        int customerId = resultSet.getInt("customerId");
                        Customer customerById = customerRepository.getCustomerById(customerId);

                        License license = getLicense(resultSet, productById, release, customerById);
                        licenses.add(license);
                    }
                    return licenses;
                }
            }
        }
    }

    public List<License> findByKeyword(String kword) throws SQLException {
      System.out.println(kword);
      try (Connection conn = ds.getConnection()) {
        try (PreparedStatement statement = conn.prepareStatement(
        "SELECT * FROM License WHERE contractNumber LIKE (CONCAT('%',?,'%')) OR productId  LIKE (CONCAT('%',?,'%')) OR releaseId LIKE (CONCAT('%',?,'%')) OR customerId LIKE (CONCAT('%',?,'%')) OR validFrom LIKE (CONCAT('%',?,'%')) OR validTill LIKE (CONCAT('%',?,'%'));")) {
          statement.setString(1, kword);
          statement.setString(2, kword);
          statement.setString(3, kword);
          statement.setString(4, kword);
          statement.setString(5, kword);
          statement.setString(6, kword);
          System.out.println(statement);
          try (ResultSet resultSet = statement.executeQuery()) {
            List<License> licenses = new ArrayList();
            while (resultSet.next()) {
                int productId = resultSet.getInt("productId");
                Product productById = productRepository.getProductById(productId);
                int releaseId = resultSet.getInt("releaseId");
                Release release = releaseRepository.getReleaseById(releaseId);
                int customerId = resultSet.getInt("customerId");
                Customer customerById = customerRepository.getCustomerById(customerId);

                License license = getLicense(resultSet, productById, release, customerById);
                licenses.add(license);
            }
            return licenses;
          }
        }
      }
    }

    private License getLicense(ResultSet resultSet, Product product, Release release, Customer customer) throws SQLException {
        Integer state = resultSet.getInt("state");

        return new License(
                resultSet.getInt("id"),
                product,
                release,
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
                        int releaseId = resultSet.getInt("releaseId");
                        Release release = releaseRepository.getReleaseById(releaseId);
                        int customerId = resultSet.getInt("customerId");
                        Customer customerById = customerRepository.getCustomerById(customerId);

                        License license = getLicense(resultSet, productById, release, customerById);
                        expiringLicenses.add(license);
                    }
                    return expiringLicenses;
                }
            }
        }

    }
}
