angular
    .module('LM')
    .config(function config($routeProvider) {
        $routeProvider
            .when('/license/add', {
                templateUrl: 'components/license/addLicense.html',
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
            .when('/authorisedUser/bylicense/:id', {
                templateUrl: 'components/license/usersView.html',
                controller: 'ProfileLicenseCtrl'
            })
            .when('/products', {
                templateUrl: 'components/product/productsView.html',
                controller: 'ViewProductsCtrl'
            })
            .when('/product/add', {
                templateUrl: 'components/product/addProduct.html',
                controller: 'AddProductCtrl'
            })
            .when('/customers', {
                templateUrl: 'components/customer/customerView.html',
                controller: 'ViewCustomerCtrl'
            })
            .when('/customer/add', {
                templateUrl: 'components/customer/addCustomer.html',
                controller: 'AddCustomerCtrl'
            })
            .when('/customer/:id', {
                templateUrl: 'components/customer/profileCustomer.html',
                controller: 'ProfileCustomerCtrl'
            })
            .when('/', {
                templateUrl: 'components/home/home.html',
                controller: 'HomeCtrl'
            })
            .when('/expiring-licenses', {
                templateUrl: 'expiringLicense/expiringLicenses.html',
                controller: 'ExpiringLicensesCtrl'
            })
            .when('/addMailTemplate',{
                templateUrl: 'components/template/addMailTemplate.html',
                controller: 'TemplateCtrl'
            })
            .when('/events',{
                templateUrl: 'components/license/eventsView.html',
                controller: 'ViewEventsCtrl'
            });

        //$routeProvider.otherwise({redirectTo: '/'}); Is not needed at the moment.
    });
