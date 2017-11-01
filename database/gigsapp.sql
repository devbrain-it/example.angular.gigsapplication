-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: dev2gigs
-- ------------------------------------------------------
-- Server version	5.7.18-log

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
-- Table structure for table `artist`
--

DROP TABLE IF EXISTS `artist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `artist` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `artist_name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `artist_artist_name_uindex` (`artist_name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='Künstler';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `artist`
--

LOCK TABLES `artist` WRITE;
/*!40000 ALTER TABLE `artist` DISABLE KEYS */;
INSERT INTO `artist` VALUES (1,'Bruno Mars'),(5,'E.A.V.'),(4,'Freiwild'),(3,'Mel C'),(2,'Spongebozz');
/*!40000 ALTER TABLE `artist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `artist_gig`
--

DROP TABLE IF EXISTS `artist_gig`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `artist_gig` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `artist_fk` int(11) NOT NULL,
  `gig_fk` int(11) NOT NULL,
  `genre_fk` int(11) NOT NULL,
  `time` time NOT NULL,
  PRIMARY KEY (`id`),
  KEY `artist_gig_artist_id_fk` (`artist_fk`),
  KEY `artist_gig_gig_id_fk` (`gig_fk`),
  KEY `artist_gig_genre_id_fk` (`genre_fk`),
  CONSTRAINT `artist_gig_artist_id_fk` FOREIGN KEY (`artist_fk`) REFERENCES `artist` (`id`) ON DELETE CASCADE,
  CONSTRAINT `artist_gig_genre_id_fk` FOREIGN KEY (`genre_fk`) REFERENCES `genre` (`id`),
  CONSTRAINT `artist_gig_gig_id_fk` FOREIGN KEY (`gig_fk`) REFERENCES `gig` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='Künstler Je Gig';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `artist_gig`
--

LOCK TABLES `artist_gig` WRITE;
/*!40000 ALTER TABLE `artist_gig` DISABLE KEYS */;
INSERT INTO `artist_gig` VALUES (1,1,1,1,'16:00:00'),(2,2,2,2,'16:00:00'),(3,3,3,3,'14:00:00'),(4,4,4,4,'14:00:00'),(5,5,5,5,'14:00:00'),(6,4,5,4,'16:00:00');
/*!40000 ALTER TABLE `artist_gig` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `city`
--

DROP TABLE IF EXISTS `city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `city` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `city_name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `city_city_name_uindex` (`city_name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='Städtenamen';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `city`
--

LOCK TABLES `city` WRITE;
/*!40000 ALTER TABLE `city` DISABLE KEYS */;
INSERT INTO `city` VALUES (2,'Berlin'),(5,'Hamburg'),(4,'Hannover'),(1,'Köln'),(3,'München');
/*!40000 ALTER TABLE `city` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `genre`
--

DROP TABLE IF EXISTS `genre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `genre` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `genre_name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `genre_genre_name_uindex` (`genre_name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='Musikkategorie';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `genre`
--

LOCK TABLES `genre` WRITE;
/*!40000 ALTER TABLE `genre` DISABLE KEYS */;
INSERT INTO `genre` VALUES (4,'HipHop'),(2,'Klassik'),(5,'Modern Jazz'),(3,'Rap'),(1,'Rock');
/*!40000 ALTER TABLE `genre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gig`
--

DROP TABLE IF EXISTS `gig`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gig` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `city_fk` int(11) NOT NULL,
  `deleted` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `gig_city_id_fk` (`city_fk`),
  CONSTRAINT `gig_city_id_fk` FOREIGN KEY (`city_fk`) REFERENCES `city` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='Alle Gigs';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gig`
--

LOCK TABLES `gig` WRITE;
/*!40000 ALTER TABLE `gig` DISABLE KEYS */;
INSERT INTO `gig` VALUES (1,'2017-09-17',1,0),(2,'2017-09-18',2,0),(3,'2017-09-19',3,0),(4,'2017-09-20',4,0),(5,'2017-09-21',5,0);
/*!40000 ALTER TABLE `gig` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `is_default` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_name_uindex` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='Benutzerrolle';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'Gast','An Gigs teilnehmen',1),(2,'Gig-Moderator','Erzeugen, Bearbeiten und Löschen von Gigs',0);
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(100) NOT NULL,
  `password` int(11) NOT NULL,
  `username` varchar(100) NOT NULL DEFAULT 'benutzer',
  `role_fk` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_login_uindex` (`login`),
  KEY `user_role_id_fk` (`role_fk`),
  CONSTRAINT `user_role_id_fk` FOREIGN KEY (`role_fk`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='Enthält Benutzer';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'gast',3165371,'gast',1),(6,'gast2',3165371,'gast2',1),(7,'ecreators',-1409271126,'ecreators',1),(8,'demo12',3079651,'demo12',1),(9,'ecr',100244,'ecr',1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_gig`
--

DROP TABLE IF EXISTS `user_gig`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_gig` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_fk` int(11) NOT NULL,
  `gig_fk` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_gig_user_id_fk` (`user_fk`),
  KEY `user_gig_gig_id_fk` (`gig_fk`),
  CONSTRAINT `user_gig_gig_id_fk` FOREIGN KEY (`gig_fk`) REFERENCES `gig` (`id`),
  CONSTRAINT `user_gig_user_id_fk` FOREIGN KEY (`user_fk`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8 COMMENT='Benutzer Je Gig';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_gig`
--

LOCK TABLES `user_gig` WRITE;
/*!40000 ALTER TABLE `user_gig` DISABLE KEYS */;
INSERT INTO `user_gig` VALUES (34,1,4),(58,9,4),(59,9,5),(61,1,5),(62,1,5),(63,1,5);
/*!40000 ALTER TABLE `user_gig` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-11-01  7:11:07
