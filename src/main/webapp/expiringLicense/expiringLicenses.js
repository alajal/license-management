angular.module('LM')
    .controller('ExpiringLicensesCtrl', function ($scope, $http, $location) {

        $http.get('rest/licenses/expiring/').
            then(function (response) {
                $scope.expiring = response.data;
                //if ($scope.expiring.length > 0) {
                //expiringLicenses.setExpiring(true);
                //}
                //alert($scope.expiring.length);
            }, function (response) {
                console.error('Something went wrong with the expiring licenses get method.');
            });

        $scope.goToLicenseProfile = function(license) {
            $location.path('/license/' + license.id);
        }
    });