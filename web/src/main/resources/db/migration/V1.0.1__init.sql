DROP TABLE IF EXISTS `tbl_cp_access_type_new`;
CREATE TABLE `tbl_cp_access_type_new` (
  `acty_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `acty_name` varchar(20) DEFAULT NULL COMMENT '名称',
  `acty_code` varchar(20) DEFAULT NULL COMMENT '编码',
  `acty_reserved` tinyint(4) DEFAULT NULL COMMENT '是否可以修改：0可以，1不可以',
  PRIMARY KEY (`acty_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
