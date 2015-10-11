INSERT INTO Product (name, release) VALUES ('MindShare', '11.2');
INSERT INTO Product (name, release) VALUES ('ShareMind','12.6');

INSERT INTO LicenseOwner (name, address, webpage, registrationCode, phone, bankAccount, fax, unitOrFaculty)
VALUES ('Example University', '123 Fake Street', 'www.example.com', '1A2B', '+372 555555', 'E100101', '+372 555555', 'Example Science');

INSERT INTO License (productId, name, licenseOwnerId, email, skype, phone, applicationArea)
VALUES (1, 'Mari Maasikas', 1, 'marii@kiisumets.ee', 'marii.kiisumets', '+372 555555', 'wood research');

INSERT INTO AuthorisedUser (licenseId, firstName, lastName, email, occupation) VALUES (1, 'John','Doe', 'johanna@gmail.com','student');