/*
 Navicat Premium Data Transfer

 Source Server         : 本地测试
 Source Server Type    : MySQL
 Source Server Version : 50729
 Source Host           : 127.0.0.1:3306
 Source Schema         : cloudproject

 Target Server Type    : MySQL
 Target Server Version : 50729
 File Encoding         : 65001

 Date: 06/07/2020 13:30:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for yy_power_etc
-- ----------------------------
DROP TABLE IF EXISTS `yy_power_etc`;
CREATE TABLE `yy_power_etc` (
  `id` varchar(32) COLLATE utf8mb4_bin NOT NULL,
  `message_id` varchar(10) COLLATE utf8mb4_bin NOT NULL COMMENT '消息ID',
  `is_valid` varchar(1) COLLATE utf8mb4_bin NOT NULL COMMENT '是否有效',
  `create_by` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '修改人',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

SET FOREIGN_KEY_CHECKS = 1;
