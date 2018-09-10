CREATE SCHEMA IF NOT EXISTS `otus_books`;
DROP TABLE IF EXISTS `comment`;
DROP TABLE IF EXISTS `book`;
DROP TABLE IF EXISTS `genre`;
DROP TABLE IF EXISTS `author`;
DROP TABLE IF EXISTS `user_role`;
DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `role`;


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


CREATE TABLE `user` (
  `id`       BIGINT       NOT NULL AUTO_INCREMENT,
  `name`     VARCHAR(120) NULL,
  `password` TEXT         NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `role` (
  `role_id` BIGINT      NOT NULL AUTO_INCREMENT,
  `name`    VARCHAR(45) NULL,
  PRIMARY KEY (`role_id`)
);

CREATE TABLE `user_role` (
  `user_role_id` BIGINT NOT NULL AUTO_INCREMENT,
  `role_id`      BIGINT NULL,
  `user_id`      BIGINT NULL,
  PRIMARY KEY (`user_role_id`),
  CONSTRAINT `role_user`
  FOREIGN KEY (`role_id`)
  REFERENCES `role` (`role_id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE,
  CONSTRAINT `user_role`
  FOREIGN KEY (`user_id`)
  REFERENCES `user` (`id`)
  ON DELETE CASCADE
  ON UPDATE CASCADE
);

