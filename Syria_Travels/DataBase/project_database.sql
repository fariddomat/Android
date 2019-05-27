-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 14, 2017 at 07:47 PM
-- Server version: 5.7.14
-- PHP Version: 5.6.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `project`
--

-- --------------------------------------------------------

--
-- Table structure for table `clients`
--

CREATE TABLE `clients` (
  `client_id` int(11) NOT NULL,
  `client_email_phone` varchar(150) COLLATE utf16_bin NOT NULL,
  `client_username` varchar(25) COLLATE utf16_bin NOT NULL,
  `client_password` varchar(100) COLLATE utf16_bin NOT NULL,
  `isAdmin` varchar(3) COLLATE utf16_bin NOT NULL DEFAULT 'no',
  `client_status` varchar(100) COLLATE utf16_bin NOT NULL DEFAULT 'hold'
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

--
-- Dumping data for table `clients`
--

INSERT INTO `clients` (`client_id`, `client_email_phone`, `client_username`, `client_password`, `isAdmin`, `client_status`) VALUES
(1, 'admin@hotmail.com', 'system_admin', 'b325765e786274f1c93de0b068589247adc0ff6acf0ea1f9d641eca3fa4960da', 'yes', 'active'),
(2, 'client@hotmail.com', 'client', 'b325765e786274f1c93de0b068589247adc0ff6acf0ea1f9d641eca3fa4960da', 'no', 'active');

-- --------------------------------------------------------

--
-- Table structure for table `companies`
--

CREATE TABLE `companies` (
  `c_id` int(15) NOT NULL,
  `c_name` varchar(50) COLLATE utf16_bin NOT NULL,
  `c_username` varchar(50) COLLATE utf16_bin NOT NULL,
  `c_password` varchar(100) COLLATE utf16_bin NOT NULL,
  `c_details` text COLLATE utf16_bin,
  `c_icon` varchar(50) COLLATE utf16_bin DEFAULT NULL,
  `c_type` varchar(1) COLLATE utf16_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

--
-- Dumping data for table `companies`
--

INSERT INTO `companies` (`c_id`, `c_name`, `c_username`, `c_password`, `c_details`, `c_icon`, `c_type`) VALUES
(1, 'Flye Emirates', 'f@e.com', 'b325765e786274f1c93de0b068589247adc0ff6acf0ea1f9d641eca3fa4960da', '    the best flights in syria', 'FE.png', 'f'),
(5, 'Turkish Airline', '@turkie', '@turkie123', 'this is turkish airlines .', 'Untitled.png', 'f'),
(6, 'FlyDamas', '@flydamas', 'b3ccca5aee605ae51f589fe0599becff71ca38685dcc53d3f07c553993196553', ' Flydamas Airline, once is authorized by the Syrian Civil Aviation Authority, aims to offer commercial air transportation services from/to the Syrian Airports and support the Syrian Market together with Syrian Air economical travel chances .', 'FlyDamas .png', 'f'),
(10, 'train', 'train@n.com', 'b325765e786274f1c93de0b068589247adc0ff6acf0ea1f9d641eca3fa4960da', 'the train', 'Untitled.png', 't');

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

CREATE TABLE `employee` (
  `e_id` int(11) NOT NULL,
  `e_username` varchar(25) COLLATE utf16_bin NOT NULL,
  `e_password` varchar(100) COLLATE utf16_bin NOT NULL,
  `e_comapny_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`e_id`, `e_username`, `e_password`, `e_comapny_id`) VALUES
(3, 'employee', 'b325765e786274f1c93de0b068589247adc0ff6acf0ea1f9d641eca3fa4960da', 1),
(5, 'ammar', 'b325765e786274f1c93de0b068589247adc0ff6acf0ea1f9d641eca3fa4960da', 1);

-- --------------------------------------------------------

--
-- Table structure for table `event_history`
--

CREATE TABLE `event_history` (
  `event_id` int(11) NOT NULL,
  `event_date` date NOT NULL,
  `event_time` time NOT NULL,
  `event_action` text CHARACTER SET utf16 COLLATE utf16_bin NOT NULL,
  `event_details` text CHARACTER SET utf16 COLLATE utf16_bin NOT NULL,
  `event_actor_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `event_history`
--

INSERT INTO `event_history` (`event_id`, `event_date`, `event_time`, `event_action`, `event_details`, `event_actor_id`) VALUES
(1, '2017-07-04', '15:20:11', 'void_reservation', 'your employee void reservation for passenger   on trip 5 for this reason: empty sit', 3),
(2, '2017-07-04', '15:24:34', 'confirm_reservation', 'your employee accept reservation for passenger   on trip 5', 3),
(3, '2017-07-04', '15:31:19', 'cancel_reservation', 'your employee accept to cancel reservation for passenger   on trip 2', 3),
(4, '2017-07-04', '15:43:01', 'signup', 'new client register with username fad', 13),
(5, '2017-07-04', '15:55:10', 'e_login', 'login to system : qaz', 3),
(6, '2017-07-04', '15:57:24', 'login', 'login to system : farid_domat', 2),
(7, '2017-07-05', '16:04:01', 'c_login', 'login to system : f@e.com', 1),
(8, '2017-07-05', '16:30:03', 'login', 'login to system : George_Saad', 1),
(9, '2017-07-05', '16:50:57', 'c_login', 'login to system : f@e.com', 1),
(10, '2017-07-07', '14:25:53', 'login', 'login to system : farid_domat', 2),
(11, '2017-07-11', '07:35:16', 'login', 'login to system : farid_domat', 2),
(12, '2017-07-11', '08:20:38', 'c_login', 'login to system : f@e.com', 1),
(13, '2017-07-11', '08:40:58', 'login', 'login to system : farid_domat', 2),
(14, '2017-07-11', '13:38:31', 'e_login', 'login to system : qaz', 3),
(15, '2017-07-11', '13:45:35', 'confirm_reservation', 'your employee accept reservation for passenger   on trip 6', 3),
(16, '2017-07-11', '13:45:39', 'confirm_reservation', 'your employee accept reservation for passenger   on trip 9', 3),
(17, '2017-07-11', '13:54:18', 'login', 'login to system : farid_domat', 2),
(32, '2017-07-12', '07:21:11', 'e_login', 'login to system : qaz', 3),
(33, '2017-07-12', '07:31:31', 'login', 'login to system : farid_domat', 2),
(34, '2017-07-20', '14:42:26', 'login', 'login to system : George_Saad', 1),
(35, '2017-07-20', '14:49:25', 'c_login', 'login to system : f@e.com', 1),
(36, '2017-07-20', '15:06:07', 'login', 'login to system : George_Saad', 1),
(37, '2017-07-20', '15:08:54', 'c_login', 'login to system : f@e.com', 1),
(38, '2017-07-20', '15:10:44', 'add_trip', 'add trip from : a to :a', 1),
(39, '2017-07-20', '15:13:58', 'add_trip', 'add trip from : aAaa to :a', 1),
(40, '2017-07-20', '15:16:00', 'add_trip', 'add trip from : a to :a', 1),
(41, '2017-07-20', '15:18:09', 'add_trip', 'add trip from : a to :a', 1),
(42, '2017-07-20', '15:26:03', 'add_trip', 'update trip with id: 27015 from : Hama to :Homs', 1),
(43, '2017-07-20', '15:26:03', 'add_trip', 'update trip with id: 27015 from : Hama to :Homs', 1),
(44, '2017-07-20', '15:27:48', 'update_trip', 'update trip with id: ', 1),
(45, '2017-07-20', '15:27:48', 'update_trip', 'update trip with id: ', 1),
(46, '2017-07-20', '15:30:21', 'login', 'update trip with id: ', 1),
(47, '2017-07-20', '15:30:21', 'login', 'update trip with id: ', 1),
(48, '2017-07-20', '15:31:47', 'login', 'login to system : George_Saad', 1),
(49, '2017-07-20', '15:36:22', 'e_login', 'login to system : qaz', 3),
(54, '2017-07-20', '15:43:02', 'login', 'login to system : George_Saad', 1),
(55, '2017-07-20', '15:50:14', 'c_login', 'login to system : f@e.com', 1),
(56, '2017-07-20', '15:51:49', 'add_employee', 'add new employee', 1),
(57, '2017-07-20', '15:52:30', 'login', 'login to system : George_Saad', 1),
(58, '2017-07-21', '12:56:51', 'login', 'login to system : farid_domat', 2),
(59, '2017-07-21', '12:59:17', 'login', 'login to system : George_Saad', 1),
(60, '2017-07-21', '12:59:35', 'login', 'login to system : farid_domat', 2),
(61, '2017-07-21', '13:02:46', 'login', 'login to system : farid_domat', 2),
(62, '2017-07-21', '13:15:49', 'login', 'login to system : farid_domat', 2),
(63, '2017-07-21', '13:30:51', 'login', 'login to system : farid_domat', 2),
(64, '2017-07-21', '13:32:30', 'login', 'login to system : farid_domat', 2),
(65, '2017-07-22', '20:52:05', 'e_login', 'login to system : qaz', 3),
(66, '2017-07-22', '21:00:58', 'login', 'login to system : farid_domat', 2),
(67, '2017-07-23', '06:50:22', 'e_login', 'login to system : qaz', 3),
(68, '2017-07-23', '06:52:27', 'login', 'login to system : George_Saad', 1),
(69, '2017-07-23', '06:53:50', 'c_login', 'login to system : f@e.com', 1),
(70, '2017-07-24', '08:05:46', 'login', 'login to system : George_Saad', 1),
(71, '2017-07-24', '08:22:18', 'c_login', 'login to system : f@e.com', 1),
(72, '2017-07-24', '08:35:43', 'remove_employee', 'delete employee', 1),
(73, '2017-07-24', '08:41:23', 'c_login', 'login to system : f@e.com', 1),
(74, '2017-07-24', '08:43:34', 'e_login', 'login to system : qaz', 3),
(75, '2017-07-24', '08:43:48', 'void_reservation', 'your employee void reservation for passenger   on trip 4 for this reason: aaa', 3),
(76, '2017-07-24', '08:46:33', 'confirm_reservation', 'your employee accept reservation for passenger   on trip 4', 3),
(77, '2017-07-24', '08:46:43', 'cancel_reservation', 'your employee accept to cancel reservation for passenger   on trip 4', 3),
(78, '2017-07-24', '08:47:15', 'login', 'login to system : farid_domat', 2),
(79, '2017-07-24', '10:06:18', 'login', 'login to system : George_Saad', 1),
(80, '2017-07-24', '10:16:02', 'signup', 'new client register with username qsc', 17),
(81, '2017-07-24', '10:21:06', 'signup', 'new client register with username qaw', 18),
(82, '2017-07-24', '10:22:05', 'login', 'login to system : qaw', 18),
(83, '2017-07-24', '10:25:18', 'login', 'login to system : farid_domat', 2),
(84, '2017-07-24', '13:21:23', 'c_login', 'login to system : f@e.com', 1),
(85, '2017-07-24', '13:26:09', 'login', 'login to system : George_Saad', 1),
(86, '2017-07-24', '13:29:26', 'login', 'login to system : farid_domat', 2),
(87, '2017-07-24', '13:40:18', 'login', 'login to system : George_Saad', 1),
(88, '2017-07-24', '13:51:24', 'login', 'login to system : farid_domat', 2),
(89, '2017-07-24', '15:41:30', 'e_login', 'login to system : abc', 5),
(90, '2017-07-24', '18:42:00', 'confirm_reservation', 'your employee confirmed resevation to passenger  a a on trip   6', 3),
(91, '2017-07-24', '18:48:06', 'cancel_reservation', 'your employee accepted to cancel resevation to passenger  farid domat on trip   3', 3),
(92, '2017-07-24', '18:48:19', 'cancel_reservation', 'your employee accepted to cancel resevation to passenger  farid domat on trip   3', 3),
(93, '2017-07-24', '18:48:32', 'cancel_reservation', 'your employee accepted to cancel resevation to passenger  farid domat on trip   3', 3),
(94, '2017-07-24', '18:48:32', 'cancel_reservation', 'your employee accepted to cancel resevation to passenger  farid domat on trip   3', 3),
(95, '2017-07-24', '18:49:07', 'cancel_reservation', 'your employee accepted to cancel resevation to passenger  farid aaa on trip   5', 3),
(96, '2017-07-24', '18:49:12', 'cancel_reservation', 'your employee accepted to cancel resevation to passenger  farid aaa on trip   5', 3),
(97, '2017-07-24', '18:49:14', 'cancel_reservation', 'your employee accepted to cancel resevation to passenger  farid aaa on trip   5', 3),
(98, '2017-07-24', '18:49:15', 'cancel_reservation', 'your employee accepted to cancel resevation to passenger  farid aaa on trip   5', 3),
(99, '2017-07-24', '18:49:17', 'cancel_reservation', 'your employee accepted to cancel resevation to passenger  farid aaa on trip   5', 3),
(100, '2017-07-24', '18:49:19', 'cancel_reservation', 'your employee accepted to cancel resevation to passenger  farid aaa on trip   5', 3),
(101, '2017-07-24', '18:49:25', 'cancel_reservation', 'your employee accepted to cancel resevation to passenger  farid aaa on trip   5', 3),
(102, '2017-07-24', '18:50:21', 'confirm_reservation', 'your employee confirmed resevation to passenger  a a on trip   6', 3),
(103, '2017-07-24', '18:51:33', 'confirm_reservation', 'your employee confirmed resevation to passenger  qq qq on trip   6', 3),
(104, '2017-07-24', '18:51:56', 'void_reservation', 'your employee void resevation to passenger  a a on trip   9for this reason :no reason', 3),
(105, '2017-07-24', '15:53:11', 'confirm_reservation', 'your employee accept reservation for passenger   on trip 6', 5),
(106, '2017-07-24', '16:04:50', 'login', 'login to system : George_Saad', 1),
(107, '2017-07-24', '16:09:50', 'c_login', 'login to system : f@e.com', 1),
(108, '2017-07-24', '16:11:30', 'e_login', 'login to system : qaz', 3),
(109, '2017-07-24', '16:16:32', 'confirm_reservation', 'your employee accept reservation for passenger   on trip 1', 3),
(110, '2017-07-24', '19:26:16', 'login', 'login to system : George_Saad', 1),
(111, '2017-07-24', '19:26:33', 'e_login', 'login to system : qaz', 3),
(112, '2017-07-24', '19:46:22', 'igrone_cancel_reservation', 'your employee accept to cancel reservation for passenger   on trip 1', 3),
(113, '2017-07-24', '20:29:10', 'cancel_reservation', 'your employee accept to cancel reservation for passenger   on trip 4', 3),
(114, '2017-07-24', '20:32:48', 'cancel_reservation', 'your employee accept to cancel reservation for passenger   on trip 4', 3),
(115, '2017-07-24', '20:37:12', 'e_login', 'login to system : ammar', 5),
(116, '2017-07-24', '20:37:43', 'login', 'login to system : system_admin', 1),
(117, '2017-07-25', '05:55:15', 'login', 'login to system : system_admin', 1),
(118, '2017-07-25', '05:56:53', 'login', 'login to system : client', 2),
(119, '2017-07-25', '05:58:13', 'e_login', 'login to system : employee', 3),
(120, '2017-07-25', '05:59:27', 'c_login', 'login to system : f@e.com', 1),
(121, '2017-07-25', '07:55:16', 'login', 'login to system : client', 2),
(122, '2017-07-25', '08:01:32', 'e_login', 'login to system : employee', 3),
(123, '2017-07-25', '08:02:11', 'confirm_reservation', 'your employee accept reservation for passenger   on trip 2', 3);

-- --------------------------------------------------------

--
-- Table structure for table `exchanging_message`
--

CREATE TABLE `exchanging_message` (
  `m_id` int(11) NOT NULL,
  `m_date` date NOT NULL,
  `m_time` time NOT NULL,
  `m_company_id` int(11) NOT NULL,
  `m_employee_id` int(11) NOT NULL,
  `m_client_id` int(11) NOT NULL,
  `m_details` text COLLATE utf16_bin NOT NULL,
  `m_is_read` varchar(3) COLLATE utf16_bin DEFAULT 'no'
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

--
-- Dumping data for table `exchanging_message`
--

INSERT INTO `exchanging_message` (`m_id`, `m_date`, `m_time`, `m_company_id`, `m_employee_id`, `m_client_id`, `m_details`, `m_is_read`) VALUES
(51, '2017-07-24', '18:49:17', 1, 3, 2, 'accepted', 'yes'),
(52, '2017-07-24', '18:49:19', 1, 3, 2, 'accepted', 'yes'),
(53, '2017-07-24', '18:49:25', 1, 3, 2, 'accepted', 'yes'),
(54, '2017-07-24', '18:50:21', 1, 3, 2, 'accepted', 'yes'),
(55, '2017-07-24', '18:51:33', 1, 3, 2, 'accepted', 'yes'),
(56, '2017-07-24', '18:51:56', 1, 3, 2, 'no reason', 'yes'),
(57, '2017-07-24', '15:53:11', 1, 5, 2, 'accepted', 'yes'),
(58, '2017-07-24', '16:16:32', 1, 3, 2, 'accepted', 'yes'),
(59, '2017-07-24', '19:46:22', 1, 3, 2, 'cancel accepted', 'yes'),
(60, '2017-07-24', '20:29:10', 1, 3, 2, 'cancel accepted', 'yes'),
(61, '2017-07-24', '20:32:48', 1, 3, 2, 'cancel accepted', 'yes'),
(62, '2017-07-25', '08:02:11', 1, 3, 2, 'accepted', 'yes');

-- --------------------------------------------------------

--
-- Table structure for table `reservations`
--

CREATE TABLE `reservations` (
  `r_id` int(11) NOT NULL,
  `r_trip_id` int(11) NOT NULL,
  `r_client_id` int(11) NOT NULL,
  `r_company_id` int(11) NOT NULL,
  `r_employee_id` int(11) DEFAULT NULL,
  `r_client_fname` varchar(20) COLLATE utf16_bin NOT NULL,
  `r_client_lname` varchar(20) COLLATE utf16_bin NOT NULL,
  `r_client_national_number` varchar(15) COLLATE utf16_bin NOT NULL,
  `r_client_country` varchar(20) COLLATE utf16_bin NOT NULL,
  `r_client_city` varchar(20) COLLATE utf16_bin NOT NULL,
  `r_client_email_phone` varchar(50) COLLATE utf16_bin NOT NULL,
  `r_date` date NOT NULL,
  `r_time` time NOT NULL,
  `r_seats_num` int(11) NOT NULL,
  `r_children_num` int(10) DEFAULT '0',
  `r_extra_weight_value` double DEFAULT '0',
  `r_type` varchar(50) COLLATE utf16_bin NOT NULL,
  `r_status` varchar(50) COLLATE utf16_bin NOT NULL,
  `r_cancel_request` varchar(10) COLLATE utf16_bin DEFAULT NULL,
  `r_card_number` varchar(25) COLLATE utf16_bin NOT NULL,
  `r_expire_date` date NOT NULL,
  `r_card_svv` int(3) NOT NULL,
  `r_round_trip` varchar(25) COLLATE utf16_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

--
-- Dumping data for table `reservations`
--

INSERT INTO `reservations` (`r_id`, `r_trip_id`, `r_client_id`, `r_company_id`, `r_employee_id`, `r_client_fname`, `r_client_lname`, `r_client_national_number`, `r_client_country`, `r_client_city`, `r_client_email_phone`, `r_date`, `r_time`, `r_seats_num`, `r_children_num`, `r_extra_weight_value`, `r_type`, `r_status`, `r_cancel_request`, `r_card_number`, `r_expire_date`, `r_card_svv`, `r_round_trip`) VALUES
(1, 1, 2, 1, 3, 'farid', 'domat', '0040021308038', 'Syria', 'Homs', 'george-s-94@hotmail.com', '2017-05-04', '02:19:58', 2, 0, NULL, 't_first_price', 'hold', '', '123', '2017-05-05', 123, NULL),
(3, 1, 1, 1, 3, 'george', 'saad', '0040021308038', 'Syria', 'Homs', 'george-s-94@hotmail.com', '2017-05-04', '04:08:15', 1, 0, NULL, 't_econ_price', 'voided', '', '123', '2017-05-05', 123, NULL),
(4, 1, 1, 1, 3, 'ghada', 'abo_fidah', '0040021308038', 'Syria', 'Homs', 'george-s-94@hotmail.com', '2017-05-04', '04:08:15', 1, 0, NULL, 't_first_price', 'confirmed', '', '123', '2017-05-05', 123, NULL),
(8, 3, 2, 1, 3, 'farid', 'domat', '0040021308038', 'Syria', 'Homs', 'george-s-94@hotmail.com', '2017-05-04', '02:19:58', 2, 0, NULL, 't_econ_price', 'canceled', 'yes', '123', '2017-05-05', 123, NULL),
(11, 4, 2, 1, 3, 'farid', 'domat', '00442023382', 'syria', 'homs', '00963933802782', '2017-06-02', '17:45:55', 3, 1, 2, 't_econ_price', 'canceled', 'yes', '0000000000000000', '2020-10-10', 0, NULL),
(12, 4, 2, 1, 3, 'farid', 'domat', '00442023382', 'France', 'Paris', '00963933802782', '2017-06-03', '20:56:01', 3, 1, 2, 't_econ_price', 'canceled', NULL, '0000000000000000', '2020-10-10', 0, NULL),
(13, 2, 2, 1, NULL, 'farid', 'domat', '123456', 'syria', 'homs', '0934538775', '2017-07-25', '07:58:39', 2, 2, 0, 't_first_price', 'confirmed', NULL, '123456', '2017-07-25', 123, '');

-- --------------------------------------------------------

--
-- Table structure for table `trip_info`
--

CREATE TABLE `trip_info` (
  `t_id` int(11) NOT NULL,
  `t_from` varchar(150) COLLATE utf16_bin NOT NULL,
  `t_to` varchar(150) COLLATE utf16_bin NOT NULL,
  `t_departure_date` date NOT NULL,
  `t_departure_time` time NOT NULL,
  `t_arrival_time` time NOT NULL,
  `t_duration` time NOT NULL,
  `t_econ_seats` int(4) NOT NULL,
  `t_first_seats` int(4) NOT NULL,
  `t_seats` int(4) NOT NULL,
  `t_econ_price` int(7) NOT NULL,
  `t_first_price` int(7) NOT NULL,
  `t_child_price` int(7) NOT NULL,
  `t_round_sold` int(7) DEFAULT '0',
  `t_status` varchar(25) COLLATE utf16_bin NOT NULL,
  `t_total_weight_value` int(3) NOT NULL,
  `t_extra_weight_price` int(3) NOT NULL,
  `t_company_id` int(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

--
-- Dumping data for table `trip_info`
--

INSERT INTO `trip_info` (`t_id`, `t_from`, `t_to`, `t_departure_date`, `t_departure_time`, `t_arrival_time`, `t_duration`, `t_econ_seats`, `t_first_seats`, `t_seats`, `t_econ_price`, `t_first_price`, `t_child_price`, `t_round_sold`, `t_status`, `t_total_weight_value`, `t_extra_weight_price`, `t_company_id`) VALUES
(1, 'Damas', 'Aleppo', '2017-08-12', '08:00:00', '09:00:00', '00:00:01', 20, 20, 40, 150, 200, 100, 0, 'available', 30, 30, 1),
(2, 'Berlinee', 'Damas', '2017-08-15', '08:00:00', '12:00:00', '04:00:00', 100, 21, 125, 201, 250, 150, 1, 'available', 30, 3, 1),
(3, 'Damas', 'Berline', '2017-08-22', '10:00:00', '14:30:00', '04:30:00', 78, 32, 100, 300, 400, 250, 50, 'available', 30, 3, 1),
(4, 'Damas', 'Berline', '2017-09-01', '08:00:00', '12:00:00', '00:00:04', 104, 21, 125, 300, 400, 200, 50, 'available', 30, 2, 1),
(6, 'Berline', 'Damas', '2017-09-18', '11:00:00', '11:45:00', '00:00:00', 56, 22, 97, 150, 200, 100, 25, 'available', 15, 1, 1),
(7, 'London', 'Paris', '2017-08-12', '01:00:00', '02:00:00', '00:00:01', 100, 20, 120, 150, 200, 100, 25, 'available', 30, 2, 1),
(8, 'LA', 'Mexeco', '2017-08-01', '22:00:00', '01:30:00', '20:30:00', 100, 25, 125, 150, 200, 100, 25, 'available', 20, 5, 1),
(9, 'Mexeco', 'LA', '2017-08-07', '08:00:00', '08:45:00', '00:45:00', 71, 25, 100, 150, 200, 100, 25, 'available', 15, 1, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `clients`
--
ALTER TABLE `clients`
  ADD PRIMARY KEY (`client_id`),
  ADD UNIQUE KEY `client_username` (`client_username`);

--
-- Indexes for table `companies`
--
ALTER TABLE `companies`
  ADD PRIMARY KEY (`c_id`),
  ADD UNIQUE KEY `c_id_2` (`c_id`),
  ADD KEY `c_id` (`c_id`),
  ADD KEY `c_id_3` (`c_id`),
  ADD KEY `c_id_4` (`c_id`);

--
-- Indexes for table `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`e_id`),
  ADD KEY `comapny_id` (`e_comapny_id`);

--
-- Indexes for table `event_history`
--
ALTER TABLE `event_history`
  ADD PRIMARY KEY (`event_id`);

--
-- Indexes for table `exchanging_message`
--
ALTER TABLE `exchanging_message`
  ADD PRIMARY KEY (`m_id`),
  ADD KEY `out_company_id` (`m_company_id`),
  ADD KEY `m_employee_id` (`m_employee_id`) USING BTREE,
  ADD KEY `m_client_id` (`m_client_id`) USING BTREE;

--
-- Indexes for table `reservations`
--
ALTER TABLE `reservations`
  ADD PRIMARY KEY (`r_id`),
  ADD KEY `r_flight_id` (`r_trip_id`),
  ADD KEY `r_user_id` (`r_client_id`),
  ADD KEY `r_employee_id_2` (`r_employee_id`),
  ADD KEY `r_employee_id_3` (`r_employee_id`),
  ADD KEY `r_company_id` (`r_company_id`);

--
-- Indexes for table `trip_info`
--
ALTER TABLE `trip_info`
  ADD PRIMARY KEY (`t_id`),
  ADD KEY `f_company_id` (`t_company_id`),
  ADD KEY `f_id` (`t_id`),
  ADD KEY `f_id_2` (`t_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `clients`
--
ALTER TABLE `clients`
  MODIFY `client_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `companies`
--
ALTER TABLE `companies`
  MODIFY `c_id` int(15) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT for table `employee`
--
ALTER TABLE `employee`
  MODIFY `e_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `event_history`
--
ALTER TABLE `event_history`
  MODIFY `event_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=124;
--
-- AUTO_INCREMENT for table `exchanging_message`
--
ALTER TABLE `exchanging_message`
  MODIFY `m_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=63;
--
-- AUTO_INCREMENT for table `reservations`
--
ALTER TABLE `reservations`
  MODIFY `r_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;
--
-- AUTO_INCREMENT for table `trip_info`
--
ALTER TABLE `trip_info`
  MODIFY `t_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `employee`
--
ALTER TABLE `employee`
  ADD CONSTRAINT `employee_ibfk_1` FOREIGN KEY (`e_comapny_id`) REFERENCES `companies` (`c_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `exchanging_message`
--
ALTER TABLE `exchanging_message`
  ADD CONSTRAINT `exchanging_message_ibfk_1` FOREIGN KEY (`m_company_id`) REFERENCES `companies` (`c_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `exchanging_message_ibfk_3` FOREIGN KEY (`m_client_id`) REFERENCES `clients` (`client_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `reservations`
--
ALTER TABLE `reservations`
  ADD CONSTRAINT `reservations_ibfk_1` FOREIGN KEY (`r_trip_id`) REFERENCES `trip_info` (`t_id`),
  ADD CONSTRAINT `reservations_ibfk_2` FOREIGN KEY (`r_client_id`) REFERENCES `clients` (`client_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `reservations_ibfk_3` FOREIGN KEY (`r_company_id`) REFERENCES `companies` (`c_id`);

--
-- Constraints for table `trip_info`
--
ALTER TABLE `trip_info`
  ADD CONSTRAINT `trip_info_ibfk_1` FOREIGN KEY (`t_company_id`) REFERENCES `companies` (`c_id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
