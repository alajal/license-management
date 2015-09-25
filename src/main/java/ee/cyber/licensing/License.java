package ee.cyber.licensing;

public class License {
    String product;
    String name;
    String organization;
    String email;
    String skype;
    String phone;
    String applicationArea;

    //DEFAULT CONSTRUCTOR FOR GSON
    public License() {
    }

    public License(String product, String name, String organization, String email, String skype, String phone, String applicationArea) {
        this.product = product;
        this.name = name;
        this.organization = organization;
        this.email = email;
        this.skype = skype;
        this.phone = phone;
        this.applicationArea = applicationArea;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
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
