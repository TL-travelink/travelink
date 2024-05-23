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
-- Table structure for table `board_file_info`
--

DROP TABLE IF EXISTS `board_file_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `board_file_info` (
  `id` int NOT NULL AUTO_INCREMENT,
  `board_id` int NOT NULL,
  `save_folder` varchar(45) DEFAULT NULL,
  `original_file` varchar(45) DEFAULT NULL,
  `save_file` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `board_file_info_to_board_idx` (`board_id`),
  CONSTRAINT `board_file_info_to_board` FOREIGN KEY (`board_id`) REFERENCES `board` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board_file_info`
--

LOCK TABLES `board_file_info` WRITE;
/*!40000 ALTER TABLE `board_file_info` DISABLE KEYS */;
INSERT INTO `board_file_info` VALUES (20,1,'board','8.jpg','8c685e20-c3a8-4599-a76f-b8f3678d9052.jpg'),(21,2,'board','11.jpeg','140583f5-06f1-4e4a-a206-fad9a128929c.jpeg'),(22,3,'board','2.jpeg','97617206-6053-428d-a903-765897fff2c1.jpeg'),(23,4,'board','산.jpeg','bc1c6196-e904-42ed-87c2-8391a6f35b58.jpeg'),(24,5,'board','ㅇㅇ.jpg','4d16af4a-36a9-4334-9cd2-d9b8777f9827.jpg'),(25,6,'board','111.jpeg','e586cc8f-3b75-4542-9d47-9e734e3d169e.jpeg'),(26,7,'board','가족과한장.jpg','c0481619-77df-455b-a372-2c85b61c7aad.jpg'),(27,8,'board','2222.jpeg','b920b3a0-d88d-4ad0-ae96-43939730ac9f.jpeg'),(28,9,'board','2333.jpeg','62a15035-9c76-492a-ad60-3213e22011ba.jpeg'),(29,10,'board','1111.jpeg','802090c0-bf82-4c81-9e32-4b4e8907b9de.jpeg'),(30,11,'board','21312.jpeg','1895f467-39b6-4c34-b49e-1d7fd4ee749d.jpeg'),(32,13,'board','6666.jpeg','d9bfad82-9c2a-403f-8462-0c0033365631.jpeg'),(33,14,'board','스크린샷 2024-05-23 오후 4.59.43.png','28b613d8-e4a0-43a6-a37b-d1c364267e18.png'),(34,15,'board','4443.jpeg','85c82dfa-ddc7-48bb-9ae3-c80a69c0a842.jpeg'),(35,16,'board','888.jpeg','b259d3ef-b562-4b46-b02a-6ea28fb5de3b.jpeg'),(36,17,'board','2222ㅁㄴㅇ.jpeg','a4725731-4d9b-42a8-9522-c2ecb9a271c9.jpeg');
/*!40000 ALTER TABLE `board_file_info` ENABLE KEYS */;
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
