DROP TABLE License;

CREATE TABLE IF NOT EXISTS Product (
  id      INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  name    VARCHAR(100)                   NOT NULL,
  release VARCHAR(100)                   NOT NULL
);


CREATE TABLE IF NOT EXISTS LicenseOwner (
  id               INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  name             VARCHAR(200)                   NOT NULL,
  address          VARCHAR(100)                   NOT NULL,
  webpage          VARCHAR(100)                   NOT NULL,
  registrationCode VARCHAR(100)                   NOT NULL,
  phone            VARCHAR(20),
  bankaccount      VARCHAR(50),
  fax              VARCHAR(20),
  unitOrFaculty    VARCHAR(100)
);

CREATE TABLE License (
  id              INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  productId       INT                            NOT NULL,
  name            VARCHAR(100)                   NOT NULL,
  licenseOwnerId  INT                            NOT NULL,
  email           VARCHAR(50)                    NOT NULL,
  skype           VARCHAR(20),
  phone           VARCHAR(100),
  applicationArea VARCHAR(200),
  validFrom       DATE                           NOT NULL,
  validTill       DATE                           NOT NULL,
  FOREIGN KEY (productId) REFERENCES Product (id),
  FOREIGN KEY (licenseOwnerId) REFERENCES LicenseOwner (id)
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
