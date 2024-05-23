-- MySQL dump 10.13  Distrib 8.0.28, for macos11 (x86_64)
--
-- Host: 127.0.0.1    Database: travelink
-- ------------------------------------------------------
-- Server version	8.0.33

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
-- Table structure for table `travel_board`
--

DROP TABLE IF EXISTS `travel_board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `travel_board` (
  `id` int NOT NULL AUTO_INCREMENT,
  `start_at` timestamp NOT NULL,
  `end_at` timestamp NOT NULL,
  `title` varchar(45) NOT NULL,
  `member_id` varchar(45) NOT NULL,
  `visibility` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `member_fk_into_travel_board_idx` (`member_id`),
  CONSTRAINT `member_fk_into_travel_board` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `travel_board`
--

LOCK TABLES `travel_board` WRITE;
/*!40000 ALTER TABLE `travel_board` DISABLE KEYS */;
INSERT INTO `travel_board` VALUES (6,'2024-05-24 15:00:00','2024-05-27 15:00:00','제주도 여행기','kimtaesoo','public'),(7,'2024-05-27 15:00:00','2024-05-28 15:00:00','전라남도 여행~','kimtaesoo','private'),(8,'2024-05-23 15:00:00','2024-05-29 15:00:00','서울 공원 탐방','kimtaesoo','public'),(9,'2024-06-06 15:00:00','2024-06-07 15:00:00','첫 대구 여행','kimtaesoo','mutual'),(10,'2024-06-09 15:00:00','2024-06-24 15:00:00','첫 서울가보자','park','public'),(11,'2024-07-15 15:00:00','2024-07-24 15:00:00','강원도 ','park','public'),(12,'2024-06-02 15:00:00','2024-06-16 15:00:00','전라남도 재미있겠다','park','mutual'),(13,'2024-05-15 15:00:00','2024-05-17 15:00:00','강원도!','da','public'),(14,'2024-04-30 15:00:00','2024-05-03 15:00:00','두번째 제주도~','kimtaesoo','private'),(15,'2024-06-06 15:00:00','2024-06-07 15:00:00','3번째 제주도 ','kimtaesoo','public'),(16,'2024-08-04 15:00:00','2024-08-07 15:00:00','부모님과 강원도','kimtaesoo','public'),(17,'2024-07-28 15:00:00','2024-08-02 15:00:00','전국일주!!','kimtaesoo','public'),(18,'2024-10-07 15:00:00','2024-10-15 15:00:00','섬 투어~','kimtaesoo','public'),(19,'2024-05-28 15:00:00','2024-05-29 15:00:00','혼자 부산여행','park','public'),(20,'2024-07-01 15:00:00','2024-07-03 15:00:00','인생 첫 솔로 여행','park','public'),(21,'2024-06-15 15:00:00','2024-06-25 15:00:00','롯데 타워 여행','park','public'),(22,'2024-05-09 15:00:00','2024-05-10 15:00:00','함양군 여행','park','public'),(23,'2024-05-30 15:00:00','2024-06-03 15:00:00','첫 광주 여행','park','public'),(24,'2024-04-30 15:00:00','2024-05-02 15:00:00','울산 여행','yoon','public'),(25,'2024-05-10 15:00:00','2024-05-13 15:00:00','혼자 충청북도에서..','yoon','public'),(26,'2024-06-04 15:00:00','2024-06-07 15:00:00','경상남도 여행기','yoon','public'),(27,'2024-07-01 15:00:00','2024-07-03 15:00:00','첫 제주도 ','yoon','public'),(28,'2024-05-24 15:00:00','2024-05-27 15:00:00','부모님과 전라북도 ','yoon','public'),(29,'2024-07-17 15:00:00','2024-07-25 15:00:00','친구들과 충청남도~','yoon','public'),(30,'2024-06-12 15:00:00','2024-06-14 15:00:00','인생 첫 평창','yoon','public'),(31,'2024-08-12 15:00:00','2024-08-14 15:00:00','울릉도 여행기','yoon','public');
/*!40000 ALTER TABLE `travel_board` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-23 18:06:39
