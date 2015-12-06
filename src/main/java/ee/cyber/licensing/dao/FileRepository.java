package ee.cyber.licensing.dao;

import ee.cyber.licensing.entity.*;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.zip.InflaterInputStream;

public class FileRepository {

    @Inject
    private DataSource ds;

    public void saveFile(MailAttachment attachment) throws SQLException {
        byte[] fileData = Base64.getDecoder().decode(attachment.getData());
        //lisada fail andmebaasi blob tulpa
        try (Connection conn = ds.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO MailAttachment (fileName, fileData) " +
                    "VALUES (?, ?);");
            statement.setString(1, attachment.getFileName());
            statement.setBlob(2, new ByteArrayInputStream(fileData));
            statement.execute();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    attachment.setId(generatedKeys.getInt(1));
                }
            }
        }

    }

    public MailAttachment findById(int id) throws SQLException {
        try (Connection connection = ds.getConnection()) {
            try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM MailAttachment WHERE id = ?;")) {
                stmt.setInt(1, id);
                try (ResultSet resultSet = stmt.executeQuery()) {
                    if (!resultSet.next()) {
                        throw new SQLException("Ei leitud ühtegi rida id-ga " + id);
                    }
                    return getFile(resultSet);
                }
            }
        }
    }

    private MailAttachment getFile(ResultSet rs) throws SQLException {
        return new MailAttachment(
                rs.getInt("id"),
                rs.getString("fileData"),
                rs.getString("fileName"));
    }



    public void saveMailBody(MailBody mailBody) throws SQLException {
        try (Connection conn = ds.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO MailBody (subject, body, licenseTypeId) " +
                    "VALUES (?, ?, ?);");
            statement.setString(1, mailBody.getSubject());
            statement.setString(2, mailBody.getBody());
            statement.setInt(3, mailBody.getLicenseTypeId());
            statement.execute();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    mailBody.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public List<MailBody> findBodies() throws SQLException {
        try (Connection conn = ds.getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM MailBody")) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    List<MailBody> bodies = new ArrayList<>();
                    while (resultSet.next()) {
                        Integer id = resultSet.getInt("id");
                        String subject = resultSet.getString("subject");
                        String body = resultSet.getString("body");
                        Integer licenseTypeId = resultSet.getInt("licenseTypeId");

                        MailBody mailBody = new MailBody(id, subject, body, licenseTypeId);
                        bodies.add(mailBody);
                    }
                    return bodies;
                }
            }
        }
    }

    //TODO - selle funktsiooni tulmust kuvatakse litsentsi profiili all
    public List<MailBody> findBodiesByLicenseType(Integer id) throws SQLException{
        return null;
    }

}
