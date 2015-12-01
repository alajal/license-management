package ee.cyber.licensing.entity;

public class LicenseType {
    private int id;
    private String validityPeriod;
    private double cost;
    private int mailBodyId;

    public LicenseType() {
    }

    public String getValidityPeriod() {
        return validityPeriod;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setValidityPeriod(String validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getMailBodyId() {
        return mailBodyId;
    }

    public void setMailBodyId(int mailBodyId) {
        this.mailBodyId = mailBodyId;
    }
}
