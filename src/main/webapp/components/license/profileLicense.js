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

            $http.put('rest/licenses/' + $scope.license.id, $scope.license).
                then(function (response) {
                    console.log($scope.license);
                }, function (response){
                    console.error(response);
                });

        };


        $scope.openAuthorisedUsersForm = function () {
            var a = $location.param1;
            $location.path('/authorisedUser/add/addAuthorisedUser');
        };

        $http.get('rest/licenses').
            then(function (response) {
                // this callback will be called asynchronously
                // when the response is available
                $scope.license = response.data[$routeParams.id - 1];
            }, function (response) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                console.error('There was something wrong with the view license request.');
            });


    });

