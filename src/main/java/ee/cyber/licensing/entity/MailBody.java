package ee.cyber.licensing.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class MailBody {
    private Integer id;
    private String subject;
    private String body;
    private String contact_ids;
    private Integer licenseTypeId;


    public MailBody() {
    }

    public MailBody(Integer id, String subject, String body, Integer licenseTypeId) {
        this.id = id;
        this.subject = subject;
        this.body = body;
        this.licenseTypeId = licenseTypeId;
    }
    public MailBody(Integer id, String subject, String body, Integer licenseTypeId, String contact_ids) {
        this.id = id;
        this.subject = subject;
        this.body = body;
        this.licenseTypeId = licenseTypeId;
        this.contact_ids = contact_ids;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getLicenseTypeId() {
        return licenseTypeId;
    }

    public void setLicenseTypeId(int licenseTypeId) {
        this.licenseTypeId = licenseTypeId;
    }

    public String getContact_ids() {
        return contact_ids;
    }

    public void setContact_ids(String contact_ids) {
        this.contact_ids = contact_ids;
    }
}
