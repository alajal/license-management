angular
    .module('LM')
    .controller('ViewProductsCtrl', function ($scope, $http, $routeParams) {

        $scope.rowforms = {};

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

        //PRODUCT METHODS
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
                        alert('This product is connected with license(s). To delete this product, delete the licenses first.');
                    }

                },  function (response) {

                    console.error('Product delete request failed');
                });
        };


        //RELEASE METHODS
        $scope.editRelease = function(release){
            release.editing = true;

        };
        $scope.cancelReleaseEditing = function(release){
            release.editing = false;

        };

    });
