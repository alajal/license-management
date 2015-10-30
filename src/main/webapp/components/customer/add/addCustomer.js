// Add controller to module.
angular
    .module('LM')
    .controller('AddCustomerCtrl', function ($scope, $http, $window) {


        $scope.saveData = function () {
            if (!$scope.form.$valid) {
                return;
            }

            $http.post('rest/customers', $scope.applicant).
                then(function (response) {
                    $window.location.href = '#/products';
                }, function (response) {

                    console.error('[addCustomer.js] Error');
                });
        }

    });

