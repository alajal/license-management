angular
    .module('LM')
    .controller('ProfileLicenseCtrl', function ($scope, $http, $routeParams, $location) {
        $scope.editorDisabled = true;

        $scope.AuthorisedUserForm = false;

        $scope.openAuthorisedUserForm = function() {
            $scope.AuthorisedUserForm = true;
        };

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

        $scope.saveData = function () {
            console.log($scope.authorised);
            if (!$scope.form.$valid) {
                return;
            }

            $http.post('rest/authorisedUser/bylicense/' + $routeParams.id, $scope.authorised).
                then(function (response) {
                    $scope.authorisedUser.push($scope.authorised);
                    $scope.form.$setUntouched();
                    $scope.form.$setPristine();
                    $scope.authorised = null;
                    $scope.AuthorisedUserForm = false;
                }, function (response) {
                    console.error('Something went wrong with post authorised users request.');
                });
        }

        $http.get('rest/authorisedUser/bylicense/'+ $routeParams.id).
            then(function (response) {
                $scope.authorisedUser = response.data;
            }, function (response) {
                console.error('Something went wrong with the authorised users get method.');
            });
    });

