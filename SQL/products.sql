-- phpMyAdmin SQL Dump
-- version 4.4.15.10
-- https://www.phpmyadmin.net
--
-- Gép: localhost
-- Létrehozás ideje: 2020. Jún 30. 13:37
-- Kiszolgáló verziója: 5.5.60-MariaDB
-- PHP verzió: 5.4.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Adatbázis: `products`
--

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `drinks`
--

CREATE TABLE IF NOT EXISTS `drinks` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `detail` varchar(100) NOT NULL,
  `price` int(11) NOT NULL,
  `picture` varchar(100) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `drinks`
--

INSERT INTO `drinks` (`id`, `name`, `detail`, `price`, `picture`) VALUES
(1, 'cola', 'szensavas', 250, 'cola'),
(2, 'corona', 'sööör', 240, 'corona');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `foods`
--

CREATE TABLE IF NOT EXISTS `foods` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `detail` varchar(100) NOT NULL,
  `price` int(11) NOT NULL,
  `picture` varchar(100) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- A tábla adatainak kiíratása `foods`
--

INSERT INTO `foods` (`id`, `name`, `detail`, `price`, `picture`) VALUES
(1, 'hamburger', 'sajtos', 750, 'hamburger'),
(2, 'durum', 'durum', 1500, 'durum');

--
-- Indexek a kiírt táblákhoz
--

--
-- A tábla indexei `drinks`
--
ALTER TABLE `drinks`
  ADD PRIMARY KEY (`id`);

--
-- A tábla indexei `foods`
--
ALTER TABLE `foods`
  ADD PRIMARY KEY (`id`);

--
-- A kiírt táblák AUTO_INCREMENT értéke
--

--
-- AUTO_INCREMENT a táblához `drinks`
--
ALTER TABLE `drinks`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT a táblához `foods`
--
ALTER TABLE `foods`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
