package ee.cyber.licensing.entity;

public class MailAttachment {
    private Integer id;
    private String data;
    private String fileName;

    public MailAttachment() {
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

    public void setData(String data) {
        this.data = data;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
