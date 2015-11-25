angular
    .module('LM')
    .controller('AddLicenseCtrl', function ($scope, $http, $window, LicensingService) {
        /* $scope.allStates = {1:'CANCELLED',2: 'NEGOTIATED', 3:'WAITING_FOR_SIGNATURE', 4:'ACTIVE', 5:'EXPIRATION_NEARING',
         6:'TERMINATED'};*/
        $scope.predecessor = {};
        $scope.applicant = {};
        $scope.user = $scope.user || {};

        $http.get('rest/licenses', $scope.license).success(function (result) {
            $scope.licenses = result;
        });

        $scope.allStates = ['REJECTED', 'NEGOTIATED', 'WAITING_FOR_SIGNATURE'];
        $scope.state = {};

        //Only for showing
        $scope.prefillProduct = LicensingService.getproduct() || LicensingService.getproductNew();
        $scope.prefillCustomer = LicensingService.getApplicant() || LicensingService.getCustomer();
        $scope.prefillContractNumber = LicensingService.getContractNumber();


        function createLicense() {
            console.log("User (license):");
            console.log($scope.user);
            $http.post('rest/licenses', $scope.user).
                then(function (response) {
                    // If something breaks and events are not created, comment the line below.
                    createEvent(response.data.id);
                    $window.location.href = '#/';
                }, function (response) {
                    console.error('There was something wrong with the add license request.');
                });
        }

        function createCustomer(applicant) {
            $http.post('rest/customers', applicant).
                then(function (response) {
                    // new customer/applicant has id now
                    $scope.user.customer = response.data;
                    $scope.user.product = LicensingService.getproduct();
                    $scope.user.release = LicensingService.getRelease();
                    createLicense();
                }, function (response) {
                    console.error('There was something wrong with the add customer request.');
                });
        }

        function createEvent(id) {
            $scope.event = {
                name: 'User name',
                description: '*user name* added license *license nr*',
                type: 'Add'
            };

            $http.post('rest/events/' + id, $scope.event).
                then(function (response) {
                    console.log("Event created");
                    console.log(response.data);
                }, function (response) {
                    console.error(response.errors);
                });
        }

        $scope.saveData = function () {
            $scope.user.contractNumber = $scope.contractNumber;
            $scope.user.state = $scope.state;
            $scope.user.predecessorLicenseId = $scope.predecessor.contractNumber;

            var applicant = LicensingService.getApplicant();
            if (applicant != undefined) {
                createCustomer(applicant);
            } else {
                $scope.user.customer = LicensingService.getCustomer();
                $scope.user.product = LicensingService.getproduct();
                $scope.user.release = LicensingService.getRelease();
                createLicense();
            }
        }
    });
