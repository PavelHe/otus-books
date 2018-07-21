CREATE SCHEMA IF NOT EXISTS `otus_books`;
DROP TABLE IF EXISTS `comment_book`;
DROP TABLE IF EXISTS `book`;
DROP TABLE IF EXISTS `genre`;
DROP TABLE IF EXISTS `author`;
DROP TABLE IF EXISTS `comment`;


CREATE TABLE `author` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `surname` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `genre` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `book` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `genre_id` bigint(20) DEFAULT NULL,
  `author_id` bigint(20) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `book_authors_idx` (`author_id`),
  KEY `book_genres_idx` (`genre_id`),
  CONSTRAINT `book_authors` FOREIGN KEY (`author_id`) REFERENCES `author` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `book_genres` FOREIGN KEY (`genre_id`) REFERENCES `genre` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `comment` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(60) NULL,
  `text` VARCHAR(120) NULL,
  `time_of_commit` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`))ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `comment_book` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `comment_id` BIGINT(20) NULL,
  `book_id` BIGINT(20) NULL,
  PRIMARY KEY (`id`),
  INDEX `book_comment_idx` (`book_id` ASC),
  INDEX `comment_comment_idx` (`comment_id` ASC),
  CONSTRAINT `book_comment`
  FOREIGN KEY (`book_id`)
  REFERENCES `book` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `comment_comment`
  FOREIGN KEY (`comment_id`)
  REFERENCES `comment` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)ENGINE=InnoDB DEFAULT CHARSET=utf8;

