'use strict'

angular
    .module('LM')
    .controller('DeliveryNotification', function ($scope, $http, $routeParams, $window) {

        $http.get('rest/releases/bylicense/' + $routeParams.id).
            then(function (response) {
                $scope.releases = response.data;
            }, function (response) {
                console.error('Something went wrong with the releases GET method by License id.');
            });

        $http.get('rest/licenses').
            then(function (response) {
                $scope.license = response.data[$routeParams.id - 1];
                console.log("One License");
                console.log($scope.license);
            }, function (response) {
                console.error('There was something wrong with the view license request.');
            });

        $scope.sendNotification = function (release) {

            $scope.sendMail = function () {
                $scope.file_id = 0;
                $scope.license_id = $routeParams.id;

                $scope.mail = {
                    id: 1,
                    subject: "Meili pealkiri siia",
                    body: $scope.mailBody.body,
                    licenseTypeId: 3
                };

                $http.put('rest/sendMail/' + $scope.file_id + '/' + $scope.license_id, $scope.mail).
                    then(function (response) {

                        $http.put('rest/licenses/bylicense/' + $routeParams.id, release).
                            then(function (response) {
                                console.log(release);
                            }, function (response) {
                                console.error('Something went wrong with License PUT request.');
                            });

                        release.user = $scope.username;
                        $http.post('rest/deliveredReleases/bylicense/' + $routeParams.id, release).
                            then(function (response) {
                                console.log($scope.username);
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

            $scope.sendMail();
        };


        $http.get('rest/template').
            then(function (response) {
                $scope.bodies = response.data;
            }, function (response) {
                console.error('Something went wrong with the bodies get method.');
            });

        $scope.mailBodySelected = function () {
            var map = {
                "${organizationName}": $scope.license.customer.organizationName,
                "${contactPerson}": $scope.license.customer.contacts[0].contactName,
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


    });