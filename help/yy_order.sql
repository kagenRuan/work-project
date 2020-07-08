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

 Date: 06/07/2020 13:30:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for yy_order
-- ----------------------------
DROP TABLE IF EXISTS `yy_order`;
CREATE TABLE `yy_order` (
  `id` varchar(32) COLLATE utf8mb4_bin NOT NULL,
  `is_valid` varchar(1) COLLATE utf8mb4_bin NOT NULL COMMENT '是否有效',
  `create_by` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '修改人',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `order_sn` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '订单编号',
  `status` varchar(1) COLLATE utf8mb4_bin NOT NULL COMMENT '订单状态',
  `amount` decimal(12,2) NOT NULL COMMENT '订单金额',
  `shopId` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '店铺ID',
  `order_type` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '订单类型',
  `pay_sn` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '支付单号',
  `pay_status` varchar(1) COLLATE utf8mb4_bin NOT NULL COMMENT '支付状态',
  `buyer_id` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '卖家D',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

SET FOREIGN_KEY_CHECKS = 1;
