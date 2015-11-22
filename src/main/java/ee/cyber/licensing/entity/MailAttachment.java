package ee.cyber.licensing.entity;

public class MailAttachment {
    private final String data;
    private final String fileName;

    public MailAttachment(String data, String fileName) {
        this.data = data;
        this.fileName = fileName;
    }

    public String getData() {
        return data;
    }

    public String getFileName() {
        return fileName;
    }
}
