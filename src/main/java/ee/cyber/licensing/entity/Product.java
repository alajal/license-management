package ee.cyber.licensing.entity;

public class Product {

    Integer id;
    String name;
    String release;

    public Product(Integer id, String name, String release) {
        this.id = id;
        this.name = name;
        this.release = release;
    }

    public Product() {
    }

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

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }
}
