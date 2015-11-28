package ee.cyber.licensing.api;

import ee.cyber.licensing.dao.FileRepository;
import ee.cyber.licensing.entity.License;
import ee.cyber.licensing.entity.MailAttachment;
import ee.cyber.licensing.entity.MailBody;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Path("template")
public class FileResource {
    @Inject
    private FileRepository fileRepository;

    //byte array
    //jdbc api
   /* @GET
    @Produces("application/json")
    public List<License> getLicenses() throws Exception {
        return fileRepository.find();
    }*/

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

        //find keywords from the text, kui tahetakse templatei

        fileRepository.saveMailBody(mailBody);

    }

}
