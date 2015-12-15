angular.module('LM')
    .controller('TemplateCtrl', function ($scope, $http) {

        $http.get('rest/licenses/type').
            then(function (response) {
                $scope.types = response.data;
            }, function (response) {
                console.error('Something went wrong with the license types get method.');
            });

        $scope.mailBody ={};
        $scope.licenseType = {};

        $scope.saveMailBody = function () {
            console.log($scope.mailBody);
            $scope.mailBody.licenseTypeId = $scope.licenseType.id;
            console.log("licensetypeid");
            console.log($scope.licenseType.id);
            $http.post('rest/template/mailBody', $scope.mailBody).
                then(function (response) {
                    $scope.mailBody.body = "";
                    $scope.mailBody.subject = "";
                    $scope.licenseType = "";

                }, function (response) {
                    console.error('Something went wrong with adding template post method.');
                });
        };

    });