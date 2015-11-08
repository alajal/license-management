/**
 * Created by siiri on 07/11/15.
 */
angular.module('LM')
    .controller('ExpiringLicensesCtrl', function ($scope, $http) {

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
    });