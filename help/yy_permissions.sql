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

 Date: 06/07/2020 13:30:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for yy_permissions
-- ----------------------------
DROP TABLE IF EXISTS `yy_permissions`;
CREATE TABLE `yy_permissions` (
  `id` varchar(32) COLLATE utf8mb4_bin NOT NULL,
  `name` varchar(10) COLLATE utf8mb4_bin NOT NULL COMMENT '资源信息名称',
  `url` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '资源请求URL',
  `parent_id` varchar(10) COLLATE utf8mb4_bin NOT NULL COMMENT '父级ID',
  `is_button` varchar(1) COLLATE utf8mb4_bin NOT NULL COMMENT '是否是按钮0为按钮1为菜单',
  `code` varchar(1) COLLATE utf8mb4_bin NOT NULL COMMENT '权限code',
  `is_valid` varchar(1) COLLATE utf8mb4_bin NOT NULL COMMENT '是否有效',
  `create_by` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '修改人',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

SET FOREIGN_KEY_CHECKS = 1;
