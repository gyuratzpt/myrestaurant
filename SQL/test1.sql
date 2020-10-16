-- phpMyAdmin SQL Dump
-- version 4.4.15.10
-- https://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Oct 16, 2020 at 09:27 AM
-- Server version: 5.5.60-MariaDB
-- PHP Version: 5.4.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `test1`
--

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

CREATE TABLE IF NOT EXISTS `categories` (
  `id` tinyint(3) unsigned NOT NULL,
  `name` varchar(64) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`id`, `name`) VALUES
(1, 'food'),
(2, 'drink'),
(3, 'drug');

-- --------------------------------------------------------

--
-- Table structure for table `drinks`
--

CREATE TABLE IF NOT EXISTS `drinks` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `detail` varchar(100) NOT NULL,
  `price` int(11) NOT NULL,
  `picture` varchar(100) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `drinks`
--

INSERT INTO `drinks` (`id`, `name`, `detail`, `price`, `picture`) VALUES
(25, 'wizard', 'ez már jó', 700, 'wizard'),
(30, 'birramoretti', 'olasz', 500, 'birramoretti');

-- --------------------------------------------------------

--
-- Table structure for table `foods`
--

CREATE TABLE IF NOT EXISTS `foods` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `detail` varchar(100) NOT NULL,
  `price` int(11) NOT NULL,
  `picture` varchar(100) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `foods`
--

INSERT INTO `foods` (`id`, `name`, `detail`, `price`, `picture`) VALUES
(4, 'Hamburger', 'teszt-bla-bla-teszt', 1500, 'hamburger'),
(6, 'Extra', 'Extra hamburger', 2000, 'extrahamburger'),
(7, 'kebab', 'borjúhússal', 1000, 'donerkebab');

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE IF NOT EXISTS `orders` (
  `orderID` int(11) NOT NULL,
  `userID` int(10) unsigned NOT NULL,
  `productID` smallint(5) unsigned NOT NULL,
  `amount` tinyint(3) unsigned NOT NULL,
  `iscompleted` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`orderID`, `userID`, `productID`, `amount`, `iscompleted`) VALUES
(2, 5, 44, 2, 1),
(3, 5, 45, 5, 1),
(4, 2, 32, 2, 1),
(8, 7, 44, 3, 1),
(9, 8, 45, 2, 1),
(10, 1, 32, 2, 1),
(11, 1, 53, 2, 1),
(12, 1, 41, 2, 1),
(13, 7, 53, 3, 1),
(14, 7, 70, 2, 1),
(15, 6, 48, 5, 0),
(16, 8, 53, 1, 1),
(17, 2, 32, 6, 0),
(18, 8, 59, 4, 1),
(19, 1, 70, 2, 0),
(20, 1, 53, 4, 0),
(21, 6, 70, 2, 0),
(22, 6, 41, 3, 0),
(23, 6, 44, 5, 0),
(24, 7, 47, 10, 1),
(25, 7, 53, 8, 1),
(26, 6, 32, 2, 0),
(27, 6, 58, 1, 0),
(28, 8, 59, 1, 1),
(29, 8, 52, 2, 1),
(30, 1, 48, 6, 0);

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE IF NOT EXISTS `products` (
  `id` smallint(5) unsigned NOT NULL,
  `categoryID` tinyint(3) unsigned NOT NULL,
  `name` varchar(32) NOT NULL,
  `detail` varchar(256) NOT NULL,
  `price` int(4) NOT NULL,
  `picture` varchar(32) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`id`, `categoryID`, `name`, `detail`, `price`, `picture`) VALUES
(1, 1, '444', '7787', 444, 'noimage'),
(32, 1, 'Kebab', 'Pita borjúhússal, zöldségekkel, joghurtos szósszal', 1000, 'donerkebab'),
(41, 3, 'Kokain', 'Felpörget és kiélesíti az érzékidet', 9500, NULL),
(44, 1, 'Kakaós csiga', 'Klasszik péksütemény, jetiknek', 200, 'csiga'),
(45, 2, 'Fanta', 'Narancsos szénsavas', 300, 'fanta'),
(47, 2, 'Coca-cola', 'Az eredeti', 300, 'cola'),
(48, 1, 'Extra hamburger', '300gr marhahús pogácsa, paradicsom, uborka, sajt', 2100, 'extrahamburger'),
(49, 1, 'Dupla hamburger', '2x300gr marhahús pogácsa, paradicsom, uborka, sajt', 3300, 'duplahamburger'),
(52, 2, 'Birra Moretti', 'Olasz lötty', 500, 'birramoretti'),
(53, 2, 'Kilkenny', 'Ír vörös sör', 730, 'kilkenny'),
(55, 2, 'Wizard', 'Ismeretlen, de tuti jó', 800, 'wizard'),
(56, 3, 'LSD', 'A kémia csodája', 8000, NULL),
(57, 3, 'Ganja', 'Ellazít', 3000, NULL),
(58, 2, 'Stella Artois', 'Francia pils', 550, 'stella'),
(59, 2, 'Sprite', 'Szénsavas', 300, 'sprite'),
(70, 1, 'Dürüm', 'Tésztalap borjúhússal, zöldségekkel, joghurtos szósszal', 900, 'durum');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id` int(10) unsigned NOT NULL,
  `is_admin` int(11) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `username` varchar(32) NOT NULL,
  `address` varchar(128) NOT NULL,
  `phonenumber` varchar(16) NOT NULL,
  `updated_at` datetime NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `is_admin`, `email`, `password`, `username`, `address`, `phonenumber`, `updated_at`) VALUES
(1, 1, 'vizirider@gmail.com', 'Almakarika01', '', '', '', '2020-07-02 00:00:00'),
(2, 0, 'johndoe@valami.com', 'Almakarika02', '', '', '', '2020-07-02 00:00:00'),
(5, 0, 'johndoe2@valami.com', '1234', 'John Doe', '21412 USA, Kentucky, Silent Hill', '612338762186', '2020-07-14 20:46:56'),
(6, 0, 'aaa@aaa.aa', 'aaaa', 'Alpha', '12312 AAAA AAA, Aaaaa. 44', '123123123', '2020-10-07 10:16:20'),
(7, 0, 'bbb@bbb.bb', 'bbbbbb', 'Beta', '12312 Beta, Bbbbb Bbbb. 4', '321321321', '2020-10-07 07:04:13'),
(8, 1, 'en@en.hu', 'enenen', 'Én', '123123 Otthon X Utca 1', '11111111', '2020-10-07 04:02:17');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `drinks`
--
ALTER TABLE `drinks`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `foods`
--
ALTER TABLE `foods`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`orderID`),
  ADD KEY `userID` (`userID`),
  ADD KEY `productID` (`productID`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`),
  ADD UNIQUE KEY `picture` (`picture`),
  ADD KEY `category` (`categoryID`),
  ADD KEY `categoryID` (`categoryID`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `categories`
--
ALTER TABLE `categories`
  MODIFY `id` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `drinks`
--
ALTER TABLE `drinks`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=31;
--
-- AUTO_INCREMENT for table `foods`
--
ALTER TABLE `foods`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `orderID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=31;
--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=71;
--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=9;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`productID`) REFERENCES `products` (`id`);

--
-- Constraints for table `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `products_ibfk_1` FOREIGN KEY (`categoryID`) REFERENCES `categories` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
