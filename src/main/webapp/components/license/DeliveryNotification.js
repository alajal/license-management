'use strict';

angular
    .module('LM')
    .controller('DeliveryNotification', function ($scope, $http, $routeParams, $window) {

        $scope.mailTemplate = {};
        $scope.mailBody = {};
        $scope.mailContact = {};

        $http.get('rest/releases/bylicense/' + $routeParams.id).
            then(function (response) {
                $scope.releases = response.data;
            }, function (response) {
                console.error('Something went wrong with the releases GET method by License id.');
            });

        $http.get('rest/licenses/' + $routeParams.id).
            then(function (response) {
                $scope.license = response.data;
                $scope.contacts = $scope.license.customer.contacts;
                console.log("One License");
                console.log($scope.license);
            }, function (response) {
                console.error('There was something wrong with the view license request.');
            });


        $scope.sendMail = function (release) {
            $scope.fileId = 0;
            $scope.licenseId = $scope.license.id;

            var mail = {
                id: 1,
                subject: $scope.mailTemplate.subject,
                body: $scope.mailBodyFormatted,
                contactIds: $scope.mailContact.id
            };

            $http.put('rest/sendMail/' + $scope.fileId + '/' + $scope.licenseId, mail).
                then(function (response) {

                    $http.put('rest/licenses/bylicense/' + $routeParams.id, release).
                        then(function (response) {
                            console.log(release);
                        }, function (response) {
                            console.error('Something went wrong with License PUT request.');
                        });

                    release.user = $scope.username;
                    console.log("release user:");
                    console.log($scope.username);
                    $http.post('rest/deliveredReleases/bylicense/' + $routeParams.id, release).
                        then(function (response) {
                            console.log("Release:");
                            console.log(release)
                        }, function (response) {
                            console.error('Something went wrong with the Delivered Release Post method');
                        });

                    $window.location.href = "#/deliveryLicenses";
                    console.log("Email sent");
                }, function (response) {
                    console.error('Could not send email!');
                });
        };


        $http.get('rest/template').
            then(function (response) {
                $scope.bodies = response.data;
            }, function (response) {
                console.error('Something went wrong with the bodies get method.');
            });

        $scope.mailBodySelected = function () {
            var customer = $scope.license.customer;
            var product = $scope.license.product;
            var latestRelease = "";
            var lastReleaseIndex = product.releases.length - 1;
            if (lastReleaseIndex != undefined) {
                latestRelease = product.releases[lastReleaseIndex].versionNumber;
            }

            var map = {
                "organizationName": customer.organizationName,
                //kui maili bodys on kontakti andmed, siis eeldatakse, et valitakse esmane kontakt, kellele meil saata
                "contactPersonFirstName": customer.contacts[0].firstName,
                "contactPersonLastName": customer.contacts[0].lastName,
                "email": customer.contacts[0].email,
                "product": product.name,
                "release": latestRelease
            };

            var bodyAsString = this.mailTemplate.body;
            for (var key in map) {
                var regex = new RegExp("\\$" + "\\{" + key + "\\}", "g");
                bodyAsString = bodyAsString.replace(regex, map[key]);
            }
            $scope.mailBodyFormatted = bodyAsString;
        };


    });