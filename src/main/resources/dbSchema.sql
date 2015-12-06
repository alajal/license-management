DROP TABLE IF EXISTS Product;
CREATE TABLE IF NOT EXISTS Product (
  id   INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  name VARCHAR(100) UNIQUE            NOT NULL
);

DROP TABLE IF EXISTS Release;
CREATE TABLE IF NOT EXISTS Release (
  id           INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  productId    INT                            NOT NULL,
  version      VARCHAR(100),
  additionDate DATE,
  FOREIGN KEY (productId) REFERENCES Product (id),
);

DROP TABLE IF EXISTS Customer;
CREATE TABLE IF NOT EXISTS Customer (
  id               INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  organizationName VARCHAR(200) UNIQUE            NOT NULL,
  applicationArea  VARCHAR(200),

  address          VARCHAR(100),
  webpage          VARCHAR(100),
  registrationCode VARCHAR(100),
  phone            VARCHAR(20),
  bankaccount      VARCHAR(50),
  fax              VARCHAR(20),
  unitOrFaculty    VARCHAR(100)
);

DROP TABLE IF EXISTS License;
CREATE TABLE IF NOT EXISTS License (
  id                    INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  productId             INT                            NOT NULL,
  releaseId             INT,
  customerId            INT                            NOT NULL,
  contractNumber        VARCHAR(100) UNIQUE            NOT NULL,
  validFrom             DATE,
  validTill             DATE,
  licenseTypeId         INT,
  state                 INT                            NOT NULL,
  predecessorLicenseId  VARCHAR(100),

  applicationSubmitDate DATE,
  latestDeliveryDate    DATE,
  FOREIGN KEY (productId) REFERENCES Product (id),
  FOREIGN KEY (customerId) REFERENCES Customer (id),
  FOREIGN KEY (releaseId) REFERENCES Release (id)
);

CREATE TABLE IF NOT EXISTS LicenseType (
  id             INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  name           VARCHAR(100),
  validityPeriod VARCHAR(100)                   NOT NULL,
  cost           DOUBLE
);

DROP TABLE IF EXISTS MailAttachment;
CREATE TABLE IF NOT EXISTS MailAttachment (
  id       INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  fileName VARCHAR(100)                   NOT NULL,
  fileData BLOB                           NOT NULL
);

DROP TABLE IF EXISTS MailBody;
CREATE TABLE IF NOT EXISTS MailBody (
  id            INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  subject       VARCHAR(100)                   NOT NULL,
  body          VARCHAR(500)                   NOT NULL,
  licenseTypeId INT
);

DROP TABLE IF EXISTS AuthorisedUser;
CREATE TABLE IF NOT EXISTS AuthorisedUser (
  id         INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  licenseId  INT                            NOT NULL,
  firstName  VARCHAR(200)                   NOT NULL,
  lastName   VARCHAR(100)                   NOT NULL,
  email      VARCHAR(100)                   NOT NULL,
  occupation VARCHAR(100)                   NOT NULL,
  FOREIGN KEY (licenseId) REFERENCES License (id)
);

DROP TABLE IF EXISTS Contact;
CREATE TABLE IF NOT EXISTS Contact (
  id         INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  customerId INT                            NOT NULL,
  firstName  VARCHAR(200),
  lastName   VARCHAR(200),
  email      VARCHAR(100),
  skype      VARCHAR(100),
  phone      VARCHAR(100),
  FOREIGN KEY (customerId) REFERENCES Customer (id)
);

DROP TABLE IF EXISTS Event;
CREATE TABLE IF NOT EXISTS Event (
  id          INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  licenseId   INT,
  name        VARCHAR(100),
  description VARCHAR(200),
  type        VARCHAR(100),
  dateCreated DATETIME,
  FOREIGN KEY (licenseId) REFERENCES License (id)
);
