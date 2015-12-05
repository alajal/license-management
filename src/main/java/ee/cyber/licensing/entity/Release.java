package ee.cyber.licensing.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;


@JsonIgnoreProperties({"editing", "new", "copy", "product"})
public class Release {

    Integer id;
    String versionNumber;
    Date additionDate;

    public Release(Integer id, String vn, Date date){
        this.id = id;
        this.versionNumber = vn;
        this.additionDate = date;
    }

    public Release(){}

    public void setId(Integer id) {
        this.id = id;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public void setAdditionDate(Date additionDate) {
        this.additionDate = additionDate;
    }

    public Integer getId() {

        return id;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public Date getAdditionDate() {
        return additionDate;
    }
}
