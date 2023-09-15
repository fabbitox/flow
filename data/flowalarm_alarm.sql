-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: flowalarm
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `alarm`
--

DROP TABLE IF EXISTS `alarm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alarm` (
  `idalarm` int NOT NULL AUTO_INCREMENT,
  `idmember` int NOT NULL,
  `idpred_result` int NOT NULL,
  `idcriteria` int NOT NULL,
  `alarm_dt` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`idalarm`),
  KEY `fk_alarm_member_idx` (`idmember`),
  KEY `fk_alarm_pred_result_idx` (`idpred_result`),
  KEY `fk_alarm_criteria_idx` (`idcriteria`),
  CONSTRAINT `fk_alarm_criteria` FOREIGN KEY (`idcriteria`) REFERENCES `criteria` (`idcriteria`),
  CONSTRAINT `fk_alarm_member` FOREIGN KEY (`idmember`) REFERENCES `member` (`idmember`),
  CONSTRAINT `fk_alarm_pred_result` FOREIGN KEY (`idpred_result`) REFERENCES `pred_result` (`idpred_result`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alarm`
--

LOCK TABLES `alarm` WRITE;
/*!40000 ALTER TABLE `alarm` DISABLE KEYS */;
INSERT INTO `alarm` VALUES (1,3,58,1,'2023-09-14 11:19:49.552549'),(3,4,58,1,'2023-09-14 11:19:49.560524'),(5,3,60,2,'2023-09-14 11:19:49.568524'),(6,4,60,2,'2023-09-14 11:19:49.571538'),(7,3,73,2,'2023-09-14 12:35:16.282500'),(10,4,73,2,'2023-09-14 12:35:16.294472'),(13,3,76,2,'2023-09-14 12:54:39.098037'),(16,4,76,2,'2023-09-14 12:54:39.109214'),(19,3,88,1,'2023-09-15 11:04:30.499185'),(20,4,88,1,'2023-09-15 11:04:30.532194'),(21,3,90,2,'2023-09-15 11:04:30.541188'),(22,4,90,2,'2023-09-15 11:04:30.545199'),(23,3,90,2,'2023-09-15 11:10:50.700971'),(24,4,90,2,'2023-09-15 11:10:50.729978');
/*!40000 ALTER TABLE `alarm` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-09-15 11:15:57
