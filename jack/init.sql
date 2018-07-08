START TRANSACTION;

CREATE DATABASE IF NOT EXISTS `ndsk`;
USE `ndsk`;
CREATE TABLE IF NOT EXISTS `city`(
	`id` int AUTO_INCREMENT PRIMARY KEY,
	`Name` varchar(255),
    `CountryCode` varchar(255),
	`District` varchar(255),
    `Population` int
);

COMMIT;