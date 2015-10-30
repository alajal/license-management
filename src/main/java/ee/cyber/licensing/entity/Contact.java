package ee.cyber.licensing.entity;

public class Contact {
    Integer id;

    Integer customerId;
    String contactName;
    String email;
    String skype;
    String phone;


    public Contact(Integer customerId, String contactName, String email, String skype, String phone) {
        this.customerId = customerId;
        this.contactName = contactName;
        this.email = email;
        this.skype = skype;
        this.phone = phone;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
