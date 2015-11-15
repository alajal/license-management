/**
 * Controller for getting navigation bar (header) links from header.json.
 */

// Add controller to module.
angular
    .module('LM')
    .controller('HeaderController', function ($http, $scope, $location, expiringLicenses) {
        var vm = this;

        $scope.expiringLicenses = false;
        $scope.numberOfExpiringLicenses = 0;

        $http.get('shared/header/header.json').success(function (data) {
            vm.menu = data;
        });

        vm.isActive = function (route) {
            return route === $location.path();
        };

        $scope.$watch(function () {
            return expiringLicenses.isExpiring
            },
                function(newVal, oldVal) {
                    $scope.expiringLicenses = newVal;
                }, true);

        $scope.$watch(function () {
                return expiringLicenses.numberOfExpiringLicenses
            },
            function(newVal, oldVal) {
                $scope.numberOfExpiringLicenses = newVal;
            }, true);

    });
