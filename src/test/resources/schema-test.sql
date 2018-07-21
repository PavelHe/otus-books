CREATE SCHEMA IF NOT EXISTS `otus_books`;
DROP TABLE IF EXISTS `comment_book`;
DROP TABLE IF EXISTS `comment`;
DROP TABLE IF EXISTS `book`;
DROP TABLE IF EXISTS `genre`;
DROP TABLE IF EXISTS `author`;


CREATE TABLE `author` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `surname` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `genre` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `book` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `genre_id` bigint DEFAULT NULL,
  `author_id` bigint DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `book_authors` FOREIGN KEY (`author_id`) REFERENCES `author` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `book_genres` FOREIGN KEY (`genre_id`) REFERENCES `genre` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `comment` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `text` VARCHAR(45) NULL,
  `time_of_commit` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`));

CREATE TABLE `comment_book` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `comment_id` BIGINT NULL,
  `book_id` BIGINT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `book_comment`
  FOREIGN KEY (`book_id`)
  REFERENCES `book` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE,
  CONSTRAINT `comment_comment`
  FOREIGN KEY (`comment_id`)
  REFERENCES `comment` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE);

