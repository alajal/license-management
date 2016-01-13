'use strict';

angular
    .module('LM')
    .controller('ProfileTemplateCtrl', function ($scope, $http, $routeParams, $window) {
        $scope.editorDisabled = true;

        $http.get('rest/template/' + $routeParams.id).
            then(function (response) {
                $scope.template = response.data;
            }, function (response) {
                console.error(response);
            });

        $scope.enableEditor = function () {
            $scope.editorDisabled = false;
        };

        $scope.disableEditor = function () {
            $scope.editorDisabled = true;
        };

        $scope.editTemplate = function (template) {
            template.editing = true;
        };

        $scope.cancelTemplateEditing = function (template) {
            template.editing = false;
        };

        $scope.saveTemplateProfile = function () {
            $scope.disableEditor();
            console.log("Template:");
            console.log($scope.template);
            $http.put('rest/template/' + $scope.template.id, $scope.template).
                then(function (response) {
                    console.log("Template peale uuendamist:");
                    console.log($scope.template);
                }, function (response) {
                    console.error(response);
                });
        };

        $scope.deleteTemplate = function () {
            $http.delete('rest/template/' + $scope.template.id).
                then(function (response) {
                    $window.location.href = '#/viewMailTemplates';
                }, function (response) {
                    console.error('Something went wrong with delete mail template request.');
                });


        }
    });
