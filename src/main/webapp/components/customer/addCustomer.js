'use strict'

angular
    .module('LM')
    .controller('AddCustomerCtrl', function ($scope, $http, $window, LicensingService) {
        $http.get('rest/customers', $scope.customer).
            then(function (response) {
                $scope.customers = response.data;
            }, function (response) {
                console.error(response);
            });

        $scope.addNewCustomer = function () {
            if (!$scope.form.$valid) {
                return;
            }
            //Here applicant.organizationNameOrObject is acctually applicant.organizationName, but since there is
            // no such field as "organizationNameOrObject" in $scope.applicant we cannot use that
            $scope.applicant.organizationName = $scope.applicant.organizationNameOrObject;
            LicensingService.addApplicant($scope.applicant);
            $window.location.href = '#/product/add';
        };

        $scope.chooseCustomer = function () {
            //Here applicant.organizationNameOrObject is actually applicant.organizationObject
            console.log("$scope.applicant.organizationNameOrObject)");
            console.log($scope.applicant);
            $scope.applicant = $scope.applicant.organizationNameOrObject;
            LicensingService.chooseCustomer($scope.applicant);
            console.log("$scope.applicant");
            console.log($scope.applicant);
            $window.location.href = '#/product/add';
        }

    });

