package ee.cyber.licensing.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import ee.cyber.licensing.entity.License;
import ee.cyber.licensing.entity.LicenseOwner;

public class LicenseOwnerRepository {

    @Inject
    private DataSource ds;

    public LicenseOwner save(LicenseOwner licenseOwner) throws SQLException {
        try (Connection conn = ds.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO LicenseOwner (name, address, " +
                    "webpage, registrationCode, phone, bankAccount, fax, unitOrFaculty) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, licenseOwner.getname());
            statement.setString(2, licenseOwner.getAddress());
            statement.setString(3, licenseOwner.getWebpage());
            statement.setString(4, licenseOwner.getRegistrationCode());
            statement.setString(5, licenseOwner.getPhone());
            statement.setString(6, licenseOwner.getBankAccount());
            statement.setString(7, licenseOwner.getFax());
            statement.setString(8, licenseOwner.getUnitOrFaculty());
            statement.execute();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    licenseOwner.setId(generatedKeys.getInt(1));
                }
            }
        }
        return licenseOwner;
    }

    public LicenseOwner getLicenseOwnerById(int id) throws SQLException {
        try (Connection connection = ds.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM LicenseOwner WHERE id = ?;")) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (!resultSet.next()) {
                        throw new SQLException("Ei leitud ühtegi rida id-ga " + id);
                    }
                    return getLicenseOwner(resultSet);
                }
            }
        }
    }

    public List<LicenseOwner> findAll() throws SQLException {
        try (Connection conn = ds.getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM LicenseOwner")) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    List<LicenseOwner> licenseOwners = new ArrayList<>();
                    while (resultSet.next()) {
                        LicenseOwner licenseOwner = getLicenseOwner(resultSet);
                        licenseOwners.add(licenseOwner);
                    }
                    return licenseOwners;
                }
            }
        }
    }

    private LicenseOwner getLicenseOwner(ResultSet resultSet) throws SQLException {
        return new LicenseOwner(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("address"),
                resultSet.getString("webpage"),
                resultSet.getString("registrationCode"),
                resultSet.getString("phone"),
                resultSet.getString("bankAccount"),
                resultSet.getString("fax"),
                resultSet.getString("unitOrFaculty"));
    }

    public LicenseOwner edit(LicenseOwner licenseOwner) throws SQLException {
        try (Connection conn = ds.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("UPDATE LicenseOwner SET name=?, " +
                    "address=?, webpage=?, registrationCode=?, phone=?, bankAccount=?, fax=?, " +
                    "unitOrFaculty=? WHERE id=?");
            statement.setString(1, licenseOwner.getname());
            statement.setString(2, licenseOwner.getAddress());
            statement.setString(3, licenseOwner.getWebpage());
            statement.setString(4, licenseOwner.getRegistrationCode());
            statement.setString(5, licenseOwner.getPhone());
            statement.setString(6, licenseOwner.getBankAccount());
            statement.setString(7, licenseOwner.getFax());
            statement.setString(8, licenseOwner.getUnitOrFaculty());
            statement.setInt(9, licenseOwner.getId());
            statement.execute();
        }
        return licenseOwner;
    }

}
