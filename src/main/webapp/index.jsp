<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html data-ng-app="LM">
<head>
    <meta charset="UTF-8">
    <script src="assets/lib/angular.js"></script>
    <script src="assets/lib/angular-route.js"></script>
    <script src="assets/lib/jquery/jquery-2.1.4.min.js"></script>
    <script src="assets/lib/bootstrap/bootstrap.min.js"></script>
    <script src="assets/lib/ui-bootstrap-tpls-0.14.3.min.js"></script>

    <script src="assets/lib/bootstrap/ui-bootstrap-tpls-0.14.3.min.js"></script>
    <!--<script src="assets/lib/ui-select/select.js"></script>-->
    <!--<link rel="stylesheet" href="assets/lib/ui-select/select.css" />-->
    <link type="text/css" rel="stylesheet" media="all" href="assets/lib/bootstrap/bootstrap.min.css">
    <link type="text/css" rel="stylesheet" media="all" href="assets/main.css">
    <link href="assets/lib/angular-tooltip/angular-tooltips.css" rel="stylesheet" type="text/css" />
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css" rel="stylesheet">
    <title>License management</title>

    <script src="app.module.js"></script>
    <script src="app.routes.js"></script>
    <script src="assets/lib/angular-tooltip/angular-tooltips.js"></script>

    <!-- Configuration -->
    <!-- <script src="app/shared/config.js"></script> -->

    <!-- Controllers -->
    <script src="components/home/HomeCtrl.js"></script>
    <script src="components/home/search.js"></script>
    <script src="shared/header/headerController.js"></script>
    <script src="components/license/licensesView.js"></script>
    <script src="components/license/addLicense.js"></script>
    <script src="components/license/profileLicense.js"></script>
    <script src="components/product/chooseProduct.js"></script>
    <script src="components/product/productsView.js"></script>
    <script src="components/product/addSingleProduct.js"></script>
    <script src="components/customer/customersView.js"></script>
    <script src="components/customer/addCustomer.js"></script>
    <script src="components/customer/profileCustomer.js"></script>
    <script src="components/customer/addSingleCustomer.js"></script>
    <script src="components/authorisedUser/add/addAuthorisedUser.js"></script>
    <script src="expiringLicense/expiringLicenses.js"></script>
    <script src="components/template/AddMailTemplateCtrl.js"></script>
    <script src="components/license/eventsView.js"></script>
    <script src="components/license/history.js"></script>
    <script src="components/license/deliveryLicenses.js"></script>
    <script src="components/license/licenseType.js"></script>
    <script src="components/license/deliveryNotification.js"></script>

    <!-- Services -->
    <script src="services/LicensingService.js"></script>
    <script src="services/SearchService.js"></script>
    <!-- Filters -->

    <!-- Directives -->
    <script src="directives/licenseTable.js"></script>
    <script src="directives/licenseOwnerTable.js"></script>
    <script src="directives/productTable.js"></script>
    <script src="directives/eventTable.js"></script>
    <script src="directives/SearchOrganization.js"></script>

</head>
<body>
<!-- Header -->
<div data-ng-include="'shared/header/header.html'"></div>

<!-- Make user and roles info available to the frontend -->
<script>
angular.module('LM')
.run(function($rootScope) {
    //TODO Change these default values back to shiro tags.
    $rootScope.username = "testUser";//"<shiro:principal></shiro:principal>";
    $rootScope.isLicensingManager = true;//<shiro:hasRole name="licensingManager">true</shiro:hasRole><shiro:lacksRole name="licensingManager">false</shiro:lacksRole>;
    $rootScope.isReleaseEngineer = true;//<shiro:hasRole name="releaseEngineer">true</shiro:hasRole><shiro:lacksRole name="releaseEngineer">false</shiro:lacksRole>;
})
</script>

<!-- *View.html -->
<div data-ng-view></div>
</body>
</html>
