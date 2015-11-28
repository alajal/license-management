// Add controller to module.
angular
    .module('LM')
    .controller('AddSingleProductCtrl', function ($scope, $http, $window) {

        $scope.addNewSingleProduct = function () {
            if (!$scope.addProductForm.$valid) {
                return;
            }
            $scope.product.releases = [];

            if($scope.release != null){
                $scope.product.releases.push($scope.release);
                $scope.release.additionDate = new Date();
                console.log($scope.release.additionDate);
            }


            $http.post('rest/products', $scope.product).
                then(function (response) {
                    $window.location.href = '#/';
                }, function (response) {
                    console.error('There was something wrong with the add product request.');
                });
        };
    });
