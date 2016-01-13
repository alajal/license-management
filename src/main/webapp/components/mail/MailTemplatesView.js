'use strict';

angular
    .module('LM')
    .controller('ViewMailTemplatesCtrl', function ($scope, $http) {

        $http.get('rest/template').
            then(function (response) {
                $scope.templates = response.data;
            }, function (response) {
                console.error('There was something wrong with the view templates request.');
            });

    });
