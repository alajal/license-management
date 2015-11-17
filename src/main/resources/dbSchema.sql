CREATE TABLE IF NOT EXISTS Product (
  id      INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  name    VARCHAR(100)                   NOT NULL,
  release VARCHAR(100)                   NOT NULL
);


CREATE TABLE IF NOT EXISTS Customer (
  id               INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  organizationName VARCHAR(200)                   NOT NULL,
  applicationArea  VARCHAR(200),

  address          VARCHAR(100),
  webpage          VARCHAR(100),
  registrationCode VARCHAR(100),
  phone            VARCHAR(20),
  bankaccount      VARCHAR(50),
  fax              VARCHAR(20),
  unitOrFaculty    VARCHAR(100)
);


CREATE TABLE IF NOT EXISTS License (
  id                    INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  productId             INT                            NOT NULL,
  customerId            INT                            NOT NULL,
  contractNumber        VARCHAR(100)                   NOT NULL,
  validFrom             DATE,
  validTill             DATE,
  state                 INT                            NOT NULL,
  predecessorLicenseId  VARCHAR(100),
  applicationSubmitDate DATE,
  FOREIGN KEY (productId) REFERENCES Product (id),
  FOREIGN KEY (customerId) REFERENCES Customer (id)
);


CREATE TABLE IF NOT EXISTS AuthorisedUser (
  id         INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  licenseId  INT                            NOT NULL,
  firstName  VARCHAR(200)                   NOT NULL,
  lastName   VARCHAR(100)                   NOT NULL,
  email      VARCHAR(100)                   NOT NULL,
  occupation VARCHAR(100)                   NOT NULL,
  FOREIGN KEY (licenseId) REFERENCES License (id)
);

CREATE TABLE IF NOT EXISTS Contact (
  id         INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  customerId INT                            NOT NULL,
  fullName   VARCHAR(200),
  email      VARCHAR(100),
  skype      VARCHAR(100),
  phone      VARCHAR(100),
  FOREIGN KEY (customerId) REFERENCES Customer (id)
);

CREATE TABLE IF NOT EXISTS Event (
  id          INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  licenseId   INT                            NULL,
  name        VARCHAR(100),
  description VARCHAR(200),
  type        VARCHAR(100),
  dateCreated DATETIME,
  FOREIGN KEY (licenseId) REFERENCES License (id)
);
