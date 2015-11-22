package ee.cyber.licensing.dao;


import ee.cyber.licensing.entity.Product;
import ee.cyber.licensing.entity.Release;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
}
