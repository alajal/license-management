// Add controller to module.
angular
    .module('LM')
    .controller('AddCustomerCtrl', function ($scope, $http, $window) {


        $scope.saveData = function () {
            if (!$scope.form.$valid) {
                return;
            }

            console.log($scope.applicant);

            $http.post('rest/customers', $scope.applicant).
                then(function (response) {
                    $window.location.href = '#/product/add';
                }, function (response) {

                    console.error('[addCustomer.js] Error');
                });
        }

    });

