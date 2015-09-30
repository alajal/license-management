// Add controller to module.
angular
    .module('LM')
    .controller('AddProductCtrl', AddLicenseCtrl);

function AddProductCtrl($scope, $http, $window){
	
    $scope.saveData = function () {
        console.log($scope.product);

        if(!$scope.form.$valid) {
            return;
        }

        $http.post('rest/products', $scope.product).
            then(function(response) {

                $window.location.href = 'rest/products';
            }, function(response) {

                console.error('Tekkis viga');
            });
    }
};

