package ee.cyber.licensing.dao;

import ee.cyber.licensing.entity.Product;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    private final Connection conn;

    @Inject
    public ProductRepository(Connection conn) {
        this.conn = conn;
    }

    @Inject
    private ReleaseRepository releaseRepository;

    public List<Product> findAll() throws SQLException {

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

    public List<Product> findByKeyword(String kword) throws SQLException {
        System.out.println(kword);
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

    public Product save(Product product) throws SQLException {

        PreparedStatement statement = conn.prepareStatement("INSERT INTO Product (name) VALUES (?)");
        statement.setString(1, product.getName());
        statement.execute();

        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                product.setId(generatedKeys.getInt(1));
            }
        }

        return product;
    }

    public Product getProductById(int id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Product WHERE id = ?")) {
            stmt.setInt(1, id);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (!resultSet.next()) {
                    throw new SQLException("Didn't find any row with product id " + id);
                }
                return getProduct(resultSet);
            }
        }

    }

    private Product getProduct(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        return new Product(
                id,
                rs.getString("name"),
                releaseRepository.findReleasesByProductId(id));
    }


    public Product editProduct(Product product) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("UPDATE Product SET name=? WHERE id=?");
        statement.setString(1, product.getName());
        statement.setInt(2, product.getId());
        statement.executeUpdate();

        return product;
    }

    private boolean deleteReleases(int productId, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE from Release WHERE productId=?");
        statement.setInt(1, productId);
        return statement.executeUpdate() != 0;
    }

    public boolean deleteProductAndReleases(int productId) throws SQLException {
        try {
            conn.setAutoCommit(false);
            deleteReleases(productId, conn);
            PreparedStatement statement = conn.prepareStatement("DELETE from Product where id=?");
            statement.setInt(1, productId);
            int i = statement.executeUpdate();
            conn.commit();
            return i != 0;
        } catch (Exception ex) {
            conn.rollback();
            throw ex;
        } finally {
            conn.setAutoCommit(true);
        }
    }

}
