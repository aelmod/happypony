SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for imgs
-- ----------------------------
DROP TABLE IF EXISTS `imgs`;
CREATE TABLE `imgs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pagination_id` int(11) DEFAULT NULL,
  `img_url` varchar(1024) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `retry_count` int(11) DEFAULT '3',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1198 DEFAULT CHARSET=latin1;
