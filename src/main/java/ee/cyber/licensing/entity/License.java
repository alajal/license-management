package ee.cyber.licensing.entity;


import java.sql.Date;

public class License {
    Integer id;

    Product product;
    Customer customer;

    String contractNumber;
    State state;
    String predecessorLicenseId;
    Date validFrom;
    Date validTill;
    Date applicationSubmitDate;

    public License() {
    }

    public License(Product product, Customer customer, String contractNumber, Date validFrom, Date validTill, State state, String predecessorLicenseId, Date applicationSubmitDate) {
        this.product = product;
        this.customer = customer;
        this.contractNumber = contractNumber;
        this.validFrom = validFrom;
        this.validTill = validTill;
        this.state = state;
        this.predecessorLicenseId = predecessorLicenseId;
        this.applicationSubmitDate = applicationSubmitDate;
    }

    public License(Integer id, Product product, Customer customer, String contractNumber, State state, String predecessorLicenseId, Date validFrom, Date validTill, Date applicationSubmitDate) {
        this.id = id;
        this.product = product;
        this.customer = customer;
        this.contractNumber = contractNumber;
        this.state = state;
        this.predecessorLicenseId = predecessorLicenseId;
        this.validFrom = validFrom;
        this.validTill = validTill;
        this.applicationSubmitDate = applicationSubmitDate;
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

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTill() {
        return validTill;
    }

    public void setValidTill(Date validTill) {
        this.validTill = validTill;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
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
}
