/**
 * Controller for getting navigation bar (header) links from header.json.
 */

// Add controller to module.
angular
    .module('LM')
    .controller('HeaderController', function ($http, $scope, $location, expiringLicenses, SearchService) {
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

        $scope.find = function(keyword) {
          SearchService.setLicenses($scope.checkLicense);
          SearchService.setCustomers($scope.checkCustomer);
          SearchService.setProducts($scope.checkProduct);

          // Set states
          SearchService.setREJECTED($scope.checkREJECTED);
          SearchService.setNEGOTIATED($scope.checkNEGOTIATED);
          SearchService.setWAITING_FOR_SIGNATURE($scope.checkWAITING_FOR_SIGNATURE);
          SearchService.setACTIVE($scope.checkACTIVE);
          SearchService.setEXPIRATION_NEARING($scope.checkEXPIRATION_NEARING);
          SearchService.setTERMINATED($scope.checkTERMINATED);

          $location.path('/search/'+keyword).replace();
        }
    });
