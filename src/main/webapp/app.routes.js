angular
    .module('LM')
    .config(config);

function config($routeProvider) {
    $routeProvider
        .when('/license/add', {
            templateUrl: 'components/license/add/addLicense.html',
            controller: 'AddLicenseCtrl',
        })
        .when('/licenses', {
            templateUrl: 'components/license/licensesView.html',
            controller: 'ViewLicensesCtrl',
        })
        .when('/product/add', {
            templateUrl: 'components/product/add/addProduct.html',
            controller: 'AddProductCtrl',
        })
    //$routeProvider.otherwise({redirectTo: '/'}); Is not needed at the moment.
};