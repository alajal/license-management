'use strict';

angular
    .module('LM')
    .controller('DeliveryLicensesCtrl', function ($scope, $http, $filter, $window) {

        //used for sorting
        $scope.sortType = 'validTill';
        $scope.sortReverse = false;
        $scope.searchLicense = '';

        $http.get('rest/licenses').
            then(function (response) {
                $scope.allLicenses = response.data;
                $scope.deliveryFilterType = "delivery";
                $scope.newValue($scope.deliveryFilterType);
            }, function (response) {
                console.error('There was something wrong with the view licenses request.');
            });
        $scope.newValue = function (value) {
            switch (value) {
                case "delivery":
                    //Delivery is needed when license is ACTIVE and release is null.
                    //TODO WAITING_FOR_SIGNATURE is for testing, should be ACTIVE
                    $scope.licenses = $filter('filter')($scope.allLicenses, {state: "ACTIVE", release: null});
                    break;
                case "update":
                    //Update is needed when license is ACTIVE and there is a newer version of the product.
                    $scope.licenses = $filter('updateNeeded')($scope.allLicenses);
                    break;
                case "upToDate":
                    //Is up to date when license is ACTIVE and has the latest release of the product.
                    $scope.licenses = $filter('upToDate')($scope.allLicenses);
                    break;
            }
        };

        $scope.getDeliveredReleases = function (licenseId) {

            $http.get('rest/deliveredReleases/bylicense/' + licenseId).
                then(function (response) {
                    $scope.deliveredReleases = response.data;
                }, function (response) {
                    console.error('Something went wrong with the GET delivered releases method.');
                });
        };

        $scope.openDeliveryPage = function (licenseId) {
            $window.location.href = '#/license/' + licenseId + '/notification'
        }
    });

angular
    .module('LM')
    .filter('updateNeeded', function () {
        return function (input) {
            var output = [];
            input.forEach(function (license) {
                var updateNeeded = false;
                var currentRelease = license.release;
                var product = license.product;
                var releases = product.releases;
                releases.forEach(function (release) {
                    if (currentRelease != null && release.id > currentRelease.id) {
                        updateNeeded = true;
                    }
                });
                //TODO WAITING_FOR_SIGNATURE is for testing, should be ACTIVE
                if (updateNeeded && license.state == "ACTIVE" && currentRelease != null) {
                    output.push(license);
                }
            });
            return output;
        }
    });

angular
    .module('LM')
    .filter('upToDate', function () {
        return function (input) {
            var output = [];
            input.forEach(function (license) {
                var updateNeeded = false;
                var currentRelease = license.release;
                var product = license.product;
                var releases = product.releases;
                releases.forEach(function (release) {
                    if (currentRelease != null && release.id > currentRelease.id) {
                        updateNeeded = true;
                    }
                });
                //TODO WAITING_FOR_SIGNATURE is for testing, should be ACTIVE
                if (!updateNeeded && license.state == "ACTIVE" && currentRelease != null) {
                    output.push(license);
                }
            });
            return output;
        }
    });
