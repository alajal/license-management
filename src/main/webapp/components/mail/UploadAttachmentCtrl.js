angular.module('LM')
    .controller('FileCtrl', function ($scope, $http) {
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