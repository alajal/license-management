angular
    .module('LM')
    .controller('SearchCtrl', function ($scope, $http, $routeParams, SearchService) {

        $scope.data = {};

        //
        $scope.licensesBool = SearchService.getLicenses();
        $scope.customersBool = SearchService.getCustomers();
        $scope.productsBool = SearchService.getProducts();
        //console.log($scope.licensesBool);
        console.log($scope.customersBool);
        console.log($scope.productsBool);

        if($scope.licensesBool == true) {
          $http.get('rest/licenses/search/'+$routeParams.keyword).
              then(function (response) {
                  $scope.licenses = response.data;
                  //console.log($scope.data.licenses[0]);
              }, function (response) {
                  console.error('Error occured.');
              });
        }

        if($scope.customersBool == true) {
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
        }

        if($scope.productsBool == true) {
        $http.get('rest/products/search/'+$routeParams.keyword).
            then(function (response) {

                $scope.products = response.data;
            }, function (response) {

                console.error('Error occured with Product get request.');
            });
        }

        console.log($scope.data);
    });
