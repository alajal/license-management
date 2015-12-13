package ee.cyber.licensing.dao;

import ee.cyber.licensing.entity.AuthorisedUser;
import ee.cyber.licensing.entity.DeliveredRelease;
import ee.cyber.licensing.entity.License;
import ee.cyber.licensing.entity.Release;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DeliveredReleaseRepository{

    @Inject
    private DataSource ds;

    @Inject
    private LicenseRepository licenseRepository;

    @Inject
    private ReleaseRepository releaseRepository;

    public DeliveredRelease save(DeliveredRelease dr) throws SQLException {

        try (Connection conn = ds.getConnection()) {
            PreparedStatement stmnt = conn.prepareStatement("INSERT INTO DeliveredRelease (licenseId, releaseId, deliveryDate) VALUES (?, ?, ?)");
            stmnt.setInt(1, dr.getLicense().getId());
            stmnt.setInt(2, dr.getRelease().getId());
            stmnt.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
            stmnt.execute();

            try (ResultSet generatedKeys = stmnt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    dr.setId(generatedKeys.getInt(1));
                }
            }
        }

        return dr;
    }

    public List<DeliveredRelease> getAllDeliveredReleases(int id) throws SQLException {
        try (Connection conn = ds.getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM DeliveredRelease where licenseId = ?;")) {
                statement.setInt(1,id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    List<DeliveredRelease> deliveredReleases = new ArrayList<>();
                    while (resultSet.next()) {
                        int licenseId = resultSet.getInt("licenseId");
                        License license = licenseRepository.findById(licenseId);
                        DeliveredRelease dr = getDeliveredRelease(resultSet, license);
                        deliveredReleases.add(dr);
                    }
                    return deliveredReleases;
                }
            }
        }
    }

    private DeliveredRelease getDeliveredRelease(ResultSet resultSet, License license) throws SQLException {
        return new DeliveredRelease(
                resultSet.getInt("id"),
                license,
                releaseRepository.getReleaseById(resultSet.getInt("releaseId")),
                resultSet.getDate("deliveryDate")
        );
    }


}
