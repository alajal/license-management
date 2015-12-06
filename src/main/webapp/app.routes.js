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
            .when('/license/:id/history', {
                templateUrl: 'components/license/history.html',
                controller: 'ViewHistoryCtrl'
            })
            .when('/license/:id/notification', {
                templateUrl: 'components/license/deliveryNotification.html',
                controller: 'DeliveryNotification'
            })
            .when('/authorisedUser/bylicense/:id', {
                templateUrl: 'components/license/usersView.html',
                controller: 'ProfileLicenseCtrl'
            })
            .when('/contactPersons/bylicense/:id', {
                templateUrl: 'components/license/contactPersonView.html',
                controller: 'ProfileLicenseCtrl'
            })
            .when('/products', {
                templateUrl: 'components/product/productsView.html',
                controller: 'ViewProductsCtrl'
            })
            .when('/product/addSingleProduct', {
                templateUrl: 'components/product/addSingleProduct.html',
                controller: 'AddSingleProductCtrl'
            })
            .when('/product/add', {
                templateUrl: 'components/product/chooseProduct.html',
                controller: 'AddProductCtrl'
            })
            .when('/customers', {
                templateUrl: 'components/customer/customersView.html',
                controller: 'ViewCustomerCtrl'
            })
            .when('/customer/add', {
                templateUrl: 'components/customer/addCustomer.html',
                controller: 'AddCustomerCtrl'
            })
            .when('/customer/addSingleCustomer',{
                templateUrl: 'components/customer/addSingleCustomer.html',
                controller: 'AddSingleCustomerCtrl'
            })
            .when('/customer/:id', {
                templateUrl: 'components/customer/profileCustomer.html',
                controller: 'ProfileCustomerCtrl'
            })
            .when('/customer/:id/history', {
                templateUrl: 'components/license/history.html',
                controller: 'ViewHistoryCtrl'
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
            .when('/eventsLicense',{
                templateUrl: 'components/license/eventsView.html',
                controller: 'ViewEventsCtrl'
            })
            .when('/eventsCustomer',{
                templateUrl: 'components/license/eventsView.html',
                controller: 'ViewEventsCtrl'
            })
            .when('/deliveryLicenses',{
                templateUrl: 'components/license/deliveryLicenses.html',
                controller: 'DeliveryLicensesCtrl'
            })
            .when('/search/:keyword/',{
                templateUrl: 'components/home/search.html',
                controller: 'SearchCtrl'
            })
            .when('/licenseType',{
                templateUrl: 'components/license/licenseType.html',
                controller: 'LicenseTypeCtrl'
            });

            //.when('/search/:keyword/'... kohta
            // Millegipärast, kui lisada lõppu kaldkriips, siis
            // location.path(...) laeb lehe uuesti(kontrolleri). Ilma kriipsuta
            // ta seda ei tee. Bug?


        //$routeProvider.otherwise({redirectTo: '/'}); Is not needed at the moment.
    });
