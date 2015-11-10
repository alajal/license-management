angular
    .module('LM')
    .controller('ViewEventsCtrl', function ($scope, $http) {

        $http.get('rest/events').
            then(function (response) {

                $scope.events = response.data;
            }, function (response) {

                console.error('Error occured.');
            });

    });
