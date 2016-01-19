package ee.cyber.licensing.dao;


import ee.cyber.licensing.entity.Customer;
import ee.cyber.licensing.entity.License;
import ee.cyber.licensing.entity.LicenseType;
import ee.cyber.licensing.entity.Product;
import ee.cyber.licensing.entity.Release;
import ee.cyber.licensing.entity.State;
import ee.cyber.licensing.entity.StateHelper;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class LicenseRepository {

    @Inject
    private Connection conn;

    @Inject
    private ProductRepository productRepository;

    @Inject
    private CustomerRepository customerRepository;

    @Inject
    private ReleaseRepository releaseRepository;

    static Calendar utc() {
        return Calendar.getInstance(TimeZone.getTimeZone("GMT"));
    }

    public License save(License license) throws SQLException {
        PreparedStatement statement = conn.prepareStatement(
                "INSERT INTO License (productId, releaseId, customerId, contractNumber, state, predecessorLicenseId, " +
                        "validFrom, validTill, applicationSubmitDate, licenseTypeId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
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
        setTimestamp(statement, 7, license.getValidFrom());
        setTimestamp(statement, 8, license.getValidTill());
        setTimestamp(statement, 9, new Date());
        statement.setInt(10, license.getType().getId());
        statement.execute();

        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                license.setId(generatedKeys.getInt(1));
            }
        }

        return license;
    }

    private void setTimestamp(PreparedStatement statement, int parameterIndex, Date date) throws SQLException {
        Timestamp timestamp = date != null ? new Timestamp(date.getTime()) : null;
        statement.setTimestamp(parameterIndex, timestamp, utc());
    }

    public License findById(int id) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM License WHERE id = ?;")) {
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
                        ? getLicenseTypeById(conn, licenseTypeId)
                        : null;
                return getLicense(resultSet, productRepository.getProductById(resultSet.getInt("productId")),
                        customerRepository.getCustomerById(resultSet.getInt("customerId")), release, licenseType);
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

    private LicenseType getLicenseTypeById(Connection connection, int licenseTypeId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM LicenseType WHERE id = ?")) {
            statement.setInt(1, licenseTypeId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    throw new SQLException("Ei leitud ühtegi litsentsi tüüpi id-ga " + licenseTypeId);
                }
                return new LicenseType(licenseTypeId, resultSet.getString("name"), resultSet.getInt("validityPeriod"),
                        resultSet.getDouble("cost"));
            }
        }

    }

    public List<License> findByKeyword(String kword, StateHelper sh) throws SQLException {
        String stmnt_string;

        if (kword.equals("sj4Ajk765Anbx")) {
            stmnt_string = "SELECT * FROM License WHERE (License.state = 1 AND ?=true) OR (License.state = 2 AND ?=true) OR (License.state = 3 AND ?=true) OR (License.state = 4 AND ?=true) OR (License.state = 5 AND ?=true) OR (License.state = 6 AND ?=true);";
        } else {
            stmnt_string = "SELECT * FROM License LEFT JOIN Customer ON License.customerId = Customer.id LEFT JOIN Release ON License.releaseId = Release.id LEFT JOIN Product ON License.productId = Product.id LEFT JOIN LicenseType ON License.licenseTypeId = LicenseType.id WHERE (License.contractNumber LIKE (LOWER(CONCAT('%',?,'%'))) OR ( License.productId = Product.id AND LOWER(Product.name) LIKE LOWER(CONCAT('%',?,'%'))) OR ( License.releaseId = Release.id AND Release.version LIKE (CONCAT('%',?,'%')) ) OR ( License.customerId = Customer.id AND LOWER(Customer.organizationName) LIKE LOWER(CONCAT('%',?,'%')) ) OR License.validFrom LIKE (CONCAT('%',?,'%')) OR License.validTill LIKE (CONCAT('%',?,'%')) OR ( License.licenseTypeId = LicenseType.id AND LicenseType.name LIKE (CONCAT('%',?,'%')) )) AND ((License.state = 1 AND ?=true) OR (License.state = 2 AND ?=true) OR (License.state = 3 AND ?=true) OR (License.state = 4 AND ?=true) OR (License.state = 5 AND ?=true) OR (License.state = 6 AND ?=true));";
        }

        try (PreparedStatement statement = conn.prepareStatement(
                stmnt_string)) {
            if (kword.equals("sj4Ajk765Anbx")) {
                statement.setBoolean(1, sh.getRejected());
                statement.setBoolean(2, sh.getNegotiated());
                statement.setBoolean(3, sh.getWaiting_for_signature());
                statement.setBoolean(4, sh.getActive());
                statement.setBoolean(5, sh.getExpiration_nearing());
                statement.setBoolean(6, sh.getTerminated());
            } else {
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
            }

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

    private License getLicense(ResultSet resultSet, Product product, Customer customer, Release release, LicenseType type) throws SQLException {
        Integer state = resultSet.getInt("state");
        Integer licenseId = resultSet.getInt("id");

        return new License(
                licenseId,
                product,
                customer,
                release,
                resultSet.getString("contractNumber"),
                State.getByStateNumber(state),
                resultSet.getString("predecessorLicenseId"),
                getDate(resultSet, "validFrom"),
                getDate(resultSet, "validTill"),
                type,
                getDate(resultSet, "applicationSubmitDate"),
                getDate(resultSet, "latestDeliveryDate"));
    }

    private Date getDate(ResultSet resultSet, String columnLabel) throws SQLException {
        Timestamp timestamp = resultSet.getTimestamp(columnLabel, utc());
        //Timestamp.valueOf(Instant.now().atOffset(ZoneOffset.UTC).toLocalDateTime());
        if (timestamp == null) {
            return null;
        }
        return new Date(timestamp.getTime());
    }

    public License updateLicense(License newLicense) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("UPDATE License SET "
                + "releaseId = ?, state = ?, licenseTypeId = ?, latestDeliveryDate = ?, validFrom = ?, validTill = ? WHERE id = ?;");
        //seti valid till ja from siis kui status muudetakse Activiks, kui see muudetakse terminated või expiringiks, siis
        //jäta need väärtused alles ja ära muuda neid enam
        Instant licenseEndDate = Instant.now().atOffset(ZoneOffset.UTC).plus(newLicense.getType().getValidityPeriod(),
                ChronoUnit.YEARS).toInstant();
        boolean newValidFromAndTill = newValidFromAndTill(newLicense.getId(), newLicense.getState(), newLicense.getType());

        statement.setObject(1, newLicense.getRelease() == null ? null : newLicense.getRelease().getId());
        statement.setInt(2, newLicense.getState().getStateNumber());
        statement.setInt(3, newLicense.getType().getId());
        setTimestamp(statement, 4, newLicense.getLatestDeliveryDate());

        License oldLicense = findById(newLicense.getId());

        //Kui valid till ja valid from on juba olemas, siis neid enam muuta ei saa ja kirjutame lihtsalt sama väärtusega üle updatei korral

        Timestamp validFrom = newValidFromAndTill ? Timestamp.from(Instant.now()) : new Timestamp(oldLicense.getValidFrom().getTime());
        newLicense.setValidFrom(validFrom);
        setTimestamp(statement, 5, validFrom);

        Timestamp validTill = newValidFromAndTill ? Timestamp.from(licenseEndDate) : new Timestamp(oldLicense.getValidTill().getTime());
        newLicense.setValidTill(validTill);
        setTimestamp(statement, 6, validTill);

        statement.setInt(7, newLicense.getId());

        int rowCount = statement.executeUpdate();
        if (rowCount == 0) {
            throw new RuntimeException("No license with id: " + newLicense.getId());
        }

        return newLicense;
    }

    /**
     * If license changes from a non-active state to one of the active states, calculate new valid till.
     * If license is in one of the active states and license type changes, calculate new valid till.
     *
     * @param licenseId
     * @param newState       - see mis sisse tuli
     * @param newLicenseType - see mis sisse tuli
     * @return
     */
    private boolean newValidFromAndTill(int licenseId, State newState, LicenseType newLicenseType) throws SQLException {
        boolean stateToActive = false;
        boolean licenseTypeChange = true;

        License oldLicense = findById(licenseId);
        int oldStateNr = oldLicense.getState().getStateNumber();
        //kontroll, kas uus state on ACTIVE või TERMINATED või EXPIRING ja vana state on NEGOTIATED või WAITING või REJECTED
        if (newState.getStateNumber() >= State.ACTIVE.getStateNumber() && oldStateNr < State.ACTIVE.getStateNumber())
            stateToActive = true;
        if (newLicenseType == null) {
            licenseTypeChange = false;
        } else {
            if (oldLicense.getType() == null) {
                licenseTypeChange = true;
            } else if (oldLicense.getType().getName().equals(newLicenseType.getName())) {
                licenseTypeChange = false;
            }
        }

        return (stateToActive || (newState.getStateNumber() >= State.ACTIVE.getStateNumber() && licenseTypeChange));
    }

    public List<License> findExpiringLicenses() throws SQLException {
        try (PreparedStatement stmnt = conn.prepareStatement("Select *, DATEDIFF('DAY', CURRENT_DATE, validTill),CURRENT_DATE from License where DATEDIFF('DAY', CURRENT_DATE, validTill) < 31 AND DATEDIFF('DAY', CURRENT_DATE, validTill) > 0")) {
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
                            ? getLicenseTypeById(conn, licenseTypeId)
                            : null;

                    License license = getLicense(resultSet, productById, customerById, release, licenseType);
                    expiringLicenses.add(license);
                }
                return expiringLicenses;
            }
        }


    }

    public LicenseType saveType(LicenseType type) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement("INSERT INTO LicenseType " +
                "(name, validityPeriod, cost) VALUES (?, ?, ?)")) {
            statement.setString(1, type.getName());
            statement.setInt(2, type.getValidityPeriod());
            statement.setDouble(3, type.getCost());
            statement.execute();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    type.setId(generatedKeys.getInt(1));
                }
            }
        }

        return type;
    }

    public List<LicenseType> findLicenseTypes() throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM LicenseType")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                List<LicenseType> licenseTypes = new ArrayList<>();
                while (resultSet.next()) {
                    LicenseType type = new LicenseType(resultSet.getInt("id"), resultSet.getString("name"),
                            resultSet.getInt("validityPeriod"), resultSet.getDouble("cost"));
                    licenseTypes.add(type);
                }
                return licenseTypes;
            }
        }

    }


    public LicenseType getLicenseTypeById(int licenseTypeId) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM LicenseType WHERE id = ?")) {
            statement.setInt(1, licenseTypeId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    throw new SQLException("Ei leitud ühtegi litsentsi tüüpi id-ga " + licenseTypeId);
                }
                return new LicenseType(licenseTypeId, resultSet.getString("name"), resultSet.getInt("validityPeriod"),
                        resultSet.getDouble("cost"));
            }
        }

    }


    public List<License> getCustomerLicenses(Integer customerId) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM License where customerId = ?")) {
            statement.setInt(1, customerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                List<License> customerLicenses = new ArrayList<>();
                while (resultSet.next()) {
                    int productId = resultSet.getInt("productId");
                    Product productById = productRepository.getProductById(productId);
                    int releaseId = resultSet.getInt("releaseId");
                    Release release = null;
                    if (releaseId != 0) release = releaseRepository.getReleaseById(releaseId);
                    Customer customerById = customerRepository.getCustomerById(customerId);
                    Integer licenseTypeId = getInteger(resultSet, "licenseTypeId");
                    LicenseType licenseType = licenseTypeId != null
                            ? getLicenseTypeById(conn, licenseTypeId)
                            : null;

                    License license = getLicense(resultSet, productById, customerById, release, licenseType);
                    customerLicenses.add(license);
                }
                return customerLicenses;
            }
        }

    }
}
