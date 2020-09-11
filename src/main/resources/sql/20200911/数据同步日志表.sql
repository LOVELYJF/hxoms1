/*
Navicat MySQL Data Transfer

Source Server         : Mysql_简易5.0版本
Source Server Version : 50024
Source Host           : localhost:3307
Source Database       : 525hxoim

Target Server Type    : MYSQL
Target Server Version : 50024
File Encoding         : 65001

Date: 2020-09-11 10:31:35
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for datasource
-- ----------------------------
DROP TABLE IF EXISTS `datasource`;
CREATE TABLE `datasource` (
  `datasource_id` varchar(255) default NULL,
  `url` varchar(255) default NULL,
  `user_name` varchar(255) default NULL,
  `pass_word` varchar(255) default NULL,
  `code` varchar(255) default NULL,
  `databasetype` varchar(255) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of datasource
-- ----------------------------
INSERT INTO `datasource` VALUES ('1', 'jdbc:mysql://127.0.0.1:3307/test2?allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8', 'root', '123456', '1', 'com.mysql.jdbc.Driver');
