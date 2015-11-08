package ee.cyber.licensing.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.Date;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Event {

    //Variables
    Integer id;
    License license;
    String name;
    String description;
    String type;
    Date dateCreated;


    //Constructors
    public Event() {
    }

    public Event(Integer id, License license, String name, String description, String type, Date dateCreated) {
        this.id = id;
        this.license = license;
        this.name = name;
        this.description = description;
        this.type = type;
        this.dateCreated = dateCreated;
    }


    //Getters/Setters
    public Integer getId() {  return id;  }

    public void setId(Integer id) { this.id = id; }

    public String getName() {  return name;  }

    public void setName(String name) { this.name = name; }

    public String getDescription() {  return description; }

    public void setDescription(String description) {  this.description = description; }

    public String getType() { return type;  }

    public void setType(String type) {  this.type = type; }

    public License getLicense() { return license; }

    public void setLicense(License license) { this.license = license; }

    public Date getDateCreated() { return dateCreated; }

    public void setDateCreated(Date dateCreated) { this.dateCreated = dateCreated; }
}
