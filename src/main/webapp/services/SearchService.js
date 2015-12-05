app.service('SearchService', function () {

    var searchFilters = {
        licenses : true,
        customers : true,
        products : true,
        states : {
          REJECTED : true,
          NEGOTIATED : true,
          WAITING_FOR_SIGNATURE : true,
          ACTIVE : true,
          EXPIRATION_NEARING : true,
          TERMINATED : true
        }
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

    //STATES
    this.getREJECTED = function () {
        return searchFilters.states.REJECTED;
    };
    this.getNEGOTIATED = function () {
        return searchFilters.states.NEGOTIATED;
    };
    this.getWAITING_FOR_SIGNATURE = function () {
        return searchFilters.states.WAITING_FOR_SIGNATURE;
    };
    this.getACTIVE = function () {
        return searchFilters.states.ACTIVE;
    };
    this.getEXPIRATION_NEARING = function () {
        return searchFilters.states.EXPIRATION_NEARING;
    };
    this.getTERMINATED = function () {
        return searchFilters.states.TERMINATED;
    };

    this.setREJECTED = function (state) {
      searchFilters.states.REJECTED = state;
    };
    this.setNEGOTIATED = function (state) {
      searchFilters.states.NEGOTIATED = state;
    };
    this.setWAITING_FOR_SIGNATURE = function (state) {
      searchFilters.states.WAITING_FOR_SIGNATURE = state;
    };
    this.setACTIVE = function (state) {
      searchFilters.states.ACTIVE = state;
    };
    this.setEXPIRATION_NEARING = function (state) {
      searchFilters.states.EXPIRATION_NEARING = state;
    };
    this.setTERMINATED = function (state) {
      searchFilters.states.TERMINATED = state;
    };
});
