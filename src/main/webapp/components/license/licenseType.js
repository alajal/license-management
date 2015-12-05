angular
    .module('LM')
    .controller('LicenseTypeCtrl', function ($scope, $http, $window) {
        $scope.type = {};

        $scope.addNewType = function(){
            if (!$scope.form.$valid) {
                return;
            }
            $http.post('rest/licenses/licensetype', $scope.type).
                then(function (response) {
                    console.log(response.data);
                    $window.location.href = '#/';
                }, function (response) {
                    $scope.errorMessage = 'Something went wrong with adding type.';
                    //console.error(response);
                });
        }
    });
