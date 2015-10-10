angular
    .module('LM')
    .controller('ProfileLicenseCtrl', function ($scope, $http, $routeParams, $location) {
        $scope.editorDisabled = true;

        $scope.enableEditor = function () {
            $scope.editorDisabled = false;
        };

        $scope.disableEditor = function () {
            $scope.editorDisabled = true;
        };

        $scope.save = function () {
            $scope.disableEditor();
        };


        $scope.openAuthorisedUsersForm = function () {
            var a = $location.param1;
            $location.path('/authorisedUser/add/addAuthorisedUser');
        };

        $http.get('rest/licenses').
            //server töötleb post päringut ja kui ta on sellega lõpetanud, siis minnakse siin alles edasi
            then(function (response) {
                // this callback will be called asynchronously
                // when the response is available
                $scope.license = response.data[$routeParams.id - 1];
                console.log($scope.license);
                console.log($scope.license.product.name);
                console.log($routeParams);
            }, function (response) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.

                console.error('There was something wrong with the view license request.');
            });



    });

