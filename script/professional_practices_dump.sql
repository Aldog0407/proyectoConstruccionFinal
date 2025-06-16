-- MySQL dump 10.13  Distrib 9.0.1, for Win64 (x86_64)
--
-- Host: localhost    Database: professional_practices
-- ------------------------------------------------------
-- Server version	9.0.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `academic`
--

DROP TABLE IF EXISTS `academic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `academic` (
  `idAcademic` int NOT NULL AUTO_INCREMENT,
  `idSubjectGroup` int DEFAULT NULL,
  `firstName` varchar(50) NOT NULL,
  `lastNameFather` varchar(50) NOT NULL,
  `lastNameMother` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `idUser` int NOT NULL,
  PRIMARY KEY (`idAcademic`),
  UNIQUE KEY `idUser` (`idUser`),
  KEY `idSubjectGroup` (`idSubjectGroup`),
  CONSTRAINT `academic_ibfk_1` FOREIGN KEY (`idSubjectGroup`) REFERENCES `subjectgroup` (`idSubjectGroup`),
  CONSTRAINT `academic_ibfk_2` FOREIGN KEY (`idUser`) REFERENCES `useraccount` (`idUser`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `academic`
--

LOCK TABLES `academic` WRITE;
/*!40000 ALTER TABLE `academic` DISABLE KEYS */;
INSERT INTO `academic` VALUES (1,1,'Diego','Martínez','López','diego.martinez@uv.mx',1,2),(2,1,'Juan','Pérez','Ramírez','juan.perez@uv.mx',1,8);
/*!40000 ALTER TABLE `academic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coordinator`
--

DROP TABLE IF EXISTS `coordinator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `coordinator` (
  `idCoordinator` int NOT NULL AUTO_INCREMENT,
  `firstName` varchar(45) NOT NULL,
  `lastNameFather` varchar(45) NOT NULL,
  `lastNameMother` varchar(45) DEFAULT NULL,
  `email` varchar(60) DEFAULT NULL,
  `idUser` int NOT NULL,
  PRIMARY KEY (`idCoordinator`),
  UNIQUE KEY `idUser` (`idUser`),
  CONSTRAINT `coordinator_ibfk_1` FOREIGN KEY (`idUser`) REFERENCES `useraccount` (`idUser`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coordinator`
--

LOCK TABLES `coordinator` WRITE;
/*!40000 ALTER TABLE `coordinator` DISABLE KEYS */;
INSERT INTO `coordinator` VALUES (1,'Valentina','García','Hernández','valentina.coordinadora@uv.mx',3);
/*!40000 ALTER TABLE `coordinator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `delivery`
--

DROP TABLE IF EXISTS `delivery`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `delivery` (
  `idDelivery` int NOT NULL AUTO_INCREMENT,
  `idRecord` int DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `startDate` datetime DEFAULT NULL,
  `endDate` datetime DEFAULT NULL,
  `deliveryType` enum('INITIAL DOCUMENT','FINAL DOCUMENT','REPORT') NOT NULL,
  `idInitialDocument` int DEFAULT NULL,
  `idFinalDocument` int DEFAULT NULL,
  `idReportDocument` int DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`idDelivery`),
  KEY `idRecord` (`idRecord`),
  KEY `idInitialDocument` (`idInitialDocument`),
  KEY `idFinalDocument` (`idFinalDocument`),
  KEY `idReportDocument` (`idReportDocument`),
  CONSTRAINT `delivery_ibfk_1` FOREIGN KEY (`idRecord`) REFERENCES `record` (`idRecord`),
  CONSTRAINT `delivery_ibfk_2` FOREIGN KEY (`idInitialDocument`) REFERENCES `initialdocument` (`idInitialDocument`),
  CONSTRAINT `delivery_ibfk_3` FOREIGN KEY (`idFinalDocument`) REFERENCES `finaldocument` (`idFinalDocument`),
  CONSTRAINT `delivery_ibfk_4` FOREIGN KEY (`idReportDocument`) REFERENCES `reportdocument` (`idReportDocument`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `delivery`
--

LOCK TABLES `delivery` WRITE;
/*!40000 ALTER TABLE `delivery` DISABLE KEYS */;
INSERT INTO `delivery` VALUES (1,NULL,'Constancia de seguro facultativo','2025-06-10 00:00:00','2025-06-13 23:59:59','INITIAL DOCUMENT',NULL,NULL,NULL,NULL),(2,NULL,'Horario','2025-06-10 00:00:00','2025-06-17 23:59:59','INITIAL DOCUMENT',NULL,NULL,NULL,NULL),(4,1,'test','2025-06-15 14:30:16','2025-06-16 00:00:00','REPORT',NULL,NULL,1,'test'),(5,1,'test','2025-06-15 14:42:41','2025-06-16 00:00:00','INITIAL DOCUMENT',NULL,NULL,NULL,'test'),(6,1,'test','2025-06-15 14:42:54','2025-06-16 00:00:00','FINAL DOCUMENT',NULL,NULL,NULL,'test'),(7,3,'testy','2025-06-15 14:43:07','2025-06-16 00:00:00','REPORT',NULL,NULL,2,'test'),(8,3,'fg','2025-06-15 14:44:19','2025-06-16 00:00:00','INITIAL DOCUMENT',NULL,NULL,NULL,'f'),(9,1,'El test final definmitivo','2025-06-15 14:54:25','2025-06-16 00:00:00','FINAL DOCUMENT',NULL,NULL,NULL,'aqui está la entrega del final test efinitivo del cu-04'),(10,1,'s','2025-06-15 14:54:44','2025-06-30 00:00:00','INITIAL DOCUMENT',NULL,NULL,NULL,'s');
/*!40000 ALTER TABLE `delivery` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `finaldocument`
--

DROP TABLE IF EXISTS `finaldocument`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `finaldocument` (
  `idFinalDocument` int NOT NULL AUTO_INCREMENT,
  `name` varchar(65) NOT NULL,
  `date` date DEFAULT NULL,
  `delivered` tinyint(1) NOT NULL DEFAULT '0',
  `status` enum('ENTREGADO','NO_ENTREGADO','EN_REVISION') NOT NULL,
  `filePath` varchar(255) DEFAULT NULL,
  `observations` varchar(200) DEFAULT NULL,
  `grade` decimal(4,2) DEFAULT NULL,
  PRIMARY KEY (`idFinalDocument`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `finaldocument`
--

LOCK TABLES `finaldocument` WRITE;
/*!40000 ALTER TABLE `finaldocument` DISABLE KEYS */;
/*!40000 ALTER TABLE `finaldocument` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `initialdocument`
--

DROP TABLE IF EXISTS `initialdocument`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `initialdocument` (
  `idInitialDocument` int NOT NULL AUTO_INCREMENT,
  `name` varchar(65) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `delivered` tinyint(1) NOT NULL DEFAULT '0',
  `status` enum('ENTREGADO','NO_ENTREGADO','EN_REVISION') NOT NULL,
  `filePath` varchar(255) DEFAULT NULL,
  `observations` varchar(200) DEFAULT NULL,
  `grade` decimal(4,2) DEFAULT NULL,
  PRIMARY KEY (`idInitialDocument`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `initialdocument`
--

LOCK TABLES `initialdocument` WRITE;
/*!40000 ALTER TABLE `initialdocument` DISABLE KEYS */;
/*!40000 ALTER TABLE `initialdocument` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `linkedorganization`
--

DROP TABLE IF EXISTS `linkedorganization`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `linkedorganization` (
  `idLinkedOrganization` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `address` varchar(70) DEFAULT NULL,
  `phone` varchar(10) DEFAULT NULL,
  `isActive` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`idLinkedOrganization`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `linkedorganization`
--

LOCK TABLES `linkedorganization` WRITE;
/*!40000 ALTER TABLE `linkedorganization` DISABLE KEYS */;
INSERT INTO `linkedorganization` VALUES (1,'Tech Innovators SA','Av. Reforma 123, Ciudad de México','5512345678',1),(2,'Global Solutions MX','Calle Independencia 456, Guadalajara','3311223344',1),(3,'Innovación Digital SA de CV','Blvd. Insurgentes Sur 789, CDMX','5544332211',1),(4,'Nómada Digital Tech','Blvd. Innovación 200, Distrito Tec, Monterrey, N.L.','8123459876',1),(5,'test','Bolivia 11 Colonia Francisco I Madero','1234567890',1);
/*!40000 ALTER TABLE `linkedorganization` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `presentationevaluation`
--

DROP TABLE IF EXISTS `presentationevaluation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `presentationevaluation` (
  `idEvaluation` int NOT NULL AUTO_INCREMENT,
  `title` varchar(65) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `grade` decimal(4,2) DEFAULT NULL,
  `observations` text,
  `idRecord` int DEFAULT NULL,
  PRIMARY KEY (`idEvaluation`),
  KEY `idRecord` (`idRecord`),
  CONSTRAINT `presentationevaluation_ibfk_1` FOREIGN KEY (`idRecord`) REFERENCES `record` (`idRecord`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `presentationevaluation`
--

LOCK TABLES `presentationevaluation` WRITE;
/*!40000 ALTER TABLE `presentationevaluation` DISABLE KEYS */;
/*!40000 ALTER TABLE `presentationevaluation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project` (
  `idProject` int NOT NULL AUTO_INCREMENT,
  `idRecord` int DEFAULT NULL,
  `idProjectManager` int DEFAULT NULL,
  `idLinkedOrganization` int DEFAULT NULL,
  `idCoordinator` int DEFAULT NULL,
  `name` varchar(50) NOT NULL,
  `department` varchar(30) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `methodology` varchar(45) DEFAULT NULL,
  `availability` int DEFAULT NULL,
  PRIMARY KEY (`idProject`),
  KEY `idRecord` (`idRecord`),
  KEY `idProjectManager` (`idProjectManager`),
  KEY `idLinkedOrganization` (`idLinkedOrganization`),
  KEY `idCoordinator` (`idCoordinator`),
  CONSTRAINT `project_ibfk_1` FOREIGN KEY (`idRecord`) REFERENCES `record` (`idRecord`),
  CONSTRAINT `project_ibfk_2` FOREIGN KEY (`idProjectManager`) REFERENCES `projectmanager` (`idProjectManager`),
  CONSTRAINT `project_ibfk_3` FOREIGN KEY (`idLinkedOrganization`) REFERENCES `linkedorganization` (`idLinkedOrganization`),
  CONSTRAINT `project_ibfk_4` FOREIGN KEY (`idCoordinator`) REFERENCES `coordinator` (`idCoordinator`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` VALUES (1,1,1,1,1,'Optimización de Procesos Industriales','Desarrollo Backend','Implementación de mejoras para optimizar la cadena de producción.','Lean Manufacturing',3),(2,3,2,2,1,'Proyecto Innovación Digital','Backend','Desarrollo de un sistema backend para gestión de usuarios y autenticación.','Agile',2);
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = cp850 */ ;
/*!50003 SET character_set_results = cp850 */ ;
/*!50003 SET collation_connection  = cp850_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `after_project_update_student` AFTER UPDATE ON `project` FOR EACH ROW BEGIN
    
    IF NEW.idRecord IS NOT NULL AND (OLD.idRecord IS NULL OR OLD.idRecord <> NEW.idRecord) THEN
        UPDATE Student s
        JOIN Record r ON s.idStudent = r.idStudent
        SET s.isAssignedToProject = 1
        WHERE r.idRecord = NEW.idRecord;
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `projectmanager`
--

DROP TABLE IF EXISTS `projectmanager`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `projectmanager` (
  `idProjectManager` int NOT NULL AUTO_INCREMENT,
  `idLinkedOrganization` int DEFAULT NULL,
  `firstName` varchar(45) NOT NULL,
  `lastNameFather` varchar(45) NOT NULL,
  `lastNameMother` varchar(45) DEFAULT NULL,
  `position` varchar(50) DEFAULT NULL,
  `email` varchar(60) DEFAULT NULL,
  `phone` varchar(10) NOT NULL,
  PRIMARY KEY (`idProjectManager`),
  KEY `idLinkedOrganization` (`idLinkedOrganization`),
  CONSTRAINT `projectmanager_ibfk_1` FOREIGN KEY (`idLinkedOrganization`) REFERENCES `linkedorganization` (`idLinkedOrganization`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `projectmanager`
--

LOCK TABLES `projectmanager` WRITE;
/*!40000 ALTER TABLE `projectmanager` DISABLE KEYS */;
INSERT INTO `projectmanager` VALUES (1,1,'Ana','Ramírez','Santos','Jefe de Desarrollo de Soluciones Tecnológicas','ana.ramirez@techinnovators.mx','5512345678'),(2,2,'Carlitos','Domínguez','Morales','Coordinador de Transformación Digital','carlos.dominguez@globalsolutions.mx','3311223344');
/*!40000 ALTER TABLE `projectmanager` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `record`
--

DROP TABLE IF EXISTS `record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `record` (
  `idRecord` int NOT NULL AUTO_INCREMENT,
  `idStudent` int NOT NULL,
  `idSubjectGroup` int NOT NULL,
  `hoursCount` int DEFAULT NULL,
  `reportPath` varchar(100) DEFAULT NULL,
  `presentationPath` varchar(100) DEFAULT NULL,
  `idTerm` int DEFAULT NULL,
  PRIMARY KEY (`idRecord`),
  KEY `idStudent` (`idStudent`),
  KEY `idSubjectGroup` (`idSubjectGroup`),
  KEY `idTerm` (`idTerm`),
  CONSTRAINT `record_ibfk_1` FOREIGN KEY (`idStudent`) REFERENCES `student` (`idStudent`),
  CONSTRAINT `record_ibfk_2` FOREIGN KEY (`idSubjectGroup`) REFERENCES `subjectgroup` (`idSubjectGroup`),
  CONSTRAINT `record_ibfk_3` FOREIGN KEY (`idTerm`) REFERENCES `term` (`idTerm`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `record`
--

LOCK TABLES `record` WRITE;
/*!40000 ALTER TABLE `record` DISABLE KEYS */;
INSERT INTO `record` VALUES (1,1,1,NULL,NULL,NULL,1),(2,2,1,NULL,NULL,NULL,1),(3,3,2,80,NULL,NULL,1),(4,4,2,NULL,NULL,NULL,1);
/*!40000 ALTER TABLE `record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reportdocument`
--

DROP TABLE IF EXISTS `reportdocument`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reportdocument` (
  `idReportDocument` int NOT NULL AUTO_INCREMENT,
  `reportedHours` int NOT NULL,
  `date` date NOT NULL,
  `grade` decimal(4,2) NOT NULL,
  `name` varchar(65) DEFAULT NULL,
  `delivered` tinyint(1) NOT NULL DEFAULT '0',
  `status` enum('ENTREGADO','NO_ENTREGADO','EN_REVISION') NOT NULL,
  `filePath` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idReportDocument`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reportdocument`
--

LOCK TABLES `reportdocument` WRITE;
/*!40000 ALTER TABLE `reportdocument` DISABLE KEYS */;
INSERT INTO `reportdocument` VALUES (1,0,'2025-06-15',0.00,'Estandar de codificación.pdf',1,'ENTREGADO','docs\\reports\\0_4_Estandar de codificación.pdf'),(2,80,'2025-06-15',0.00,'Documento sin título (1).pdf',1,'ENTREGADO','docs\\reports\\0_7_Documento sin título (1).pdf');
/*!40000 ALTER TABLE `reportdocument` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student` (
  `idStudent` int NOT NULL AUTO_INCREMENT,
  `firstName` varchar(45) NOT NULL,
  `lastNameFather` varchar(45) NOT NULL,
  `lastNameMother` varchar(45) DEFAULT NULL,
  `enrollment` varchar(45) NOT NULL,
  `email` varchar(45) DEFAULT NULL,
  `phone` varchar(10) DEFAULT NULL,
  `credits` int DEFAULT NULL,
  `semester` varchar(45) DEFAULT NULL,
  `isAssignedToProject` tinyint(1) DEFAULT NULL,
  `projectSelection` varchar(100) DEFAULT NULL,
  `grade` decimal(3,2) DEFAULT NULL,
  `idUser` int NOT NULL,
  PRIMARY KEY (`idStudent`),
  UNIQUE KEY `idUser` (`idUser`),
  CONSTRAINT `student_ibfk_1` FOREIGN KEY (`idUser`) REFERENCES `useraccount` (`idUser`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (1,'Camila','Soto','García','S20000001','zS20000001@estudiantes.uv.mx','5512345678',330,'10',0,NULL,NULL,1),(2,'Mariana','López','Fernández','S20050202','zS20050202@estudiantes.uv.mx','2281234567',340,'10',0,NULL,NULL,5),(3,'Santiago','Gómez','Martínez','S19038073','zS19038073@estudiantes.uv.mx','2299876543',351,'12',1,NULL,NULL,6),(4,'Isabela','Ramírez','Domínguez','S20015624','zS20015624@estudiantes.uv.mx','2287654321',345,'10',0,NULL,NULL,7);
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `studentingroup`
--

DROP TABLE IF EXISTS `studentingroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `studentingroup` (
  `idStudent` int NOT NULL,
  `idSubjectGroup` int NOT NULL,
  PRIMARY KEY (`idStudent`,`idSubjectGroup`),
  KEY `idSubjectGroup` (`idSubjectGroup`),
  CONSTRAINT `studentingroup_ibfk_1` FOREIGN KEY (`idStudent`) REFERENCES `student` (`idStudent`),
  CONSTRAINT `studentingroup_ibfk_2` FOREIGN KEY (`idSubjectGroup`) REFERENCES `subjectgroup` (`idSubjectGroup`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `studentingroup`
--

LOCK TABLES `studentingroup` WRITE;
/*!40000 ALTER TABLE `studentingroup` DISABLE KEYS */;
INSERT INTO `studentingroup` VALUES (1,1),(2,1),(3,2),(4,2);
/*!40000 ALTER TABLE `studentingroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subject`
--

DROP TABLE IF EXISTS `subject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subject` (
  `idSubject` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `credits` int NOT NULL,
  PRIMARY KEY (`idSubject`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subject`
--

LOCK TABLES `subject` WRITE;
/*!40000 ALTER TABLE `subject` DISABLE KEYS */;
INSERT INTO `subject` VALUES (1,'Prácticas Profesionales',14);
/*!40000 ALTER TABLE `subject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subjectgroup`
--

DROP TABLE IF EXISTS `subjectgroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subjectgroup` (
  `idSubjectGroup` int NOT NULL AUTO_INCREMENT,
  `idTerm` int DEFAULT NULL,
  `idSubject` int DEFAULT NULL,
  `schedule` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idSubjectGroup`),
  KEY `idTerm` (`idTerm`),
  KEY `idSubject` (`idSubject`),
  CONSTRAINT `subjectgroup_ibfk_1` FOREIGN KEY (`idTerm`) REFERENCES `term` (`idTerm`),
  CONSTRAINT `subjectgroup_ibfk_2` FOREIGN KEY (`idSubject`) REFERENCES `subject` (`idSubject`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subjectgroup`
--

LOCK TABLES `subjectgroup` WRITE;
/*!40000 ALTER TABLE `subjectgroup` DISABLE KEYS */;
INSERT INTO `subjectgroup` VALUES (1,1,1,'Lunes y Miércoles 09:00 - 11:00'),(2,1,1,'Martes y Jueves 14:00 - 16:00');
/*!40000 ALTER TABLE `subjectgroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teachingassignment`
--

DROP TABLE IF EXISTS `teachingassignment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `teachingassignment` (
  `idSubjectGroup` int NOT NULL,
  `idAcademic` int NOT NULL,
  PRIMARY KEY (`idSubjectGroup`,`idAcademic`),
  KEY `idAcademic` (`idAcademic`),
  CONSTRAINT `teachingassignment_ibfk_1` FOREIGN KEY (`idSubjectGroup`) REFERENCES `subjectgroup` (`idSubjectGroup`),
  CONSTRAINT `teachingassignment_ibfk_2` FOREIGN KEY (`idAcademic`) REFERENCES `academic` (`idAcademic`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teachingassignment`
--

LOCK TABLES `teachingassignment` WRITE;
/*!40000 ALTER TABLE `teachingassignment` DISABLE KEYS */;
INSERT INTO `teachingassignment` VALUES (1,1),(2,2);
/*!40000 ALTER TABLE `teachingassignment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `term`
--

DROP TABLE IF EXISTS `term`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `term` (
  `idTerm` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `startDate` date DEFAULT NULL,
  `endDate` date DEFAULT NULL,
  PRIMARY KEY (`idTerm`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `term`
--

LOCK TABLES `term` WRITE;
/*!40000 ALTER TABLE `term` DISABLE KEYS */;
INSERT INTO `term` VALUES (1,'Febrero 2025 - Julio 2025','2025-02-01','2025-07-31'),(2,'Agosto 2025 - Enero 2026','2025-08-01','2026-01-31');
/*!40000 ALTER TABLE `term` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `useraccount`
--

DROP TABLE IF EXISTS `useraccount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `useraccount` (
  `idUser` int NOT NULL AUTO_INCREMENT,
  `username` varchar(30) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('STUDENT','TEACHER','COORDINATOR','EVALUATOR') NOT NULL,
  PRIMARY KEY (`idUser`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `useraccount`
--

LOCK TABLES `useraccount` WRITE;
/*!40000 ALTER TABLE `useraccount` DISABLE KEYS */;
INSERT INTO `useraccount` VALUES (1,'camila.soto','$2a$10$Pwyws4x/En1kTf5Nyn5B8uYytGh8crNUqbHYDhKOnYXx3olKecPO6','STUDENT'),(2,'diego.martinez','$2a$10$Me9csHlVAGAims14UFDnv.2KRgTzm6UABIg7xwBtWYm4j9XiZG7Rq','TEACHER'),(3,'valentina.coordinadora','$2a$10$xxVIErvl.P2zqSJHP4DqYOrEhs40L/0dNKQYvwjwkT6kojkyImZrO','COORDINATOR'),(4,'leonardo.eval','$2a$10$3xQ2OHt8oaFYXOuGC3LFVeggqq6OkXqBV7sk.zvJgAzCf42Z9JULW','EVALUATOR'),(5,'mariana.lopez','$2a$10$iIp70172T.xpVBCBud5BVePXnwSz8I0UMqbPKtRcgQgP7Hg5o9Vf.','STUDENT'),(6,'santiago.gomez','$2a$10$uyi90wPZmLE24hzfja/lyOlQumYnwLZ5OAS8LMW1siWX5hApdmyfq','STUDENT'),(7,'isabela.ramirez','$2a$10$OCmyoyAnaX89Z.77xxuYDeOg1xCWRlwai55C1DUYRw9utKGWRz9t2','STUDENT'),(8,'juan.perez','$2a$10$s8VRB9wZFcw0ZxpSH9bemuyTm2lMeyx1pbMA51IjCfoQNbj21dkjy','TEACHER'),(9,'alejandro.vargas','$2a$10$1/zOi7moCqUBdKZdUP.XDuMy3Jza38gvX6gIG/UOja5.r/VkkZgFi','EVALUATOR');
/*!40000 ALTER TABLE `useraccount` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-15 18:03:49
