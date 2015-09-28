var licensesView = angular.module('licensesView', []);
licensesView.controller('licensesCtrl', ['$scope', '$http', function ($scope, $http) {

    $http.get('rest/licenses').
        //server töötleb post päringut ja kui ta on sellega lõpetanud, siis minnakse siin alles edasi
        then(function (response) {
            // this callback will be called asynchronously
            // when the response is available
            $scope.licenses = response.data;
        }, function (response) {
            // called asynchronously if an error occurs
            // or server returns response with an error status.

            //nt kui päringus on midagi valesti - emailis on ainult numbrid
            console.error('Mis iganes.');
        });


}]);
