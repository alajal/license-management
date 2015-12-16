INSERT INTO Product (name) VALUES ('i-Voting');
INSERT INTO Product (name) VALUES ('sharemind');

INSERT INTO Release (productId, version, additionDate) VALUES (1, 11.2, '2015-10-22');
INSERT INTO Release (productId, version, additionDate) VALUES (1, 11.4, '2015-10-24');
INSERT INTO Release (productId, version, additionDate) VALUES (2, 1.0, '2015-10-09');
INSERT INTO Release (productId, version, additionDate) VALUES (2, 2.0, '2015-12-07');


//INSERT INTO Release (productId, version, additionDate) VALUES (1, 11.6, '2015-10-26');

INSERT INTO Customer (organizationName, applicationArea, address, webpage, registrationCode, phone, bankAccount, fax, unitOrFaculty)
VALUES ('IT Enterprises', 'Java technologies and training', 'Kensington Street 56', 'info@jtech.com', '1A2B', '(212) 556‑7325', 'E100101',
        '(212) 556‑7333', 'Department of training');

INSERT INTO LicenseType(name, validityPeriod, cost) VALUES('EU stiilis 1 a kommerts litsents', 1, 100);
INSERT INTO LicenseType(name, validityPeriod, cost) VALUES('EU stiilis 3 a kommerts litsents', 3, 300);
INSERT INTO LicenseType(name, validityPeriod, cost) VALUES('TestTypeWith15Days', 15, 100);

INSERT INTO License (productId, releaseId, customerId, contractNumber, licenseTypeId, state, applicationSubmitDate)
VALUES (1, 1, 1, 'AA-4456-87', 1, 3, '2015-09-20');

INSERT INTO License (productId, releaseId, customerId, contractNumber, licenseTypeId, state, applicationSubmitDate)
VALUES (1, 2, 1, 'AB-4457-90', 3, 3, '2015-10-20');

INSERT INTO AuthorisedUser (licenseId, firstName, lastName, email, occupation)
VALUES (1, 'John', 'Doe', 'johanna@test.com', 'student');

INSERT INTO Contact(customerId, firstName, lastName, email, skype, phone) VALUES (1, 'Mary', 'Bergman', 'anulajal@gmail.com', 'mary.bergman', '5555555');

INSERT INTO Event(licenseId, name, description, type, dateCreated) VALUES (1, 'New license added', 'New license added description', 'Add', GETDATE());

INSERT INTO Event(name, description, type, dateCreated) VALUES ('New customer added', 'New customer added description', 'Add', GETDATE());

INSERT INTO DeliveredRelease(licenseId, releaseId, deliveryDate, user) VALUES (1, 1, '2015-11-10', 'admin');
INSERT INTO DeliveredRelease(licenseId, releaseId, deliveryDate, user) VALUES (1, 2, '2015-12-12', 'testuser');

INSERT INTO MailBody(subject, body, licenseTypeId) VALUES ('License purchasing 2', 'Dear interested customer from ${organizationName}!
Here is the agreement draft of our European commercial license for product ${product} (latest release ${release}).
We will assume that the contact person is ${contactPersonFirstName} ${contactPersonLastName}.
You can find the agreement draft from the attachment.
Best wishes,
Thomas McConnell
thomas@itfuture.com
Licensing manager', 2);
INSERT INTO MailBody(subject, body, licenseTypeId) VALUES ('License expiring in 1 month', 'Dear ${contactPersonFirstName} ${contactPersonLastName},
This e-mail is a reminder that your license for ${product} (${release}) is expiring in 1 month. In order to keep using the software in your work,
please contact your license manager Thomas McConnell at thomas@itfuture.com.
Best wishes,
Thomas McConnell
thomas@itfuture.com
Licensing manager', 2);
INSERT INTO MailBody(subject, body, licenseTypeId) VALUES ('License has expired', 'Dear ${contactPersonFirstName} ${contactPersonLastName},
This e-mail is to notify you that your license for ${product} (${release}) has expired as of <expirationdate>. In order to keep using the
software in your work, please contact your license manager Thomas McConnell at thomas@itfuture.com.
Best wishes,
Thomas McConnell
thomas@itfuture.com
Licensing manager', 2);

INSERT INTO MailAttachment(fileName, fileData) VALUES ('TestFailBaitides', '546572652120536565206f6e206d65696520746573746661696c2e');
