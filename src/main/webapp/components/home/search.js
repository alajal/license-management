angular
    .module('LM')
    .controller('SearchCtrl', function ($scope, $http, $routeParams) {

        $scope.data = {};

        $http.get('rest/licenses/search/'+$routeParams.keyword).
            then(function (response) {
                $scope.licenses = response.data;
                //console.log($scope.data.licenses[0]);
            }, function (response) {
                console.error('Error occured.');
            });
        $http.get('rest/products/search/'+$routeParams.keyword).
            then(function (response) {

                $scope.products = response.data;
            }, function (response) {

                console.error('Error occured with Product get request.');
            });

        $http.get('rest/customers/search/'+$routeParams.keyword).
            then(function (response) {
                // this callback will be called asynchronously
                // when the response is available
                $scope.customers = response.data;
            }, function (response) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.

                console.error('[customerView.js] Error retrieving license owners.');
            });

        console.log($scope.data);
    });
