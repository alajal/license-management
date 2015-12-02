package ee.cyber.licensing.entity;

public class MailBody {
    private Integer id;
    private String subject;
    private String body;

    public MailBody() {
    }

    public MailBody(Integer id, String subject, String body) {
        this.id = id;
        this.subject = subject;
        this.body = body;
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
}
