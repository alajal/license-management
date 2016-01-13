app.service('LicensingService', function () {

    var applicantL;
    var productL;
    var releaseL;
    var licenseL;
    var customerL;

    this.addApplicant = function (applicantV) {
        applicantL = applicantV;
        customerL = undefined;
    };

    this.chooseCustomer = function (customerV) {
        customerL = customerV;
        applicantL = undefined;
    };

    this.addProduct = function (productV) {
        productL = productV;
    };

    this.addRelease = function (releaseV) {
        releaseL = releaseV;
    };

    this.addProductNew = function (productV) {
        productL = productV;
    };

    this.addLicense = function (licenseV) {
        licenseL = licenseV;
    };

    this.getApplicant = function () {
        return applicantL;
    };

    this.getCustomer = function () {
        return customerL;
    };

    this.getProduct = function () {
        return productL;
    };

    this.getRelease = function () {
        return releaseL;
    };

    this.getProductNew = function () {
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

    var mailSubject;
    var mailBody;
    var mailLicenseTypeId;
    var mailContact;
    var mailAttachmentId;
    var mailLicenseId;

    this.setSubject = function (subject) {
        mailSubject = subject;
    };

    this.setBody = function (body) {
        mailBody = body;
    };

    this.setLicenseType = function (licenseTypeId) {
        mailLicenseTypeId = licenseTypeId;
    };

    this.setMailContact = function (contact) {
        mailContact = contact;
    };

    this.setAttachmentId = function (attachmentId) {
        mailAttachmentId = attachmentId;
    };

    this.setLicenseId = function (licenseId) {
        mailLicenseId = licenseId;
    };

    this.getSubject = function () {
        return mailSubject;
    };

    this.getBody = function () {
        return mailBody;
    };

    this.getLicenseType = function () {
        return mailLicenseTypeId;
    };

    this.getContact = function () {
        return mailContact;
    };

    this.getAttachmentId = function () {
        return mailAttachmentId;
    };

    this.getLicenseId = function () {
        return mailLicenseId;
    }

});
