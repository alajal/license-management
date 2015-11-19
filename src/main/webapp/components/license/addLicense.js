angular
    .module('LM')
    .controller('AddLicenseCtrl', function ($scope, $http, $window, LicensingService) {
        /* $scope.allStates = {1:'CANCELLED',2: 'NEGOTIATED', 3:'WAITING_FOR_SIGNATURE', 4:'ACTIVE', 5:'EXPIRATION_NEARING',
         6:'TERMINATED'};*/
        $scope.predecessor = {};
        $scope.applicant = {};

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

        function createProduct(product) {
            console.log("New Product:");
            console.log(product);
            $http.post('rest/products', product).
                then(function (response) {
                    $scope.user.product = response.data;
                    console.log("New product with id:");
                    console.log($scope.user.product);
                    createLicense();

                }, function (response) {
                    console.error('There was something wrong with the add product request.');
                });
        }

        function createCustomer(applicant) {
            console.log("New applicant:");
            console.log(applicant);
            $http.post('rest/customers', applicant).
                then(function (response) {
                    $scope.user.customer = response.data;
                    console.log("New customer(applicant) with id:");
                    console.log($scope.user.customer);

                    var newProduct = LicensingService.getproductNew();
                    if (newProduct != undefined) {
                        createProduct(newProduct);
                    } else {
                        //var existingProduct = LicensingService.getproduct() != 'undefined';
                    }
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
            $scope.user = $scope.user || {};

            $scope.user.contractNumber = $scope.prefillContractNumber;
            $scope.user.state = $scope.state;
            console.log("Predecessor");
            console.log($scope.predecessor.contractNumber);
            $scope.user.predecessorLicenseId = $scope.predecessor.contractNumber;

            var applicant = LicensingService.getApplicant();
            if (applicant != undefined) {
                createCustomer(applicant);
            } else {
                console.log("else haru");
                console.log($scope.user.customer);
                console.log($scope.user.product);
                console.log($scope.user)
                $scope.user.customer = LicensingService.getCustomer();
                $scope.user.product = LicensingService.getproduct();
                createLicense();
                //LicensingService.getCustomer() != 'undefined')
            }
        }
    });
