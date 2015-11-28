angular
    .module('LM')
    .controller('ViewProductsCtrl', function ($scope, $http, $routeParams) {

        $scope.rowforms = {};

        //PRODUCT METHODS
        $http.get('rest/products').
            then(function (response) {

                $scope.products = response.data;
                $scope.products.forEach(function(entry){ // entry is product
                    entry.editing = false;
                    entry.releases.forEach(function(release){
                        release.editing = false;
                    });
                });
            }, function (response) {

                console.error('Error occured with Product get request.');
            });

        $scope.editProduct = function(entry){
            entry.editing = true;
        };

        $scope.cancelProductEditing = function(entry){
            entry.editing = false;
        };

        $scope.saveProduct = function(product){
            $http.put('rest/products', product).then(function(response) {
                console.log($scope.product);
                product.editing = false;
            }, function (response) {
                console.error(response);
                product.editing = true;
            });
        };

        $scope.deleteProduct = function(product){

            $http.delete('rest/products/' + product.id).
                then(function(response){
                    if(response.data == 'true'){
                        var deletableProductIndex = $scope.products.indexOf(product);
                        $scope.products.splice(deletableProductIndex,1);
                    }
                    else{
                        $scope.showProductDeleteNotification = true;
                    }

                },  function (response) {

                    console.error('Product delete request failed');
                });
        };

        $scope.sayHello = function(){
            alert("hi");
        };

        //RELEASE METHODS
        $scope.editRelease = function(release){
            release.editing = true;

        };
        $scope.cancelReleaseEditing = function(release){
            release.editing = false;

        };

        $scope.saveRelease = function(release){
            $http.put('rest/releases', release).then(function(response){
                console.log(release);
            }, function (response) {
                console.error(response);
            });

            release.editing = false;
        };

        $scope.deleteRelease = function(release, product){

            $http.delete('rest/releases/' + release.id).
                then(function(response){
                    if(response.data == 'true'){
                        product.releases.splice(product.releases.indexOf(release), 1);
                    }
                    else{
                        $scope.showReleaseDeleteNotification = true;
                    }

                },  function (response) {

                    console.error('Release delete request failed');
                });
        };

    });
