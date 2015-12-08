package ee.cyber.licensing.entity;

public class LicenseType {
    private int id;
    private String name;
    private int validityPeriod;
    private double cost;

    public LicenseType() {
    }

    public LicenseType(int id, String name, int validityPeriod, double cost) {
        this.id = id;
        this.name = name;
        this.validityPeriod = validityPeriod;
        this.cost = cost;
    }

    public int getValidityPeriod() {
        return validityPeriod;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setValidityPeriod(int validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
