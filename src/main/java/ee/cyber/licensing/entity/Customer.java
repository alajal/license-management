package ee.cyber.licensing.entity;

public class Customer {


    private Integer id;

    private String organizationName;
    private String applicationArea;

    private String address;
    private String webpage;
    private String registrationCode;
    private String phone;
    private String bankAccount;
    private String fax;
    private String unitOrFaculty;

    public Customer() {
    }

    public Customer(String organizationName, String applicationArea) {
        this.organizationName = organizationName;
        this.applicationArea = applicationArea;
    }

    public Customer(Integer id, String organizationName, String applicationArea, String address, String webpage, String registrationCode, String phone, String bankAccount, String fax, String unitOrFaculty) {
        this.id = id;
        this.organizationName = organizationName;
        this.applicationArea = applicationArea;
        this.address = address;
        this.webpage = webpage;
        this.registrationCode = registrationCode;
        this.phone = phone;
        this.bankAccount = bankAccount;
        this.fax = fax;
        this.unitOrFaculty = unitOrFaculty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        return organizationName.equals(customer.organizationName) && unitOrFaculty.equals(customer.unitOrFaculty);

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String name) {
        this.organizationName = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWebpage() {
        return webpage;
    }

    public void setWebpage(String webpage) {
        this.webpage = webpage;
    }

    public String getRegistrationCode() {
        return registrationCode;
    }

    public void setRegistrationCode(String registrationCode) {
        this.registrationCode = registrationCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getUnitOrFaculty() {
        return unitOrFaculty;
    }

    public void setUnitOrFaculty(String unitOrFaculty) {
        this.unitOrFaculty = unitOrFaculty;
    }

    public String getApplicationArea() {
        return applicationArea;
    }

    public void setApplicationArea(String applicationArea) {
        this.applicationArea = applicationArea;
    }
}