// Add controller to module.
angular
    .module('LM')
    .controller('ViewLicenseOwnersCtrl', function ($scope, $http) {

        $http.get('rest/licenseOwners').
            //server töötleb post päringut ja kui ta on sellega lõpetanud, siis minnakse siin alles edasi
            then(function (response) {
                // this callback will be called asynchronously
                // when the response is available
                $scope.licenseOwners = response.data;
            }, function (response) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.

                console.error('[licenseOwnersView.js] Error retrieving license owners.');
            });

    });
