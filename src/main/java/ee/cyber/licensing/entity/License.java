package ee.cyber.licensing.entity;


import java.sql.Date;

public class License {
    Integer id;

    Product product;
    Customer customer;
    Release release;

    String contractNumber;
    State state;
    String predecessorLicenseId;
    Date validFrom;
    Date validTill;
    LicenseType type;
    Date applicationSubmitDate;
    Date latestDeliveryDate;

    public License() {
    }

    public License(Product product, Release release, Customer customer, String contractNumber, Date validFrom, Date validTill, State state, String predecessorLicenseId, Date applicationSubmitDate, Date latestDeliveryDate) {
        this.product = product;
        this.release = release;
        this.customer = customer;
        this.contractNumber = contractNumber;
        this.state = state;
        this.predecessorLicenseId = predecessorLicenseId;
        this.applicationSubmitDate = applicationSubmitDate;
        this.latestDeliveryDate = latestDeliveryDate;
    }

    public License(Integer id, Product product, Customer customer, Release release, String contractNumber, State state, String predecessorLicenseId, Date validFrom, Date validTill, LicenseType type, Date applicationSubmitDate, Date latestDeliveryDate) {
        this.id = id;
        this.product = product;
        this.customer = customer;
        this.release = release;
        this.contractNumber = contractNumber;
        this.state = state;
        this.predecessorLicenseId = predecessorLicenseId;
        this.validFrom = validFrom;
        this.validTill = validTill;
        this.type = type;
        this.applicationSubmitDate = applicationSubmitDate;
        this.latestDeliveryDate = latestDeliveryDate;
    }

    //UNUSED GETTERS AND SETTERS FOR JERSEY WHEN DEALING WITH ARRAYS

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Release getRelease() {
        return release;
    }

    public void setRelease(Release release) {
        this.release = release;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public LicenseType getType() {
        return type;
    }

    public void setType(LicenseType type) {
        this.type = type;
    }

    public void setValidTill(Date validTill) {
        this.validTill = validTill;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTill() {
        return validTill;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public String getPredecessorLicenseId() {
        return predecessorLicenseId;
    }

    public void setPredecessorLicenseId(String predecessorLicenseId) {
        this.predecessorLicenseId = predecessorLicenseId;
    }

    public Date getApplicationSubmitDate() {
        return applicationSubmitDate;
    }

    public void setApplicationSubmitDate(Date applicationSubmitDate) {
        this.applicationSubmitDate = applicationSubmitDate;
    }

    public Date getLatestDeliveryDate() {
        return latestDeliveryDate;
    }

    public void setLatestDeliveryDate(Date latestDeliveryDate) {
        this.latestDeliveryDate = latestDeliveryDate;
    }
}
