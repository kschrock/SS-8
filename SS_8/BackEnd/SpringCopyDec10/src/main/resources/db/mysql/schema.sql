
CREATE TABLE IF NOT EXISTS profile (
  id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  first_name VARCHAR(30),
  last_name VARCHAR(30),
  address VARCHAR(255),
  telephone VARCHAR(80),
  points INT(5),
  username VARCHAR(30),
  password VARCHAR(30),
  INDEX(last_name)
) engine=InnoDB;


CREATE TABLE IF NOT EXISTS testing2 (
  id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  user_name VARCHAR(30),
  user_pass VARCHAR(30),
  user_email VARCHAR(255),
  INDEX(user_email)
) engine=InnoDB;




CREATE TABLE IF NOT EXISTS qrcode (
  id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  points INT(4),
  company VARCHAR(30),
  INDEX(company)
) engine=InnoDB;
INSERT IGNORE INTO qrcode VALUES (NULL, 'Intel', '1000')
INSERT IGNORE INTO qrcode VALUES (NULL, 'Ibm', '2500')

