app.directive('licenseOwnerTable', function() {
  return {
    restrict: 'E',
    templateUrl: 'directives/licenseOwnerTable.html',
    link: function(scope, element, attrs) {
      //Could be used later to write less code in html(theaders to be precise)
      //scope.heads = { h:['name', 'address', 'webpage', 'registrationCode', 'phone', 'bankAccount', 'fax', 'unitOrFaculty']};

      //used for sorting
      scope.sortType     = 'organizationName'; // set the default sort type
      scope.sortReverse  = false;  // set the default sort order
      scope.searchLicenseOwner   = '';     // set the default search/filter term

    }
  };
});
