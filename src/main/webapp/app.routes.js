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
            .when('/license/:id', {
                templateUrl: 'components/license/profileLicense.html',
                controller: 'ProfileLicenseCtrl'
            })
/*            .when('/license/edit/:id',{
                templateUrl: 'components/license/editLicense.html',
                controller: 'EditLicenseCtrl'
            })*/
            .when('/products', {
                templateUrl: 'components/product/productsView.html',
                controller: 'ViewProductsCtrl'
            })
            .when('/product/add', {
                templateUrl: 'components/product/add/addProduct.html',
                controller: 'AddProductCtrl'
            })
            .when('/licenseOwners', {
                templateUrl: 'components/licenseOwner/licenseOwnersView.html',
                controller: 'ViewLicenseOwnersCtrl'
            })
            .when('/licenseOwner/add', {
                templateUrl: 'components/licenseOwner/add/addLicenseOwner.html',
                controller: 'AddLicenseOwnerCtrl'
            })
            .when('/licenseOwner/:id', {
                templateUrl: 'components/licenseOwner/profile/profileLicenseOwner.html',
                controller: 'ProfileLicenseOwnerCtrl'
            })
            .when('/edit/:id', {
                templateUrl: 'components/license/edit.html',
                controller: 'EditController'
             });
        //$routeProvider.otherwise({redirectTo: '/'}); Is not needed at the moment.
    });
