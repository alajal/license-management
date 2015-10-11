package ee.cyber.licensing.entity;

public class LicenseOwner {

    Integer id;
    String name;
    String address;
    String webpage;
    String registrationCode;
    String phone;
    String bankAccount;
    String fax;
    String unitOrFaculty;

    public LicenseOwner(Integer id, String name, String address, String webpage, String registrationCode,
            String phone, String bankAccount, String fax, String unitOrFaculty) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.webpage = webpage;
        this.registrationCode = registrationCode;
        this.phone = phone;
        this.bankAccount = bankAccount;
        this.fax = fax;
        this.unitOrFaculty = unitOrFaculty;
    }

    public LicenseOwner() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LicenseOwner licenseOwner = (LicenseOwner) o;

        if (!name.equals(licenseOwner.name)) return false;
        if (!unitOrFaculty.equals(licenseOwner.unitOrFaculty)) return false;

        return true;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
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
}