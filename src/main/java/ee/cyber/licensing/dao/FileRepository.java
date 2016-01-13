package ee.cyber.licensing.dao;

import ee.cyber.licensing.entity.*;

import java.sql.Blob;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class FileRepository {

    @Inject
    private Connection conn;

    @Inject
    private LicenseRepository licenseRepository;

    public void saveFile(MailAttachment attachment) throws SQLException {
        byte[] fileData = Base64.getDecoder().decode(attachment.getData());
        //lisada fail andmebaasi blob tulpa
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

    public void getFileDataAsByteArray() throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM MailAttachment")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    //byte[] fileDataAsString = resultSet.getBlob("fileData");

                }
            }
        }
    }

    public MailAttachment findById(int id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM MailAttachment WHERE id = ?")) {
            stmt.setInt(1, id);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (!resultSet.next()) {
                    throw new SQLException("Ei leitud ühtegi Attachmenti id-ga " + id);
                }
                return getFile(resultSet);
            }
        }

    }

    //TODO FileData pole String - pärida blob, kasutada base24 convertimist
    private MailAttachment getFile(ResultSet rs) throws SQLException {
        Blob blob = rs.getBlob("fileData");
        int blobLength = (int) blob.length();
        byte[] blobAsBytes = blob.getBytes(1, blobLength);

        System.out.println("Siin");

        System.out.println(blobAsBytes);
        System.out.println("Siin");


        return new MailAttachment(
                rs.getInt("id"),
                blobAsBytes,
                rs.getString("fileName"));

    }


    public void saveMailBody(MailBody mailBody) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("INSERT INTO MailBody (subject, body, licenseTypeId) " +
                "VALUES (?, ?, ?);");
        statement.setString(1, mailBody.getSubject());
        statement.setString(2, mailBody.getBody());
        statement.setInt(3, mailBody.getLicenseType().getId());
        statement.execute();

        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                mailBody.setId(generatedKeys.getInt(1));
            }
        }
    }


    public List<MailBody> findBodies() throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM MailBody")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                List<MailBody> bodies = new ArrayList<>();
                while (resultSet.next()) {
                    Integer id = resultSet.getInt("id");
                    String subject = resultSet.getString("subject");
                    String body = resultSet.getString("body");
                    Integer licenseTypeId = resultSet.getInt("licenseTypeId");
                    LicenseType licenseTypeById = licenseRepository.getLicenseTypeById(licenseTypeId);
                    MailBody mailBody = new MailBody(id, subject, body, licenseTypeById);
                    bodies.add(mailBody);
                }
                return bodies;
            }
        }
    }


    public List<MailBody> findBodiesByLicenseType(Integer licenseTypeId) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM MailBody WHERE licenseTypeId = ?")) {
            statement.setInt(1, licenseTypeId);
            try (ResultSet resultSet = statement.executeQuery()) {
                List<MailBody> mailBodies = new ArrayList<>();
                while (resultSet.next()) {
                    MailBody body = new MailBody(resultSet.getInt("id"), resultSet.getString("subject"), resultSet.getString("body"),
                            licenseRepository.getLicenseTypeById(licenseTypeId));
                    mailBodies.add(body);
                }
                return mailBodies;
            }
        }

    }

    public List<MailAttachment> findAttachments() throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM MailAttachment")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                List<MailAttachment> attachments = new ArrayList<>();
                while (resultSet.next()) {
                    MailAttachment attachment = new MailAttachment(resultSet.getInt("id"), resultSet.getString("fileName"));
                    attachments.add(attachment);
                }
                return attachments;
            }
        }
    }

    public MailBody updatetemplate(MailBody template) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement("UPDATE MailBody SET subject=?, " +
                "body=?, licenseTypeId=? WHERE id=?")) {
            statement.setString(1, template.getSubject());
            statement.setString(2, template.getBody());
            statement.setInt(3, template.getLicenseType().getId());
            statement.setInt(4, template.getId());
            statement.executeUpdate();
        }
        return template;
    }

    public void deleteTemplate(Integer templateId) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement("DELETE from MailBody where id=?")){
            statement.setInt(1, templateId);
            statement.executeUpdate();
        }
    }

    public MailBody findTemplateById(Integer templateId) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM MailBody WHERE id = ?")) {
            stmt.setInt(1, templateId);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (!resultSet.next()) {
                    throw new SQLException("Ei leitud ühtegi Attachmenti id-ga " + templateId);
                }
                Integer licenseTypeId = resultSet.getInt("licenseTypeId");
                return new MailBody(templateId, resultSet.getString("subject"), resultSet.getString("body"),
                        licenseRepository.getLicenseTypeById(licenseTypeId));
            }
        }
    }
}
