angular
    .module('LM')
    .controller('LicenseTypeCtrl', function ($scope, $http, $window) {
        $scope.type = {};
        $http.get('rest/template').
            then(function (response) {
                $scope.bodies = response.data;
            }, function (response) {
                console.error('Something went wrong with the bodies get method.');
            });

        $scope.addNewType = function(){
            if (!$scope.form.$valid) {
                return;
            }

            $scope.type.mailBodyId = $scope.mailBody.id;
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
