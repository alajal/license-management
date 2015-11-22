app.service('LicensingService', function () {

    var applicantL;
    var productL;
    var licenseL;
    var customerL;

    this.addApplicant = function (applicantV) {
        applicantL = applicantV;
        console.log(applicantL);
    };

    this.addCustomer = function (customerV) {
        customerL = customerV;
        console.log(customerL);
    };

    this.addProduct = function (productV) {
        productL = productV;
        console.log(productL);
    };

    this.addProductNew = function(productV){
        productL = productV;
    };

    this.addLicense = function (licenseV) {
        licenseL = licenseV;
        console.log(licenseL)
    };

    this.getApplicant = function () {
        return applicantL;
    };

    this.getCustomer = function () {
        return customerL;
    };

    this.getproduct = function () {
        return productL;
    };

    this.getproductNew = function () {
        return productL;
    };

    this.getLicense = function () {
        return licenseL;
    };

    this.getContractNumber = function () {
        var text = "";
        var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        for (var i = 0; i < 5; i++)
            text += possible.charAt(Math.floor(Math.random() * possible.length));

        return text;
    };

});