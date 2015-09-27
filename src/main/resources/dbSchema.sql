CREATE TABLE IF NOT EXISTS License (
  id              INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  product         VARCHAR(100)                   NOT NULL,
  name            VARCHAR(100)                   NOT NULL,
  organization    VARCHAR(200)                   NOT NULL,
  email           VARCHAR(50)                    NOT NULL,
  skype           VARCHAR(20),
  phone           VARCHAR(100),
  applicationArea VARCHAR(200)
);

CREATE TABLE IF NOT EXISTS Product (
  id        INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  name      VARCHAR(100)                   NOT NULL,
  release   VARCHAR(100)                   NOT NULL
);