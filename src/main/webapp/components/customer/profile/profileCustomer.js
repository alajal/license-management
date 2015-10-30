// Add controller to module.
angular
    .module('LM')
    .controller('ProfileCustomerCtrl', function ($scope, $http, $routeParams) {

        $http.get('rest/licenses').
            then(function (response) {
                $scope.licenses = response.data;
            }, function (response) {
                console.error('Mis iganes.');
            });

        $http.get('rest/customers').
            then(function (response) {
                $scope.customer = response.data[$routeParams.id-1];
            }, function (response) {
                console.error('[customerView.js] Error retrieving license owners.');
            });
    });
