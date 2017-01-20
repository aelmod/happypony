SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for pagination
-- ----------------------------
DROP TABLE IF EXISTS `pagination`;
CREATE TABLE `pagination` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task_id` int(11) DEFAULT NULL,
  `page_url` varchar(1024) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
