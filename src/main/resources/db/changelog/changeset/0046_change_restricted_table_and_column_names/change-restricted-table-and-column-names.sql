RENAME TABLE IF EXISTS `session` TO `SESSION`;
RENAME TABLE IF EXISTS `user` TO `_USER`;
ALTER TABLE `session_data` CHANGE COLUMN `value` `value_` VARCHAR(255);