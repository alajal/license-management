angular.module('LM')
    .controller('TemplateCtrl', function ($scope, $http) {

        $scope.saveMailBody = function () {
            if (!$scope.addTemplateForm.$valid) {
                return;
            }

            $scope.mailBody = {};

            $http.post('rest/template/mailBody', mailBody).
                then(function (response) {

                }, function (response) {
                    console.error('Something went wrong with adding template post method.');
                });
        };


        $scope.uploadFile = function () {

            var file = document.getElementById('file').files[0], reader = new FileReader();

            reader.onloadend = function(e){
                var arraybuffer = e.target.result;
                var base64String = btoa(String.fromCharCode.apply(null, new Uint8Array(arraybuffer)));
                console.log("base64 string:");
                console.log(base64String);

                var upload = {
                    data: base64String,
                    fileName: file.name
                };

                console.log("upload");
                console.log(upload);

                $http.post('#/template/attachment', upload).
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