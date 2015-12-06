angular
    .module('LM')
    .controller('ProfileLicenseCtrl', function ($scope, $http, $routeParams, $location) {

        $scope.licenseId = $routeParams.id;

        $scope.editorDisabled = true;

        $scope.AuthorisedUserForm = false;

        $scope.rowforms = {};

        $http.get('rest/authorisedUser/bylicense/' + $routeParams.id).
            then(function (response) {
                $scope.authorisedUser = response.data;
            }, function (response) {
                console.error('Something went wrong with the authorised users get method.');
            });

        $http.get('rest/licenses').
            then(function (response) {
                // this callback will be called asynchronously
                // when the response is available
                $scope.license = response.data[$routeParams.id - 1];
                console.log("One License");
                console.log($scope.license);
            }, function (response) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
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

        $scope.licenseTypeSelected = function () {
            //päring leidmaks kõiki mailbodysid, mille litsentsitüüp on valitud tüübi id
            $http.get('rest/template/mailbodys/' + $scope.license.type.id).
                then(function (response) {

                }, function (response) {

                })
        };

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
                    console.log("Litsents peale uuendamist:");
                    console.log($scope.license);
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

        $scope.openAuthorisedUsersForm = function () {
            var a = $location.param1;
            $location.path('/authorisedUser/add/addAuthorisedUser');
        };

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

        $scope.saveData = function () {
            console.log($scope.authorised);
            if (!$scope.form.$valid) {
                return;
            }

            $http.post('rest/authorisedUser/bylicense/' + $routeParams.id, $scope.authorised).
                then(function (response) {
                    $scope.authorisedUser.push(response.data);
                    $scope.form.$setUntouched();
                    $scope.form.$setPristine();
                    $scope.authorised = null;
                    $scope.AuthorisedUserForm = false;
                }, function (response) {
                    console.error('Something went wrong with post authorised users request.');
                });
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


        $scope.updateAuthorisedUser = function (au) {
            $.extend(au, $scope.selected); //jQuery extend method to save the changes made in $scope.selected to au
            $http.put('rest/authorisedUser/bylicense/' + $routeParams.id, au).then(function (response) {
                console.log($scope.selected);
            }, function (response) {
                console.error(response);

            });

            $scope.selected = {};

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
                "${organizationName}": $scope.license.customer.organizationName,
                "${contactPersonFirstName}": $scope.license.customer.contacts[0].firstName,
                "${contactPersonLastName}": $scope.license.customer.contacts[0].lastName,
                "${email}": $scope.license.customer.contacts[0].email,
                "${product}": $scope.license.product.name,
                "${release}": $scope.license.release.versionNumber
            };

            var bodyAsString = $scope.mailBody.body;
            for (var key in map) {
                console.log(key);
                bodyAsString = bodyAsString.replace(key, map[key]);
            }
            $scope.mailBody.body = bodyAsString;
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

        $scope.sendMail = function () {
            $scope.file_id = 0;       //Kui faili ei lisata, jätke $scope.file_id 0.
            $scope.license_id = 1;    //Siia peab õige litsentsi id saama. Vale ID korral saadetakse valedele kontaktidele.

            $scope.mail = {
                id: 1,                         //vahet ei ole, mis see on...
                subject: "Meili pealkiri siia",       //meili pealkiri
                body: "Sisu siia",    //meili sisu. Kontrollige, et siia satuks html kujul tekst. Muidu läheb kõik ühele reale
                licenseTypeId: 3               //vahet ei ole, mis see on...
            }
            $http.put('rest/sendMail/' + $scope.file_id + '/' + $scope.license_id, $scope.mail).
                then(function (response) {
                    console.log("Email sent");
                }, function (response) {
                    console.error('Could not send email!');
                })
        }

    });
