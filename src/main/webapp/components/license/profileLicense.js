angular
    .module('LM')
    .controller('ProfileLicenseCtrl', function ($scope, $http, $routeParams) {

        $http.get('rest/licenses').
            //server töötleb post päringut ja kui ta on sellega lõpetanud, siis minnakse siin alles edasi
            then(function (response) {
                // this callback will be called asynchronously
                // when the response is available
                $scope.license = response.data[$routeParams.id-1];
                console.log($scope.license);
            }, function (response) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.

                console.error('There was something wrong with the view license request.');
            });

    });

