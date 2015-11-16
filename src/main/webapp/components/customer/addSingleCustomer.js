angular
    .module('LM')
    .controller('AddSingleCustomerCtrl', function ($scope, $http, $window) {
        $scope.applicant = {};
        $scope.addNewSingleCustomer = function () {
            if (!$scope.form.$valid) {
                return;
            }
            $http.post('rest/customers', $scope.applicant).
                then(function (response) {
                    $window.location.href = '#/';
                }, function (response) {
                    console.error('There was something wrong with the add single customer request.');
                });
        };

    });

