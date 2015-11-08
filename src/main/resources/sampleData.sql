INSERT INTO Product (name, release) VALUES ('i-Voting', '11.2');
INSERT INTO Product (name, release) VALUES ('ShareMind', '12.6');

INSERT INTO Customer (organizationName, applicationArea, address, webpage, registrationCode, phone, bankAccount, fax, unitOrFaculty)
VALUES ('Example University', 'example area', '123 Fake Street', 'www.example.com', '1A2B', '+372 555555', 'E100101',
        '+372 555555',
        'Example Science');

INSERT INTO License (productId, customerId, contractNumber, validFrom, validTill, state, applicationSubmitDate)
VALUES (1, 1, '1234qwer', '2015-10-17', '2015-11-20', 3, '2015-09-20');

INSERT INTO AuthorisedUser (licenseId, firstName, lastName, email, occupation)
VALUES (1, 'John', 'Doe', 'johanna@test.com', 'student');

INSERT INTO Contact(customerId, fullName) VALUES (1, 'Contact Testname');

INSERT INTO Event(licenseId, name, description, type, dateCreated) VALUES (1, 'New license added', 'New license added description', 'Add', GETDATE());
