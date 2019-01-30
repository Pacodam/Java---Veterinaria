-- phpMyAdmin SQL Dump
-- version 4.4.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jan 28, 2019 at 07:14 PM
-- Server version: 5.6.26
-- PHP Version: 5.6.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `vetstucom`
--

-- --------------------------------------------------------

--
-- Table structure for table `expedientes`
--

CREATE TABLE IF NOT EXISTS `expedientes` (
  `ID` int(11) NOT NULL,
  `NOMBRE` varchar(30) DEFAULT NULL,
  `APELLIDOS` varchar(30) DEFAULT NULL,
  `DNI` varchar(12) DEFAULT NULL,
  `CP` varchar(6) DEFAULT NULL,
  `FECHA_ALTA` date DEFAULT NULL,
  `TELEFONO` varchar(12) DEFAULT NULL,
  `N_MASCOTAS` int(11) DEFAULT NULL,
  `USUARIO_ALTA` int(11) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `usuarios`
--

CREATE TABLE IF NOT EXISTS `usuarios` (
  `ID` int(11) NOT NULL,
  `NOMBRE` varchar(25) DEFAULT NULL,
  `APELLIDOS` varchar(25) DEFAULT NULL,
  `DNI` varchar(12) DEFAULT NULL,
  `MATRICULA` varchar(6) DEFAULT NULL,
  `PASS` varchar(8) DEFAULT NULL,
  `TIPO_USUARIO` int(11) DEFAULT NULL,
  `ULTIMO_ACCESO` date DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `expedientes`
--
ALTER TABLE `expedientes`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `fk_usuarioAlta` (`USUARIO_ALTA`);

--
-- Indexes for table `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `expedientes`
--
ALTER TABLE `expedientes`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=7;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `expedientes`
--
ALTER TABLE `expedientes`
  ADD CONSTRAINT `expedientes_ibfk_1` FOREIGN KEY (`USUARIO_ALTA`) REFERENCES `usuarios` (`ID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
