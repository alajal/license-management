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


        $scope.uploadFile = function () {
            var file = document.getElementById('file').files[0], reader = new FileReader();

            reader.onloadend = function (e) {
                var arraybuffer = e.target.result;
                var base64String = btoa(String.fromCharCode.apply(null, new Uint8Array(arraybuffer)));
                var upload = {
                    data: base64String,
                    fileName: file.name
                };

                console.log("upload");
                console.log(upload);

                $http.post('rest/template/attachment', upload).
                    then(function (response) {
                        console.log("File was uploaded");
                    }, function (rsponse) {
                        console.error('Something went wrong with adding file/attachment post method.')
                    });
            };
            //kui reader on blobi ära lugenud, minnakse onloadend funktsiooni
            reader.readAsArrayBuffer(file);
        };
    });