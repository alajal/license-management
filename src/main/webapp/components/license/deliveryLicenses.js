angular
    .module('LM')
    .controller('DeliveryLicensesCtrl', function ($scope, $http, $filter) {

        //used for sorting
        $scope.sortType     = 'validTill'; // set the default sort type
        $scope.sortReverse  = false;  // set the default sort order
        $scope.searchLicense   = '';     // set the default search/filter term

        $http.get('rest/licenses').
            then(function (response) {
                $scope.allLicenses = response.data;
                $scope.deliveryFilterType = "delivery";
                $scope.newValue($scope.deliveryFilterType);
            }, function (response) {
                console.error('There was something wrong with the view licenses request.');
            });
        $scope.newValue = function(value){
        	switch (value) {
        	case "delivery":
        		//Delivery is needed when license is ACTIVE and release is null.
        		//TODO WAITING_FOR_SIGNATURE is for testing, should be ACTIVE
                $scope.licenses = $filter('filter')($scope.allLicenses, {state: "ACTIVE", release: null});
                break;
        	case "update":
        		//Update is needed when license is ACTIVE and there is a newer version of the product.
        		$scope.licenses = $filter('updateNeeded')($scope.allLicenses);
        		break;
        	case "upToDate":
        		//Is up to date when license is ACTIVE and has the latest release of the product.
        		$scope.licenses = $filter('upToDate')($scope.allLicenses);
        		break;
        	}
        };
    });

angular
	.module('LM')
	.filter('updateNeeded', function() {
		  return function(input) {
			  var output = [];
			  input.forEach(function(license){
				  var updateNeeded = false;
				  var currentRelease = license.release;
				  var product = license.product;
				  var releases = product.releases;
				  releases.forEach(function(release){
					  if(currentRelease != null && release.id > currentRelease.id){
						  updateNeeded = true;
					  }
				  });
	        		//TODO WAITING_FOR_SIGNATURE is for testing, should be ACTIVE
				  if(updateNeeded && license.state == "ACTIVE" && currentRelease != null){
					  output.push(license);
				  }
			  });
			  return output;
		  }
		  });

angular
.module('LM')
.filter('upToDate', function() {
	  return function(input) {
		  var output = [];
		  input.forEach(function(license){
			  var updateNeeded = false;
			  var currentRelease = license.release;
			  var product = license.product;
			  var releases = product.releases;
			  releases.forEach(function(release){
				  if(currentRelease != null && release.id > currentRelease.id){
					  updateNeeded = true;
				  }
			  });
        		//TODO WAITING_FOR_SIGNATURE is for testing, should be ACTIVE
			  if(!updateNeeded && license.state == "ACTIVE" && currentRelease != null){
				  output.push(license);
			  }
		  });
		  return output;
	  }
	  });
