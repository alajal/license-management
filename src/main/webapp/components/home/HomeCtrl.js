angular.module('LM')
    .controller('HomeCtrl', function ($scope, $http) {

      $http.get('rest/events').
        then(function(response) {
          $scope.events = response.data;
          //console.log($scope.events[0]);
        }, function(reponse) {
          console.error('Error occured.');
          //console.error(reponse);  //Use this for detailed error. Make sure to comment out before publishing!
        });
        /*
        $http.get('rest/licenses').
            //server töötleb post päringut ja kui ta on sellega lõpetanud, siis minnakse siin alles edasi
            then(function (response) {
                // this callback will be called asynchronously
                // when the response is available
                $scope.licenses = response.data;

            }, function (response) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.

                //nt kui päringus on midagi valesti - emailis on ainult numbrid
                console.error('There was something wrong with the view licenses request.');
            });
            */
    });
