
var application = angular.module('application', []);
application.controller('formCtrl', ['$scope','$http', '$window', function ($scope, $http, $window) {

    $scope.saveData = function () {
         console.log($scope.user);
        //tee päring
        //urliks /application
        if(!$scope.form.$valid) {
            return;
        }

        $http.post('/licenses', $scope.user).
            then(function(response) {
                // this callback will be called asynchronously
                // when the response is available

                //vii mind uuele lehele
                $window.location.href = '/licenses';
            }, function(response) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.

                //nt kui päringus on midagi valesti - emailis on ainult numbrid
                console.error('Mis iganes.');
            });
    }
}]);

