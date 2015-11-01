// Add controller to module.
angular
    .module('LM')
    .controller('AddProductCtrl', function ($scope, $http, $window, LicensingService) {

        $scope.saveData = function () {
            console.log($scope.product);

            if (!$scope.form.$valid) {
                return;
            }
            LicensingService.addProduct($scope.product);
            $window.location.href = '#/license/add';
        }
    });

