package ee.cyber.licensing.dao;

import ee.cyber.licensing.entity.AuthorisedUser;
import ee.cyber.licensing.entity.Product;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    private final DataSource ds;

    @Inject
    public ProductRepository(DataSource ds) {
        this.ds = ds;
    }

    @Inject
    private ReleaseRepository releaseRepository;

    public List<Product> findAll() throws SQLException {
        try (Connection conn = ds.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement("SELECT * from Product")) {
                try (ResultSet rs = stmt.executeQuery()) {
                    List<Product> products = new ArrayList<>();
                    while (rs.next()) {
                        products.add(getProduct(rs));
                    }
                    return products;
                }
            }
        }
    }

    public List<Product> findByKeyword(String kword) throws SQLException {
        System.out.println(kword);
        try (Connection conn = ds.getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement(
                    "SELECT * FROM Product WHERE LOWER(name) LIKE LOWER(CONCAT('%',?,'%'))")) {
                statement.setString(1, kword);
                try (ResultSet resultSet = statement.executeQuery()) {
                    List<Product> products = new ArrayList<>();
                    while (resultSet.next()) {
                        Product product = getProduct(resultSet);
                        products.add(product);
                    }
                    return products;
                }
            }
        }
    }

    public Product save(Product product) throws SQLException {
        try (Connection conn = ds.getConnection()) {

            PreparedStatement statement = conn.prepareStatement("INSERT INTO Product (name) VALUES (?)");
            statement.setString(1, product.getName());
            statement.execute();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    product.setId(generatedKeys.getInt(1));
                }
            }
        }
        return product;
    }

    public Product getProductById(int id) throws SQLException {
        try (Connection connection = ds.getConnection()) {
            try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Product WHERE id = ?;")) {
                stmt.setInt(1, id);
                try (ResultSet resultSet = stmt.executeQuery()) {
                    if (!resultSet.next()) {
                        throw new SQLException("Ei leitud ühtegi rida id-ga " + id);
                    }
                    return getProduct(resultSet);
                }
            }
        }
    }

    private Product getProduct(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        return new Product(
                id,
                rs.getString("name"),
                releaseRepository.findByProductId(id));
    }


    public Product editProduct(Product product) throws SQLException {
        try (Connection conn = ds.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("UPDATE Product SET name=? WHERE id=?");
            statement.setString(1, product.getName());
            statement.setInt(2, product.getId());
            statement.executeUpdate();
        }
        return product;
    }

    public boolean deleteProduct(Product product) {
        try (Connection conn = ds.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("DELETE from Product where id=?");
            statement.setInt(1, product.getId());
            statement.execute();

            return true;

        } catch (SQLException e) {

            return false;

        }

    }
}
