package ee.cyber.licensing.entity;

public class Applicant {

    String organizationName;
    String applicationArea;

    String contactName;
    String email;
    String skype;
    String phone;


    public Applicant() {
    }

    public Applicant(String organizationName, String applicationArea, String contactName, String email, String skype, String phone) {
        this.organizationName = organizationName;
        this.applicationArea = applicationArea;
        this.contactName = contactName;
        this.email = email;
        this.skype = skype;
        this.phone = phone;
    }



    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getApplicationArea() {
        return applicationArea;
    }

    public void setApplicationArea(String applicationArea) {
        this.applicationArea = applicationArea;
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
