package ee.cyber.licensing.entity;


import java.util.Date;

public class License {
    Integer id;
    String productName;
    String name;
    String organization;
    String email;
    String skype;
    String phone;
    String applicationArea;
    String contractNumber;
    Date validFrom;
    Date validTill;
    State licenseState;
    Integer predecessor;
    Date applicationSubmitDate;

    //id, productName,contactName, organization, email, skype, phone, applicationArea, contractnr(Dan loob ise),
    //dan saab valida kahe state'i vahel, license active state siduda validFrom, preecessor on täiendinfo all


    //DEFAULT CONSTRUCTOR
    public License() {
    }


    public License(Integer id, String productName, String name, String organization, String email, String skype, String phone, String applicationArea) {
        this.id = id;
        this.productName = productName;
        this.name = name;
        this.organization = organization;
        this.email = email;
        this.skype = skype;
        this.phone = phone;
        this.applicationArea = applicationArea;
    }


    //UNUSED GETTERS AND SETTERS FOR JERSEY WHEN DEALING WITH ARRAYS

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
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

    public String getApplicationArea() {
        return applicationArea;
    }

    public void setApplicationArea(String applicationArea) {
        this.applicationArea = applicationArea;
    }
}
