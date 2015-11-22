package ee.cyber.licensing.api;

import ee.cyber.licensing.dao.FileRepository;
import ee.cyber.licensing.entity.License;
import ee.cyber.licensing.entity.MailAttachment;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

@Path("file")
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

    @Path("/mailBody")
    @POST
    public void saveBody(byte[] mailBody) throws Exception {
        //MailBody save = fileRepository.save(mailBody);

    }


    @Path("/attachment")
    @POST
    public void saveFile(byte[] mailAttachment) throws Exception {
        fileRepository.saveFile(mailAttachment);
    }


}
