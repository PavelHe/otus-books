INSERT INTO `author` VALUES (1,'Neil','Gaiman'),(2,'Douglas','Adams');
INSERT INTO `genre` VALUES (1,'Fantasy'),(2,'Comedy');
INSERT INTO `book` VALUES (1,'Neverwhere',1,1,'Neverwhere book'),(2,'The Hitchhiker’s Guide to the Galaxy',2,2,'The Hitchhiker’s Guide to the Galaxy book');
INSERT INTO `comment`(`id`, `name`, `text`) VALUES (1,'Alfred', 'comment');
INSERT INTO `comment_book` VALUES (1, 1, 1);