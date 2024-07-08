-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: wedsneaker
-- ------------------------------------------------------
-- Server version	8.0.34

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
-- Table structure for table `anh`
--

DROP TABLE IF EXISTS `anh`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `anh` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `SanPham` varchar(50) DEFAULT NULL,
  `ten` varchar(255) DEFAULT NULL,
  `ViTriAnh` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `Anh_SanPham_idx` (`SanPham`),
  CONSTRAINT `Anh_SanPham` FOREIGN KEY (`SanPham`) REFERENCES `sanpham` (`Ma`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK53pp80mhis4r7rno10wmv5q81` FOREIGN KEY (`SanPham`) REFERENCES `sanpham` (`Ma`)
) ENGINE=InnoDB AUTO_INCREMENT=802 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `anh`
--

LOCK TABLES `anh` WRITE;
/*!40000 ALTER TABLE `anh` DISABLE KEYS */;
INSERT INTO `anh` VALUES (771,'1','36673621.jpg',NULL),(777,'3','4a8af57.jpg',NULL),(778,'4','8e9e2064.jpg',NULL),(779,'5','ff9fa55c.png',NULL),(780,'7','30bf5750.png',NULL),(781,'6','f4f1b85.jpg',NULL),(782,'6','3f4ebed1.jpg',NULL),(783,'6','dd923e5.jpg',NULL),(784,'6','76d6cd79.jpg',NULL),(785,'6','92273377.jpg',NULL),(786,'8','39a4eb16.jpg',NULL),(787,'8','fa85e2ef.jpg',NULL),(788,'8','7cf46cc1.jpg',NULL),(789,'8','ad53dfe.jpg',NULL),(790,'8','338726e4.jpg',NULL),(791,'9','63ce7d2d.jpg',NULL),(792,'9','b791dcf4.jpg',NULL),(793,'9','8b32b2d9.jpg',NULL),(794,'CV1','2e753032.jpg',NULL),(795,'CV1','c5b2a6f2.jpg',NULL),(796,'CV1','500e8451.jpg',NULL),(797,'CV1','6cffff36.png',NULL),(798,'D1','b9a596b5.jpg',NULL),(799,'D1','1acf9d08.jpg',NULL),(800,'D1','78af0bdf.jpg',NULL),(801,'D1','b6088e18.jpg',NULL);
/*!40000 ALTER TABLE `anh` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chatlieu`
--

DROP TABLE IF EXISTS `chatlieu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chatlieu` (
  `id` varchar(36) NOT NULL DEFAULT (uuid()),
  `Ten` varchar(200) DEFAULT NULL,
  `NgayTao` datetime DEFAULT NULL,
  `NgayCapNhat` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chatlieu`
--

LOCK TABLES `chatlieu` WRITE;
/*!40000 ALTER TABLE `chatlieu` DISABLE KEYS */;
INSERT INTO `chatlieu` VALUES ('0c4ef1b1-59c1-4c3f-85fb-825549033579','Da','2024-06-02 18:19:57','2024-06-02 18:19:57'),('a7814731-8c75-4c1c-8a52-aa448f0298ed','Vải','2024-06-02 18:20:06','2024-06-02 18:20:06');
/*!40000 ALTER TABLE `chatlieu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chitietdonhang`
--

DROP TABLE IF EXISTS `chitietdonhang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chitietdonhang` (
  `id` varchar(255) NOT NULL,
  `DonHang` varchar(50) DEFAULT NULL,
  `ChiTietSanPham` varchar(36) DEFAULT NULL,
  `SoLuong` int DEFAULT NULL,
  `dongia` decimal(38,2) DEFAULT NULL,
  `dongiasaugiam` decimal(38,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `CTDH_DonHang_idx` (`DonHang`),
  KEY `CTDH_CTSP_idx` (`ChiTietSanPham`),
  CONSTRAINT `CTDH_CTSP` FOREIGN KEY (`ChiTietSanPham`) REFERENCES `chitietsanpham` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `CTDH_DonHang` FOREIGN KEY (`DonHang`) REFERENCES `donhang` (`Ma`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK3i3vxmpwi6lkhoodqw3w5wkuh` FOREIGN KEY (`DonHang`) REFERENCES `donhang` (`Ma`),
  CONSTRAINT `FK7nb6wcsi5thxc4m83myp6aa9r` FOREIGN KEY (`ChiTietSanPham`) REFERENCES `chitietsanpham` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chitietdonhang`
--

LOCK TABLES `chitietdonhang` WRITE;
/*!40000 ALTER TABLE `chitietdonhang` DISABLE KEYS */;
INSERT INTO `chitietdonhang` VALUES ('01f3a445-257b-46ac-a966-7f8f5740cebb','mxFdH00F','14650e70-4851-4736-abff-2b078b6c783f',1,5990000.00,5990000.00),('191bdf84-5e1a-4b47-b0e9-e21de7f5f5b2','31QbnqMN','10c381c7-860c-4504-89ae-030baf78ecb1',2,1990000.00,1990000.00),('67257ab5-eca8-4320-b559-c98549d7217f','G9lzFfrO','21cae469-2329-440a-ae9b-eb47059a3855',5,8800000.00,8800000.00),('a6708ad2-5276-4bfc-b6cf-e8516d057693','jPCgPFO8','0ca192bc-0420-41e8-96a3-3182ab351d88',1,15000000.00,15000000.00),('bc4dd1ba-cfad-4045-a8b9-48d0e67cd14e','3vFqaWvO','0b546155-9f67-4b6e-81d3-6324c0868684',1,2300000.00,2300000.00),('c90afb3a-574d-49cc-88b4-686b2c1fe12f','Uazhj0Cq','0b546155-9f67-4b6e-81d3-6324c0868684',1,2300000.00,2300000.00),('e15dd442-f382-4e0a-b08a-52166e3c308a','6FhcyhyK','0ca192bc-0420-41e8-96a3-3182ab351d88',1,15000000.00,15000000.00),('efb47e9b-89c2-456c-bd28-e492fc26d1f0','0FSvng3E','1717713d-6003-41ac-b551-684890c5f541',1,15000000.00,15000000.00),('ffd57e8b-ef9f-43cc-9c02-47bd7c3b2e46','gpk1t6KT','0cd471d4-228d-4840-96f2-f58605e72403',1,1000000.00,1000000.00);
/*!40000 ALTER TABLE `chitietdonhang` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chitietsanpham`
--

DROP TABLE IF EXISTS `chitietsanpham`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chitietsanpham` (
  `id` varchar(36) NOT NULL DEFAULT (uuid()),
  `SanPham` varchar(36) DEFAULT NULL,
  `size` float DEFAULT NULL,
  `SoLuong` bigint DEFAULT NULL,
  `TrangThai` bit(1) DEFAULT NULL,
  `ngaytao` datetime(6) DEFAULT NULL,
  `ngaycapnhap` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `CTSP_SanPham_idx` (`SanPham`),
  KEY `CTSP_Size_idx` (`size`),
  CONSTRAINT `CTSP_SanPham` FOREIGN KEY (`SanPham`) REFERENCES `sanpham` (`Ma`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `CTSP_Size` FOREIGN KEY (`size`) REFERENCES `size` (`Ma`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `FK22ehihg5i83q8ifbpp3gbj890` FOREIGN KEY (`SanPham`) REFERENCES `sanpham` (`Ma`) ON UPDATE CASCADE,
  CONSTRAINT `FK5k4j7pstdofbkd6lna7hs0qnd` FOREIGN KEY (`size`) REFERENCES `size` (`Ma`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chitietsanpham`
--

LOCK TABLES `chitietsanpham` WRITE;
/*!40000 ALTER TABLE `chitietsanpham` DISABLE KEYS */;
INSERT INTO `chitietsanpham` VALUES ('0100d077-9526-4357-96b9-a9ba8312bd7b','3',42,10,_binary '','2024-06-02 22:00:59.551000','2024-06-02 22:00:59.551000'),('0b546155-9f67-4b6e-81d3-6324c0868684','7',NULL,29,_binary '\0','2024-06-02 19:12:04.428000','2024-06-02 21:44:06.507000'),('0ca192bc-0420-41e8-96a3-3182ab351d88','4',NULL,8,_binary '\0','2024-06-02 18:47:07.367000','2024-06-02 19:32:09.753000'),('0cd471d4-228d-4840-96f2-f58605e72403','1',NULL,9,_binary '\0','2024-06-02 18:29:47.900000','2024-06-02 18:30:24.285000'),('10c381c7-860c-4504-89ae-030baf78ecb1','CV1',44,13,_binary '','2024-06-02 22:00:12.226000','2024-06-02 22:03:43.755000'),('14650e70-4851-4736-abff-2b078b6c783f','D1',39,19,_binary '','2024-06-02 22:01:21.090000','2024-06-02 22:06:15.448000'),('1717713d-6003-41ac-b551-684890c5f541','3',NULL,9,_binary '\0','2024-06-02 18:46:03.031000','2024-06-02 19:15:54.264000'),('21cae469-2329-440a-ae9b-eb47059a3855','8',NULL,15,_binary '\0','2024-06-02 21:30:36.218000','2024-06-02 21:43:04.891000'),('24871287-e502-465a-bdd6-2c838169c54c','CV1',39,15,_binary '','2024-06-02 22:00:12.140000','2024-06-02 22:00:12.140000'),('273b0de6-704e-4b1e-afd9-f800b4a15dd1','1',37,10,_binary '','2024-06-02 21:52:18.955000','2024-06-02 21:52:18.955000'),('2ca209bc-73e6-4607-9566-40acfa6e7ca6','CV1',36,15,_binary '','2024-06-02 22:00:12.074000','2024-06-02 22:00:12.074000'),('356ccf88-0bd8-4c75-b98d-b93b4eeee297','1',35,10,_binary '','2024-06-02 21:52:18.931000','2024-06-02 21:52:18.931000'),('35d35500-6676-4a25-8f10-ea58c567f2fd','D1',41,20,_binary '','2024-06-02 22:01:21.109000','2024-06-02 22:01:21.109000'),('49331ad3-276f-4197-ae42-787459fdc924','1',38,10,_binary '','2024-06-02 21:52:18.965000','2024-06-02 21:52:18.965000'),('54d80056-1fa7-4095-9a5f-0eed84e99747','1',41,10,_binary '','2024-06-02 21:52:18.993000','2024-06-02 21:52:18.993000'),('5519b621-811c-4581-8166-60b9bb785f27','1',39,10,_binary '','2024-06-02 21:52:18.975000','2024-06-02 21:52:18.975000'),('5744474d-453c-42f5-879e-1f41592d04fa','3',40,10,_binary '','2024-06-02 22:00:59.502000','2024-06-02 22:00:59.502000'),('5a4b96b8-058b-4706-9cfa-c5bf4d3588d6','1',36,10,_binary '','2024-06-02 21:52:18.943000','2024-06-02 21:52:18.943000'),('6bb3972b-7542-4a8b-bd1f-59a31611860c','D1',37,20,_binary '','2024-06-02 22:01:21.060000','2024-06-02 22:01:21.060000'),('742ceb2f-1c5b-4edc-908c-d17c67dcd184','D1',44,20,_binary '','2024-06-02 22:01:21.144000','2024-06-02 22:01:21.144000'),('7f6a2a7c-f472-458f-99c0-1fdabbad95a0','CV1',40,15,_binary '','2024-06-02 22:00:12.161000','2024-06-02 22:00:12.161000'),('86f684ac-a40e-4408-bd4a-9d8eeb436c90','D1',36,20,_binary '','2024-06-02 22:01:21.041000','2024-06-02 22:01:21.041000'),('8a402050-356e-4a2a-a6f9-ee02364dedca','1',42,10,_binary '','2024-06-02 21:52:19.005000','2024-06-02 21:52:19.005000'),('911a425a-2eae-4b94-a5c5-5b4ae6465e8f','D1',43,20,_binary '','2024-06-02 22:01:21.137000','2024-06-02 22:01:21.137000'),('98af758d-f2c4-4e5e-99d9-d59c85df0cef','CV1',42,15,_binary '','2024-06-02 22:00:12.193000','2024-06-02 22:00:12.193000'),('a0c225ca-366c-4b53-a1b5-17aab48f4e3e','CV1',38,15,_binary '','2024-06-02 22:00:12.119000','2024-06-02 22:00:12.119000'),('a309fa6b-67df-4e95-a1b5-1a2a3733d24d','3',41,10,_binary '','2024-06-02 22:00:59.529000','2024-06-02 22:00:59.529000'),('a7fbdfdf-445d-4e50-b89e-f6d8753f96ed','D1',38,20,_binary '','2024-06-02 22:01:21.072000','2024-06-02 22:01:21.072000'),('ad717fd1-044d-466b-8928-ea65c2ac08e2','1',44,10,_binary '','2024-06-02 21:52:19.023000','2024-06-02 21:52:19.023000'),('adf2a699-1a9a-421e-80a0-a55709b8d55e','D1',42,20,_binary '','2024-06-02 22:01:21.123000','2024-06-02 22:01:21.123000'),('b25d2a8b-70e7-4451-8987-b17af4e7e887','3',39,10,_binary '','2024-06-02 22:00:59.473000','2024-06-02 22:00:59.473000'),('b44ceb03-0a4e-448d-9dea-84f8d495c3fa','1',43,10,_binary '','2024-06-02 21:52:19.016000','2024-06-02 21:52:19.016000'),('c381bbd3-072f-4b2b-94f7-f99120e4e390','CV1',35,15,_binary '','2024-06-02 22:00:12.059000','2024-06-02 22:00:12.059000'),('c7968fa2-937d-45a3-bef4-b7ffea1272d8','3',43,10,_binary '','2024-06-02 22:00:59.576000','2024-06-02 22:00:59.576000'),('c7fbfd48-29d3-485a-a1a6-0cf2a0383719','D1',40,20,_binary '','2024-06-02 22:01:21.101000','2024-06-02 22:01:21.101000'),('c9fdad3d-7c22-4fe5-8b0f-bca24f7bfceb','CV1',37,15,_binary '','2024-06-02 22:00:12.095000','2024-06-02 22:00:12.095000'),('cb880c58-fe19-4d6c-80fa-801f7220f1b5','CV1',43,15,_binary '','2024-06-02 22:00:12.208000','2024-06-02 22:00:12.208000'),('d674dca5-ca3c-4a76-867c-de8e46a83d48','1',40,10,_binary '','2024-06-02 21:52:18.986000','2024-06-02 21:52:18.986000'),('f36f2c57-b62a-4326-b4c7-003b051e3e13','CV1',41,15,_binary '','2024-06-02 22:00:12.177000','2024-06-02 22:00:12.177000');
/*!40000 ALTER TABLE `chitietsanpham` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `danhsachyeuthich`
--

DROP TABLE IF EXISTS `danhsachyeuthich`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `danhsachyeuthich` (
  `id` varchar(255) NOT NULL,
  `SanPham` varchar(50) DEFAULT NULL,
  `KhachHang` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `DSYT_SanPham_idx` (`SanPham`),
  KEY `DSYT_KhachHang_idx` (`KhachHang`),
  CONSTRAINT `DSYT_KhachHang` FOREIGN KEY (`KhachHang`) REFERENCES `khachhang` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `DSYT_SanPham` FOREIGN KEY (`SanPham`) REFERENCES `sanpham` (`Ma`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `danhsachyeuthich`
--

LOCK TABLES `danhsachyeuthich` WRITE;
/*!40000 ALTER TABLE `danhsachyeuthich` DISABLE KEYS */;
INSERT INTO `danhsachyeuthich` VALUES ('13ee74c7-821c-48d7-b846-78b46142964e','CONVERSE','khach1'),('340e9f97-c3d3-4fdf-9574-7a4a003cf11b','2222','Tuan'),('5dbd215d-3e1b-437b-8f49-21ac83df07b5','CONVERSE','Tuan'),('9f830782-0948-489f-a03b-44dcad5264c2','124','Tuan'),('b636b43a-0be6-4db2-9762-76e34d7a6211','124','khach1'),('d1b88d00-2cef-4c27-a001-70e650c15239','123','Tuan');
/*!40000 ALTER TABLE `danhsachyeuthich` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `diachi`
--

DROP TABLE IF EXISTS `diachi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `diachi` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `KhachHang` varchar(20) DEFAULT NULL,
  `ThanhPhoCode` int DEFAULT NULL,
  `QuanHuyenCode` int DEFAULT NULL,
  `xaphuongCode` varchar(255) DEFAULT NULL,
  `diachichitiet` varchar(255) DEFAULT NULL,
  `thanhphoname` varchar(255) DEFAULT NULL,
  `quanhuyenname` varchar(255) DEFAULT NULL,
  `xaphuongName` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `sodienthoai` varchar(255) DEFAULT NULL,
  `tennguoinhan` varchar(255) DEFAULT NULL,
  `macDinh` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `DiaChi_KhachHang_idx` (`KhachHang`),
  CONSTRAINT `DiaChi_KhachHang` FOREIGN KEY (`KhachHang`) REFERENCES `khachhang` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `diachi`
--

LOCK TABLES `diachi` WRITE;
/*!40000 ALTER TABLE `diachi` DISABLE KEYS */;
/*!40000 ALTER TABLE `diachi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dongsanpham`
--

DROP TABLE IF EXISTS `dongsanpham`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dongsanpham` (
  `id` varchar(36) NOT NULL DEFAULT (uuid()),
  `ten` varchar(255) DEFAULT NULL,
  `ThuongHieu` varchar(36) DEFAULT NULL,
  `ngaytao` datetime(6) DEFAULT NULL,
  `ngaycapnhat` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2g9isxcw3ry0tpkusv9f7crsx` (`ThuongHieu`),
  CONSTRAINT `DongSP_ThuongHieu` FOREIGN KEY (`ThuongHieu`) REFERENCES `thuonghieu` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK2g9isxcw3ry0tpkusv9f7crsx` FOREIGN KEY (`ThuongHieu`) REFERENCES `thuonghieu` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dongsanpham`
--

LOCK TABLES `dongsanpham` WRITE;
/*!40000 ALTER TABLE `dongsanpham` DISABLE KEYS */;
INSERT INTO `dongsanpham` VALUES ('0ae4cdfc-1345-4502-af87-5cf78121ef4b','Hàng fake 1:1','27296473-9fb6-44ac-b61f-21ed97fa4b62','2024-06-02 21:55:59.395000','2024-06-02 21:55:59.395000'),('1717f56c-7ce9-4900-9260-5d9ad6c5631d','Chinh Hãng','a0caa4a1-d76c-411e-8d73-fd345c9fad80','2024-06-02 21:30:02.284000','2024-06-02 21:30:02.284000'),('21ea9295-1521-4044-bc82-51eb74cf2550','Hàng mẫu','ed4e0553-e93f-4490-af2b-4612537f43c0','2024-06-02 18:28:02.169000','2024-06-02 18:28:02.169000'),('2e8b8906-75df-4b65-b335-88b317c8bd08','Chính Hãng','6ce98e5a-d01b-412c-81a9-6cd0adfddc25','2024-06-02 19:01:01.022000','2024-06-02 19:01:01.022000'),('433870a7-2bf5-4fe3-93c5-ef483a587715','Phổ Thông','ed4e0553-e93f-4490-af2b-4612537f43c0','2024-06-02 18:27:11.143000','2024-06-02 18:27:11.143000'),('4a907d0d-7ef5-47e8-b5b0-91061002fe5e','Chính Hãng','95d7f1c8-e27c-4681-b6ad-547b4054b54e','2024-06-02 19:46:36.982000','2024-06-02 19:46:36.982000'),('60723f4e-83c1-4406-8b38-bad5deacb52e','Limited','ed4e0553-e93f-4490-af2b-4612537f43c0','2024-06-02 18:27:46.350000','2024-06-02 18:27:46.350000'),('7030780c-240b-49d4-bc04-6325e75d5aab','Chính Hãng','8c56dc79-4d9e-4716-a425-8d7ccedd8832','2024-06-02 18:37:21.518000','2024-06-02 18:37:21.518000'),('73de8410-89e0-4af5-9844-ff1393603f80','Cao Cấp','ed4e0553-e93f-4490-af2b-4612537f43c0','2024-06-02 18:27:26.035000','2024-06-02 18:27:26.035000'),('9c64c14f-bac3-45c5-a256-55a1f37ce903','Chính Hãng','2066d25f-d081-4018-a432-bf50157df6f4','2024-06-02 18:37:49.428000','2024-06-02 18:37:49.428000'),('e6564813-1932-4f6a-8b4d-dd269346fd1d','Chính Hàng','27296473-9fb6-44ac-b61f-21ed97fa4b62','2024-06-02 21:55:41.676000','2024-06-02 21:55:41.676000'),('ed282eeb-7d33-46d5-8337-e2c880068dfe','Rep 1:1','6ce98e5a-d01b-412c-81a9-6cd0adfddc25','2024-06-02 19:01:29.815000','2024-06-02 19:01:29.815000');
/*!40000 ALTER TABLE `dongsanpham` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `donhang`
--

DROP TABLE IF EXISTS `donhang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `donhang` (
  `Ma` varchar(50) NOT NULL,
  `KhachHang` varchar(20) DEFAULT NULL,
  `Voucher` varchar(36) DEFAULT NULL,
  `tennguoinhan` varchar(255) DEFAULT NULL,
  `sodienthoai` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `thanhpho_name` varchar(255) DEFAULT NULL,
  `ThanhPho_Code` int DEFAULT NULL,
  `quanhuyen_name` varchar(255) DEFAULT NULL,
  `QuanHuyen_Code` int DEFAULT NULL,
  `xaphuong_name` varchar(255) DEFAULT NULL,
  `xaphuong_Code` varchar(255) DEFAULT NULL,
  `diachichitiet` varchar(255) DEFAULT NULL,
  `ngaydathang` datetime(6) DEFAULT NULL,
  `trangthai` int DEFAULT NULL,
  `ghichu` varchar(255) DEFAULT NULL,
  `tiengiam` decimal(38,2) DEFAULT NULL,
  `phigiaohang` decimal(38,2) DEFAULT NULL,
  `phuongThucThanhToan` bit(1) DEFAULT NULL,
  `lyDoHuy` varchar(255) DEFAULT NULL,
  `maGiaoDich` varchar(45) DEFAULT NULL,
  `ngayxacnhan` datetime(6) DEFAULT NULL,
  `ngaygiaohang` datetime(6) DEFAULT NULL,
  `ngayhoanthanh` datetime(6) DEFAULT NULL,
  `ngayhuy` datetime(6) DEFAULT NULL,
  `loai` int NOT NULL DEFAULT '0',
  `nhanvien` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Ma`),
  KEY `DonHang_Voucher_idx` (`Voucher`),
  KEY `DonHang_KhachHang_idx` (`KhachHang`),
  KEY `DonHang_NhanVien_idx` (`nhanvien`),
  CONSTRAINT `DonHang_KhachHang` FOREIGN KEY (`KhachHang`) REFERENCES `khachhang` (`username`) ON UPDATE CASCADE,
  CONSTRAINT `DonHang_NhanVien` FOREIGN KEY (`nhanvien`) REFERENCES `nhanvien` (`username`),
  CONSTRAINT `DonHang_Voucher` FOREIGN KEY (`Voucher`) REFERENCES `voucher` (`Ma`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `donhang`
--

LOCK TABLES `donhang` WRITE;
/*!40000 ALTER TABLE `donhang` DISABLE KEYS */;
INSERT INTO `donhang` VALUES ('0FSvng3E',NULL,NULL,'NV Dat','0537254861',NULL,'a',1,'a',1,'a','12','a','2024-06-02 19:15:54.187000',5,NULL,0.00,0.00,_binary '',NULL,NULL,NULL,NULL,NULL,NULL,1,'tranquocthanh'),('31QbnqMN',NULL,NULL,'Trần văn sơn','0537252812',NULL,'a',1,'a',1,'a','12','a','2024-06-02 22:03:43.589000',4,NULL,0.00,0.00,_binary '',NULL,NULL,NULL,NULL,NULL,NULL,1,'tranquocthanh'),('3vFqaWvO',NULL,NULL,'Bảo Thi','0536282517',NULL,'a',1,'a',1,'a','12','a','2024-06-02 19:13:48.943000',5,NULL,0.00,0.00,_binary '',NULL,NULL,NULL,NULL,NULL,NULL,1,'tranquocthanh'),('6FhcyhyK','0388700177',NULL,'Trần Quốc Thành','0388700177',NULL,'a',1,'a',1,'a','12','a','2024-06-02 19:32:09.726000',4,NULL,0.00,0.00,_binary '',NULL,NULL,NULL,NULL,NULL,NULL,1,'tranquocthanh'),('G9lzFfrO',NULL,NULL,'Hoài Anh','0637289164',NULL,'a',1,'a',1,'a','12','a','2024-06-02 21:43:04.856000',4,NULL,0.00,0.00,_binary '',NULL,NULL,NULL,NULL,NULL,NULL,1,'tranquocthanh'),('gpk1t6KT','0388700177',NULL,'Khách lẻ','0000000000',NULL,'a',1,'a',1,'a','12','a','2024-06-02 18:30:24.221000',4,NULL,0.00,0.00,_binary '',NULL,NULL,NULL,NULL,NULL,NULL,1,'tranquocthanh'),('jPCgPFO8',NULL,NULL,'Kì Anh','0836248516',NULL,'a',1,'a',1,'a','12','a','2024-06-02 19:18:39.184000',4,NULL,0.00,0.00,_binary '',NULL,NULL,NULL,NULL,NULL,NULL,1,'tranquocthanh'),('mxFdH00F','0388700177',NULL,'Trần Quốc Thành','0388700177',NULL,'a',1,'a',1,'a','12','a','2024-06-02 22:06:15.287000',4,NULL,0.00,0.00,_binary '\0',NULL,NULL,NULL,NULL,NULL,NULL,1,'tranquocthanh'),('Uazhj0Cq',NULL,NULL,'Bảo Thi','0000000000',NULL,'a',1,'a',1,'a','12','a','2024-06-02 20:20:19.613000',0,NULL,0.00,0.00,_binary '','Khách đổi mẫu',NULL,NULL,NULL,NULL,'2024-06-02 21:44:06.402000',1,'tranquocthanh');
/*!40000 ALTER TABLE `donhang` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `giohang`
--

DROP TABLE IF EXISTS `giohang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `giohang` (
  `id` varchar(255) NOT NULL,
  `ChiTietSanPham` varchar(36) DEFAULT NULL,
  `KhachHang` varchar(45) DEFAULT NULL,
  `soluong` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `GioHang_ChiTietSanPham` (`ChiTietSanPham`),
  KEY `GioHang_KhachHang_idx` (`KhachHang`),
  CONSTRAINT `FK6hhf8rq2fut2nilncw53wep1e` FOREIGN KEY (`KhachHang`) REFERENCES `khachhang` (`username`),
  CONSTRAINT `FK974s80x4ir1j7ic37hvg19r4y` FOREIGN KEY (`ChiTietSanPham`) REFERENCES `chitietsanpham` (`id`),
  CONSTRAINT `GioHang_ChiTietSanPham` FOREIGN KEY (`ChiTietSanPham`) REFERENCES `chitietsanpham` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `GioHang_KhachHang` FOREIGN KEY (`KhachHang`) REFERENCES `khachhang` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `giohang`
--

LOCK TABLES `giohang` WRITE;
/*!40000 ALTER TABLE `giohang` DISABLE KEYS */;
/*!40000 ALTER TABLE `giohang` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `khachhang`
--

DROP TABLE IF EXISTS `khachhang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `khachhang` (
  `username` varchar(20) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `hovaten` varchar(255) DEFAULT NULL,
  `NgaySinh` date DEFAULT NULL,
  `GioiTinh` bit(1) DEFAULT NULL,
  `sodienthoai` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `anhdaidien` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `khachhang`
--

LOCK TABLES `khachhang` WRITE;
/*!40000 ALTER TABLE `khachhang` DISABLE KEYS */;
INSERT INTO `khachhang` VALUES ('0000000000','0000000000','Bảo Thi',NULL,NULL,'0000000000','quocthanh2929@gmail.com',NULL),('0388700177','0388700177','Trần Quốc Thành','2002-09-02',NULL,'0388700177','quocthanh2929@gmail.com',NULL),('0537252812','0537252812','Trần văn sơn',NULL,NULL,'0537252812','quocthanh2929@gmail.com',NULL),('0637289164','0637289164','Hoài Anh',NULL,NULL,'0637289164','quocthanh2929@gmail.com',NULL),('0984732721','0984732721','Nguyen van Dat',NULL,NULL,'0984732721','datvip123@gmail.com',NULL),('0987654321','0987654321','Duong Huy',NULL,NULL,'0987654321','duonghuy112@gmail.com',NULL);
/*!40000 ALTER TABLE `khachhang` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `khuyenmai`
--

DROP TABLE IF EXISTS `khuyenmai`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `khuyenmai` (
  `Ma` varchar(36) NOT NULL,
  `Ten` varchar(200) DEFAULT NULL,
  `Loai` varchar(30) DEFAULT NULL,
  `MucGiam` double DEFAULT NULL,
  `NgayBatDau` datetime DEFAULT NULL,
  `NgayKetThuc` datetime DEFAULT NULL,
  `TrangThai` bit(1) DEFAULT NULL,
  `NgayTao` datetime DEFAULT NULL,
  `NgayCapNhat` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`Ma`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `khuyenmai`
--

LOCK TABLES `khuyenmai` WRITE;
/*!40000 ALTER TABLE `khuyenmai` DISABLE KEYS */;
/*!40000 ALTER TABLE `khuyenmai` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `khuyenmai_sanpham`
--

DROP TABLE IF EXISTS `khuyenmai_sanpham`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `khuyenmai_sanpham` (
  `KhuyenMai` varchar(36) NOT NULL,
  `SanPham` varchar(50) NOT NULL,
  PRIMARY KEY (`KhuyenMai`,`SanPham`),
  KEY `KMSP_KhuyenMai_idx` (`KhuyenMai`),
  KEY `KMSP_SanPham_idx` (`SanPham`),
  CONSTRAINT `KMSP_KhuyenMai` FOREIGN KEY (`KhuyenMai`) REFERENCES `khuyenmai` (`Ma`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `KMSP_SanPham` FOREIGN KEY (`SanPham`) REFERENCES `sanpham` (`Ma`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `khuyenmai_sanpham`
--

LOCK TABLES `khuyenmai_sanpham` WRITE;
/*!40000 ALTER TABLE `khuyenmai_sanpham` DISABLE KEYS */;
INSERT INTO `khuyenmai_sanpham` VALUES ('1KKE6KMS','2222'),('JR3NY57S','11111'),('JR3NY57S','123'),('JR3NY57S','ADIASYEEZYRED'),('JVNMKRNL','CONVERSE'),('Z44FUMTN','124'),('ZVBSSD2O','124');
/*!40000 ALTER TABLE `khuyenmai_sanpham` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kieudang`
--

DROP TABLE IF EXISTS `kieudang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `kieudang` (
  `id` varchar(36) NOT NULL DEFAULT (uuid()),
  `Ten` varchar(200) DEFAULT NULL,
  `NgayTao` datetime DEFAULT NULL,
  `NgayCapNhat` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kieudang`
--

LOCK TABLES `kieudang` WRITE;
/*!40000 ALTER TABLE `kieudang` DISABLE KEYS */;
INSERT INTO `kieudang` VALUES ('06b1b78f-e66f-47be-94a8-c043dde24449','Sục','2024-06-02 21:30:12','2024-06-02 21:30:12'),('36302f8d-192d-462a-b63a-168174759d2c','Độn đế','2024-05-23 09:51:11','2024-06-02 18:19:50'),('6d66f1f1-f7c9-4a3c-a5cf-93618827fdb4','Cổ thấp','2024-05-23 09:57:07','2024-05-23 09:57:07'),('ba6fa368-4f85-11ee-8a18-ee19b027b30c','Cổ cao','2024-05-23 09:57:07','2024-05-23 09:57:07'),('ef631792-18f9-4172-a6f6-3905127385f4','Thể thao','2024-05-23 09:34:47','2024-05-23 09:34:47');
/*!40000 ALTER TABLE `kieudang` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mausac`
--

DROP TABLE IF EXISTS `mausac`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mausac` (
  `Ma` varchar(36) NOT NULL,
  `Ten` varchar(200) DEFAULT NULL,
  `NgayTao` datetime DEFAULT NULL,
  `NgayCapNhat` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`Ma`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mausac`
--

LOCK TABLES `mausac` WRITE;
/*!40000 ALTER TABLE `mausac` DISABLE KEYS */;
INSERT INTO `mausac` VALUES ('1f42a223-ee6c-4b4b-93ea-c34d069f9e06','Xanh Lá','2024-06-02 18:46:26','2024-06-02 18:46:26'),('3410f6bc-a5f3-41e8-876a-1db6f9252d00','Kem','2024-06-02 18:17:13','2024-06-02 18:17:13'),('684e1d88-d896-47f1-9433-3eeaefe0ee48','Đỏ','2024-06-02 18:16:57','2024-06-02 18:16:57'),('be969a3f-1279-4149-a5e9-a4077761a0aa','Xanh Dương','2024-06-02 18:17:19','2024-06-02 18:17:19'),('c0efcb51-7527-43d1-b605-a5c00afc0821','Trắng','2024-06-02 18:16:50','2024-06-02 18:16:50'),('d6ab1087-e0b2-44b2-a7dc-564b4c84af13','Đen','2024-06-02 18:17:04','2024-06-02 18:17:04');
/*!40000 ALTER TABLE `mausac` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nhanvien`
--

DROP TABLE IF EXISTS `nhanvien`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nhanvien` (
  `VaiTro` varchar(20) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `hovaten` varchar(255) DEFAULT NULL,
  `NgaySinh` date DEFAULT NULL,
  `GioiTinh` bit(1) DEFAULT NULL,
  `sodienthoai` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `anhdaidien` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`username`),
  KEY `TaiKhoan_VaiTro_idx` (`VaiTro`),
  CONSTRAINT `FK45g6drt18h45qc84cr9w2fj2k` FOREIGN KEY (`VaiTro`) REFERENCES `vaitro` (`Ma`),
  CONSTRAINT `NhanVien_VaiTro` FOREIGN KEY (`VaiTro`) REFERENCES `vaitro` (`Ma`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nhanvien`
--

LOCK TABLES `nhanvien` WRITE;
/*!40000 ALTER TABLE `nhanvien` DISABLE KEYS */;
INSERT INTO `nhanvien` VALUES ('ADMIN','tranquocthanh','Thanh0209','Tran Quoc Thanh','2002-09-02',_binary '','0388700177','quocthanh2929@gmail.com','quocthanh.jpg');
/*!40000 ALTER TABLE `nhanvien` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nhanxet`
--

DROP TABLE IF EXISTS `nhanxet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nhanxet` (
  `id` varchar(255) NOT NULL,
  `rating` float DEFAULT NULL,
  `tieude` varchar(255) DEFAULT NULL,
  `noidung` varchar(255) DEFAULT NULL,
  `thoigian` datetime(6) DEFAULT NULL,
  `chiTietDonHang` varchar(50) DEFAULT NULL,
  `pheDuyet` bit(1) DEFAULT NULL,
  `daChinhSua` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `NHANXET_CTDH_idx` (`chiTietDonHang`),
  CONSTRAINT `NHANXET_CTDH` FOREIGN KEY (`chiTietDonHang`) REFERENCES `chitietdonhang` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nhanxet`
--

LOCK TABLES `nhanxet` WRITE;
/*!40000 ALTER TABLE `nhanxet` DISABLE KEYS */;
/*!40000 ALTER TABLE `nhanxet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sanpham`
--

DROP TABLE IF EXISTS `sanpham`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sanpham` (
  `Ma` varchar(50) NOT NULL,
  `MauSac` varchar(36) DEFAULT NULL,
  `DongSP` varchar(36) DEFAULT NULL,
  `KieuDang` varchar(36) DEFAULT NULL,
  `ChatLieu` varchar(36) DEFAULT NULL,
  `XuatXu` varchar(36) DEFAULT NULL,
  `ten` varchar(255) DEFAULT NULL,
  `gianhap` decimal(38,2) DEFAULT NULL,
  `giaban` decimal(38,2) DEFAULT NULL,
  `mota` varchar(255) DEFAULT NULL,
  `ngaytao` datetime(6) DEFAULT NULL,
  `ngaycapnhat` datetime(6) DEFAULT NULL,
  `HienThi` bit(1) DEFAULT NULL,
  `TrangThai` bit(1) DEFAULT NULL,
  PRIMARY KEY (`Ma`),
  KEY `SanPham_MauSac_idx` (`MauSac`),
  KEY `SanPham_ChatLieu` (`ChatLieu`),
  KEY `SanPham_DongSP` (`DongSP`),
  KEY `SanPham_KieuDang` (`KieuDang`),
  KEY `XuatXu_SP` (`XuatXu`),
  CONSTRAINT `FK9q3jk0g1tweclvobbe3scku93` FOREIGN KEY (`ChatLieu`) REFERENCES `chatlieu` (`id`) ON DELETE SET NULL,
  CONSTRAINT `FKh931tv4r24r9c5u7x5uhetoka` FOREIGN KEY (`KieuDang`) REFERENCES `kieudang` (`id`) ON DELETE SET NULL,
  CONSTRAINT `FKhi7eqmwh89442ymk9slsf2kqk` FOREIGN KEY (`MauSac`) REFERENCES `mausac` (`Ma`) ON DELETE SET NULL,
  CONSTRAINT `FKrw9eho1e1sbgpa5u3pb148hxg` FOREIGN KEY (`DongSP`) REFERENCES `dongsanpham` (`id`) ON DELETE SET NULL,
  CONSTRAINT `SanPham_ChatLieu` FOREIGN KEY (`ChatLieu`) REFERENCES `chatlieu` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `SanPham_DongSP` FOREIGN KEY (`DongSP`) REFERENCES `dongsanpham` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `SanPham_KieuDang` FOREIGN KEY (`KieuDang`) REFERENCES `kieudang` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `SanPham_MauSac` FOREIGN KEY (`MauSac`) REFERENCES `mausac` (`Ma`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `XuatXu_SP` FOREIGN KEY (`XuatXu`) REFERENCES `xuatxu` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sanpham`
--

LOCK TABLES `sanpham` WRITE;
/*!40000 ALTER TABLE `sanpham` DISABLE KEYS */;
INSERT INTO `sanpham` VALUES ('1','c0efcb51-7527-43d1-b605-a5c00afc0821','433870a7-2bf5-4fe3-93c5-ef483a587715','6d66f1f1-f7c9-4a3c-a5cf-93618827fdb4','a7814731-8c75-4c1c-8a52-aa448f0298ed','407a7310-cb72-4145-a8a5-9c0a0fa7f2b6','Vans FOG',1000000.00,1000000.00,'Mẫu mới về ','2024-06-02 18:28:18.426000','2024-06-02 18:28:18.467000',_binary '',_binary ''),('3','be969a3f-1279-4149-a5e9-a4077761a0aa','9c64c14f-bac3-45c5-a256-55a1f37ce903','36302f8d-192d-462a-b63a-168174759d2c','0c4ef1b1-59c1-4c3f-85fb-825549033579','0dc432ad-332f-412f-9b85-0c2a88a0d200','Giày LV Xanh Dương V1',15000000.00,15000000.00,'hàng chính hãng','2024-06-02 18:45:50.074000','2024-06-02 18:45:50.095000',_binary '',_binary ''),('4','1f42a223-ee6c-4b4b-93ea-c34d069f9e06','9c64c14f-bac3-45c5-a256-55a1f37ce903','36302f8d-192d-462a-b63a-168174759d2c','0c4ef1b1-59c1-4c3f-85fb-825549033579','0dc432ad-332f-412f-9b85-0c2a88a0d200','Giày LV Xanh Lá V1',15000000.00,15000000.00,'hàng chính hãng','2024-06-02 18:46:55.817000','2024-06-02 18:46:55.829000',_binary '',_binary ''),('5','c0efcb51-7527-43d1-b605-a5c00afc0821','2e8b8906-75df-4b65-b335-88b317c8bd08','ba6fa368-4f85-11ee-8a18-ee19b027b30c','0c4ef1b1-59c1-4c3f-85fb-825549033579','470e2611-1afd-4bb8-85e8-90f8c33ae7e9','Nike FOG',5500000.00,5500000.00,'','2024-06-02 19:03:20.420000','2024-06-02 19:03:20.434000',_binary '',_binary ''),('6','c0efcb51-7527-43d1-b605-a5c00afc0821','4a907d0d-7ef5-47e8-b5b0-91061002fe5e','6d66f1f1-f7c9-4a3c-a5cf-93618827fdb4','0c4ef1b1-59c1-4c3f-85fb-825549033579','0dc432ad-332f-412f-9b85-0c2a88a0d200','Adidas Gezelle White Navy',5500000.00,5500000.00,'','2024-06-02 19:46:46.151000','2024-06-02 19:46:46.191000',_binary '',_binary ''),('7','c0efcb51-7527-43d1-b605-a5c00afc0821','2e8b8906-75df-4b65-b335-88b317c8bd08','36302f8d-192d-462a-b63a-168174759d2c','0c4ef1b1-59c1-4c3f-85fb-825549033579','2daa2fc8-5498-41d7-b1c4-ccd41237aa44','Nike Air Force One',2300000.00,2300000.00,'','2024-06-02 19:11:46.404000','2024-06-02 19:12:45.983000',_binary '',_binary ''),('8','684e1d88-d896-47f1-9433-3eeaefe0ee48','4a907d0d-7ef5-47e8-b5b0-91061002fe5e','ef631792-18f9-4172-a6f6-3905127385f4','a7814731-8c75-4c1c-8a52-aa448f0298ed','0dc432ad-332f-412f-9b85-0c2a88a0d200','Adidas Samba Red',8800000.00,8800000.00,'','2024-06-02 21:28:59.873000','2024-06-02 21:28:59.950000',_binary '',_binary ''),('9','3410f6bc-a5f3-41e8-876a-1db6f9252d00','1717f56c-7ce9-4900-9260-5d9ad6c5631d','06b1b78f-e66f-47be-94a8-c043dde24449','a7814731-8c75-4c1c-8a52-aa448f0298ed','0dc432ad-332f-412f-9b85-0c2a88a0d200','Sục Balenciaga',2200000.00,2200000.00,'','2024-06-02 21:30:15.785000','2024-06-02 21:30:15.807000',_binary '',_binary ''),('CV1','c0efcb51-7527-43d1-b605-a5c00afc0821','7030780c-240b-49d4-bc04-6325e75d5aab','ba6fa368-4f85-11ee-8a18-ee19b027b30c','a7814731-8c75-4c1c-8a52-aa448f0298ed','2daa2fc8-5498-41d7-b1c4-ccd41237aa44','Converst',1990000.00,1990000.00,'','2024-06-02 21:59:49.576000','2024-06-02 21:59:49.619000',_binary '',_binary ''),('D1','c0efcb51-7527-43d1-b605-a5c00afc0821','e6564813-1932-4f6a-8b4d-dd269346fd1d','36302f8d-192d-462a-b63a-168174759d2c','a7814731-8c75-4c1c-8a52-aa448f0298ed','0dc432ad-332f-412f-9b85-0c2a88a0d200','Dior V1',5990000.00,5990000.00,'','2024-06-02 21:57:49.123000','2024-06-02 22:01:59.855000',_binary '',_binary '');
/*!40000 ALTER TABLE `sanpham` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `size`
--

DROP TABLE IF EXISTS `size`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `size` (
  `Ma` float NOT NULL,
  `ChieuDai` float DEFAULT NULL,
  `NgayTao` datetime DEFAULT NULL,
  `NgayCapNhat` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`Ma`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `size`
--

LOCK TABLES `size` WRITE;
/*!40000 ALTER TABLE `size` DISABLE KEYS */;
INSERT INTO `size` VALUES (35,22,'2024-06-02 21:49:32','2024-06-02 21:49:32'),(36,22.5,'2024-06-02 21:49:43','2024-06-02 21:49:43'),(37,23,'2024-06-02 21:49:54','2024-06-02 21:49:54'),(38,23.5,'2024-06-02 21:50:07','2024-06-02 21:50:07'),(39,24,'2024-06-02 21:50:14','2024-06-02 21:50:14'),(40,25.2,'2024-06-02 21:50:24','2024-06-02 21:51:15'),(41,26,'2024-06-02 21:50:36','2024-06-02 21:50:36'),(42,27,'2024-06-02 21:51:36','2024-06-02 21:51:36'),(43,27.5,'2024-06-02 21:51:45','2024-06-02 21:51:45'),(44,28,'2024-06-02 21:51:56','2024-06-02 21:51:56');
/*!40000 ALTER TABLE `size` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `thongbao`
--

DROP TABLE IF EXISTS `thongbao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `thongbao` (
  `id` varchar(36) NOT NULL DEFAULT (uuid()),
  `NhanVien` varchar(20) DEFAULT NULL,
  `loaithongbao` varchar(255) DEFAULT NULL,
  `noidung` varchar(255) DEFAULT NULL,
  `thoigian` datetime(6) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ThongBao_NhanVien_idx` (`NhanVien`),
  CONSTRAINT `ThongBao_NhanVien` FOREIGN KEY (`NhanVien`) REFERENCES `nhanvien` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thongbao`
--

LOCK TABLES `thongbao` WRITE;
/*!40000 ALTER TABLE `thongbao` DISABLE KEYS */;
/*!40000 ALTER TABLE `thongbao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `thongbaonhan`
--

DROP TABLE IF EXISTS `thongbaonhan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `thongbaonhan` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ThongBao` varchar(36) DEFAULT NULL,
  `TrangThai` bit(1) DEFAULT NULL,
  `NhanVien` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ThongBaoNhan_ThongBao` (`ThongBao`),
  KEY `ThongBaoNhan_NhanVien_idx` (`NhanVien`),
  CONSTRAINT `FK4x4ehsj7p04xay6phhl8w1y50` FOREIGN KEY (`ThongBao`) REFERENCES `thongbao` (`id`),
  CONSTRAINT `ThongBaoNhan_NhanVien` FOREIGN KEY (`NhanVien`) REFERENCES `nhanvien` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ThongBaoNhan_ThongBao` FOREIGN KEY (`ThongBao`) REFERENCES `thongbao` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thongbaonhan`
--

LOCK TABLES `thongbaonhan` WRITE;
/*!40000 ALTER TABLE `thongbaonhan` DISABLE KEYS */;
INSERT INTO `thongbaonhan` VALUES (1,'f3214694-c071-4396-b1e9-7341f911129b',_binary '\0',NULL),(2,'8a84f697-4232-4ee3-a38e-3cd04e824fc2',_binary '\0',NULL),(3,'d9e19e33-918b-4420-90b1-e38f62fa1e7c',_binary '\0',NULL),(4,'e8f51527-1883-41ee-b3e9-b0f4bfca6595',_binary '\0',NULL),(5,'2d2a9cc7-ab89-4215-ba98-5fd581e457de',_binary '\0',NULL),(6,'c4699374-cf09-4543-a7b9-6fc91d88b7b9',_binary '\0',NULL),(7,'7bbc4d6d-b37b-4ad2-a64b-6861b80d0a1d',_binary '\0',NULL),(8,'43b75804-daa4-447a-8bec-bd4854a703b5',_binary '\0',NULL),(9,'ce75009d-d139-4166-aaeb-3ba43d95c4f5',_binary '\0',NULL),(10,'6ca6a287-413e-49fe-92e4-c9e1b25066dc',_binary '\0',NULL),(11,'2c882a67-463a-4b60-9b49-c9af8caf2138',_binary '\0',NULL),(12,'ad79e322-ce40-408d-94c1-8e1c19478450',_binary '\0',NULL),(13,'633f247d-7a97-4e04-8591-f8d137bcb86f',_binary '\0',NULL),(14,'903876b7-2253-4854-a0db-7c0f9564d642',_binary '\0',NULL),(15,'7c49fa6d-bb50-4e08-bbf5-226096e808fd',_binary '\0',NULL),(16,'5aa23fd6-13d1-4211-8bc5-ba6a7cffc874',_binary '\0',NULL),(17,'3e621eb5-4048-4d8c-bcba-341eedb8db08',_binary '\0',NULL),(18,'87366cd2-5485-4db1-af11-89d927d92744',_binary '\0',NULL),(19,'5efc0315-adbc-4b5e-bfd3-e2664c0e5837',_binary '\0',NULL),(20,'cc177f97-c416-490e-91ff-76b5ddf1c656',_binary '\0',NULL),(21,'1e4f240b-c46c-4f04-95a4-c8116fea573e',_binary '\0',NULL),(22,'84b7ae53-5ebf-4430-b6e7-eb0563a6060b',_binary '\0',NULL),(23,'8c7ad9a4-75d8-4f2f-8db7-672e0d962ad5',_binary '\0',NULL),(24,'abe15567-cf13-4f0f-8964-d7b3e60247f2',_binary '\0',NULL),(25,'c50d83a1-866d-4b64-8a28-971fd5cb92fb',_binary '\0',NULL),(26,'437d49f8-50b1-4f1a-a0e2-6df60d88383a',_binary '\0',NULL),(27,'44a7369a-b21b-4570-b552-b03298b109b0',_binary '\0',NULL),(28,'f6b7bab0-0a34-4ed0-b87e-d9b029e9a88d',_binary '\0',NULL),(29,'e4fe335e-b44f-416a-95e8-2ac743295ddd',_binary '\0',NULL),(30,'e3fca204-8d7c-4e23-be78-3b1d4dea20bb',_binary '\0',NULL),(31,'278ce76b-c210-44cc-b90d-40915bb26f6f',_binary '\0',NULL),(32,'497090aa-971e-4243-8df3-9810a1f213d6',_binary '\0',NULL),(33,'a94504d5-5122-4d5b-8b2f-4dbc6de3aa38',_binary '\0',NULL),(34,'abf8803e-cd0c-4df3-b2f4-8e918297f663',_binary '\0',NULL),(35,'2504099a-50b2-4328-8834-4a046e1e5037',_binary '\0',NULL),(36,'fbec81a5-5547-45d2-8271-8a35b29c550b',_binary '\0',NULL),(37,'57161f98-ad9b-427f-b90b-0c24cf03233f',_binary '\0',NULL),(38,'d5ed3250-fd78-41a6-b4e5-b2d74ead8d47',_binary '\0',NULL),(39,'20ef2876-9fc9-4be1-8f78-ab5ff9310cbc',_binary '\0',NULL),(40,'5143ab92-7bf2-44e7-a41a-db0058b2acfe',_binary '\0',NULL),(41,'dcddb40c-b588-414d-a47b-20cf3babd15e',_binary '\0',NULL),(42,'895256f4-bf2f-4edf-a5d8-4ff2b7718197',_binary '\0',NULL),(43,'7caf40a6-e8f9-4f03-9d88-da953cdb50ce',_binary '\0',NULL),(44,'7a9a715e-f0b2-439a-bcfe-b0cdc15c3f7b',_binary '\0',NULL),(45,'9a78684d-fd13-4dd7-8d5d-50135ef10f32',_binary '\0',NULL),(46,'912c7b2b-da79-45af-9fab-f2259030b3be',_binary '\0',NULL),(47,'171be8cb-ec40-4fce-8d17-813959725809',_binary '\0',NULL),(48,'67634815-d740-42e1-83eb-80fbfc1289bf',_binary '\0',NULL),(49,'89eea2dd-2246-4333-b48c-4dbbbe106d57',_binary '\0',NULL),(50,'5e9a34e5-20fe-4f73-8bf0-0c887d4fd88e',_binary '\0',NULL),(51,'23a63a1f-188c-472d-871d-4e48ff0f6f0d',_binary '\0',NULL),(52,'8dbd4f34-8a59-48d0-ad76-4bc0c6f37944',_binary '\0',NULL),(53,'6a65fa1d-8dd4-4960-aec2-a7a7ff04c222',_binary '\0',NULL),(54,'9dc13e3b-b16b-4c87-8779-48d660a99f61',_binary '\0',NULL),(55,'ec1d8448-f806-490f-a9bb-d945ec8e667b',_binary '\0',NULL),(56,'f503fcf3-da84-492d-942a-67b76b278758',_binary '\0',NULL),(57,'be58f65e-2901-4dfe-b8aa-a63c3df57334',_binary '\0',NULL),(58,'0523b819-0c77-4b47-b34b-9b4dd6d573a0',_binary '\0',NULL),(59,'088b643b-ff65-40c0-a61d-69e76a45b1fe',_binary '\0',NULL),(60,'63d10209-2070-4e78-917d-a65826c5c904',_binary '\0',NULL),(61,'dcd342c5-2365-40bb-a4b7-50f0808d5bfd',_binary '\0',NULL),(62,'9e54dc6b-e2ed-422f-abe6-903d7fd13adf',_binary '\0',NULL),(63,'ca0cb38e-8131-4a11-bb45-0b4080170bf2',_binary '\0',NULL),(64,'429758c3-b65b-4625-8260-bb49749bbca7',_binary '\0',NULL),(65,'35201a4e-bcd0-411e-9f9e-97978d8ec1b9',_binary '\0',NULL),(66,'a0a9114b-e6a0-48b9-ba47-f3eb8548a920',_binary '\0',NULL),(67,'c2f5a9fd-8444-4795-9f8a-db2e6139d74c',_binary '\0',NULL),(68,'0159d590-ef9a-42e7-a4f9-418947637e9e',_binary '\0',NULL),(69,'b22cef5c-1122-42f1-aed6-f740f691158d',_binary '\0',NULL),(70,'ea7c89d7-a9bf-490d-a440-a2be6ca40ee6',_binary '\0',NULL),(71,'60a24f52-1b29-46cb-9a04-1a255a3d9b89',_binary '\0',NULL),(72,'1f0b369b-b24e-496d-bc11-7bd4a80b45c8',_binary '\0',NULL),(73,'c8934b89-3381-44cd-97de-96aae19d9ec6',_binary '\0',NULL),(74,'f9eb21c3-6591-433b-8d33-2621b57128ca',_binary '\0',NULL),(75,'7d5220b1-b44e-4f75-9038-cbdd2bb828af',_binary '\0',NULL),(76,'81165ad0-548a-4dbc-bc54-da4768c3f138',_binary '\0',NULL);
/*!40000 ALTER TABLE `thongbaonhan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `thuonghieu`
--

DROP TABLE IF EXISTS `thuonghieu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `thuonghieu` (
  `id` varchar(36) NOT NULL DEFAULT (uuid()),
  `Ten` varchar(200) DEFAULT NULL,
  `NgayTao` datetime DEFAULT NULL,
  `NgayCapNhat` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thuonghieu`
--

LOCK TABLES `thuonghieu` WRITE;
/*!40000 ALTER TABLE `thuonghieu` DISABLE KEYS */;
INSERT INTO `thuonghieu` VALUES ('2066d25f-d081-4018-a432-bf50157df6f4','Louis Vuitton','2024-06-02 18:22:37','2024-06-02 18:22:37'),('27296473-9fb6-44ac-b61f-21ed97fa4b62','Dior','2024-06-02 21:54:32','2024-06-02 21:54:32'),('6ce98e5a-d01b-412c-81a9-6cd0adfddc25','Nike','2024-06-02 18:22:11','2024-06-02 18:22:11'),('8c56dc79-4d9e-4716-a425-8d7ccedd8832','Converse','2024-06-02 18:36:58','2024-06-02 18:36:58'),('95d7f1c8-e27c-4681-b6ad-547b4054b54e','Adidas','2024-06-02 18:22:06','2024-06-02 18:22:06'),('a0caa4a1-d76c-411e-8d73-fd345c9fad80','Balenciaga','2024-06-02 18:22:57','2024-06-02 18:22:57'),('ed4e0553-e93f-4490-af2b-4612537f43c0','Vans','2024-06-02 18:23:11','2024-06-02 18:23:11');
/*!40000 ALTER TABLE `thuonghieu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vaitro`
--

DROP TABLE IF EXISTS `vaitro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vaitro` (
  `Ma` varchar(20) NOT NULL,
  `Ten` varchar(50) NOT NULL,
  PRIMARY KEY (`Ma`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vaitro`
--

LOCK TABLES `vaitro` WRITE;
/*!40000 ALTER TABLE `vaitro` DISABLE KEYS */;
INSERT INTO `vaitro` VALUES ('ADMIN','Quản Lý'),('CUST','Khách Hàng'),('STAFF','Nhân Viên');
/*!40000 ALTER TABLE `vaitro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `voucher`
--

DROP TABLE IF EXISTS `voucher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `voucher` (
  `Ma` varchar(36) NOT NULL,
  `mota` varchar(200) DEFAULT NULL,
  `loaimucgiam` varchar(255) DEFAULT NULL,
  `MucGiam` double DEFAULT NULL,
  `GiaTriDonHang` decimal(12,2) DEFAULT NULL,
  `MucGiamToiDa` decimal(12,2) DEFAULT NULL,
  `NgayBatDau` datetime DEFAULT NULL,
  `NgayKetThuc` datetime DEFAULT NULL,
  `SoLuong` bigint DEFAULT NULL,
  `trangthaixoa` bit(1) DEFAULT NULL,
  `hinhthucthanhtoan` int DEFAULT NULL,
  `trangthai` int DEFAULT NULL,
  `doituongsudung` bit(1) DEFAULT NULL,
  PRIMARY KEY (`Ma`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `voucher`
--

LOCK TABLES `voucher` WRITE;
/*!40000 ALTER TABLE `voucher` DISABLE KEYS */;
/*!40000 ALTER TABLE `voucher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `voucherkhachhang`
--

DROP TABLE IF EXISTS `voucherkhachhang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `voucherkhachhang` (
  `voucher` varchar(255) NOT NULL,
  `khachhang` varchar(255) NOT NULL,
  KEY `FK1b9344hc3h5u61oua5hdubxgy` (`khachhang`),
  KEY `FKo1k8oa0dyhw8e05ni9d4sy4r` (`voucher`),
  CONSTRAINT `FK1b9344hc3h5u61oua5hdubxgy` FOREIGN KEY (`khachhang`) REFERENCES `khachhang` (`username`),
  CONSTRAINT `FKo1k8oa0dyhw8e05ni9d4sy4r` FOREIGN KEY (`voucher`) REFERENCES `voucher` (`Ma`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `voucherkhachhang`
--

LOCK TABLES `voucherkhachhang` WRITE;
/*!40000 ALTER TABLE `voucherkhachhang` DISABLE KEYS */;
/*!40000 ALTER TABLE `voucherkhachhang` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `xuatxu`
--

DROP TABLE IF EXISTS `xuatxu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `xuatxu` (
  `id` varchar(36) NOT NULL DEFAULT (uuid()),
  `Ten` varchar(200) DEFAULT NULL,
  `NgayTao` datetime DEFAULT NULL,
  `NgayCapNhat` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `xuatxu`
--

LOCK TABLES `xuatxu` WRITE;
/*!40000 ALTER TABLE `xuatxu` DISABLE KEYS */;
INSERT INTO `xuatxu` VALUES ('0dc432ad-332f-412f-9b85-0c2a88a0d200','Mỹ','2024-06-02 18:17:41','2024-06-02 18:17:41'),('1adf5f74-0bb3-4453-9cec-1b88b3807c76','Pháp','2024-06-02 18:17:54','2024-06-02 18:17:54'),('2daa2fc8-5498-41d7-b1c4-ccd41237aa44','Trung Quốc','2024-06-02 18:17:35','2024-06-02 18:17:35'),('407a7310-cb72-4145-a8a5-9c0a0fa7f2b6','Việt Nam','2024-06-02 18:17:27','2024-06-02 18:17:27'),('470e2611-1afd-4bb8-85e8-90f8c33ae7e9','Hàn Quốc','2024-06-02 18:17:49','2024-06-02 18:17:49');
/*!40000 ALTER TABLE `xuatxu` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-02 22:30:57
