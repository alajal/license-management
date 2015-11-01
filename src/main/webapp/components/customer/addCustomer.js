// Add controller to module.
angular
    .module('LM')
    .controller('AddCustomerCtrl', function ($scope, $http, $window, LicensingService) {
        $scope.saveData = function () {
            if (!$scope.form.$valid) {
                return;
            }

            LicensingService.addCustomer($scope.applicant);

            console.log($scope.applicant);
            $window.location.href = '#/product/add';

        }


    });

