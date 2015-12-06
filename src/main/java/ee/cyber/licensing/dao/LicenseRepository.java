package ee.cyber.licensing.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import ee.cyber.licensing.entity.Customer;
import ee.cyber.licensing.entity.License;
import ee.cyber.licensing.entity.LicenseType;
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
            PreparedStatement statement = conn.prepareStatement(
                    "INSERT INTO License (productId, releaseId, customerId, contractNumber, state, predecessorLicenseId, " +
                            "validFrom, validTill, applicationSubmitDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
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
            //statement.setInt(9, license.getType().getId());
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
        try (Connection connection = ds.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM License WHERE id = ?;")) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (!resultSet.next()) {
                        throw new SQLException("Ei leitud ühtegi litsentsi, kus rida on id-ga " + id);
                    }
                    int releaseId = resultSet.getInt("releaseId");
                    Release release = null;
                    if (releaseId != 0) release = releaseRepository.getReleaseById(releaseId);
                    Integer licenseTypeId = getInteger(resultSet, "licenseTypeId");
                    LicenseType licenseType = licenseTypeId != null
                            ? getLicenseTypeById(connection, licenseTypeId)
                            : null;
                    return getLicense(resultSet, productRepository.getProductById(resultSet.getInt("productId")),
                            customerRepository.getCustomerById(resultSet.getInt("customerId")), release, licenseType);
                }
            }
        }
    }

    private Integer getInteger(ResultSet resultSet, String columnLabel) throws SQLException {
        int result = resultSet.getInt(columnLabel);
        if (resultSet.wasNull())
            return null;
        return result;
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
                        Integer licenseTypeId = getInteger(resultSet, "licenseTypeId");
                        LicenseType licenseType = licenseTypeId != null
                                ? getLicenseTypeById(conn, licenseTypeId)
                                : null;

                        License license = getLicense(resultSet, productById, customerById, release, licenseType);
                        licenses.add(license);
                    }
                    return licenses;
                }
            }
        }
    }

    private LicenseType getLicenseTypeById(Connection connection, int licenseTypeId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM LicenseType WHERE id = ?")) {
            statement.setInt(1, licenseTypeId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    throw new SQLException("Ei leitud ühtegi litsentsi tüüpi id-ga " + licenseTypeId);
                }
                return new LicenseType(licenseTypeId, resultSet.getString("name"), resultSet.getString("validityPeriod"),
                        resultSet.getDouble("cost"));
            }
        }

    }

    public List<License> findByKeyword(String kword, StateHelper sh) throws SQLException {
        try (Connection conn = ds.getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement(
                    "SELECT * FROM License LEFT JOIN Customer ON License.customerId = Customer.id LEFT JOIN Release ON License.releaseId = Release.id LEFT JOIN Product ON License.productId = Product.id LEFT JOIN LicenseType ON License.licenseTypeId = LicenseType.id WHERE (License.contractNumber LIKE (LOWER(CONCAT('%',?,'%'))) OR ( License.productId = Product.id AND LOWER(Product.name) LIKE LOWER(CONCAT('%',?,'%'))) OR ( License.releaseId = Release.id AND Release.version LIKE (CONCAT('%',?,'%')) ) OR ( License.customerId = Customer.id AND LOWER(Customer.organizationName) LIKE LOWER(CONCAT('%',?,'%')) ) OR License.validFrom LIKE (CONCAT('%',?,'%')) OR License.validTill LIKE (CONCAT('%',?,'%')) OR ( License.licenseTypeId = LicenseType.id AND LicenseType.name LIKE (CONCAT('%',?,'%')) )) AND ((License.state = 1 AND ?=true) OR (License.state = 2 AND ?=true) OR (License.state = 3 AND ?=true) OR (License.state = 4 AND ?=true) OR (License.state = 5 AND ?=true) OR (License.state = 6 AND ?=true));")) {
                statement.setString(1, kword);
                statement.setString(2, kword);
                statement.setString(3, kword);
                statement.setString(4, kword);
                statement.setString(5, kword);
                statement.setString(6, kword);
                statement.setString(7, kword);
                statement.setBoolean(8, sh.getRejected());
                statement.setBoolean(9, sh.getNegotiated());
                statement.setBoolean(10, sh.getWaiting_for_signature());
                statement.setBoolean(11, sh.getActive());
                statement.setBoolean(12, sh.getExpiration_nearing());
                statement.setBoolean(13, sh.getTerminated());
                System.out.println(statement);
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
                        Integer licenseTypeId = getInteger(resultSet, "licenseTypeId");
                        LicenseType licenseType = licenseTypeId != null
                                ? getLicenseTypeById(conn, licenseTypeId)
                                : null;

                        License license = getLicense(resultSet, productById, customerById, release, licenseType);
                        licenses.add(license);
                    }
                    return licenses;
                }
            }
        }
    }

    private License getLicense(ResultSet resultSet, Product product, Customer customer, Release release, LicenseType type) throws SQLException {
        Integer state = resultSet.getInt("state");

        //resultSet.getInt("licenseTypeId");
        return new License(
                resultSet.getInt("id"),
                product,
                customer,
                release,
                resultSet.getString("contractNumber"),
                State.getByStateNumber(state),
                resultSet.getString("predecessorLicenseId"),
                resultSet.getDate("validFrom"),
                resultSet.getDate("validTill"),
                type,
                resultSet.getDate("applicationSubmitDate"),
                resultSet.getDate("latestDeliveryDate"));
    }

    public License updateLicense(License license) throws SQLException {
        try (Connection conn = ds.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("UPDATE License SET " +
                    "releaseId = ?, state = ?, licenseTypeId = ?, latestDeliveryDate = ? WHERE id = ?;");
            State licenseState = license.getState();
            statement.setObject(1, license.getRelease() == null ? null : license.getRelease().getId());
            statement.setInt(2, licenseState.getStateNumber());
            statement.setInt(3, license.getType().getId());
            statement.setObject(4, license.getLatestDeliveryDate() == null ? null : license.getLatestDeliveryDate());
            statement.setInt(5, license.getId());

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
                        Integer licenseTypeId = getInteger(resultSet, "licenseTypeId");
                        LicenseType licenseType = licenseTypeId != null
                                ? getLicenseTypeById(connection, licenseTypeId)
                                : null;

                        License license = getLicense(resultSet, productById, customerById, release, licenseType);
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
                    "(name, validityPeriod, cost) VALUES (?, ?, ?)")) {
                statement.setString(1, type.getName());
                statement.setString(2, type.getValidityPeriod());
                statement.setDouble(3, type.getCost());
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
                                resultSet.getString("validityPeriod"), resultSet.getDouble("cost"));
                        licenseTypes.add(type);
                    }
                    return licenseTypes;
                }
            }
        }
    }
}
