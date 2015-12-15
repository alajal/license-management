package ee.cyber.licensing.entity;


import java.time.LocalDate;
import java.util.Date;

public class DeliveredRelease {

    Integer id;
    License license;
    Release release;
    Date deliveryDate;
    String user;
    String userEmail;

    public DeliveredRelease(Integer id, License license, Release release, Date deliveryDate, String user) {
        this.id = id;
        this.license = license;
        this.release = release;
        this.deliveryDate = deliveryDate;
        this.user = user;
    }

    public DeliveredRelease(License license, Release release, Date deliveryDate, String user) {
        this.license = license;
        this.release = release;
        this.deliveryDate = deliveryDate;
        this.user = user;
    }

    public DeliveredRelease(License license, Date deliveryDate, Release release, String user, String userEmail) {
        this.license = license;
        this.deliveryDate = deliveryDate;
        this.release = release;
        this.user = user;
        this.userEmail = userEmail;
    }

    public Integer getId() {
        return id;
    }

    public License getLicense() {
        return license;
    }

    public Release getRelease() {
        return release;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public String getUser() {
        return user;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLicense(License license) {
        this.license = license;
    }

    public void setRelease(Release release) {
        this.release = release;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
