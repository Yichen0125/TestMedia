/*
SQLyog Ultimate v11.26 (32 bit)
MySQL - 5.5.28 : Database - testshiro2
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`testshiro2` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `testshiro2`;

/*Table structure for table `authorities` */

DROP TABLE IF EXISTS `authorities`;

CREATE TABLE `authorities` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `display_name` varchar(200) DEFAULT NULL,
  `permissions` varchar(200) DEFAULT NULL,
  `parent_authority_id` int(20) DEFAULT NULL,
  `url` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

/*Data for the table `authorities` */

insert  into `authorities`(`id`,`name`,`display_name`,`permissions`,`parent_authority_id`,`url`) values (1,'','项目管理','',NULL,'/'),(2,'xjxm','新建项目','',1,'/xm/pro/toAdd'),(3,'xmlb','项目列表','',1,'/xm/pro/list'),(4,'rwlb','任务列表','',1,'/xm/pro/rw/list'),(5,'spgl','视频管理','',1,'/xm/pro/sp/list'),(6,'wjgl','文件管理','',1,'/xm/pro/wj/list'),(7,'','任务管理','',NULL,'/'),(8,'jsrw','接受任务','',7,'/rw/js'),(9,'rwlb','任务列表','',7,'/rw/list'),(10,'','劳务结算','',NULL,'/'),(11,'jsqd','结算清单','',10,'/lw/jsqd'),(12,'rzjl','入账记录','',10,'/lw/rzjl'),(13,'','数据统计','',NULL,'/'),(14,'xmht','项目合同','',13,'/'),(15,'','权限','',NULL,'/'),(16,'gwqx','岗位权限','',15,'/system/auth');

/*Table structure for table `employees` */

DROP TABLE IF EXISTS `employees`;

CREATE TABLE `employees` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `emp_name` varchar(200) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `employees_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `employees` */

insert  into `employees`(`id`,`emp_name`,`user_id`) values (1,'施艺辰',1),(2,'申文冲',2);

/*Table structure for table `medias` */

DROP TABLE IF EXISTS `medias`;

CREATE TABLE `medias` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(500) DEFAULT NULL,
  `src` varchar(500) DEFAULT NULL,
  `picture` varchar(500) DEFAULT NULL,
  `descript` varchar(500) DEFAULT NULL,
  `uptime` varchar(200) DEFAULT NULL,
  `convert` tinyint(1) DEFAULT NULL,
  `remote_url` varchar(500) DEFAULT NULL,
  `convert_area` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `medias` */

insert  into `medias`(`id`,`title`,`src`,`picture`,`descript`,`uptime`,`convert`,`remote_url`,`convert_area`) values (1,'文玩刷子3GP.3gp','d:\\TempVideo\\syc\\文玩刷子3GP.3gp',NULL,'测试','2016-11-30 15:30:16.0',0,NULL,0),(2,'文玩刷子3GP-副本1.3gp','d:\\TempVideo\\syc\\文玩刷子3GP-副本1.3gp',NULL,'测试','2016-11-30 15:32:04.0',0,NULL,0),(3,'文玩刷子3GP-副本2.3gp','d:\\TempVideo\\syc\\文玩刷子3GP-副本2.3gp',NULL,'测试','2016-11-30 15:35:42.0',0,NULL,0),(4,'文玩刷子3GP-副本3.3gp','d:\\TempVideo\\syc\\文玩刷子3GP-副本3.3gp',NULL,'测试','2016-11-30 15:35:42.0',0,NULL,0);

/*Table structure for table `project_groups` */

DROP TABLE IF EXISTS `project_groups`;

CREATE TABLE `project_groups` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `groupnum` varchar(200) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

/*Data for the table `project_groups` */

insert  into `project_groups`(`id`,`groupnum`,`user_id`,`date`) values (2,'41635926',NULL,'2016-11-30'),(3,'36928059',NULL,'2016-11-30'),(4,'36928059',NULL,'2016-11-30'),(5,'64351202',NULL,'2016-11-30'),(6,'64351202',NULL,'2016-11-30'),(7,'34907850',NULL,'2016-11-30'),(8,'34907850',NULL,'2016-11-30'),(9,'42076839',NULL,'2016-11-30'),(10,'19050533',NULL,'2016-11-30');

/*Table structure for table `projects` */

DROP TABLE IF EXISTS `projects`;

CREATE TABLE `projects` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pronum` varchar(200) DEFAULT NULL,
  `name` varchar(200) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `groupnum` varchar(500) DEFAULT NULL,
  `yq` varchar(500) DEFAULT NULL,
  `cus` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `group_id` (`groupnum`(255)),
  CONSTRAINT `projects_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*Data for the table `projects` */

insert  into `projects`(`id`,`pronum`,`name`,`status`,`user_id`,`date`,`url`,`groupnum`,`yq`,`cus`) values (4,'87509924','fdsa',0,NULL,'2016-11-29','d://upload//syc',NULL,NULL,'fdsa'),(5,'71927505','重中之重',0,NULL,'2016-11-30','d://upload//syc','41635926','fdsa','施艺辰'),(6,'89071884','重中之重',0,NULL,'2016-11-30','d://upload//syc','36928059','啊啊啊','施艺辰2'),(7,'75029933','aaa',0,NULL,'2016-11-30','d://upload//syc','64351202','fdsa ','aaa'),(8,'45347062','fd啊',0,1,'2016-11-30','d://upload//syc','42076839','范德萨','范德萨'),(9,'86526604','fda',0,2,'2016-11-30','d://upload//syc','19050533','fdas ','fdsa');

/*Table structure for table `role_authority` */

DROP TABLE IF EXISTS `role_authority`;

CREATE TABLE `role_authority` (
  `role_id` int(11) DEFAULT NULL,
  `authority_id` int(11) DEFAULT NULL,
  KEY `role_id` (`role_id`),
  KEY `authority_id` (`authority_id`),
  CONSTRAINT `role_authority_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `role_authority_ibfk_2` FOREIGN KEY (`authority_id`) REFERENCES `authorities` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `role_authority` */

insert  into `role_authority`(`role_id`,`authority_id`) values (1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,7),(1,8),(1,9),(1,10),(1,11),(1,12),(1,13),(1,14),(1,15),(1,16);

/*Table structure for table `roles` */

DROP TABLE IF EXISTS `roles`;

CREATE TABLE `roles` (
  `id` int(11) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  `enabled` int(20) DEFAULT NULL,
  `name` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `roles` */

insert  into `roles`(`id`,`description`,`enabled`,`name`) values (1,'',1,'管理员'),(2,'',1,'测试'),(3,'测试时使用, 上线需删除',1,'测试管理员');

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `enabled` int(11) DEFAULT NULL,
  `name` varchar(200) DEFAULT NULL,
  `password` varchar(200) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  `salt` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `users_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `users` */

insert  into `users`(`id`,`enabled`,`name`,`password`,`role_id`,`salt`) values (1,1,'admin','352c24f6d3d455608ca3d4b64a45fb4c',1,'e2b87e6eced06509'),(2,1,'admin2','e59671096c95932994d20b4d99a0bda9',2,'db314a8d91bd6f83'),(3,1,'test','2095e207397f1830d95f0bd987bebd4a',3,'ceadfd47cdaa814c');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
