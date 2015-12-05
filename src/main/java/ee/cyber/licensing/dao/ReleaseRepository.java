package ee.cyber.licensing.dao;



import ee.cyber.licensing.entity.Release;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReleaseRepository {

    private final DataSource ds;

    @Inject
    public ReleaseRepository(DataSource ds) {
        this.ds = ds;
    }

    public List<Release> findByProductId(int productId) throws SQLException {
        try (Connection conn = ds.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement("SELECT * from Release WHERE productId = ?;")) {
                stmt.setInt(1, productId);
                try (ResultSet rs = stmt.executeQuery()) {
                    List<Release> releases = new ArrayList<>();
                    while (rs.next()) {
                        releases.add(getRelease(rs));
                    }
                    return releases;
                }
            }
        }
    }

    private Release getRelease(ResultSet rs) throws SQLException {
        return new Release(
                rs.getInt("id"),
                rs.getString("version"),
                rs.getDate("additionDate"));
    }

    public Release editRelease(Release release) throws SQLException {
        try (Connection conn = ds.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("UPDATE Release SET version=?, additionDate=? WHERE id=?");
            statement.setString(1, release.getVersionNumber());
            statement.setDate(2, new java.sql.Date(release.getAdditionDate().getTime()));
            statement.setInt(3, release.getId());
            statement.executeUpdate();
        }
        return release;
    }

    public Release getReleaseById(int id) throws SQLException {
        try (Connection connection = ds.getConnection()) {
            try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Release WHERE id = ?;")) {
                stmt.setInt(1, id);
                try (ResultSet resultSet = stmt.executeQuery()) {
                    if (id != 0 && !resultSet.next()) {
                        throw new SQLException("Ei leitud ühtegi rida id-ga " + id);
                    }
                    return getRelease(resultSet);
                }
            }
        }
    }

    public boolean deleteRelease(Release release) {
        try (Connection conn = ds.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("DELETE from Release where id=?");
            statement.setInt(1, release.getId());
            statement.execute();

            return true;

        } catch (SQLException e) {

            return false;
        }
    }


    public Release saveRelease(int productId, Release release) throws SQLException {
        try (Connection conn = ds.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO Release (productId, version, additionDate) VALUES (?, ?, ?)");
            statement.setInt(1, productId); // release.product.getId()
            statement.setString(2, release.getVersionNumber());
            statement.setDate(3, new java.sql.Date(release.getAdditionDate().getTime()));
            statement.execute();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    release.setId(generatedKeys.getInt(1));
                }
            }
        }
        return release;
    }
}
