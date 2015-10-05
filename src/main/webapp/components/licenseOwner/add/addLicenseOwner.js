// Add controller to module.
angular
    .module('LM')
    .controller('AddLicenseOwnerCtrl', function ($scope, $http, $window) {

        $scope.saveData = function () {
            console.log($scope.licenseOwner);

            if (!$scope.form.$valid) {
                return;
            }

            $http.post('rest/licenseOwners', $scope.licenseOwner).
                then(function (response) {

                    $window.location.href = '#/licenseOwners';
                }, function (response) {

                    console.error('[addLicenseOwner.js] Error');
                });
        }
    });

