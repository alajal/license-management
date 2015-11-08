app.directive('eventTable', function() {
  return {
    restrict: 'E',
    templateUrl: 'directives/eventTable.html',
    link: function(scope, element, attrs) {

      //used for sorting
      scope.sortType     = 'name'; // set the default sort type
      scope.sortReverse  = false;  // set the default sort order
      scope.searchProduct   = '';     // set the default search/filter term
    }
  };
});
