'use strict'

angular
    .module('LM')
    .controller('ProfileLicenseCtrl', function ($scope, $http, $routeParams, $location) {

        $scope.licenseId = $routeParams.id;
        $scope.editorDisabled = true;
        $scope.AuthorisedUserForm = false;
        $scope.rowforms = {};
        $scope.allStates = ['REJECTED', 'NEGOTIATED', 'WAITING_FOR_SIGNATURE', 'ACTIVE', 'EXPIRATION_NEARING', 'TERMINATED'];
        $scope.state = {};
        $scope.mailBody = {};

        $http.get('rest/licenses').
            then(function (response) {
                $scope.license = response.data[$routeParams.id - 1];
            }, function (response) {
                console.error('There was something wrong with the view license request.');
            });

        $http.get('rest/licenses/type').
            then(function (response) {
                $scope.types = response.data;
            }, function (response) {
                console.error('Something went wrong with the license types get method.');
            });

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
            //päring leidmaks kõiki mailbodysid, mille litsentsitüüp on valitud tüübi id
            $http.get('rest/template/mailbodies/' + $scope.license.type.id).
                then(function (response) {
                    $scope.bodiesByLicenseType = response.data;
                }, function (response) {
                    console.error('Something went wrong with the bodies by license type id get method.');
                })
        };

        //TODO
        $scope.ifLicenseTypeHasValue = function () {
            //show the possible templates when page is loaded
        };

        //AUTHORISED USERS METHODS
        $http.get('rest/authorisedUser/bylicense/' + $routeParams.id).
            then(function (response) {
                $scope.authorisedUser = response.data;
            }, function (response) {
                console.error('Something went wrong with the authorised users get method.');
            });

        $scope.openAuthorisedUserForm = function () {
            //$scope.AuthorisedUserForm = true;
            var newLineNotThere = true;
            for (var i = 0; i < $scope.authorisedUser.length; i++) {
                if (typeof $scope.authorisedUser[i].id === "undefined") {
                    newLineNotThere = false;
                    break;
                }
            }
            if (newLineNotThere) {
                var newUser = {};
                $scope.authorisedUser.push(newUser);
                $scope.selected = newUser;
            }

        };

        $scope.enableEditor = function () {
            $scope.editorDisabled = false;
        };

        $scope.disableEditor = function () {
            $scope.editorDisabled = true;
        };

        $scope.saveProfile = function () {
            $scope.disableEditor();
            console.log("Litsents:");
            console.log($scope.license);
            $http.put('rest/licenses/' + $scope.license.id, $scope.license).
                then(function (response) {
                    //Updating license
                    $scope.license = response.data;
                    createEvent($scope.license, 0);
                }, function (response) {
                    console.error(response);
                });
        };

        function createEvent(license, event_nr) {
            var contract_nr = license.contractNumber;
            // Event numbers
            // 0 - edit license info
            // 1 - remove license
            // 2 - change license status

            $scope.events = [
                {
                    name: 'User name',
                    description: ' modified license ' + contract_nr,
                    type: 'Modify'
                },
                {
                    name: 'User name',
                    description: ' deleted license ' + contract_nr,
                    type: 'Remove'
                },
                {
                    name: 'User name',
                    description: ' modified status of license ' + contract_nr + ' to ' + license.state,
                    type: 'Modify'
                }
            ];

            $http.post('rest/events/' + license.id, $scope.events[event_nr]).
                then(function (response) {
                    console.log("Event created");
                    console.log(response.data);
                }, function (response) {
                    console.error(response.errors);
                });
        }

        $scope.deleteEntry = function (au) {
            $http.delete('rest/authorisedUser/bylicense/' + $routeParams.id + '/' + au.id).
                then(function (response) {
                    var deletableUserIndex = $scope.authorisedUser.indexOf(au);
                    $scope.authorisedUser.splice(deletableUserIndex, 1);
                }, function (response) {
                    console.error('HTTP delete request failed');
                });
        };

        $scope.isActive = function (viewLocation) {
            return viewLocation === $location.path();
        };

        $scope.getScriptId = function (au) {
            if ($scope.selected && au.id === $scope.selected.id) { // or au.id == null
                return 'edit';
            }
            else return 'display';
        };

        $scope.editAuthorisedUser = function (au) {
            $scope.selected = angular.copy(au); //creates a copy of au and stores it to $scope.selected variable which will be edited

        };

        $scope.save = function (au) {
            if (typeof au.id === "undefined") {
                console.log(au);
                if (!$scope.rowforms.form.$valid) {
                    console.log(au, " not valid");
                    return;
                }

                $http.post('rest/authorisedUser/bylicense/' + $routeParams.id, au).
                    then(function (response) {
                        $.extend(au, response.data); //response.data adds id. This is stored in au.
                        $scope.selected = {};

                    }, function (response) {
                        console.error('Something went wrong with post authorised users request.');
                    });
            }
            else {
                $.extend(au, $scope.selected); //jQuery extend method to save the changes made in $scope.selected to au
                $http.put('rest/authorisedUser/bylicense/' + $routeParams.id, au).then(function (response) {
                    console.log($scope.selected);
                }, function (response) {
                    console.error(response);
                });

                $scope.selected = {};
            }
        };

        $scope.reset = function (au) {
            if (typeof au.id === "undefined") {
                var deletableUserIndex = $scope.authorisedUser.indexOf($scope.authorisedUser.length - 1);
                $scope.authorisedUser.splice(deletableUserIndex, 1);
            }
            else {
                $scope.selected = {};
            }
        };

        //todo kontakti eesnimi ja perenimi tuleb leida olemasoleva litsentsi jaoks kahte moodi:
        //todo kui litsenseerimise etapi 1. sammus lisati uus customer, siis tuleb kontakt võtta Applicanti/customeri küljest
        //todo kui valiti customer, siis tuleb kontakt võtta olemasoleva customeri küljest - mitme kontakti puhul
        //todo võtta esimene kontakt - teeme eelduse, et esimene kontakt on main contact
        $scope.mailBodySelected = function () {
            var map = {
                "organizationName": $scope.license.customer.organizationName,
                "contactPersonFirstName": $scope.license.customer.contacts[0].firstName,
                "contactPersonLastName": $scope.license.customer.contacts[0].lastName,
                "email": $scope.license.customer.contacts[0].email,
                "product": $scope.license.product.name,
                "release": $scope.license.release.versionNumber
            };
            var bodyAsString = $scope.mailBody.body;
            for (var key in map) {
                var regex = new RegExp("\\$" + "\\{" + key + "\\}", "g");
                bodyAsString = bodyAsString.replace(regex, map[key]);
            }
            $scope.mailBody.body = bodyAsString;
        };

        $scope.sendMail = function () {
            $scope.file_id = 0;
            if ($scope.chosenAttachment != undefined) {
                $scope.file_id = $scope.chosenAttachment.id;       //Kui faili ei lisata, jätke $scope.file_id 0.
            }

            $scope.license_id = $scope.license.id;    //Siia peab õige litsentsi id saama. Vale ID korral saadetakse valedele kontaktidele.
            console.log($scope.file_id);
            var mail = {
                id: 1,                         //vahet ei ole, mis see on...
                subject: $scope.mailsubject,       //meili pealkiri
                body: $scope.mailBody.body,    //meili sisu. Kontrollige, et siia satuks html kujul tekst. Muidu läheb kõik ühele reale
                licenseTypeId: $scope.license.type.id,               //vahet ei ole, mis see on...
                contact_ids: $scope.mailContact.id //"1,2"          //  contacti id-d sellisel kujul nagu nad on. Kui see jätta tühjaks, ehk "" või üldse ära jätta,
                // siis saadab kõikidele antud litsentsi isikutele
            };

            $http.put('rest/sendMail/' + $scope.file_id + '/' + $scope.license_id, mail).
                then(function (response) {
                    console.log("Email sent");
                }, function (response) {
                    console.error('Could not send email!');
                })
        };

        //CONTACT PERSON METHODS
        $http.get('rest/contactPersons/bylicense/' + $routeParams.id).
            then(function (response) {
                $scope.contacts = response.data;
            }, function (response) {
                console.error('Something went wrong with the contacts GET method.');
            });

        $scope.editContactPerson = function (cp) {
            cp.editing = true;
        };

        $scope.cancelContactPersonEditing = function (cp) {
            cp.editing = false;
        };

        $scope.deleteContactPerson = function (cp) {
            //$routeParams.id on license.id
            $http.delete('rest/contactPersons/bylicense/' + $routeParams.id + '/' + cp.id).
                then(function (response) {
                    var deletableContactPersonIndex = $scope.contacts.indexOf(cp);
                    $scope.contacts.splice(deletableContactPersonIndex, 1);
                }, function (response) {
                    console.error('HTTP delete contact person request failed');
                });
        };

        $scope.openContactPersonForm = function (cp) {
            var emptyRowNotOpened = true;

            if (emptyRowNotOpened) {
                var newContactPerson = {};
                newContactPerson.new = true;
                newContactPerson.editing = true;
                $scope.contacts.push(newContactPerson);
            }
        };

        $scope.saveContactPerson = function (cp) {
            if (cp.new == true) {
                //delete cp.copy;
                $http.post('rest/contactPersons/bylicense/' + $routeParams.id, cp).
                    then(function (response) {
                        cp.id = response.data.id;
                        cp.editing = false;
                        cp.new = false;
                    }, function (response) {
                        console.error('Something went wrong with contact person POST request.');
                    });
            } else {
                $http.put('rest/contactPersons/bylicense/' + $routeParams.id, cp).then(function (response) {
                    console.log(cp);
                }, function (response) {
                    console.error(response);
                });
                cp.editing = false;
            }
        };

    });
