CREATE SCHEMA IF NOT EXISTS `otus_books`;
DROP TABLE IF EXISTS `comment`;
DROP TABLE IF EXISTS `book`;
DROP TABLE IF EXISTS `genre`;
DROP TABLE IF EXISTS `author`;
DROP TABLE IF EXISTS `user_role`;
DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `role`;


CREATE TABLE `author` (
  `id`      BIGINT(20) NOT NULL AUTO_INCREMENT,
  `name`    VARCHAR(45)         DEFAULT NULL,
  `surname` VARCHAR(45)         DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `genre` (
  `id`   BIGINT(20) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45)         DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `book` (
  `id`          BIGINT(20) NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(45)         DEFAULT NULL,
  `genre_id`    BIGINT(20)          DEFAULT NULL,
  `author_id`   BIGINT(20)          DEFAULT NULL,
  `description` VARCHAR(200)        DEFAULT NULL,
  `photo`       LONGBLOB            DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `book_authors_idx` (`author_id`),
  KEY `book_genres_idx` (`genre_id`),
  CONSTRAINT `book_authors` FOREIGN KEY (`author_id`) REFERENCES `author` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `book_genres` FOREIGN KEY (`genre_id`) REFERENCES `genre` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `comment` (
  `id`             BIGINT(20)   NOT NULL AUTO_INCREMENT,
  `name`           VARCHAR(60)  NULL,
  `text`           VARCHAR(120) NULL,
  `time_of_commit` TIMESTAMP    NULL     DEFAULT CURRENT_TIMESTAMP,
  `book_id`        BIGINT(20)            DEFAULT NULL,
  KEY `book_comment_idx` (`book_id`),
  CONSTRAINT `book_comments` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `user` (
  `id`       BIGINT(20)   NOT NULL AUTO_INCREMENT,
  `name`     VARCHAR(120) NULL,
  `password` TEXT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `role` (
  `role_id` BIGINT(20)  NOT NULL AUTO_INCREMENT,
  `name`    VARCHAR(45) NULL,
  PRIMARY KEY (`role_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `user_role` (
  `user_role_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `role_id`      BIGINT(20) NULL,
  `user_id`      BIGINT(20) NULL,
  PRIMARY KEY (`user_role_id`),
  INDEX `role_user_idx` (`role_id` ASC),
  INDEX `user_role_idx` (`user_id` ASC),
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
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

