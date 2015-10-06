angular
    .module('LM')
    .controller('ViewProductsCtrl', function ($scope, $http) {

        $http.get('rest/products').
            then(function (response) {

                $scope.products = response.data;
            }, function (response) {

                console.error('Error occured.');
            });

    });
