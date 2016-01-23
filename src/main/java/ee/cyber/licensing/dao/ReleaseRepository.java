package ee.cyber.licensing.dao;

import ee.cyber.licensing.entity.Release;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReleaseRepository {

    private final Connection conn;

    @Inject
    public ReleaseRepository(Connection conn) {
        this.conn = conn;
    }

    public List<Release> findReleasesByProductId(int productId) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * from Release WHERE productId = ?")) {
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

    private Release getRelease(ResultSet rs) throws SQLException {
        return new Release(
                rs.getInt("id"),
                rs.getString("version"),
                rs.getDate("additionDate"),
                rs.getString("user"));
    }

    public Release editRelease(Release release) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("UPDATE Release SET version=?, additionDate=? WHERE id=?");
        statement.setString(1, release.getVersionNumber());
        statement.setDate(2, new java.sql.Date(release.getAdditionDate().getTime()));
        statement.setInt(3, release.getId());
        statement.executeUpdate();

        return release;
    }

    public Release getReleaseById(int id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Release WHERE id = ?")) {
            stmt.setInt(1, id);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (id != 0 && !resultSet.next()) {
                    throw new SQLException("Ei leitud ühtegi rida id-ga " + id);
                }
                return getRelease(resultSet);
            }
        }

    }

    public boolean deleteRelease(int releaseId) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("DELETE from Release where id=?");
        statement.setInt(1, releaseId);
        return statement.executeUpdate() != 0;

    }


    public Release saveRelease(int productId, Release release) throws SQLException {

        PreparedStatement statement = conn.prepareStatement("INSERT INTO Release (productId, version, additionDate, user) VALUES (?, ?, ?, ?)");
        statement.setInt(1, productId); // release.product.getId()
        statement.setString(2, release.getVersionNumber());
        statement.setDate(3, new java.sql.Date(release.getAdditionDate().getTime()));
        statement.setString(4, release.getUser());
        statement.execute();

        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                release.setId(generatedKeys.getInt(1));
            }
        }

        return release;
    }
}
