package ee.cyber.licensing.api;

import ee.cyber.licensing.dao.FileRepository;
import ee.cyber.licensing.entity.License;
import ee.cyber.licensing.entity.LicenseType;
import ee.cyber.licensing.entity.MailAttachment;
import ee.cyber.licensing.entity.MailBody;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Path("template")
public class FileResource {
    @Inject
    private FileRepository fileRepository;

    @GET
    @Produces("application/json")
    public List<MailBody> getBodies() throws Exception {
        return fileRepository.findBodies();
    }

    @Path("/attachment")
    @POST
    public void saveFile(MailAttachment mailAttachment) throws Exception {
        fileRepository.saveFile(mailAttachment);
    }

    @Path("/mailBody")
    @POST
    public void saveBody(MailBody mailBody) throws Exception {
        List<String> keywords = Arrays.asList("$(organizationName)", "$(contactName)", "$(phone)", "$(email)", "$(product)",
                "$(release)");
        fileRepository.saveMailBody(mailBody);
    }

    @Path("/mailbodies/{licenseTypeId}")
    @GET
    public List<MailBody> getMailBodiesByLicenseType(@PathParam("licenseTypeId")Integer licenseTypeId) throws Exception {
        return fileRepository.findBodiesByLicenseType(licenseTypeId);
    }

    @Path("/fileIdAndName")
    @GET
    public List<MailAttachment> getFileIdsAndNames() throws Exception {
        return fileRepository.findAttachments();
    }

    @Path("/{id}")
    @PUT
    public MailBody editCustomer(@PathParam("id") Integer id, MailBody template) throws Exception {
        if (Objects.equals(template.getId(), id)) {
            return fileRepository.updatetemplate(template);
        } else {
            throw new Exception("The template with id " + id + "was not possible to retrieve");
        }
    }

    @Path("/{templateId}")
    @DELETE
    public void deleteTemplate(@PathParam("templateId") Integer templateId) throws SQLException {
        fileRepository.deleteTemplate(templateId);
    }

    @Path("/{templateId}")
    @GET
    public MailBody getTemplateById(@PathParam("templateId") Integer templateId) throws SQLException {
        return fileRepository.findTemplateById(templateId);
    }
}
