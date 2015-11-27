package ee.cyber.licensing.entity;

public class MailAttachment {
    private Integer id;
    private final String data;
    private final String fileName;

    public MailAttachment(String data, String fileName) {
        this.data = data;
        this.fileName = fileName;
    }

    public MailAttachment(Integer id, String data, String fileName) {
        this.id = id;
        this.data = data;
        this.fileName = fileName;
    }

    public String getData() {
        return data;
    }

    public String getFileName() {
        return fileName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
