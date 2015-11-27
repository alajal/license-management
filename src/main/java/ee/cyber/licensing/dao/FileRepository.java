package ee.cyber.licensing.dao;

import ee.cyber.licensing.entity.MailAttachment;
import ee.cyber.licensing.entity.MailBody;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

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
            statement.setString(2, attachment.getData());
            statement.execute();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    attachment.setId(generatedKeys.getInt(1));
                }
            }
        }

    }

    public void saveMailBody(MailBody mailBody){

    }

}
