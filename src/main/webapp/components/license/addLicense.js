angular
    .module('LM')
    .controller('AddLicenseCtrl', function ($scope, $http, $window, LicensingService) {
        //Prefill
        $scope.prefillProduct = LicensingService.getproduct();
        $scope.prefillCustomer = LicensingService.getApplicant() || LicensingService.getCustomer();
        $scope.prefillContractNumber = LicensingService.getContractNumber();

        //TODO dropdown choose for state (negotiated, cancelled)
        $scope.prefillState = 'NEGOTIATED';
        //TODO dropdown with existing license contract numbers
        $scope.prefillPredecessor = 1;

        $scope.saveData = function () {
            $scope.user = $scope.user || {};

            $scope.user.contractNumber = $scope.prefillContractNumber;
            $scope.user.state = $scope.prefillState;
            $scope.user.predecessorLicenseId = $scope.prefillPredecessor;

            var applicant = LicensingService.getApplicant();
            var customer = LicensingService.getCustomer();
            console.log("Applicant");
            console.log(applicant);
            console.log("Customer");
            console.log(customer);

            //TODO if applicant == 'undefined', siis tuleb valida customer ning post päringut applicantile'ile ei tule
            var product = LicensingService.getproduct();
            //TODO lahendada probleem, kus olemasolevat valitud producti/customeri ei lisata post päringuga uuesti
            //kui applicanti pole, võib loota, et customer on olemas, seega see tuleb anda litsentsile

            $http.post('rest/customers', applicant).
                then(function (response) {
                    $scope.user.customer = response.data;

                    $http.post('rest/products', product).
                        then(function (response) {
                            $scope.user.product = response.data;

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

