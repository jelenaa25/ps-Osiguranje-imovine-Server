/*
SQLyog Community v13.1.7 (64 bit)
MySQL - 10.4.24-MariaDB : Database - osiguranja
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`osiguranja` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `osiguranja`;

/*Table structure for table `agentosiguranja` */

DROP TABLE IF EXISTS `agentosiguranja`;

CREATE TABLE `agentosiguranja` (
  `AgentID` int(11) NOT NULL,
  `Ime` varchar(255) DEFAULT NULL,
  `Prezime` varchar(255) DEFAULT NULL,
  `Username` varchar(255) DEFAULT NULL,
  `Password` varchar(255) DEFAULT NULL,
  `StrucnaSprema` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`AgentID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `agentosiguranja` */

insert  into `agentosiguranja`(`AgentID`,`Ime`,`Prezime`,`Username`,`Password`,`StrucnaSprema`) values 
(1,'Jelena','Beganovic','jelena','jelena1','VII'),
(2,'Ivana','Beganovic','ivana','ivana1','VII');

/*Table structure for table `klijent` */

DROP TABLE IF EXISTS `klijent`;

CREATE TABLE `klijent` (
  `KlijentID` int(11) NOT NULL AUTO_INCREMENT,
  `ImePrezime` varchar(255) DEFAULT NULL,
  `JMBG` bigint(13) DEFAULT NULL,
  `Mesto` int(11) DEFAULT NULL,
  PRIMARY KEY (`KlijentID`),
  KEY `Mesto` (`Mesto`),
  CONSTRAINT `klijent_ibfk_1` FOREIGN KEY (`Mesto`) REFERENCES `mesto` (`PTT`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;

/*Data for the table `klijent` */

insert  into `klijent`(`KlijentID`,`ImePrezime`,`JMBG`,`Mesto`) values 
(1,'Nina Veljkovic',2147483647112,11000),
(2,'Andjela Basara',2147483647112,11000),
(3,'Jovan Kostic',2147483647124,11000),
(6,'Ana Antic',8974563218967,11420),
(7,'Vanja Kostic',9999999999999,11000),
(8,'Sanja Savic',8742310986324,11000),
(9,'Nikola Nikolic',8749632581087,11420),
(11,'Luka Pavic',8785320149304,11300);

/*Table structure for table `mesto` */

DROP TABLE IF EXISTS `mesto`;

CREATE TABLE `mesto` (
  `PTT` int(11) NOT NULL,
  `Naziv` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`PTT`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `mesto` */

insert  into `mesto`(`PTT`,`Naziv`) values 
(11000,'Beograd'),
(11300,'Smederevo'),
(11420,'Smederevska Palanka'),
(12000,'Požarevac'),
(16000,'Leskovac'),
(17500,'Vranje'),
(18000,'Niš'),
(23000,'Zrenjanin'),
(24000,'Subotica'),
(26000,'Pančevo'),
(31330,'Priboj'),
(32000,'Čačak'),
(34000,'Kragujevac'),
(36000,'Kraljevo'),
(37000,'Kruševac');

/*Table structure for table `pokrice` */

DROP TABLE IF EXISTS `pokrice`;

CREATE TABLE `pokrice` (
  `PokriceID` int(11) NOT NULL AUTO_INCREMENT,
  `Naziv` varchar(255) DEFAULT NULL,
  `Napomena` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`PokriceID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;

/*Data for the table `pokrice` */

insert  into `pokrice`(`PokriceID`,`Naziv`,`Napomena`) values 
(1,'Osnovni rizik',NULL),
(2,'Izlivanje vode iz instalacija',NULL),
(3,'Provalna kradja i razabojnistvo',NULL),
(4,'Lom stakla',''),
(5,'Lom masina','Nan'),
(6,'Rizici iznenadjenja',NULL),
(7,'Odgovornost prema drugim licima',NULL),
(8,'Opsta odgovornost',NULL),
(9,'Lom uredjaja','Napomena1'),
(10,'Pokrice1','');

/*Table structure for table `polisa` */

DROP TABLE IF EXISTS `polisa`;

CREATE TABLE `polisa` (
  `PolisaID` int(11) NOT NULL AUTO_INCREMENT,
  `KlijentID` int(11) DEFAULT NULL,
  `PovrsinaStana` decimal(10,0) DEFAULT NULL,
  `VrednostPoKvM` decimal(10,0) DEFAULT NULL,
  `GradjevinskaVrednost` decimal(10,0) DEFAULT NULL,
  `UkupnaPremija` decimal(10,0) DEFAULT NULL,
  `DatumOd` date DEFAULT NULL,
  `DatumDo` date DEFAULT NULL,
  `AgentID` int(11) DEFAULT NULL,
  PRIMARY KEY (`PolisaID`),
  KEY `KlijentID` (`KlijentID`),
  KEY `AgentID` (`AgentID`),
  CONSTRAINT `polisa_ibfk_1` FOREIGN KEY (`KlijentID`) REFERENCES `klijent` (`KlijentID`),
  CONSTRAINT `polisa_ibfk_2` FOREIGN KEY (`AgentID`) REFERENCES `agentosiguranja` (`AgentID`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4;

/*Data for the table `polisa` */

insert  into `polisa`(`PolisaID`,`KlijentID`,`PovrsinaStana`,`VrednostPoKvM`,`GradjevinskaVrednost`,`UkupnaPremija`,`DatumOd`,`DatumDo`,`AgentID`) values 
(1,1,120,8974,1076904,6892186,'2021-12-12','2023-10-01',1),
(6,2,100,1587,158700,1745700,'2021-02-01','2022-12-12',1),
(10,3,87,1254,109098,872784,'2021-01-12','2023-01-12',1),
(11,1,124,8715,1080598,3025674,'2022-02-02','2024-01-01',1),
(12,3,79,1368,108072,756504,'2021-01-01','2022-01-01',1);

/*Table structure for table `predmetosiguranja` */

DROP TABLE IF EXISTS `predmetosiguranja`;

CREATE TABLE `predmetosiguranja` (
  `PredmetID` int(11) NOT NULL AUTO_INCREMENT,
  `Naziv` varchar(255) DEFAULT NULL,
  `Napomena` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`PredmetID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4;

/*Data for the table `predmetosiguranja` */

insert  into `predmetosiguranja`(`PredmetID`,`Naziv`,`Napomena`) values 
(1,'Gradjevinski objekat',NULL),
(2,'Stvari domacinstva',NULL),
(3,'Novac',NULL),
(4,'Prozori i vrata na objektu',NULL),
(5,'Instalacije u sastavu objekta',NULL),
(6,'Clanovi domacinstva',NULL),
(8,'Nov predmet','Napomena predmet'),
(9,'Predmet1','');

/*Table structure for table `stavkapolise` */

DROP TABLE IF EXISTS `stavkapolise`;

CREATE TABLE `stavkapolise` (
  `PolisaID` int(11) NOT NULL,
  `RB` int(11) NOT NULL AUTO_INCREMENT,
  `PredmetID` int(11) DEFAULT NULL,
  `PokriceID` int(11) DEFAULT NULL,
  `SumaOsiguranja` decimal(10,0) DEFAULT NULL,
  `ProcenatAmortizacije` int(11) DEFAULT NULL,
  `Premija` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`PolisaID`,`RB`),
  KEY `RB` (`RB`),
  KEY `PredmetID` (`PredmetID`),
  KEY `PokriceID` (`PokriceID`),
  CONSTRAINT `stavkapolise_ibfk_1` FOREIGN KEY (`PolisaID`) REFERENCES `polisa` (`PolisaID`),
  CONSTRAINT `stavkapolise_ibfk_2` FOREIGN KEY (`PredmetID`) REFERENCES `predmetosiguranja` (`PredmetID`),
  CONSTRAINT `stavkapolise_ibfk_3` FOREIGN KEY (`PokriceID`) REFERENCES `pokrice` (`PokriceID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

/*Data for the table `stavkapolise` */

insert  into `stavkapolise`(`PolisaID`,`RB`,`PredmetID`,`PokriceID`,`SumaOsiguranja`,`ProcenatAmortizacije`,`Premija`) values 
(1,1,1,1,1076904,5,5384520),
(1,2,3,2,215381,7,1507666),
(6,1,1,1,158700,9,1428300),
(6,2,2,8,31740,10,317400),
(10,1,1,1,109098,8,872784),
(11,1,6,6,216120,14,3025674),
(12,1,1,1,108072,7,756504);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
