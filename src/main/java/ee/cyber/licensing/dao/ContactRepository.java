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
                resultSet.getString("firstName"),
                resultSet.getString("lastName"),
                resultSet.getString("email"),
                resultSet.getString("skype"),
                resultSet.getString("phone")
        );
    }
}
