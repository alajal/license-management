package ee.cyber.licensing.dao;


import ee.cyber.licensing.entity.AuthorisedUser;
import ee.cyber.licensing.entity.License;

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

    public AuthorisedUser findById(Integer licenseId, Integer id) throws SQLException {
        List<AuthorisedUser> users = findAll(licenseId);
        for(AuthorisedUser user : users){
            if(user.getId().equals(id)){
                return user;
            }
        }
        throw new IllegalArgumentException("No suitable user found");
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

    public AuthorisedUser deleteAuthorisedUser(Integer licenseId, AuthorisedUser au) throws SQLException {
        try (Connection conn = ds.getConnection()) {
            PreparedStatement stmnt = conn.prepareStatement("DELETE from AuthorisedUser where email=? and licenseId =?;");
            stmnt.setString(1, au.getEmail());
            stmnt.setInt(2, licenseId);
            stmnt.execute();

            try (ResultSet generatedKeys = stmnt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    au.setId(generatedKeys.getInt(1));
                }
            }
        }
        return au;
    }

    public AuthorisedUser editAuthorisedUser(Integer licenseId, AuthorisedUser au) throws SQLException {
        try (Connection conn = ds.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("UPDATE AuthorisedUser SET firstName=?, lastName=?, email=?, occupation=? WHERE licenseId=? and id=?");
            statement.setString(1, au.getFirstName());
            statement.setString(2, au.getLastName());
            statement.setString(3, au.getEmail());
            statement.setString(4, au.getOccupation());
            statement.setInt(5, licenseId);
            statement.setInt(6, au.getId());
            statement.executeUpdate();
        }
        return au;
    }
}
