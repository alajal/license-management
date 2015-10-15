app.directive('licenseTable', function() {
  return {
    restrict: 'E',
    templateUrl: 'directives/licenseTable.html',
    link: function(scope, element, attrs) {

      //used for sorting
      scope.sortType     = 'validTill'; // set the default sort type
      scope.sortReverse  = false;  // set the default sort order
      scope.searchLicense   = '';     // set the default search/filter term

      // used for calculating expiration
      scope.currentDate = new Date().getTime();
      scope.compare = function(expDate) {
        var eDate = new Date(expDate);
        return eDate.getTime();
      };
    }
  };
});
