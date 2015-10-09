// Add controller to module.
angular
    .module('LM')
    .controller('AddLicenseCtrl', function ($scope, $http, $window) {

        $http.get('rest/products', $scope.user).success(function (result) {
            $scope.products = result;
        });

        $scope.saveData = function () {
            console.log($scope.user);
            //tee päring
            //urliks /application
            if (!$scope.form.$valid) {
                return;
            }

            $http.post('rest/licenses', $scope.user).
                //server töötleb post päringut ja kui ta on sellega lõpetanud, siis minnakse siin alles edasi
                then(function (response) {
                    // this callback will be called asynchronously
                    // when the response is available

                    //vii mind uuele lehele
                    $window.location.href = '#/licenses';
                }, function (response) {
                    // called asynchronously if an error occurs
                    // or server returns response with an error status.

                    //nt kui päringus on midagi valesti - emailis on ainult numbrid
                    console.error('There was something wrong with the add license request.');
                });
        }
    });

