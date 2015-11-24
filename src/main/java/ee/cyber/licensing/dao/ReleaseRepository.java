package ee.cyber.licensing.dao;


import ee.cyber.licensing.entity.Product;
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
            try (PreparedStatement stmt = conn.prepareStatement("SELECT * from Release INNER JOIN Product on product.id=release.productId;")) {
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
}
