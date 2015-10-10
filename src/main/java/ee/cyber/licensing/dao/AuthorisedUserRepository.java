package ee.cyber.licensing.dao;


import ee.cyber.licensing.entity.AuthorisedUser;
import ee.cyber.licensing.entity.License;
import ee.cyber.licensing.entity.LicenseOwner;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorisedUserRepository {

    @Inject
    private DataSource ds;

    @Inject
    private LicenseRepository licenseRepository;

    public AuthorisedUser save(AuthorisedUser au) throws SQLException {
        try (Connection conn = ds.getConnection()) {
            PreparedStatement stmnt = conn.prepareStatement("INSERT INTO AuthorisedUser (licenseId, firstName, lastName, email, occupation) VALUES (?, ?, ?, ?, ?)");
            stmnt.setInt(1, au.getLicense().getId());
            stmnt.setString(2, au.getFirstName());
            stmnt.setString(3, au.getLastName());
            stmnt.setString(4, au.getEmail());
            stmnt.setString(5, au.getOccupation());
            stmnt.execute();

            try (ResultSet generatedKeys = stmnt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    au.setId(generatedKeys.getInt(1));
                }
            }
        }
        return au;
    }

    public List<AuthorisedUser> findAll(Integer licId) throws SQLException {
        try (Connection conn = ds.getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM AuthorisedUser where licenseId = ?;")) {
                statement.setInt(1,licId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    List<AuthorisedUser> authorisedUsers = new ArrayList<>();
                    while (resultSet.next()) {
                        int licenseId = resultSet.getInt("licenseId");
                        License license = licenseRepository.findById(licenseId);
                        AuthorisedUser au = getAuthorisedUser(resultSet, license);
                        authorisedUsers.add(au);
                    }
                    return authorisedUsers;
                }
            }
        }
    }

    private AuthorisedUser getAuthorisedUser(ResultSet rs, License license) throws SQLException {
        return new AuthorisedUser(
                rs.getInt("id"),
                license,
                rs.getString("firstName"),
                rs.getString("lastName"),
                rs.getString("email"),
                rs.getString("occupation")
        );
    }

    public AuthorisedUser save(AuthorisedUser au, int licenseId) throws SQLException {
        License license = licenseRepository.findById(licenseId);
        au.setLicense(license);
        return save(au);
    }
}
