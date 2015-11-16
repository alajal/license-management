angular
    .module('LM')
    .controller('AddCustomerCtrl', function ($scope, $http, $window, LicensingService) {
        $http.get('rest/customers', $scope.customer).success(function (result) {
            $scope.customers = result;
        });

        $scope.addNewCustomer = function () {
            if (!$scope.form.$valid) {
                return;
            }
            LicensingService.addApplicant($scope.applicant);
            $window.location.href = '#/product/add';
        };

        $scope.chooseCustomer = function(){
            if (!$scope.chooseCustomerForm.$valid) {
                return;
            }
            LicensingService.addCustomer($scope.customer);
            $window.location.href = '#/product/add';
        }

    });

