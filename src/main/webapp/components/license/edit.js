/**
 * Created by siiri on 08/10/15.
 */
angular
    .module('LM')
    .controller('EditController', function($scope, $location){

    $scope.openAuthorisedUsersForm = function() {
        var a = $location.param1;
        $location.path('/authorisedUser/add/addAuthorisedUser');
        };
});