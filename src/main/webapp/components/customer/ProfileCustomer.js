'use strict'

angular
    .module('LM')
    .controller('ProfileCustomerCtrl', function ($scope, $http, $routeParams) {
        $scope.editorDisabled = true;
        $http.get('rest/licenses').
            then(function (response) {
                $scope.licenses = response.data;
            }, function (response) {
                console.error('Mis iganes.');
            });

        $http.get('rest/customers').
            then(function (response) {
                $scope.customer = response.data[$routeParams.id - 1];
            }, function (response) {
                console.error('[CustomersView.js] Error retrieving license owners.');
            });

        $scope.enableEditor = function () {
            $scope.editorDisabled = false;
        };

        $scope.disableEditor = function () {
            $scope.editorDisabled = true;
        };

        function createEvent(customer, event_nr) {
            // Event numbers
            // 0 - edit customer info
            // 1 - remove customer
            var customer_name = customer.organizationName;

            $scope.events = [
                {
                    name: 'Modified Customer',
                    description: ' modified customer ' + customer_name,
                    type: 'Modify'
                },
                {
                    name: 'Deleted Customer',
                    description: ' deleted customer ' + customer_name,
                    type: 'Remove'
                }
            ];

            $http.post('rest/events/' + 0, $scope.events[event_nr]).
                then(function (response) {
                }, function (response) {
                    console.error(response.errors);
                });
        }

        $scope.saveProfile = function () {
            $scope.disableEditor();
            console.log("Customer:");
            console.log($scope.customer);
            $http.put('rest/customers/' + $scope.customer.id, $scope.customer).
                then(function (response) {
                    console.log("Customer peale uuendamist:");
                    console.log($scope.customer);
                    createEvent($scope.customer, 0);
                }, function (response) {
                    console.error(response);
                });
        }
    });
