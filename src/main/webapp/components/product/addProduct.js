// Add controller to module.
angular
    .module('LM')
    .controller('AddProductCtrl', function ($scope, $http, $window) {

        $scope.saveData = function () {
            console.log($scope.product);

            if (!$scope.form.$valid) {
                return;
            }

            $http.post('rest/products', $scope.product).
                then(function (response) {

                    $window.location.href = '#/products';
                }, function (response) {

                    console.error('There was something wrong with the add product request.');
                });
        }
    });

