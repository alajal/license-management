angular.module('LM')
    .controller('TemplateCtrl', function ($scope, $http) {

        $scope.mailBody ={};
        $scope.saveMailBody = function () {
            console.log($scope.mailBody);
            $http.post('rest/template/mailBody', $scope.mailBody).
                then(function (response) {
                    $scope.mailBody.body = "";
                    $scope.mailBody.subject = "";

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