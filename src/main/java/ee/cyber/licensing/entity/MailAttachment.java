package ee.cyber.licensing.entity;

public class MailAttachment {
    private Integer id;
    private String data;
    private byte[] data_b;
    private String fileName;

    public MailAttachment() {
    }

    public MailAttachment(Integer id, String data, String fileName) {
      this.id = id;
      this.data = data;
      this.fileName = fileName;
    }

    public MailAttachment(Integer id, byte[] data_b, String fileName) {
      this.id = id;
      this.data_b = data_b;
      this.fileName = fileName;
    }
    
    public MailAttachment(Integer id, String fileName) {
        this.id = id;
        this.fileName = fileName;
    }

    public String getData() {
        return data;
    }

    public byte[] getData_b() {
        return data_b;
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

    public void setData_b(byte[] data_b) {
        this.data_b = data_b;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
