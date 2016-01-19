'use strict';

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
                    var c = response.data;
                    console.log("c");
                    console.log(c);
                    createEvent(c);
                    $window.location.href = '#/';
                }, function (response) {
                    $scope.errorMessage = 'Something went wrong. Maybe customer with this name already exists?';
                    console.error(response);
                });
        };

        function createEvent(customer) {
            $scope.event = {
                name: 'New Customer added',
                description: '*user name* added customer ' + customer.organizationName,
                type: 'Add'
            };

            $http.post('rest/events/' + 0, $scope.event).
                then(function (response) {
                }, function (response) {
                    console.error(response.errors);
                });
        }

    });
