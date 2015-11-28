angular
    .module('LM')
    .controller('ViewEventsCtrl', function ($scope, $http, $location) {

      var table_type;

      if($location.url() == '/eventsLicense') {
        table_type = 0;
      }

      else {
        table_type = 1;
      }

        $http.get('rest/events').
            then(function (response) {
                console.log(table_type);
                $scope.data = { events : response.data, type : table_type};
            }, function (response) {

                console.error('Error occured.');
            });

    });
