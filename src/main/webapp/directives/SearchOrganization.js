function searchDirectiveLink(scope, element, attrs, parse) {
    var input = element.find('input');
    var modelAccessor = parse(attrs.ngObjectModel);
    //todo modalsetter is not a values
    var modelSetter = modelAccessor.assign;
    if (attrs.required != undefined) {
        input.attr('required', '');
    }

    //if modelAccessor changes, do this function
    scope.$watch(modelAccessor, function (val) {
        scope.searchResult = val;

    });

    /*input.bind('blur', function () {
     scope.$apply(function () {
     modelSetter(scope, input.val());
     });
     });*/
}

function searchDirectiveController($scope, $http, restUrl, displayKey) {
    $scope.searchResult = '';
    $scope.search = function (val) {
        var config = {
            params: {
                search: val
            }
        };

        var responseFunction = function (response) {
            var filter = [];
            angular.forEach(response.data, function (item) {
                //typeahead needs json object with key-value pair and displayKey to be "name"
                var items = {};
                items[displayKey] = item.organizationName;
                filter.push(items);
            });
            return filter;
        };


        var httpPromise = $http.get(restUrl, config);
        console.log("Promise:");
        console.log(httpPromise);
        return httpPromise.then(responseFunction);
    };
}


app.directive('searchOrganization', function ($parse) {
    return {
        restrict: 'E',
        templateUrl: 'directives/searchOrganization.html',
        controller: function ($scope, $http) {
            searchDirectiveController($scope, $http, 'rest/customers/', 'organizationName');
        },
        link: function ($scope, element, attrs) {
            searchDirectiveLink($scope, element, attrs, $parse);
        }

    };
});
