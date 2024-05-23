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
-- Table structure for table `member_info`
--

DROP TABLE IF EXISTS `member_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member_info` (
  `id` int NOT NULL AUTO_INCREMENT,
  `member_id` varchar(45) NOT NULL,
  `height` int DEFAULT NULL,
  `gender` varchar(45) DEFAULT NULL,
  `age` int DEFAULT NULL,
  `pro_img` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `member_info_to_member_member_id_idx` (`member_id`),
  CONSTRAINT `member_info_to_member_member_id` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member_info`
--

LOCK TABLES `member_info` WRITE;
/*!40000 ALTER TABLE `member_info` DISABLE KEYS */;
INSERT INTO `member_info` VALUES (1,'kimtaesoo',180,'여자',25,'724f5496-8075-4316-8ce3-f06295d2255b.png'),(2,'park',183,'남자',29,'77497039-5c3f-4ebb-a91d-e1c57aff4ef8.png'),(3,'yoon',176,'남자',24,'a6b05777-5931-4d32-b4e0-27eff035d912.png'),(4,'da',176,'남자',27,'c9ada43b-e508-4f16-8484-72a1ed629bf1.png'),(5,'hi',179,'남자',35,'c4310be1-b26b-45cc-8dcd-8e2a5651fb36.png'),(6,'go',177,'남자',27,'551d87d8-7c2e-475c-94a8-8c407c09519b.png'),(7,'my',194,'남자',33,'7992a135-6712-495d-b526-7c4e80652b8f.png'),(8,'gun',0,'',0,'3de844ad-67d6-4578-b6da-3ba731e1f817.png'),(9,'hong',179,'남자',24,'008a1095-9caa-4ce1-bb87-dfd2f5fa82f3.png'),(10,'han',185,'남자',22,'3633229f-aefa-4e86-ab8f-516c99111f66.png'),(11,'yong',165,'여자',25,'bdf30745-b1ba-41d1-b52d-7c60381cd696.png'),(12,'woo',167,'여자',22,'7f03d2c0-ed29-49cd-b0b4-e6acdf93bfb2.png'),(13,'john',178,'남자',29,'ae5bbc87-3f1b-43a6-bacc-d5f7c9f9846a.png'),(14,'maple',158,'여자',19,'ec2f3ee5-850a-4d93-9322-b10aa54bf061.png'),(15,'sang',0,'',0,NULL),(16,'jerry',168,'여자',22,'aa68ab2b-5fb0-4ab2-9e1c-b98987547737.png');
/*!40000 ALTER TABLE `member_info` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-23 18:06:42
