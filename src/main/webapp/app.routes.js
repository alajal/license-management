angular
    .module('LM')
    .config(function config($routeProvider) {
        $routeProvider
            .when('/license/add', {
                templateUrl: 'components/license/add/addLicense.html',
                controller: 'AddLicenseCtrl'
            })
            .when('/licenses', {
                templateUrl: 'components/license/licensesView.html',
                controller: 'ViewLicensesCtrl'
            })
            .when('/product/add', {
                templateUrl: 'components/product/add/addProduct.html',
                controller: 'AddProductCtrl'
            })
            .when('/licenseOwners', {
                templateUrl: 'components/licenseOwner/licenseOwnersView.html',
                controller: 'ViewLicenseOwnersCtrl',
            })
            .when('/licenseOwner/add', {
                templateUrl: 'components/licenseOwner/add/addLicenseOwner.html',
                controller: 'AddLicenseOwnerCtrl',
            });
        //$routeProvider.otherwise({redirectTo: '/'}); Is not needed at the moment.
    });