package ee.cyber.licensing.entity;

public class MailBody {
    private Integer id;
    private String subject;
    private String body;
    private String contact_ids;
    private LicenseType licenseType;


    public MailBody() {
    }

    public MailBody(Integer id, String subject, String body, LicenseType licenseType) {
        this.id = id;
        this.subject = subject;
        this.body = body;
        this.licenseType = licenseType;
    }
    public MailBody(Integer id, String subject, String body, LicenseType licenseType, String contact_ids) {
        this.id = id;
        this.subject = subject;
        this.body = body;
        this.licenseType = licenseType;
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

    public LicenseType getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(LicenseType licenseType) {
        this.licenseType = licenseType;
    }

    public String getContact_ids() {
        return contact_ids;
    }

    public void setContact_ids(String contact_ids) {
        this.contact_ids = contact_ids;
    }
}
