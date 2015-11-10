angular
    .module('LM')
    .controller('ViewHistoryCtrl', function ($scope, $http, $routeParams) {

        $http.get('rest/events').
            then(function (response) {

                $scope.events = response.data;
            }, function (response) {

                console.error('Error occured.');
            });

        $scope.licenseId = $routeParams.id;

        //used for sorting
        $scope.sortType     = 'name'; // set the default sort type
        $scope.sortReverse  = false;  // set the default sort order
        $scope.searchLicense   = '';     // set the default search/filter term
    });
