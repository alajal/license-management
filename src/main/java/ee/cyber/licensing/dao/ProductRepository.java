package ee.cyber.licensing.dao;

import ee.cyber.licensing.entity.Product;

import javax.inject.Inject;
import javax.sql.DataSource;
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

    public Product save(Product product) throws SQLException {
        try (Connection conn = ds.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO Product (name, release) VALUES (?, ?)");
            statement.setString(1, product.getName());
            statement.setString(2, product.getRelease());
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
        return new Product(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("release"));
    }
}
