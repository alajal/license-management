// Add controller to module.
angular
    .module('LM')
    .controller('ViewCustomerCtrl', function ($scope, $http) {

        $http.get('rest/customers').
            then(function (response) {
                // this callback will be called asynchronously
                // when the response is available
                $scope.customers = response.data;
            }, function (response) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.

                console.error('[customerView.js] Error retrieving license owners.');
            });

    });
