// Add controller to module.
angular
    .module('LM')
    .controller('AddProductCtrl', function ($scope, $http, $window, LicensingService) {
        $http.get('rest/products', $scope.product).success(function (result) {
            $scope.products = result;
        });

        $scope.addNewProduct = function () {
            if (!$scope.addProductForm.$valid) {
                return;
            }
            LicensingService.addProductNew($scope.newProduct);
            $window.location.href = '#/license/add';
        };

        $scope.chooseProduct = function () {
            if (!$scope.chooseProductForm.$valid) {
                return;
            }
            LicensingService.addProduct($scope.product);
            $window.location.href = '#/license/add';
        }
    });

