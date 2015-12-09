INSERT INTO Product (name) VALUES ('i-Voting');
INSERT INTO Product (name) VALUES ('Sharemind');

INSERT INTO Release (productId, version, additionDate) VALUES (1, 11.2, '2015-10-22');
INSERT INTO Release (productId, version, additionDate) VALUES (1, 11.4, '2015-10-24');
//INSERT INTO Release (productId, version, additionDate) VALUES (1, 11.6, '2015-10-26');

INSERT INTO Customer (organizationName, applicationArea, address, webpage, registrationCode, phone, bankAccount, fax, unitOrFaculty)
VALUES ('Example University', 'example area', '123 Fake Street', 'www.example.com', '1A2B', '+372 555555', 'E100101',
        '+372 555555',
        'Example Science');

INSERT INTO LicenseType(name, validityPeriod, cost) VALUES('EU stiilis 3 a akadeemilist litsents', 3, 300);
INSERT INTO LicenseType(name, validityPeriod, cost) VALUES('EU stiilis 1 a kommerts litsents', 1, 100);
INSERT INTO LicenseType(name, validityPeriod, cost) VALUES('TestTypeWith15Days', 15, 100);

INSERT INTO License (productId, releaseId, customerId, contractNumber, licenseTypeId, state, applicationSubmitDate)
VALUES (1, 1, 1, '1234qwer', 1, 3, '2015-09-20');

INSERT INTO License (productId, releaseId, customerId, contractNumber, licenseTypeId, state, applicationSubmitDate)
VALUES (1, 2, 1, 'a1b2', 3, 3, '2015-10-20');

INSERT INTO AuthorisedUser (licenseId, firstName, lastName, email, occupation)
VALUES (1, 'John', 'Doe', 'johanna@test.com', 'student');

INSERT INTO Contact(customerId, firstName, lastName, email, skype, phone) VALUES (1, 'Siiri', 'Saar', 'siiri.saar1307@gmail.com', 'siiri', '56892314');

INSERT INTO Event(licenseId, name, description, type, dateCreated) VALUES (1, 'New license added', 'New license added description', 'Add', GETDATE());

INSERT INTO Event(name, description, type, dateCreated) VALUES ('New customer added', 'New customer added description', 'Add', GETDATE());

INSERT INTO MailBody(subject, body, licenseTypeId) VALUES ('License purchasing 1', 'tere organisatsioon ${organizationName}, kontakt inimsega ${contactPerson}. Org. email on ${email}. Toode on ${product} ja release ${release}.', 1);
INSERT INTO MailBody(subject, body, licenseTypeId) VALUES ('License purchasing 2', 'Dear interested customer ${organizationName}!
Here is the agreement draft of our European 1 year commercial license for product ${product} wiht release ${release}.
We will assume that the contact person is  ${contactPersonFirstName} ${contactPersonLastName} and the email of the company is ${email}.
The agreement draft is in the attachment. Have a nice day! ', 2);


