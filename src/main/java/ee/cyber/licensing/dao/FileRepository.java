package ee.cyber.licensing.dao;

import ee.cyber.licensing.entity.MailAttachment;
import ee.cyber.licensing.entity.MailBody;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
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

    public void saveMailBody(MailBody mailBody) throws SQLException {
        try (Connection conn = ds.getConnection()) {
            PreparedStatement statement = conn.prepareStatement("INSERT INTO MailBody (subject, body) " +
                    "VALUES (?, ?);");
            statement.setString(1, mailBody.getSubject());
            statement.setString(2, mailBody.getBody());
            statement.execute();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    mailBody.setId(generatedKeys.getInt(1));
                }
            }
        }

    }

}
