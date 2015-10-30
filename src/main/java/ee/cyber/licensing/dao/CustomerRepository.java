package ee.cyber.licensing.dao;


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
            PreparedStatement statement = conn.prepareStatement("INSERT INTO Customer (organizationName, applicationArea) VALUES (?, ?);");
            statement.setString(1, customer.getOrganizationName());
            statement.setString(2, customer.getApplicationArea());
            statement.execute();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    customer.setId(generatedKeys.getInt(1));
                }
            }
        }
        return customer;
    }


    public Customer getCustomerById(int id) throws SQLException {
        try (Connection connection = ds.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM Customer WHERE id = ?;")) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (!resultSet.next()) {
                        throw new SQLException("No customer with id: " + id);
                    }
                    return getCustomer(resultSet);
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
                        Customer customer = getCustomer(resultSet);
                        customers.add(customer);
                    }
                    return customers;
                }
            }
        }
    }

    private Customer getCustomer(ResultSet resultSet) throws SQLException {
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
                resultSet.getString("unitOrFaculty"));
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

}
