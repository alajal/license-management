package ee.cyber.licensing.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class MailBody {
    private Integer id;
    private String subject;
    private String body;
    private Integer licenseTypeId;

    public MailBody() {
    }

    //TODO konstruktorisse lisada licesnetypeid
    public MailBody(Integer id, String subject, String body, Integer licenseTypeId) {
        this.id = id;
        this.subject = subject;
        this.body = body;
        this.licenseTypeId = licenseTypeId;
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
}
