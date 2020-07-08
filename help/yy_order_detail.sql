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

 Date: 06/07/2020 13:30:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for yy_order_detail
-- ----------------------------
DROP TABLE IF EXISTS `yy_order_detail`;
CREATE TABLE `yy_order_detail` (
  `id` varchar(32) COLLATE utf8mb4_bin NOT NULL,
  `id_product` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '商品ID',
  `order_sn` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '订单流水号',
  `name` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '商品名称',
  `num` bigint(5) NOT NULL COMMENT '商品数量',
  `price` decimal(10,2) NOT NULL COMMENT '商品价格',
  `total_price` decimal(10,2) NOT NULL COMMENT '商品总价格',
  `is_valid` varchar(1) COLLATE utf8mb4_bin NOT NULL COMMENT '是否有效',
  `create_by` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '修改人',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

SET FOREIGN_KEY_CHECKS = 1;
