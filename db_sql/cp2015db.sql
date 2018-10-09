


--
-- Current Database: `cp2015db`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `cp2015db` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `cp2015db`;


SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tbl_cp_access_type
-- ----------------------------
DROP TABLE IF EXISTS `tbl_cp_access_type`;
CREATE TABLE `tbl_cp_access_type` (
  `acty_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `acty_name` varchar(20) DEFAULT NULL COMMENT '名称',
  `acty_code` varchar(20) DEFAULT NULL COMMENT '编码',
  `acty_reserved` tinyint(4) DEFAULT NULL COMMENT '是否可以修改：0可以，1不可以',
  PRIMARY KEY (`acty_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_cp_access_type
-- ----------------------------
BEGIN;
INSERT INTO `tbl_cp_access_type` VALUES (1, '新增', 'ADD', 1);
INSERT INTO `tbl_cp_access_type` VALUES (2, '修改', 'MODIFY', 1);
INSERT INTO `tbl_cp_access_type` VALUES (3, '删除', 'DELETE', 1);
INSERT INTO `tbl_cp_access_type` VALUES (4, '上传', 'UPLOAD', 0);
INSERT INTO `tbl_cp_access_type` VALUES (5, '导出', 'EXPORT', 0);
COMMIT;

-- ----------------------------
-- Table structure for tbl_cp_auth_access
-- ----------------------------
DROP TABLE IF EXISTS `tbl_cp_auth_access`;
CREATE TABLE `tbl_cp_auth_access` (
  `RAAY_ID` int(11) NOT NULL AUTO_INCREMENT,
  `ROLE_ID` int(11) DEFAULT NULL,
  `AUTH_ID` int(11) DEFAULT NULL,
  `ACTY_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`RAAY_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=109 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for tbl_cp_authority
-- ----------------------------
DROP TABLE IF EXISTS `tbl_cp_authority`;
CREATE TABLE `tbl_cp_authority` (
  `auth_id` int(11) NOT NULL AUTO_INCREMENT,
  `auth_name` varchar(255) NOT NULL COMMENT '权限名称',
  `auth_name_en` varchar(255) DEFAULT NULL COMMENT '权限英文名称',
  `auth_entrance` varchar(255) DEFAULT NULL COMMENT '权限访问url',
  `auth_description` varchar(255) DEFAULT NULL COMMENT '描述',
  `auth_date` datetime DEFAULT NULL COMMENT '创建日期',
  `auth_reserved` tinyint(4) DEFAULT NULL COMMENT '是否可以修改：0可以，1不可以',
  `auth_order` int(11) DEFAULT NULL COMMENT '排序',
  `auth_code` varchar(20) DEFAULT NULL COMMENT '访问权限代码前缀',
  PRIMARY KEY (`auth_id`),
  UNIQUE KEY `code_index` (`auth_code`) USING BTREE COMMENT '唯一索引'
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_cp_authority
-- ----------------------------
BEGIN;
INSERT INTO `tbl_cp_authority` VALUES (1, '功能权限配置', 'AuthorityConfig', '/auth/authority/list.do', '功能权限配置', '2015-01-16 09:21:50', 1, 1, 'AUTHORITY');
INSERT INTO `tbl_cp_authority` VALUES (2, '系统角色配置', 'RoleConfig', '/auth/role/list.do', '系统角色配置', '2015-01-16 09:22:34', 1, 2, 'ROLE');
INSERT INTO `tbl_cp_authority` VALUES (3, '用户角色配置', 'UserConfig', '/auth/user/list.do', '用户角色配置', '2015-01-16 09:22:50', 1, 3, 'USER');
INSERT INTO `tbl_cp_authority` VALUES (4, '缓存管理', 'CacheManage', '/cache/list.do', '产品线管理后台自身缓存管理功能', '2015-01-16 09:23:30', 1, 4, 'CACHE');
INSERT INTO `tbl_cp_authority` VALUES (5, '访问类型配置', 'Access Type Config', '/auth/accesstype/list.do', '访问类型配置', '2018-09-14 16:33:25', 1, 5, 'ACCESSTPTE');
INSERT INTO `tbl_cp_authority` VALUES (6, '审计日志', 'system log', '/auth/systemLogger/list.do', '审计日志', '2018-09-17 20:20:36', 1, 6, 'LOGGER');
INSERT INTO `tbl_cp_authority` VALUES (8, '文件管理', 'FileManage', '/elfinder/view.do', '文件管理', '2018-09-21 19:03:56', 0, 1, 'FILE');
INSERT INTO `tbl_cp_authority` VALUES (9, '共享文件', 'Share File', '/elfinder/shareview.do', '共享文件123', '2018-09-27 19:41:17', 0, 2, 'FILE_SHARE');
COMMIT;

-- ----------------------------
-- Table structure for tbl_cp_logger
-- ----------------------------
DROP TABLE IF EXISTS `tbl_cp_logger`;
CREATE TABLE `tbl_cp_logger` (
  `log_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `log_client_ip` varchar(30) DEFAULT NULL COMMENT '客户端ID',
  `log_uri` varchar(255) DEFAULT NULL COMMENT '请求url',
  `log_type` varchar(50) DEFAULT NULL COMMENT '终端请求方式,普通请求,ajax请求',
  `log_method` varchar(10) DEFAULT NULL COMMENT '请求方式method,post,get等',
  `log_param_data` longtext COMMENT '请求参数内容,json',
  `log_session_id` varchar(100) DEFAULT NULL COMMENT '请求接口唯一session标识',
  `log_time` datetime DEFAULT NULL COMMENT '请求时间',
  `log_returm_time` varchar(100) DEFAULT NULL COMMENT '接口返回时间',
  `log_return_data` longtext COMMENT '接口返回数据json',
  `log_http_status_code` varchar(10) DEFAULT NULL COMMENT '请求时httpStatusCode代码，如：200,400,404等',
  `log_time_consuming` int(10) DEFAULT NULL COMMENT '请求耗时秒单位',
  `log_desc` varchar(30) DEFAULT NULL COMMENT '操作内容',
  `log_user` varchar(50) DEFAULT NULL COMMENT '用户名称',
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3947 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for tbl_cp_role
-- ----------------------------
DROP TABLE IF EXISTS `tbl_cp_role`;
CREATE TABLE `tbl_cp_role` (
  `ROLE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `ROLE_NAME` varchar(255) DEFAULT NULL,
  `ROLE_NAME_EN` varchar(255) DEFAULT NULL,
  `ROLE_DESCRIPTION` varchar(255) DEFAULT NULL,
  `ROLE_DATE` datetime DEFAULT NULL,
  `ROLE_RESERVED` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_cp_role
-- ----------------------------
BEGIN;
INSERT INTO `tbl_cp_role` VALUES (1, '系统管理***', 'SystemManage***', '系统管理角色，系统的基础角色，不可修改和删除！', '2015-01-16 09:23:58', 1);
INSERT INTO `tbl_cp_role` VALUES (3, '文件管理', 'FileManage', '文件管理', '2018-09-21 18:55:49', 0);
INSERT INTO `tbl_cp_role` VALUES (5, '审计管理', 'Log Manage', '审计管理', '2018-10-09 15:07:15', 0);
COMMIT;

-- ----------------------------
-- Table structure for tbl_cp_roleauthority
-- ----------------------------
DROP TABLE IF EXISTS `tbl_cp_roleauthority`;
CREATE TABLE `tbl_cp_roleauthority` (
  `ROAU_ROLE_ID_FK` int(11) DEFAULT NULL,
  `ROAU_AUTH_ID_FK` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_cp_roleauthority
-- ----------------------------
BEGIN;
INSERT INTO `tbl_cp_roleauthority` VALUES (1, 1);
INSERT INTO `tbl_cp_roleauthority` VALUES (1, 2);
INSERT INTO `tbl_cp_roleauthority` VALUES (1, 3);
INSERT INTO `tbl_cp_roleauthority` VALUES (1, 4);
INSERT INTO `tbl_cp_roleauthority` VALUES (3, 8);
INSERT INTO `tbl_cp_roleauthority` VALUES (1, 5);
INSERT INTO `tbl_cp_roleauthority` VALUES (1, 6);
INSERT INTO `tbl_cp_roleauthority` VALUES (5, 6);
INSERT INTO `tbl_cp_roleauthority` VALUES (3, 9);
COMMIT;

-- ----------------------------
-- Table structure for tbl_cp_urlresource
-- ----------------------------
DROP TABLE IF EXISTS `tbl_cp_urlresource`;
CREATE TABLE `tbl_cp_urlresource` (
  `URRE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `URRE_AUTH_ID_FK` int(11) DEFAULT NULL,
  `URRE_URL_PATTERN` varchar(255) DEFAULT NULL,
  `URRE_DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`URRE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_cp_urlresource
-- ----------------------------
BEGIN;
INSERT INTO `tbl_cp_urlresource` VALUES (1, 1, '/auth/authority/**/*.do*', '2015-01-16 09:24:56');
INSERT INTO `tbl_cp_urlresource` VALUES (2, 2, '/auth/role/**/*.do*', '2015-01-16 09:25:06');
INSERT INTO `tbl_cp_urlresource` VALUES (3, 3, '/auth/user/**/*.do*', '2015-01-16 09:25:13');
INSERT INTO `tbl_cp_urlresource` VALUES (4, 4, '/cache/**/*.do*', '2015-01-16 09:25:24');
INSERT INTO `tbl_cp_urlresource` VALUES (5, 5, '/auth/accesstype/**/*.do*', NULL);
INSERT INTO `tbl_cp_urlresource` VALUES (13, 6, '/auth/systemLogger/**/*.do*', NULL);
INSERT INTO `tbl_cp_urlresource` VALUES (20, 8, '/elfinder/**/*.do*', NULL);
INSERT INTO `tbl_cp_urlresource` VALUES (33, 9, '/edit/**/*.do/*', NULL);
INSERT INTO `tbl_cp_urlresource` VALUES (34, 9, '/elfinder/**/*.do*', NULL);
INSERT INTO `tbl_cp_urlresource` VALUES (35, 9, '/demo/**/*.do*', NULL);
COMMIT;

-- ----------------------------
-- Table structure for tbl_cp_user
-- ----------------------------
DROP TABLE IF EXISTS `tbl_cp_user`;
CREATE TABLE `tbl_cp_user` (
  `USER_ID` varchar(255) NOT NULL,
  `USER_PASSWORD` varchar(255) DEFAULT NULL,
  `USER_NAME` varchar(255) DEFAULT NULL,
  `USER_EMAIL` varchar(255) DEFAULT NULL,
  `USER_PHNUM` varchar(255) DEFAULT NULL,
  `USER_MOBILE` varchar(255) DEFAULT NULL,
  `USER_ADDRESS` varchar(255) DEFAULT NULL,
  `USER_STATUS` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_cp_user
-- ----------------------------
BEGIN;
INSERT INTO `tbl_cp_user` VALUES ('admin', '$2a$10$JHj.XB.5RtpY60JEuTTGjuIT4m.hYT1yWoevJ6inU6Q7JE1qcvTYC', '管理员', 'admin@admin.com', '', '', NULL, 1);
INSERT INTO `tbl_cp_user` VALUES ('test', '$2a$10$X6FfwoOJ5E7ahQlU6kNxFue0KrH8rAKu51hWhwPbhaNzLQatOi..u', '测试用户', 'test@mm.com', '', '', NULL, 1);
COMMIT;

-- ----------------------------
-- Table structure for tbl_cp_userrole
-- ----------------------------
DROP TABLE IF EXISTS `tbl_cp_userrole`;
CREATE TABLE `tbl_cp_userrole` (
  `USRO_USERID_FK` varchar(255) DEFAULT NULL,
  `USRO_ROLEID_FK` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_cp_userrole
-- ----------------------------
BEGIN;
INSERT INTO `tbl_cp_userrole` VALUES ('test', 3);
INSERT INTO `tbl_cp_userrole` VALUES ('test', 5);
INSERT INTO `tbl_cp_userrole` VALUES ('admin', 1);
INSERT INTO `tbl_cp_userrole` VALUES ('admin', 3);
INSERT INTO `tbl_cp_userrole` VALUES ('admin', 5);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
