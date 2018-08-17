CREATE SCHEMA IF NOT EXISTS `otus_books`;
DROP TABLE IF EXISTS `comment`;
DROP TABLE IF EXISTS `book`;
DROP TABLE IF EXISTS `genre`;
DROP TABLE IF EXISTS `author`;


CREATE TABLE `author` (
  `id`      BIGINT NOT NULL AUTO_INCREMENT,
  `name`    VARCHAR(45)     DEFAULT NULL,
  `surname` VARCHAR(45)     DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `genre` (
  `id`   BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45)     DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `book` (
  `id`          BIGINT NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(45)     DEFAULT NULL,
  `genre_id`    BIGINT          DEFAULT NULL,
  `author_id`   BIGINT          DEFAULT NULL,
  `description` VARCHAR(200)    DEFAULT NULL,
  `photo`       LONGBLOB        DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `book_authors` FOREIGN KEY (`author_id`) REFERENCES `author` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `book_genres` FOREIGN KEY (`genre_id`) REFERENCES `genre` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `comment` (
  `id`             BIGINT      NOT NULL AUTO_INCREMENT,
  `name`           VARCHAR(45) NULL,
  `text`           VARCHAR(45) NULL,
  `time_of_commit` TIMESTAMP   NULL     DEFAULT CURRENT_TIMESTAMP,
  `book_id`        BIGINT               DEFAULT NULL,
  CONSTRAINT `book_comments` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE,
  PRIMARY KEY (`id`)
);

