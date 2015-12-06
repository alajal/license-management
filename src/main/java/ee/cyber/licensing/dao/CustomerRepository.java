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

public class CustomerRepository {

    @Inject
    private DataSource ds;

    public Customer save(Customer customer) throws SQLException {
        try (Connection conn = ds.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO Customer " +
                    "(organizationName, applicationArea) VALUES (?, ?)");
            statement.setString(1, customer.getOrganizationName());
            statement.setString(2, customer.getApplicationArea());
            statement.execute();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    customer.setId(generatedKeys.getInt(1));
                }
            }
            //todo transaction needed - kas sama nimega customer on juba olemas?
            for (Contact contact : customer.getContacts()) {
                save(contact, customer, conn);
            }
        }
        return customer;
    }


    public Customer getCustomerById(int customerId) throws SQLException {
        try (Connection connection = ds.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM Customer WHERE id = ?")) {
                statement.setInt(1, customerId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (!resultSet.next()) {
                        throw new SQLException("No customer with id: " + customerId);
                    }
                    return getCustomer(connection, resultSet);
                }
            }
        }
    }


    public List<Customer> findAll() throws SQLException {
        try (Connection conn = ds.getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM Customer")) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    List<Customer> customers = new ArrayList<>();
                    while (resultSet.next()) {
                        Customer customer = getCustomer(conn, resultSet);
                        customers.add(customer);
                    }
                    return customers;
                }
            }
        }
    }

    public List<Customer> findByKeyword(String kword) throws SQLException {
      try (Connection conn = ds.getConnection()) {
        try (PreparedStatement statement = conn.prepareStatement(
        "SELECT * FROM Customer WHERE LOWER(organizationName) LIKE LOWER(CONCAT('%',?,'%')) OR LOWER(applicationArea) LIKE LOWER(CONCAT('%',?,'%')) OR LOWER(address) LIKE LOWER(CONCAT('%',?,'%')) OR LOWER(webpage) LIKE LOWER(CONCAT('%',?,'%')) OR LOWER(registrationCode) LIKE LOWER(CONCAT('%',?,'%')) OR phone LIKE (CONCAT('%',?,'%')) OR LOWER(bankaccount) LIKE LOWER(CONCAT('%',?,'%')) OR fax LIKE (CONCAT('%',?,'%')) OR LOWER(unitOrFaculty) LIKE LOWER(CONCAT('%',?,'%'));")) {
          statement.setString(1, kword);
          statement.setString(2, kword);
          statement.setString(3, kword);
          statement.setString(4, kword);
          statement.setString(5, kword);
          statement.setString(6, kword);
          statement.setString(7, kword);
          statement.setString(8, kword);
          statement.setString(9, kword);
          try (ResultSet resultSet = statement.executeQuery()) {
            List<Customer> customers = new ArrayList();
            while (resultSet.next()) {
                Customer customer = getCustomer(conn, resultSet);
                customers.add(customer);
            }
            return customers;
          }
        }
      }
    }

    private Customer getCustomer(Connection conn, ResultSet resultSet) throws SQLException {
        return new Customer(
                resultSet.getInt("id"),
                resultSet.getString("organizationName"),
                resultSet.getString("applicationArea"),
                resultSet.getString("address"),
                resultSet.getString("webpage"),
                resultSet.getString("registrationCode"),
                resultSet.getString("phone"),
                resultSet.getString("bankAccount"),
                resultSet.getString("fax"),
                resultSet.getString("unitOrFaculty"),
                getContacts(conn, resultSet.getInt("id")));
    }

    private List<Contact> getContacts(Connection conn, Integer customerId) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM Contact WHERE customerId = ?");
        statement.setInt(1, customerId);
        try (ResultSet resultSet = statement.executeQuery()) {
            List<Contact> contacts = new ArrayList<>();
            while (resultSet.next()) {
                Contact contact = getContact(resultSet);
                contacts.add(contact);
            }
            return contacts;
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

    public Customer update(Customer customer) throws SQLException {
        try (Connection conn = ds.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("UPDATE Customer SET organizationName=?, " +
                    "applicationArea=?, address=?, webpage=?, registrationCode=?, phone=?, bankAccount=?, fax=?, " +
                    "unitOrFaculty=? WHERE id=?");
            statement.setString(1, customer.getOrganizationName());
            statement.setString(2, customer.getApplicationArea());
            statement.setString(3, customer.getAddress());
            statement.setString(4, customer.getWebpage());
            statement.setString(5, customer.getRegistrationCode());
            statement.setString(6, customer.getPhone());
            statement.setString(7, customer.getBankAccount());
            statement.setString(8, customer.getFax());
            statement.setString(9, customer.getUnitOrFaculty());
            statement.setInt(10, customer.getId());
            statement.execute();
        }
        return customer;
    }


    private Contact save(Contact contact, Customer customer, Connection conn) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("INSERT INTO Contact " +
                "(customerId, firstName, lastName, email, skype, phone) VALUES (?, ?, ?, ?, ?, ?)");
        statement.setInt(1, customer.getId());
        statement.setString(2, contact.getFirstName());
        statement.setString(3, contact.getLastName());
        statement.setString(4, contact.getEmail());
        statement.setString(5, contact.getSkype());
        statement.setString(6, contact.getPhone());
        statement.execute();

        return contact;
    }

}
