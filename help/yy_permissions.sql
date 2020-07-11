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

 Date: 10/07/2020 18:45:32
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
  `url` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '资源请求URL',
  `parent_id` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '父级ID',
  `is_button` varchar(1) COLLATE utf8mb4_bin NOT NULL COMMENT '是否是按钮0为按钮1为菜单',
  `code` varchar(25) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '权限code',
  `image` varchar(150) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '资源图片',
  `target` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '点击时间',
  `is_valid` varchar(1) COLLATE utf8mb4_bin NOT NULL COMMENT '是否有效',
  `create_by` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) COLLATE utf8mb4_bin NOT NULL COMMENT '修改人',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of yy_permissions
-- ----------------------------
BEGIN;
INSERT INTO `yy_permissions` VALUES ('07e020d2aec9ae35fcddc1d73f47a427', '查询用户', NULL, '0bad162ace584f8e8761a914d28678db', '0', 'user:list', NULL, NULL, 'Y', 'system', '2020-07-10 06:25:32', 'system', '2020-07-10 06:25:32');
INSERT INTO `yy_permissions` VALUES ('0bad162ace584f8e8761a914d28678db', '用户列表', 'page/user.html', '572d2f490f4cd810c18231faec849c76', '1', '', NULL, NULL, 'Y', 'system', '2020-07-10 06:34:50', 'system', '2020-07-10 06:34:50');
INSERT INTO `yy_permissions` VALUES ('1', '常规管理', NULL, '0', '1', NULL, NULL, '_self', 'Y', 'admin', '2020-07-07 21:44:29', 'admin', '2020-07-07 21:44:34');
INSERT INTO `yy_permissions` VALUES ('10', '查询角色', NULL, '14', '0', 'role:list', NULL, '_self', 'Y', 'admin', '2020-07-07 21:53:53', 'admin', '2020-07-07 21:53:57');
INSERT INTO `yy_permissions` VALUES ('11', '添加角色', NULL, '14', '0', 'role:add', NULL, '_self', 'Y', 'admin', '2020-07-07 21:54:30', 'admin', '2020-07-07 21:54:35');
INSERT INTO `yy_permissions` VALUES ('12', '删除角色', NULL, '9', '0', 'role:delete', NULL, '_self', 'Y', 'admin', '2020-07-07 21:55:04', 'admin', '2020-07-07 21:55:08');
INSERT INTO `yy_permissions` VALUES ('13', '修改角色', NULL, '9', '0', 'role:update', NULL, '_self', 'Y', 'admin', '2020-07-07 21:55:40', 'admin', '2020-07-07 21:55:47');
INSERT INTO `yy_permissions` VALUES ('133024ee73bc06656a1110785a3dc79e', '修改用户', NULL, '0bad162ace584f8e8761a914d28678db', '0', 'user:update', NULL, NULL, 'Y', 'system', '2020-07-10 06:25:43', 'system', '2020-07-10 06:25:43');
INSERT INTO `yy_permissions` VALUES ('14', '角色列表', 'page/role.html', '8', '1', '', NULL, '_self', 'Y', 'admin', '2020-07-10 06:35:36', 'system', '2020-07-10 06:35:36');
INSERT INTO `yy_permissions` VALUES ('2', '资源管理', NULL, '1', '1', NULL, NULL, '_self', 'Y', 'admin', '2020-07-07 21:45:22', 'admin', '2020-07-07 21:45:26');
INSERT INTO `yy_permissions` VALUES ('3', '资源列表', 'page/menu.html', '2', '1', NULL, NULL, '_self', 'Y', 'admin', '2020-07-07 21:46:15', 'admin', '2020-07-07 21:46:22');
INSERT INTO `yy_permissions` VALUES ('4', '查询资源', NULL, '3', '0', 'perm:list', NULL, '_self', 'Y', 'admin', '2020-07-07 21:48:04', 'admin', '2020-07-07 21:48:10');
INSERT INTO `yy_permissions` VALUES ('5', '添加资源', NULL, '3', '0', 'perm:add', NULL, '_self', 'Y', 'admin', '2020-07-07 21:49:02', 'admin', '2020-07-07 21:49:13');
INSERT INTO `yy_permissions` VALUES ('572d2f490f4cd810c18231faec849c76', '用户管理', NULL, '1', '1', NULL, NULL, NULL, 'Y', 'system', '2020-07-10 06:21:10', 'system', '2020-07-10 06:21:10');
INSERT INTO `yy_permissions` VALUES ('6', '删除资源', NULL, '3', '0', 'perm:delete', NULL, '_self', 'Y', 'admin', '2020-07-07 21:49:42', 'admin', '2020-07-07 21:49:46');
INSERT INTO `yy_permissions` VALUES ('7', '修改资源', NULL, '3', '0', 'perm:update', NULL, '_self', 'Y', 'admin', '2020-07-07 21:50:52', 'admin', '2020-07-07 21:50:59');
INSERT INTO `yy_permissions` VALUES ('70bc2fefab0d91421128cae51bf52f0f', '添加用户', NULL, '0bad162ace584f8e8761a914d28678db', '0', 'user:add', NULL, NULL, 'Y', 'system', '2020-07-10 06:25:54', 'system', '2020-07-10 06:25:54');
INSERT INTO `yy_permissions` VALUES ('8', '角色管理', NULL, '1', '1', '', NULL, '_self', 'Y', 'admin', '2020-07-07 21:51:49', 'admin', '2020-07-07 21:51:55');
INSERT INTO `yy_permissions` VALUES ('929feceb27612eba67d974d3ae53b191', '删除用户', NULL, '0bad162ace584f8e8761a914d28678db', '0', 'user:delete', NULL, NULL, 'Y', 'system', '2020-07-10 06:26:02', 'system', '2020-07-10 06:26:02');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
