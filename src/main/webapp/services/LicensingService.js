app.service('LicensingService', function () {

    var applicantL;
    var productL;
    var releaseL;
    var licenseL;
    var customerL;

    this.addApplicant = function (applicantV) {
        applicantL = applicantV;
        customerL = undefined;
        console.log(applicantL);
    };

    this.addCustomer = function (customerV) {
        customerL = customerV;
        applicantL = undefined;
        console.log(customerL);
    };

    this.addProduct = function (productV) {
        productL = productV;
        console.log(productL);
    };

    this.addRelease = function (releaseV) {
        releaseL = releaseV;
        console.log(releaseL);
    }

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

    this.getRelease = function () {
        return releaseL;
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
