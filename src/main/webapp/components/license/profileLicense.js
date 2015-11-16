angular
    .module('LM')
    .controller('ProfileLicenseCtrl', function ($scope, $http, $routeParams, $location) {

        $scope.licenseId = $routeParams.id;

        $scope.editorDisabled = true;

        $scope.AuthorisedUserForm = false;

        $scope.rowforms = {};

        $scope.openAuthorisedUserForm = function () {
            //$scope.AuthorisedUserForm = true;
            var newLineNotThere = true;
            for(var i = 0; i < $scope.authorisedUser.length; i++){
                if(typeof $scope.authorisedUser[i].id === "undefined"){
                    newLineNotThere = false;
                    break;
                }
            }
            if(newLineNotThere){
                var newUser = {};
                $scope.authorisedUser.push(newUser);
                $scope.selected = newUser;
            }

        };

        $scope.enableEditor = function () {
            $scope.editorDisabled = false;
        };

        $scope.disableEditor = function () {
            $scope.editorDisabled = true;
        };

        $scope.saveProfile = function () {
            $scope.disableEditor();

            $http.put('rest/licenses/' + $scope.license.id, $scope.license).
                then(function (response) {
                    console.log($scope.license);
                    createEvent($scope.license.id);
                }, function (response) {
                    console.error(response);
                });
        };

        function createEvent(id) {
          $scope.event = {
            name : 'User name',
            description : '*user name* modified license *license nr*',
            type : 'Modify'
          };

          $http.post('rest/events/'+id, $scope.event).
              then(function(response) {
                  console.log("Event created");
                  console.log(response.data);
              }, function(response) {
                  console.error(response.errors);
              });
        }

        $scope.openAuthorisedUsersForm = function () {
            var a = $location.param1;
            $location.path('/authorisedUser/add/addAuthorisedUser');
        };

        $scope.deleteEntry = function(au){

            $http.delete('rest/authorisedUser/bylicense/' + $routeParams.id + '/' + au.id).
                then(function(response){
                    var deletableUserIndex = $scope.authorisedUser.indexOf(au);
                    $scope.authorisedUser.splice(deletableUserIndex,1);

            },  function (response) {

                    console.error('HTTP delete request failed');
                });
        };

        $scope.isActive = function (viewLocation) {
            return viewLocation === $location.path();
        };

        $http.get('rest/licenses').
            then(function (response) {
                // this callback will be called asynchronously
                // when the response is available
                $scope.license = response.data[$routeParams.id - 1];
                console.log($scope.license)
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
                    $scope.authorisedUser.push(response.data);
                    $scope.form.$setUntouched();
                    $scope.form.$setPristine();
                    $scope.authorised = null;
                    $scope.AuthorisedUserForm = false;
                }, function (response) {
                    console.error('Something went wrong with post authorised users request.');
                });
        };

        $http.get('rest/authorisedUser/bylicense/' + $routeParams.id).
            then(function (response) {
                $scope.authorisedUser = response.data;
            }, function (response) {
                console.error('Something went wrong with the authorised users get method.');
            });

        $scope.getScriptId = function(au){
            if($scope.selected && au.id === $scope.selected.id){ // or au.id == null
                return 'edit';
            }
            else return 'display';
        };

        $scope.editAuthorisedUser = function(au){
            $scope.selected = angular.copy(au); //creates a copy of au and stores it to $scope.selected variable which will be edited

        };

        $scope.save = function(au){

            if(typeof au.id === "undefined"){
                console.log(au);
                if (!$scope.rowforms.form.$valid) {
                    console.log(au, " not valid");
                    return;
                }

                $http.post('rest/authorisedUser/bylicense/' + $routeParams.id, au).
                    then(function (response) {
                        $.extend(au, response.data); //response.data adds id. This is stored in au.
                        $scope.selected = {};

                    }, function (response) {
                        console.error('Something went wrong with post authorised users request.');
                    });
            }
            else{
                $.extend(au,$scope.selected); //jQuery extend method to save the changes made in $scope.selected to au
                $http.put('rest/authorisedUser/bylicense/' + $routeParams.id, au).then(function(response){
                    console.log($scope.selected);
                }, function (response) {
                    console.error(response);

                });

                $scope.selected = {};
            }
        };


        $scope.updateAuthorisedUser = function(au){
            $.extend(au,$scope.selected); //jQuery extend method to save the changes made in $scope.selected to au
            $http.put('rest/authorisedUser/bylicense/' + $routeParams.id, au).then(function(response){
            console.log($scope.selected);
        }, function (response) {
            console.error(response);

            });

            $scope.selected = {};

        };

        $scope.reset = function(au){
            if(typeof au.id === "undefined"){
                var deletableUserIndex = $scope.authorisedUser.indexOf($scope.authorisedUser.length-1);
                $scope.authorisedUser.splice(deletableUserIndex,1);
            }
            else{
                $scope.selected = {};
            }
        };


    });
