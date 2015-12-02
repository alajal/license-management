package ee.cyber.licensing.entity;

public class Contact {
    String contactName;
    String email;
    String skype;
    String phone;

    public Contact() {
    }

    public Contact(String contactName, String email, String skype, String phone) {
        this.contactName = contactName;
        this.email = email;
        this.skype = skype;
        this.phone = phone;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /*    public String getContactFirstName() {
        return contactFirstName;
    }

    public String getContactLastName() {
        return contactLastName;
    }

    public void setContactFirstName(String contactFirstName) {
        this.contactFirstName = contactFirstName;
    }

    public void setContactLastName(String contactLastName) {
        this.contactLastName = contactLastName;
    }*/

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
