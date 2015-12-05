package ee.cyber.licensing.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import ee.cyber.licensing.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ee.cyber.licensing.entity.Customer;
import ee.cyber.licensing.entity.License;
import ee.cyber.licensing.entity.Product;
import ee.cyber.licensing.entity.Release;
import ee.cyber.licensing.entity.State;
import ee.cyber.licensing.entity.StateHelper;

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
            try (PreparedStatement statement = conn.prepareStatement(
                    "INSERT INTO License (productId, releaseId, customerId, contractNumber, state, predecessorLicenseId, " +
                            "validFrom, validTill, applicationSubmitDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
                statement.setInt(1, license.getProduct().getId());
                if (license.getRelease() == null) {
                    statement.setNull(2, java.sql.Types.INTEGER);
                } else {
                    statement.setInt(2, license.getRelease().getId());
                }
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
                    int releaseId = resultSet.getInt("releaseId");
                    Release release = null;
                    if (releaseId != 0) release = releaseRepository.getReleaseById(releaseId);
                    return getLicense(resultSet, productRepository.getProductById(resultSet.getInt("productId")), release,
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
                        Release release = null;
                        if (releaseId != 0) release = releaseRepository.getReleaseById(releaseId);
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

    public List<License> findByKeyword(String kword, StateHelper sh) throws SQLException {
      try (Connection conn = ds.getConnection()) {
        try (PreparedStatement statement = conn.prepareStatement(
        "SELECT * FROM License LEFT JOIN Customer ON License.customerId = Customer.id LEFT JOIN Release ON License.releaseId = Release.id LEFT JOIN Product ON License.productId = Product.id WHERE (License.contractNumber LIKE (LOWER(CONCAT('%',?,'%'))) OR ( License.productId = Product.id AND Product.name LIKE (CONCAT('%',?,'%')) ) OR ( License.releaseId = Release.id AND Release.version LIKE (CONCAT('%',?,'%')) ) OR ( License.customerId = Customer.id AND Customer.organizationName LIKE (CONCAT('%',?,'%')) ) OR License.validFrom LIKE (CONCAT('%',?,'%')) OR License.validTill LIKE (CONCAT('%',?,'%'))) AND ((License.state = 1 AND ?=true) OR (License.state = 2 AND ?=true) OR (License.state = 3 AND ?=true) OR (License.state = 4 AND ?=true) OR (License.state = 5 AND ?=true) OR (License.state = 6 AND ?=true));")) {
          statement.setString(1, kword);
          statement.setString(2, kword);
          statement.setString(3, kword);
          statement.setString(4, kword);
          statement.setString(5, kword);
          statement.setString(6, kword);
          statement.setBoolean(7, sh.getRejected());
          statement.setBoolean(8, sh.getNegotiated());
          statement.setBoolean(9, sh.getWaiting_for_signature());
          statement.setBoolean(10, sh.getActive());
          statement.setBoolean(11, sh.getExpiration_nearing());
          statement.setBoolean(12, sh.getTerminated());
          try (ResultSet resultSet = statement.executeQuery()) {
            List<License> licenses = new ArrayList();
            while (resultSet.next()) {
                int productId = resultSet.getInt("productId");
                Product productById = productRepository.getProductById(productId);
                int releaseId = resultSet.getInt("releaseId");
                Release release = null;
                if(releaseId != 0) release = releaseRepository.getReleaseById(releaseId);
                int customerId = resultSet.getInt("customerId");
                Customer customerById = customerRepository.getCustomerById(customerId);

                License license = getLicense(resultSet, productById, release, customerById);
                licenses.add(license);

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
        try (Connection connection = ds.getConnection()) {
            try (PreparedStatement stmnt = connection.prepareStatement("Select *, DATEDIFF('DAY', CURRENT_DATE, validTill),CURRENT_DATE from License where DATEDIFF('DAY', CURRENT_DATE, validTill) < 31 AND DATEDIFF('DAY', CURRENT_DATE, validTill) > 0")) {
                try (ResultSet resultSet = stmnt.executeQuery()) {
                    List<License> expiringLicenses = new ArrayList<>();
                    while (resultSet.next()) {
                        int productId = resultSet.getInt("productId");
                        Product productById = productRepository.getProductById(productId);
                        int releaseId = resultSet.getInt("releaseId");
                        Release release = null;
                        if (releaseId != 0) release = releaseRepository.getReleaseById(releaseId);
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

    public LicenseType saveType(LicenseType type) throws SQLException {
        try (Connection connection = ds.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO LicenseType " +
                    "(name, validityPeriod, cost, mailBodyId) VALUES (?, ?, ?, ?)")) {
                statement.setString(1, type.getName());
                statement.setString(2, type.getValidityPeriod());
                statement.setDouble(3, type.getCost());
                statement.setInt(4, type.getMailBodyId());
                statement.execute();

                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        type.setId(generatedKeys.getInt(1));
                    }
                }
            }
        }
        return type;
    }

    public List<LicenseType> findLicenseTypes() throws SQLException {
        try (Connection connection = ds.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM LicenseType")) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    List<LicenseType> licenseTypes = new ArrayList<>();
                    while (resultSet.next()) {
                        LicenseType type = new LicenseType(resultSet.getInt("id"), resultSet.getString("name"),
                                resultSet.getString("validityPeriod"), resultSet.getDouble("cost"),
                                resultSet.getInt("mailBodyId"));
                        licenseTypes.add(type);
                    }
                    return licenseTypes;
                }
            }
        }
    }
}
