'use strict'

angular.module('LM')
    .controller('HomeCtrl', function ($http, $scope, $location, expiringLicenses) {

        $http.get('rest/events').
            then(function (response) {
                $scope.events = response.data;
            }, function (response) {
                console.error('Error occured.' + response);
            });

        $http.get('rest/licenses/expiring/').
            then(function (response) {
                $scope.expiring = response.data;
                if ($scope.expiring.length > 0) {
                    expiringLicenses.setExpiring(true);
                    expiringLicenses.setNumberOfExpiringLicenses($scope.expiring.length);
                }
            }, function (response) {
                console.error('Something went wrong with the expiring licenses get method.');
            });

    });

//todo tõsta eraldi klassi
angular
    .module('LM').service("expiringLicenses", function () {
        this.isExpiring = false;
        this.numberOfExpiringLicenses = 0;
        this.setExpiring = function (boolean) {
            this.isExpiring = boolean;
        };
        this.setNumberOfExpiringLicenses = function (number) {
            this.numberOfExpiringLicenses = number;
        }

    });
