angular
    .module('LM')
    .controller('ShowEmail', ['$scope', '$http', '$window', 'LicensingService', 'notifications',
        function ($scope, $http, $window, LicensingService, notifications) {

            $scope.subject = LicensingService.getSubject();
            $scope.formattedBody = LicensingService.getBody();
            $scope.licenseType = LicensingService.getLicenseType();
            $scope.contact = LicensingService.getContact();   //need kontaktid, mis meili saatmisel valitakse - kuigi hetkel saab valida eianult ühte kontakti
            $scope.attachmentId = LicensingService.getAttachmentId();
            $scope.licenseId = LicensingService.getLicenseId();

            $scope.showSuccess = function () {
                notifications.showSuccess({
                    message: 'Email has been sent successfully!',
                    hideDelay: 500,
                    hide: true
                });
            };

            $scope.sendMail = function () {

                var mail = {
                    id: 1,                         //vahet ei ole, mis see on...
                    subject: $scope.subject,
                    body: $scope.formattedBody,
                    licenseType: $scope.licenseType,
                    contactIds: $scope.contact.id
                };

                $http.put('rest/sendMail/' + $scope.attachmentId + '/' + $scope.licenseId, mail).
                    then(function (response) {
                        console.log("Email sent");
                        $scope.showSuccess();
                    }, function (response) {
                        console.error('Could not send email!');
                    })
            };
        }]);