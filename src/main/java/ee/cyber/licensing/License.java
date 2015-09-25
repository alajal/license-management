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
}
