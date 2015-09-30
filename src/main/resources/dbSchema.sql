CREATE TABLE IF NOT EXISTS Product (
  id      INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  name    VARCHAR(100)                   NOT NULL,
  release VARCHAR(100)                   NOT NULL
);


CREATE TABLE IF NOT EXISTS License (
  id              INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  productId       INT                            NOT NULL,
  name            VARCHAR(100)                   NOT NULL,
  organization    VARCHAR(200)                   NOT NULL,
  email           VARCHAR(50)                    NOT NULL,
  skype           VARCHAR(20),
  phone           VARCHAR(100),
  applicationArea VARCHAR(200),
  FOREIGN KEY (productId) REFERENCES Product (id)
);

