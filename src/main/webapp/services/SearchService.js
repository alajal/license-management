app.service('SearchService', function () {

    var searchFilters = {
        licenses : true,
        customers : true,
        products : true
    };


    this.getLicenses = function () {
        return searchFilters.licenses;
    };

    this.getCustomers = function () {
        return searchFilters.customers;
    };

    this.getProducts = function () {
        return searchFilters.products;
    };

    this.setLicenses = function (filter) {
        searchFilters.licenses = filter;
    };

    this.setCustomers = function (filter) {
        searchFilters.customers = filter;
    };

    this.setProducts = function (filter) {
        searchFilters.products = filter;
    };
});
