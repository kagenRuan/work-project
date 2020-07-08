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

 Date: 06/07/2020 13:30:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for yy_order_pay
-- ----------------------------
DROP TABLE IF EXISTS `yy_order_pay`;
CREATE TABLE `yy_order_pay` (
  `id` varchar(32) COLLATE utf8mb4_bin NOT NULL,
  `is_valid` varchar(1) COLLATE utf8mb4_bin NOT NULL COMMENT '是否有效',
  `create_by` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '修改人',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `three_sn` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '第三方支付单号',
  `pay_type` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '支付类型',
  `amount` decimal(12,2) NOT NULL COMMENT '支付金额',
  `status` varchar(1) COLLATE utf8mb4_bin NOT NULL COMMENT '支付状态',
  `pay_sn` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '支付单号',
  `buyer_id` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '卖家id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

SET FOREIGN_KEY_CHECKS = 1;
