angular
    .module('LM')
    .controller('SearchCtrl', function ($scope, $http, $routeParams, SearchService) {
        var ss = SearchService;
        $scope.data = {};
        $scope.licensesBool = ss.getLicenses();
        $scope.customersBool = ss.getCustomers();
        $scope.productsBool = ss.getProducts();


        if($scope.licensesBool == true) {
          $scope.states = {
            rejected : ss.getREJECTED(),
            negotiated : ss.getNEGOTIATED(),
            waiting_for_signature : ss.getWAITING_FOR_SIGNATURE(),
            active : ss.getACTIVE(),
            expiration_nearing : ss.getEXPIRATION_NEARING(),
            terminated : ss.getTERMINATED()
          };

          $http.put('rest/licenses/search/'+$routeParams.keyword, $scope.states).
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

                console.error('[customersView.js] Error retrieving license owners.');
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
