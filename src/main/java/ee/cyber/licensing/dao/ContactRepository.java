package ee.cyber.licensing.dao;

import ee.cyber.licensing.entity.AuthorisedUser;
import ee.cyber.licensing.entity.Contact;
import ee.cyber.licensing.entity.License;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContactRepository {

    @Inject
    private DataSource ds;

    public Contact save(Contact contact) throws SQLException {
        try (Connection conn = ds.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO Contact " +
                    "(customerId, fullName, email, skype, phone) VALUES (?, ?, ?, ?, ?)");
            statement.setInt(1, contact.getCustomerId());
            statement.setString(2, contact.getContactName());
            statement.setString(3, contact.getEmail());
            statement.setString(4, contact.getSkype());
            statement.setString(5, contact.getPhone());
            statement.execute();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    contact.setId(generatedKeys.getInt(1));
                }
            }
        }
        return contact;
    }

/*    public List<Contact> findAll(Integer licenseId) throws SQLException {
        try (Connection conn = ds.getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM Contact where licenseId = ?;")) {
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
    }*/
}


