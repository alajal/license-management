app.directive('productTable', function() {
  return {
    restrict: 'E',
    templateUrl: 'directives/productTable.html',
    link: function(scope, element, attrs) {

      //used for sorting
      scope.sortType     = 'name'; // set the default sort type
      scope.sortReverse  = false;  // set the default sort order
      scope.searchProduct   = '';     // set the default search/filter term

    }
  };
});
