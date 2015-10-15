// Add controller to module.
angular
    .module('LM')
    .controller('ProfileLicenseOwnerCtrl', function ($scope, $http, $routeParams) {

        $http.get('rest/licenses').
            //server töötleb post päringut ja kui ta on sellega lõpetanud, siis minnakse siin alles edasi
            then(function (response) {
                // this callback will be called asynchronously
                // when the response is available
                $scope.currentDate = new Date().getTime();
                $scope.licenses = response.data;

                $scope.compare = function(expDate) {
                  var eDate = new Date(expDate);
                  return eDate.getTime();
                };
            }, function (response) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.

                //nt kui päringus on midagi valesti - emailis on ainult numbrid
                console.error('Mis iganes.');
            });
        $http.get('rest/licenseOwners').
            //server töötleb post päringut ja kui ta on sellega lõpetanud, siis minnakse siin alles edasi
            then(function (response) {
                // this callback will be called asynchronously
                // when the response is available
                $scope.licenseOwner = response.data[$routeParams.id-1];
                console.log($scope.licenseOwner);
            }, function (response) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.

                console.error('[licenseOwnersView.js] Error retrieving license owners.');
            });
    });
