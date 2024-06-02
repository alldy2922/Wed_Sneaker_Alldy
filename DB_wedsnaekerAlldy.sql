CREATE DATABASE wedneakerAlldy;

--
-- Host: localhost    Database: webseneakerAlldy
-- ------------------------------------------------------
-- Server version	8.2.0

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
) ENGINE=InnoDB AUTO_INCREMENT=770 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `anh`
--

LOCK TABLES `anh` WRITE;
/*!40000 ALTER TABLE `anh` DISABLE KEYS */;
INSERT INTO `anh` VALUES 
(240,'NIKE4','b498dca0.jpg',NULL),
(491,'VANSOLDSCHOOLBLACK','8173b6ab.jpg',NULL),
(492,'VANSOLDSCHOOLBLACK','c45b4fdc.jpg',NULL),
(567,'NIKE1BLACK','6fe68aaa.jpg',NULL),
(568,'NIKE1BLACK','e878244e.jpg',NULL),
(569,'NIKE1BLACK','a0d6e7d9.jpg',NULL),
(570,'NIKE1BLACK','5b95fe33.jpg',NULL),
(571,'NIKE1BLACK','6758c9a.jpg',NULL),
(584,'2222','f5a4d408.jpg',NULL),
(585,'2222','5a3ca9d5.jpg',NULL),
(586,'2222','668899f7.jpg',NULL),
(587,'2222','8a5fad06.jpg',NULL),
(588,'2222','3b572c30.JPG',NULL),
(592,'HAHAHA','728c7445.JPG',NULL),
(607,'NIKEAIR','98bdfcfe.jpg',NULL),
(608,'NIKEAIR','2664e790.jpg',NULL
610,'NIKEAIR','bd7f50c9.jpg',NULL),
(611,'NIKEAIR','1c22f590.jpg',NULL),
(649,'124','d3dce307.JPG',NULL),
(650,'124','fbcac1a4.JPG',NULL),
(651,'124','ea319923.JPG',NULL),(652,'124','e5760b88.JPG',NULL),(653,'124','25bfebca.JPG',NULL),(663,'11111','e895f916.jpg',NULL),(664,'11111','aab73a7c.jpg',NULL),(665,'11111','c9b2f660.JPG',NULL),(666,'11111','232f674e.jpg',NULL),(667,'11111','1e694a94.jpg',NULL),(668,'123','5cc5fd63.JPG',NULL),(669,'123','d2672ac0.JPG',NULL),(670,'123','9ab47763.JPG',NULL),(671,'123','a79fb77d.JPG',NULL),(672,'123','90259e19.jpg',NULL),(673,'22222','2ed01490.jpg',NULL),(674,'22222','d36b9a61.jpg',NULL),(675,'22222','415b526d.jpg',NULL),(676,'22222','e6df3ef8.jpg',NULL),(677,'22222','e3c77c28.jpg',NULL),(678,'ADIDAS2PINK','1cbe0dc6.jpg',NULL),(679,'ADIDAS2PINK','cd6b7366.jpg',NULL),(680,'ADIDAS2PINK','316725a3.jpg',NULL),(681,'ADIDAS2PINK','33db19ca.jpg',NULL),(682,'ADIDAS2PINK','449a1d49.jpg',NULL),(683,'ADIDASSAMBA1','eb3fc0ac.jpg',NULL),(684,'ADIDASSAMBA1','110b1cb8.jpg',NULL),(685,'ADIDASSAMBA1','d982a4d6.jpg',NULL),(686,'ADIDASSAMBA1','3223bc1b.jpg',NULL),(687,'ADIDASSAMBA1','659f46c.jpg',NULL),(688,'CONVERALLSTARRED','8acf3ecb.jpg',NULL),(689,'CONVERALLSTARRED','acbe6a58.jpg',NULL),(690,'CONVERALLSTARRED','467a2ba9.jpg',NULL),(691,'CONVERALLSTARRED','2816ba27.jpg',NULL),(692,'CONVERALLSTARRED','7c62a481.jpg',NULL),(693,'CONVERSE','89f6f979.jpg',NULL),(694,'CONVERSE','93742702.jpg',NULL),(695,'CONVERSE','86b4f04d.jpg',NULL),(696,'CONVERSE','2148ceae.jpg',NULL),(697,'CONVERSE','dfba657d.jpg',NULL),(698,'CVAST','fe061bc6.JPG',NULL),(699,'CVAST','ec7b0ac6.JPG',NULL),(700,'CVAST','6bcbb15d.JPG',NULL),(701,'CVAST','8f163aa6.jpg',NULL),(702,'CVAST','51c6664a.jpg',NULL),(703,'GV6','ed8184a3.jpg',NULL),(704,'GV6','cbc5e9dd.jpg',NULL),(705,'GV6','9b92ef7e.jpg',NULL),(706,'GV6','8eca041a.jpg',NULL),(707,'GV6','a5c99fb4.jpg',NULL),(708,'JORDAN1','db33dd3f.jpg',NULL),(709,'JORDAN1','752345a0.jpg',NULL),(710,'JORDAN1','98fa58af.jpg',NULL),(711,'JORDAN1','b17f7eca.jpg',NULL),(712,'JORDAN1','900d5b7c.jpg',NULL),(713,'JORDAN4BLACK','a4c956af.jpg',NULL),(714,'JORDAN4BLACK','2cb38516.jpg',NULL),(715,'JORDAN4BLACK','42218350.jpg',NULL),(716,'JORDAN4BLACK','b37ccfdd.jpg',NULL),(717,'JORDANREDWHITE','63df8ab8.jpg',NULL),(718,'JORDANREDWHITE','1040cb6a.jpg',NULL),(719,'JORDANREDWHITE','37d7397a.jpg',NULL),(720,'JORDANREDWHITE','20673f51.jpg',NULL),(721,'JORDANREDWHITE','195811fe.jpg',NULL),(722,'NIKE3','a7cc87f7.jpg',NULL),(723,'NIKE3','bb38b197.jpg',NULL),(724,'NIKE3','cdce0643.jpg',NULL),(725,'NIKE3','aeacae2f.jpg',NULL),(726,'NIKE3','9431573c.jpg',NULL),(727,'NIKEFOG1','c3e32ca.jpg',NULL),(728,'NIKEFOG1','e9be8fc7.jpg',NULL),(729,'NIKEFOG1','691ed444.jpg',NULL),(730,'NIKEFOG1','382e7715.jpg',NULL),(731,'NIKEFOG1','9eb31796.jpg',NULL),(732,'PHUOC2003','6ea608f6.jpg',NULL),(733,'PHUOC2003','66fef613.jpg',NULL),(734,'PHUOC2003','41668e2b.jpg',NULL),(735,'PHUOC2003','8c854746.jpg',NULL),(736,'PHUOC2003','ea0db954.jpg',NULL),(737,'VANSCLASSICBLACK','a289f077.jpg',NULL),(738,'VANSCLASSICBLACK','48682a00.jpg',NULL),(739,'VANSCLASSICBLACK','7b0a5bac.jpg',NULL),(740,'VANSCLASSICBLACK','9d0a316b.jpg',NULL),(741,'VANSCLASSICBLACK','57d71cf.jpg',NULL),(746,'HGDFDVV','78ba76d9.jpg',NULL),(747,'HGDFDVV','c2a7b3ad.jpg',NULL),(748,'HGDFDVV','ed743c66.jpg',NULL),(749,'HGDFDVV','7b609f8d.jpg',NULL),(760,'ADIDASSTANSMITBLACK','5b6a65dc.jpg',NULL),(761,'ADIDASSTANSMITBLACK','e8fa4ef8.jpg',NULL),(762,'ADIDASSTANSMITBLACK','1b357d61.jpg',NULL),(763,'ADIDASSTANSMITBLACK','74262dc9.jpg',NULL),(764,'ADIDASSTANSMITBLACK','28963d48.jpg',NULL),(765,'ADIASYEEZYRED','b874f51d.jpg',NULL),(766,'ADIASYEEZYRED','346dcbf3.jpg',NULL),(767,'ADIASYEEZYRED','901f00b1.jpg',NULL),(768,'ADIASYEEZYRED','40d44304.jpg',NULL),(769,'ADIASYEEZYRED','7c5cea72.jpg',NULL);
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
INSERT INTO `khachhang` VALUES ('0388700177','0388700177','Trần Quốc Thành','2002-09-02',NULL,'0388700177','quocthanh2929@gmail.com',NULL),('0984732721','0984732721','Nguyen van Dat',NULL,NULL,'0984732721','datvip123@gmail.com',NULL),('0987654321','0987654321','Duong Huy',NULL,NULL,'0987654321','duonghuy112@gmail.com',NULL);
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
INSERT INTO `kieudang` VALUES ('36302f8d-192d-462a-b63a-168174759d2c','độn đế','2024-05-23 09:51:11','2024-05-23 19:59:50'),('6d66f1f1-f7c9-4a3c-a5cf-93618827fdb4','Cổ thấp','2024-05-23 09:57:07','2024-05-23 09:57:07'),('ba6fa368-4f85-11ee-8a18-ee19b027b30c','Cổ cao','2024-05-23 09:57:07','2024-05-23 09:57:07'),('ef631792-18f9-4172-a6f6-3905127385f4','Thể thao','2024-05-23 09:34:47','2024-05-23 09:34:47');
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
INSERT INTO `nhanvien` VALUES ('ADMIN','tranquocthanh','12345','Tran Quoc Thanh','2002-09-02',_binary '','0388700177','quocthanh2929@gmail.com','quocthanh.jpg');
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

-- Dump completed on 2024-01-12 17:21:44
