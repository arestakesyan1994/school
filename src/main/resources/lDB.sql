/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.18-log : Database - school
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`school` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `school`;

/*Table structure for table `article` */

DROP TABLE IF EXISTS `article`;

CREATE TABLE `article` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `article_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `article` */

insert  into `article`(`id`,`title`,`description`,`date`,`user_id`) values (6,'aaaa','\r\nThe selection sort is a combination of searching and sorting. During each pass, the unsorted element with the smallest (or largest) value is moved to its proper position in the array. The number of times the sort passes through the array is one less than the number of items in the array. In the selection sort, the inner loop finds the next smallest (or largest) value and the outer loop places that value into its proper location.','2018-07-02 15:06:18',35);

/*Table structure for table `article_picture` */

DROP TABLE IF EXISTS `article_picture`;

CREATE TABLE `article_picture` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pic_url` varchar(255) DEFAULT NULL,
  `article_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `article_id` (`article_id`),
  CONSTRAINT `article_picture_ibfk_1` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `article_picture` */

insert  into `article_picture`(`id`,`pic_url`,`article_id`) values (5,'1530529578478_img6.jpg',6);

/*Table structure for table `comment` */

DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` text NOT NULL,
  `user_id` int(11) NOT NULL,
  `article_picture_id` int(11) DEFAULT NULL,
  `question_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `question_id` (`question_id`),
  KEY `comment_ibfk_2` (`article_picture_id`),
  CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`article_picture_id`) REFERENCES `article_picture` (`id`),
  CONSTRAINT `comment_ibfk_3` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

/*Data for the table `comment` */

insert  into `comment`(`id`,`description`,`user_id`,`article_picture_id`,`question_id`) values (15,'awfasfasfs',35,5,NULL),(16,'coommmennt',36,NULL,29);

/*Table structure for table `lesson` */

DROP TABLE IF EXISTS `lesson`;

CREATE TABLE `lesson` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

/*Data for the table `lesson` */

insert  into `lesson`(`id`,`name`) values (1,'Algebra'),(2,'Geometry'),(3,'Armenian language'),(4,'Literature'),(5,'Russian language'),(6,'English language'),(7,'Fhysics'),(8,'Chemistry'),(9,'Biology'),(10,'Geography'),(11,'Mathematics'),(12,'Natural science'),(13,'Armenian history'),(14,'World history'),(15,'The history of Armenian Church'),(16,'Social Science'),(17,'Informatics'),(18,'Physical education'),(19,'Technology');

/*Table structure for table `message` */

DROP TABLE IF EXISTS `message`;

CREATE TABLE `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `from_user_id` int(11) NOT NULL,
  `to_user_id` int(11) NOT NULL,
  `description` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `from_user_id` (`from_user_id`),
  KEY `to_user_id` (`to_user_id`),
  CONSTRAINT `message_ibfk_1` FOREIGN KEY (`from_user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `message_ibfk_2` FOREIGN KEY (`to_user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

/*Data for the table `message` */

insert  into `message`(`id`,`from_user_id`,`to_user_id`,`description`) values (19,35,36,'barevner poxosic valodin'),(20,36,35,'barevner valodic poxosin\r\n'),(21,36,35,'v\r\n'),(22,35,35,'v2'),(23,35,36,'p1'),(24,35,36,'bla\r\nbal'),(25,36,35,'bla bla valod\r\n'),(26,37,35,'Barevner useric poxosin'),(27,35,36,'\r\nYou have it covered aside from using the wrong property. The scrollbar can be triggered with any property overflow, overflow-x, or overflow-y and each can be set to any of visible, hidden, scroll, auto, or inherit. You are currently looking at these two:\r\n\r\nauto - This value will look at the width and height of the box. If they are defined, it won\'t let the box expand past those boundaries. Instead (if the content exceeds those boundaries), it will create a scrollbar for either boundary (or both) that exceeds its length.\r\n\r\nscroll - This values forces a scrollbar, no matter what, even if the content does not exceed the boundary set. If the content doesn\'t need to be scrolled, the bar will appear as \"disabled\" or non-interactive.\r\n\r\nIf you always want the vertical scrollbar to appear:\r\n\r\nYou should use overflow-y: scroll. This forces a scrollbar to appear for the vertical axis whether or not it is needed. If you can\'t actually scroll the context, it will appear as a\"disabled\" scrollbar.\r\n\r\nIf you only want a scrollbar to appear if you can scroll the box:\r\n\r\nJust use overflow: auto. Since your content by default just breaks to the next line when it cannot fit on the current line, a horizontal scrollbar won\'t be created (unless it\'s on an element that has word-wrapping disabled). For the vertical bar,it will allow the content to expand up to the height you have specified. If it exceeds that height, it will show a vertical scrollbar to view the rest of the content, but will not show a scrollbar if it does not exceed the height.');

/*Table structure for table `question` */

DROP TABLE IF EXISTS `question`;

CREATE TABLE `question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` int(11) NOT NULL,
  `lesson_id` int(11) NOT NULL,
  `pic_url` varchar(255) DEFAULT NULL,
  `comment_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `lesson_id` (`lesson_id`),
  KEY `comment_id` (`comment_id`),
  CONSTRAINT `question_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `question_ibfk_2` FOREIGN KEY (`lesson_id`) REFERENCES `lesson` (`id`),
  CONSTRAINT `question_ibfk_4` FOREIGN KEY (`comment_id`) REFERENCES `comment` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

/*Data for the table `question` */

insert  into `question`(`id`,`title`,`description`,`date`,`user_id`,`lesson_id`,`pic_url`,`comment_id`) values (29,'bla','\r\nThe selection sort is a combination of searching and sorting. During each pass, the unsorted element with the smallest (or largest) value is moved to its proper position in the array. The number of times the sort passes through the array is one less than the number of items in the array. In the selection sort, the inner loop finds the next smallest (or largest) value and the outer loop places that value into its proper location.','2018-07-02 15:07:49',35,1,'1530529669289_img7.jpg',NULL),(30,'aaaa','yh gt fg g g gh vhvbhvgh vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv vvvvvvvvvvvvvvvvvvvvvv vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv','2018-07-02 15:12:13',36,2,'1530529933583_bg2.jpg',NULL);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `age` int(11) DEFAULT NULL,
  `pic_url` varchar(255) DEFAULT NULL,
  `user_type` enum('ADMIN','PUPIL','GUEST','TEACHER') NOT NULL DEFAULT 'GUEST',
  `note` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`id`,`name`,`surname`,`email`,`password`,`age`,`pic_url`,`user_type`,`note`) values (34,'Admin','Admin','admin@mail.com','$2a$10$WxmCqNhZ31bTjfS2m.6nVe6BY35Ndzt1SC.D.EIFiGMiXLWHECcGy',0,NULL,'ADMIN',0),(35,'Poxos','Poxosyan','poxos@mail.com','$2a$10$QvOHuJ8g/Bfo82FX9AV7c.LlPmENU799AHsBosGXOh9CvM9P91Xs6',0,NULL,'GUEST',1),(36,'Valod','Valodyan','valod@gmail.com','$2a$10$.CDHP8FCAfPYQyR3lXwyYOJYa3B4ZWRT2VAvMFifP9iZ.AoISrnIm',0,'1530529669289_img7.jpg','GUEST',1),(37,'User','Useryan','user@mail.com','$2a$10$TgzWek9TW1VmVZ4tSmB1re9P6wSqd7Wzr/nc7cw12QKJcPXQ1VZuS',0,NULL,'GUEST',1);

/*Table structure for table `user_friends` */

DROP TABLE IF EXISTS `user_friends`;

CREATE TABLE `user_friends` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `friend_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`friend_id`,`id`),
  KEY `friend_id` (`friend_id`),
  KEY `id` (`id`),
  CONSTRAINT `user_friends_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `user_friends_ibfk_2` FOREIGN KEY (`friend_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

/*Data for the table `user_friends` */

insert  into `user_friends`(`id`,`user_id`,`friend_id`) values (28,36,35),(30,37,35),(31,35,36),(32,35,37);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
