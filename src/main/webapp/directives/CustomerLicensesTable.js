app.directive('customerLicensesTable', function () {
    return {
        restrict: 'E',
        templateUrl: 'directives/customerLicensesTable.html',
        link: function (scope) {

            //used for sorting
            scope.sortType = 'validTill'; // set the default sort type
            scope.sortReverse = true;  // set the default sort order
            scope.searchLicense = '';     // set the default search/filter term

            // used for calculating expiration
            scope.currentDate = new Date().getTime();
            scope.compare = function (expDate) {
                var eDate = new Date(expDate);
                return eDate.getTime();
            };
        }
    };
});
