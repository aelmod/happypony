SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for download_tasks
-- ----------------------------
DROP TABLE IF EXISTS `download_tasks`;
CREATE TABLE `download_tasks` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
