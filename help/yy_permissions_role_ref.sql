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

 Date: 08/07/2020 22:04:21
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for yy_permissions_role_ref
-- ----------------------------
DROP TABLE IF EXISTS `yy_permissions_role_ref`;
CREATE TABLE `yy_permissions_role_ref` (
  `id` varchar(32) COLLATE utf8mb4_bin NOT NULL,
  `permissions_id` varchar(10) COLLATE utf8mb4_bin NOT NULL COMMENT '资源信息ID',
  `role_id` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '角色ID',
  `is_valid` varchar(1) COLLATE utf8mb4_bin NOT NULL COMMENT '是否有效',
  `create_by` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '修改人',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of yy_permissions_role_ref
-- ----------------------------
BEGIN;
INSERT INTO `yy_permissions_role_ref` VALUES ('1', '4', '1', 'Y', 'admin', '2020-07-06 19:46:04', 'admin', '2020-07-06 19:46:11');
INSERT INTO `yy_permissions_role_ref` VALUES ('10', '2', '1', 'Y', 'admin', '2020-07-07 22:18:31', 'admin', '2020-07-07 22:18:37');
INSERT INTO `yy_permissions_role_ref` VALUES ('11', '3', '1', 'Y', 'admin', '2020-07-07 22:18:31', 'admin', '2020-07-07 22:18:37');
INSERT INTO `yy_permissions_role_ref` VALUES ('12', '8', '2', 'Y', 'admin', '2020-07-07 22:18:31', 'admin', '2020-07-07 22:18:37');
INSERT INTO `yy_permissions_role_ref` VALUES ('13', '14', '2', 'Y', 'admin', '2020-07-07 22:18:31', 'admin', '2020-07-07 22:18:37');
INSERT INTO `yy_permissions_role_ref` VALUES ('14', '8', '3', 'Y', 'admin', '2020-07-07 22:18:31', 'admin', '2020-07-07 22:18:37');
INSERT INTO `yy_permissions_role_ref` VALUES ('15', '14', '3', 'Y', 'admin', '2020-07-07 22:18:31', 'admin', '2020-07-07 22:18:37');
INSERT INTO `yy_permissions_role_ref` VALUES ('16', '1', '2', 'Y', 'admin', '2020-07-08 16:56:09', 'admin', '2020-07-08 16:56:14');
INSERT INTO `yy_permissions_role_ref` VALUES ('17', '1', '3', 'Y', 'admin', '2020-07-08 16:57:01', 'admin', '2020-07-08 16:57:07');
INSERT INTO `yy_permissions_role_ref` VALUES ('2', '5', '1', 'Y', 'admin', '2020-07-06 19:51:51', 'admin', '2020-07-06 19:51:59');
INSERT INTO `yy_permissions_role_ref` VALUES ('3', '6', '1', 'Y', 'admin', '2020-07-07 21:58:03', 'admin', '2020-07-07 21:58:09');
INSERT INTO `yy_permissions_role_ref` VALUES ('4', '7', '1', 'Y', 'admin', '2020-07-07 21:58:03', 'admin', '2020-07-07 21:58:09');
INSERT INTO `yy_permissions_role_ref` VALUES ('5', '10', '2', 'Y', 'admin', '2020-07-07 21:58:03', 'admin', '2020-07-07 21:58:09');
INSERT INTO `yy_permissions_role_ref` VALUES ('6', '11', '2', 'Y', 'admin', '2020-07-07 21:58:03', 'admin', '2020-07-07 21:58:09');
INSERT INTO `yy_permissions_role_ref` VALUES ('7', '12', '3', 'Y', 'admin', '2020-07-07 21:58:03', 'admin', '2020-07-07 21:58:09');
INSERT INTO `yy_permissions_role_ref` VALUES ('8', '13', '3', 'Y', 'admin', '2020-07-07 21:58:03', 'admin', '2020-07-07 21:58:09');
INSERT INTO `yy_permissions_role_ref` VALUES ('9', '1', '1', 'Y', 'admin', '2020-07-07 22:18:31', 'admin', '2020-07-07 22:18:37');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
