package ee.cyber.licensing.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties("editing")
public class Product {

    Integer id;
    String name;
    //String release;
    List<Release> releases;

    public Product(Integer id, String name, List<Release> releases) {
        this.id = id;
        this.name = name;
        this.releases = releases;
    }

    public Product() {
    }

    //@Override
    /*public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (!name.equals(product.name)) return false;
        if (!release.equals(product.release)) return false;

        return true;
    }*/


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Release> getReleases() {
        return releases;
    }

    public void setReleases(List<Release> releases) {
        this.releases = releases;
    }
}
