package ee.cyber.licensing.dao;

import ee.cyber.licensing.entity.MailAttachment;
import ee.cyber.licensing.entity.MailBody;

import java.util.Base64;

public class FileRepository {

    public void saveFile(MailAttachment attachment){
        byte[] fileData = Base64.getDecoder().decode(attachment.getData());
    }

    public void saveMailBody(MailBody mailBody){

    }

}
