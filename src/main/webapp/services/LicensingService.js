app.service('LicensingService', function ($http) {

    var customerL;
    var productL;
    var licenseL;

    this.addCustomer = function (customerV) {
        customerL = customerV;
        console.log(customerL);
    };

    this.addProduct = function (productV) {
        productL = productV;
        console.log(productL);
    };

    this.addLicense = function (licenseV) {
        licenseL = licenseV;
        console.log(licenseL)
    };

    this.getCustomer = function () {
        return customerL;
    };

    this.getproduct = function () {
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