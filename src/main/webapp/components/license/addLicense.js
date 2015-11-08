angular
    .module('LM')
    .controller('AddLicenseCtrl', function ($scope, $http, $window, LicensingService) {
        //Prefill
        $scope.prefillProduct = LicensingService.getproduct();
        $scope.prefillCustomer = LicensingService.getCustomer();
        $scope.prefillContractNumber = LicensingService.getContractNumber();

        //TODO dropdown choose for state (negotiated, cancelled)
        $scope.prefillState = 'NEGOTIATED';
        //TODO dropdown with existing license contract numbers
        $scope.prefillPredecessor = 1;

        $scope.saveData = function () {
            if (!$scope.form.$valid) {
                return;
            }

            $scope.user = $scope.user || {};

            $scope.user.contractNumber = $scope.prefillContractNumber;
            $scope.user.state = $scope.prefillState;
            $scope.user.predecessorLicenseId = $scope.prefillPredecessor;

            var customer = LicensingService.getCustomer();
            var product = LicensingService.getproduct();

            $http.post('rest/customers', customer).
                then(function (response) {
                    console.log("customer response:");
                    console.log(response);
                    //$scope.user.customerId = response.data.id;
                    $scope.user.customer = response.data;

                    $http.post('rest/products', product).
                        then(function (response) {
                            console.log("product response:");
                            console.log(response);
                            //$scope.user.productId = response.data.id;
                            $scope.user.product = response.data;
                            console.log($scope.user);

                            $http.post('rest/licenses', $scope.user).
                                //If POST request has been processed:
                                then(function (response) {

                                }, function (response) {
                                    console.error('There was something wrong with the add license request.');
                                });

                        }, function (response) {
                            console.error('There was something wrong with the add product request.');
                        });

                    $window.location.href = '#/';
                }, function (response) {
                    console.error('There was something wrong with the add customer request.');
                });
        }
    });

