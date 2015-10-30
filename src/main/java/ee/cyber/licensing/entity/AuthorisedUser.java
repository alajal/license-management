package ee.cyber.licensing.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown=true)
public class AuthorisedUser {

    Integer id;
    License license;
    String firstName;
    String lastName;
    String email;
    String occupation;

    public AuthorisedUser(){

    }

    public AuthorisedUser(Integer id, License license, String firstName, String lastName, String email, String occupation) {
        this.id = id;
        this.license = license;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.occupation = occupation;
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public License getLicense() { return license; }

    public void setLicense(License license) { this.license = license; }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

}
