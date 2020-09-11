/*
Navicat MySQL Data Transfer

Source Server         : Mysql_简易5.0版本
Source Server Version : 50024
Source Host           : localhost:3307
Source Database       : hxoms

Target Server Type    : MYSQL
Target Server Version : 50024
File Encoding         : 65001

Date: 2020-06-15 09:06:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for synchronization_data_log
-- ----------------------------
DROP TABLE IF EXISTS `synchronization_data_log`;
CREATE TABLE `synchronization_data_log` (
  `ID` varchar(36) NOT NULL,
  `OPERATION` varchar(255) default NULL,
  `METHOD` varchar(255) default NULL,
  `PARAMS` varchar(3900) default NULL,
  `TIME` bigint(20) NOT NULL,
  `IP` varchar(192) default NULL,
  `CREATE_DATE` timestamp NULL default NULL on update CURRENT_TIMESTAMP,
  PRIMARY KEY  (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;