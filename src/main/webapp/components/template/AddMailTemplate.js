angular.module('LM')
    .controller('TemplateCtrl', function ($scope, $http) {

        $scope.saveTemplate = function () {
            if (!$scope.addTemplateForm.$valid) {
                return;
            }

            $http.post('rest/template').
                then(function (response) {

                }, function (response) {
                    console.error('Something went wrong with adding template post method.');
                });
        }

    });