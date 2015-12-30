'use strict';

angular
    .module('LM')
    .controller('AddLicenseCtrl', function ($scope, $http, $window, LicensingService) {
        $scope.predecessor = {};
        $scope.applicant = {};
        $scope.license = $scope.license || {};
        $scope.allStates = ['REJECTED', 'NEGOTIATED', 'WAITING_FOR_SIGNATURE'];
        $scope.state = {};
        $scope.mailTemplate = {};
        $scope.licenseType = {};
        $scope.contacts = {};
        $scope.licenseSaved = false;
        $scope.savedLicense = $scope.savedLicense || {};
        $scope.mailsubject = '';

        $http.get('rest/licenses').
            then(function (response) {
                $scope.licenses = response.data;
            }, function (response) {
                console.error('Something went wrong with the licenses get method.')
            });

        $http.get('rest/licenses/type').
            then(function (response) {
                $scope.types = response.data;
            }, function (response) {
                console.error('Something went wrong with the license types get method.');
            });

        //Only for showing
        $scope.prefillProduct = LicensingService.getProduct() || LicensingService.getProductNew();
        $scope.prefillCustomer = LicensingService.getApplicant() || LicensingService.getCustomer();
        $scope.prefillContractNumber = LicensingService.getContractNumber();

        function createLicense() {
            console.log("User (license):");
            console.log($scope.license);
            $http.post('rest/licenses', $scope.license).
                then(function (response) {
                    $scope.licenseSaved = true;
                    $scope.savedLicense.id = response.data.id;
                    $scope.contacts = $scope.license.customer.contacts;
                    createEvent(response.data, 0);
                }, function (response) {
                    $scope.errorMessage = 'Something went wrong. Maybe license with this id already exists?';
                    console.error(response);
                });
        }

        function createCustomerAndLicense(applicant) {
            //todo leida parem lahendus, kus applicant objektil poleks kunagi sellist välja, mis serverile ei sobiks
            // hetkel tuleb see addCustomer failis oleva inputi väärtusest, kuhu binditakse tglt kas objekti või fieldi
            delete applicant["organizationNameOrObject"];
            $http.post('rest/customers', applicant).
                then(function (response) {
                    // new customer/applicant has id now
                    $scope.license.customer = response.data;
                    $scope.license.product = LicensingService.getProduct();
                    $scope.license.release = LicensingService.getRelease();
                    createEvent(response.data, 1);
                    createLicense();
                }, function (response) {
                    console.error('There was something wrong with the add customer request.');
                });
        }

        function createEvent(obj, event_nr) {
            // Event numbers
            // 0 - add new license
            // 1 - add new customer
            var id;
            $scope.events = [
                {
                    name: 'Created License',
                    description: $scope.username + ' added license ' + obj.contractNumber + ', state is: ' + obj.state,
                    type: 'Add'
                },
                {
                    name: 'Created Customer',
                    description: $scope.username + ' added customer ' + obj.organizationName,
                    type: 'Add'
                }
            ];

            if (event_nr == 0) {
                id = obj.id;
            } else {
                id = 0;
            }

            $http.post('rest/events/' + id, $scope.events[event_nr]).
                then(function (response) {
                }, function (response) {
                    console.error(response.errors);
                });
        }

        $scope.saveNewLicense = function () {
            $scope.license.contractNumber = $scope.contractNumber;
            $scope.license.state = $scope.state;
            $scope.license.predecessorLicenseId = $scope.predecessor.contractNumber;
            $scope.licenseType = $scope.license.type;
            if ($scope.license.predecessorLicenseId == $scope.license.contractNumber) {
                $scope.predecessorErrorMessage = 'Predecessor license is the same as contract number.';
                return;
            }

            var applicant = LicensingService.getApplicant();
            if (applicant != undefined) {
                createCustomerAndLicense(applicant);
            } else {
                $scope.license.customer = LicensingService.getCustomer();
                $scope.license.product = LicensingService.getProduct();
                $scope.license.release = LicensingService.getRelease();
                createLicense();
            }
        };

        $http.get('rest/template').
            then(function (response) {
                $scope.bodies = response.data;
            }, function (response) {
                console.error('Something went wrong with the bodies get method.');
            });

        $http.get('rest/template/fileIdAndName').
            then(function (response) {
                $scope.attachments = response.data;
            }, function (response) {
                console.error('Something went wrong with the bodies get method.');
            });

        $scope.licenseTypeSelected = function () {
            //TODO ng-change tuleb asendada millegi muuga
            //päring leidmaks kõiki mailbodysid, mille litsentsitüüp on valitud tüübi id
            $http.get('rest/template/mailbodys/' + $scope.license.type.id).
                then(function (response) {
                    $scope.bodiesByLicenseType = response.data;
                }, function (response) {
                    console.error('Something went wrong with the bodies by license type id get method.');
                })
        };

        $scope.mailBodySelected = function () {
            var customer = $scope.license.customer;
            var product = $scope.license.product;
            var latestRelease = "";
            console.log("pro");
            console.log(product);
            var lastReleaseIndex = product.releases.length - 1;
            if (lastReleaseIndex != undefined) {
                latestRelease = product.releases[lastReleaseIndex].versionNumber;
            }
            var map = {
                "organizationName": customer.organizationName,
                "contactPersonFirstName": customer.contacts[0].firstName,
                "contactPersonLastName": customer.contacts[0].lastName,
                "email": customer.contacts[0].email,
                "product": product.name,
                "release": latestRelease
            };
            var bodyAsString = this.mailTemplate.body;
            for (var key in map) {
                /*while (bodyAsString.find(key) != -1)
                 bodyAsString = bodyAsString.replace(key, map[key]);*/
                var regex = new RegExp("\\$" + "\\{" + key + "\\}", "g");
                bodyAsString = bodyAsString.replace(regex, map[key]);
            }
            $scope.mailBodyFormatted = bodyAsString;
        };

        $scope.sendMail = function () {
            $scope.file_id = 0;
            if (this.chosenAttachment != undefined) {
                $scope.file_id = this.chosenAttachment.id;       //Kui faili ei lisata, jätke $scope.file_id 0.
            }

            $scope.license_id = $scope.savedLicense.id;    //Siia peab õige litsentsi id saama. Vale ID korral saadetakse valedele kontaktidele.
            console.log($scope.file_id);
            var mail = {
                id: 1,                         //vahet ei ole, mis see on...
                subject: this.mailTemplate.subject,       //meili pealkiri
                body: $scope.mailBodyFormatted,    //meili sisu. Kontrollige, et siia satuks html kujul tekst. Muidu läheb kõik ühele reale
                licenseTypeId: $scope.license.type.id,               //vahet ei ole, mis see on...
                contact_ids: this.mailContact.id //"1,2"          //  contacti id-d sellisel kujul nagu nad on. Kui see jätta tühjaks, ehk "" või üldse ära jätta,
            };

            $http.put('rest/sendMail/' + $scope.file_id + '/' + $scope.license_id, mail).
                then(function (response) {
                    console.log("Email sent");
                }, function (response) {
                    console.error('Could not send email!');
                })
        }
    });
