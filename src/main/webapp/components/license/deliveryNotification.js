angular
    .module('LM')
    .controller('DeliveryNotification', function ($scope, $http, $routeParams, $window) {

        $http.get('rest/releases/bylicense/' + $routeParams.id).
            then(function (response) {
                $scope.releases = response.data;
            }, function (response) {
                console.error('Something went wrong with the releases GET method by License id.');
            });

        $scope.sendNotification = function(release){

            $http.put('rest/licenses/bylicense/' + $routeParams.id, release).
                then(function (response) {
                console.log(release);
                }, function (response) {
                    console.error('Something went wrong with License PUT request.');
                });
            $window.location.href = "#/deliveryLicenses";


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