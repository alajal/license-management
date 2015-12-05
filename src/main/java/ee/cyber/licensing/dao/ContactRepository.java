package ee.cyber.licensing.dao;

import ee.cyber.licensing.entity.Contact;
import ee.cyber.licensing.entity.Customer;

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

    @Inject
    private LicenseRepository licenseRepository;

    public List<Contact> findAll(Customer customer) throws SQLException {

        try (Connection conn = ds.getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM Contact where customerId=?")) {
                statement.setInt(1, customer.getId());
                try (ResultSet resultSet = statement.executeQuery()) {
                    List<Contact> contacts = new ArrayList<>();
                    while (resultSet.next()) {
                        Contact contact = getContact(resultSet);
                        contacts.add(contact);
                    }
                    return contacts;
                }
            }
        }
    }


    private Contact getContact(ResultSet resultSet) throws SQLException {
        return new Contact(
                resultSet.getInt("id"),
                resultSet.getString("firstName"),
                resultSet.getString("lastName"),
                resultSet.getString("email"),
                resultSet.getString("skype"),
                resultSet.getString("phone")
        );
    }

    public Contact save(Contact contactPerson, int licenseId) throws SQLException {
        try (Connection conn = ds.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO Contact (customerId, firstName, lastName, email, skype, phone) VALUES (?, ?, ?, ?, ?, ?)");
            statement.setInt(1, licenseRepository.findById(licenseId).getCustomer().getId());
            statement.setString(2, contactPerson.getFirstName());
            statement.setString(3, contactPerson.getLastName());
            statement.setString(4, contactPerson.getEmail());
            statement.setString(5, contactPerson.getSkype());
            statement.setString(6, contactPerson.getPhone());
            statement.execute();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    contactPerson.setId(generatedKeys.getInt(1));
                }
            }
        }
        return contactPerson;
    }

    public Contact updateContactPerson(Contact contactPerson, int licenseId) throws SQLException {
        try (Connection conn = ds.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("UPDATE Contact SET firstName=?, lastName=?, email=?, skype=?, phone=? WHERE customerId=? and id=?");
            statement.setString(1, contactPerson.getFirstName());
            statement.setString(2, contactPerson.getLastName());
            statement.setString(3, contactPerson.getEmail());
            statement.setString(4, contactPerson.getSkype());
            statement.setString(5, contactPerson.getPhone());
            statement.setInt(6, licenseRepository.findById(licenseId).getCustomer().getId());
            statement.setInt(7, contactPerson.getId());
            statement.executeUpdate();
        }
        return contactPerson;
    }

}
