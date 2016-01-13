app.directive('templatesTable', function () {
    return {
        restrict: 'E',
        templateUrl: 'directives/templatesTable.html',
        link: function (scope) {
            scope.sortType = 'id'; // set the default sort type
            scope.sortReverse = false;  // set the default sort order
        }
    };
});
