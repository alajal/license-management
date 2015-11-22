package ee.cyber.licensing.entity;

public class MailBody {
    private final String subject;
    private final String body;

    public MailBody(String subject, String body) {
        this.subject = subject;
        this.body = body;
    }
}
