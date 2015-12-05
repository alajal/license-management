angular
    .module('LM')
    .controller('AddLicenseCtrl', function ($scope, $http, $window, LicensingService, $rootScope) {
        /* $scope.allStates = {1:'CANCELLED',2: 'NEGOTIATED', 3:'WAITING_FOR_SIGNATURE', 4:'ACTIVE', 5:'EXPIRATION_NEARING',
         6:'TERMINATED'};*/
        $scope.predecessor = {};
        $scope.applicant = {};
        $scope.user = $scope.user || {};

        $http.get('rest/licenses').success(function (result) {
            $scope.licenses = result;
        });

        $http.get('rest/licenses/type').
            then(function (response) {
                $scope.types = response.data;
            }, function (response) {
                console.error('Something went wrong with the license types get method.');
            });


        $scope.allStates = ['REJECTED', 'NEGOTIATED', 'WAITING_FOR_SIGNATURE'];
        $scope.state = {};

        //Only for showing
        $scope.prefillProduct = LicensingService.getproduct() || LicensingService.getproductNew();
        console.log("new customer:");
        console.log(LicensingService.getApplicant());
        console.log("existing customer:");
        console.log(LicensingService.getCustomer());
        $scope.prefillCustomer = LicensingService.getApplicant() || LicensingService.getCustomer();
        $scope.prefillContractNumber = LicensingService.getContractNumber();


        function createLicense() {
            console.log("User (license):");
            console.log($scope.user);
            $http.post('rest/licenses', $scope.user).
                then(function (response) {
                    // If something breaks and events are not created, comment the line below.
                    console.log(response.data);
                    createEvent(response.data, 0);
                    $window.location.href = '#/';
                }, function (response) {
                    $scope.errorMessage = 'Something went wrong. Maybe license with this id already exists?';
                    console.error(response);
                });
        }

        function createCustomer(applicant) {
            $http.post('rest/customers', applicant).
                then(function (response) {
                    // new customer/applicant has id now
                    $scope.user.customer = response.data;
                    $scope.user.product = LicensingService.getproduct();
                    $scope.user.release = LicensingService.getRelease();
                    createEvent(response.data, 1);
                    console.log("Customer event created!!!");
                    createLicense();
                }, function (response) {
                    console.error('There was something wrong with the add customer request.');
                });
        }

        function createEvent(obj, event_nr) {
            // Event numbers
            // 0 - add new license
            // 1 - add new customer

            $scope.events = [
              {
                name: 'Created License',
                description: $scope.username+' added license '+obj.contractNumber+', state is: '+obj.state,
                type: 'Add'
              },
              {
                name: 'Created Customer',
                description: $scope.username+' added customer '+obj.organizationName,
                type: 'Add'
              }
              ];

              /*
            $scope.event = {
                name: 'User name',
                description: '*user name* added license '+license.contractNumber+', state is: '+license.state,
                type: 'Add'
            };
            */

            if(event_nr == 0) {
              id = obj.id;
            }
            else {
              id = 0;
            }

            $http.post('rest/events/' + id, $scope.events[event_nr]).
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
            //$scope.user.type = $scope.type;

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
