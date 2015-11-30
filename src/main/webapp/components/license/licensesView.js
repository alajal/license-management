angular
    .module('LM')
    .controller('ViewLicensesCtrl', function ($scope, $http) {

        $http.get('rest/licenses/search/'+'4').
            then(function (response) {
                $scope.licenses = response.data;

            }, function (response) {

                console.error('There was something wrong with the view licenses request.');
            });
    });
