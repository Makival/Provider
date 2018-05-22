-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: internet_provider
-- ------------------------------------------------------
-- Server version	5.7.20-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `clients_accounts`
--

DROP TABLE IF EXISTS `clients_accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `clients_accounts` (
  `cash_balance` decimal(10,2) NOT NULL DEFAULT '0.00',
  `traffic_balance` decimal(10,2) NOT NULL DEFAULT '0.00',
  `users_id` int(11) NOT NULL,
  KEY `fk_clients_accounts_users1_idx` (`users_id`),
  CONSTRAINT `fk_clients_accounts_users1` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clients_accounts`
--

LOCK TABLES `clients_accounts` WRITE;
/*!40000 ALTER TABLE `clients_accounts` DISABLE KEYS */;
INSERT INTO `clients_accounts` VALUES (84.26,62.00,4),(0.00,0.00,15),(0.00,0.00,16),(104.05,10.00,21);
/*!40000 ALTER TABLE `clients_accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clients_service_plans`
--

DROP TABLE IF EXISTS `clients_service_plans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `clients_service_plans` (
  `signing_date` date NOT NULL,
  `cancelation_date` date DEFAULT NULL,
  `users_id` int(11) NOT NULL,
  `service_plans_id` int(11) NOT NULL,
  KEY `fk_clients_service_plans_users_idx` (`users_id`),
  KEY `fk_clients_service_plans_service_plans1_idx` (`service_plans_id`),
  CONSTRAINT `fk_clients_service_plans_service_plans` FOREIGN KEY (`service_plans_id`) REFERENCES `service_plans` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_clients_service_plans_users` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clients_service_plans`
--

LOCK TABLES `clients_service_plans` WRITE;
/*!40000 ALTER TABLE `clients_service_plans` DISABLE KEYS */;
INSERT INTO `clients_service_plans` VALUES ('2018-04-26','2018-04-26',4,4),('2018-04-26','2018-04-29',4,2),('2018-04-29','2018-04-29',4,4),('2018-04-29','2018-04-29',4,7),('2018-04-29','2018-05-08',4,10),('2018-05-08','2018-05-09',4,3),('2018-05-09','2018-05-09',21,16),('2018-05-09',NULL,21,3),('2018-05-09','2018-05-09',4,7),('2018-05-09','2018-05-09',4,7),('2018-05-09','2018-05-09',4,7),('2018-05-09',NULL,4,7);
/*!40000 ALTER TABLE `clients_service_plans` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `promos`
--

DROP TABLE IF EXISTS `promos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `promos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `access_discount` int(2) NOT NULL,
  `active` tinyint(4) NOT NULL,
  `traffic_bonus` int(2) NOT NULL,
  `service_plans_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_promos_promos_service_plans_idx` (`service_plans_id`),
  CONSTRAINT `fk_promos_promos_service_plans` FOREIGN KEY (`service_plans_id`) REFERENCES `service_plans` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `promos`
--

LOCK TABLES `promos` WRITE;
/*!40000 ALTER TABLE `promos` DISABLE KEYS */;
INSERT INTO `promos` VALUES (1,'Tempo storm',25,1,25,7),(3,'Super shot',10,1,10,6);
/*!40000 ALTER TABLE `promos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service_plans`
--

DROP TABLE IF EXISTS `service_plans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `service_plans` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `traffic_limit` int(11) NOT NULL,
  `monthly_fee` decimal(10,2) NOT NULL,
  `description` varchar(45) NOT NULL,
  `access_cost` decimal(10,2) NOT NULL,
  `relevant` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service_plans`
--

LOCK TABLES `service_plans` WRITE;
/*!40000 ALTER TABLE `service_plans` DISABLE KEYS */;
INSERT INTO `service_plans` VALUES (1,'Unlim',0,49.95,'10/5 MB/s',9.95,1),(2,'Unlim+',0,99.95,'25/12,5 MB/s',19.95,1),(3,'Comfort',10,19.95,'10/5 MB/s',4.95,1),(4,'Confort+',15,24.95,'10/5 MB/s',7.95,1),(5,'Social',5,5.95,'10/5 MB/s',0.95,1),(6,'Turbo connect',25,35.95,'25/12,5 MB/s',9.95,1),(7,'Premium connect',50,65.95,'50/25 MB/s',14.95,1),(8,'MMO connect',10,12.95,'10/5 MB/s',1.95,1),(9,'MMO connect+',20,19.95,'10/5 MB/s',3.95,1),(10,'MMO unlim',0,29.95,'10/5 MB/s',9.95,1),(11,'Unlim light',0,34.95,'8/4 MB/s',9.95,1),(12,'TV plan',3,9.95,'10/5 MB/s 60 channels',2.50,1),(13,'TV plan comfort',10,24.95,'10/5 MB/s 90 channels',7.50,1),(16,'New plan2',11,1.00,'dsadasd',1.00,0);
/*!40000 ALTER TABLE `service_plans` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_statuses`
--

DROP TABLE IF EXISTS `user_statuses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_statuses` (
  `id` int(11) NOT NULL,
  `status` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_statuses`
--

LOCK TABLES `user_statuses` WRITE;
/*!40000 ALTER TABLE `user_statuses` DISABLE KEYS */;
INSERT INTO `user_statuses` VALUES (1,'new'),(2,'active'),(3,'blocked');
/*!40000 ALTER TABLE `user_statuses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `name` varchar(25) NOT NULL,
  `surname` varchar(45) NOT NULL,
  `e_mail` varchar(45) NOT NULL,
  `registration_date` date NOT NULL,
  `user_statuses_id` int(11) NOT NULL,
  `birth_date` date NOT NULL,
  `role` enum('administrator','client') NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_users_user_statuses1_idx` (`user_statuses_id`),
  CONSTRAINT `fk_users_user_statuses1` FOREIGN KEY (`user_statuses_id`) REFERENCES `user_statuses` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (3,'Vasian','7c4a8d09ca3762af61e59520943dc26494f8941b','Vasia','Sosisov','Vasvas@mail.ru','2016-01-01',2,'1984-10-01','administrator'),(4,'Petian','7c4a8d09ca3762af61e59520943dc26494f8941b','Petr','Petrov','petrP@tut.by','2018-04-06',2,'1985-10-10','client'),(8,'Fafnir','908f704ccaadfd86a74407d234c7bde30f2744fe','Mishania','Migdosov','asdasd@tut.by','2018-04-06',1,'2018-04-10','client'),(9,'Faf','dd5fef9c1c1da1394d6d34b248c51be2ad740840','Petr','Vaslov','asdasd@tut.by','2018-04-06',1,'2018-04-10','client'),(10,'Jenik','dd5fef9c1c1da1394d6d34b248c51be2ad740840','Evgen','Donto','dsdf@tut.by','2018-04-06',3,'2018-04-17','client'),(11,'Koliat','dd5fef9c1c1da1394d6d34b248c51be2ad740840','Kloppe','Petfonk','dsdf@tut.by','2018-04-06',3,'2018-04-17','client'),(12,'Krokan','a4aa860568d8f21b0186474deabb08ddad702e86','Vlaska','Trottonko','dsdf@tut.by','2018-04-06',3,'2018-04-17','client'),(15,'Pepsos','7c4a8d09ca3762af61e59520943dc26494f8941b','Crotan','Vatanov','masmas@tut.by','2000-01-01',1,'1980-01-01','client'),(16,'Boboba','7c4a8d09ca3762af61e59520943dc26494f8941b','Bobs','Kanotor','mamamama','2018-01-01',1,'1980-01-01','client'),(21,'Vasnada','7c4a8d09ca3762af61e59520943dc26494f8941b','Valisaia','Velasikoratto','fafsaf@tut.by','2018-05-09',1,'2018-05-16','client');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-22 14:41:58
