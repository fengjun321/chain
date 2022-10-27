SET @@global.sql_mode ='STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
SET sql_mode ='STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
SET GLOBAL log_bin_trust_function_creators = 1;

INSERT INTO `ts_sys_config` VALUES ('26', 'unique_id', '0', '唯一id');
INSERT INTO `ts_sys_config` VALUES ('27', 'bank_read_hash_id', '0', 'bank表已经处理到的hash_id');
INSERT INTO `ts_sys_config` VALUES ('28', 'nft_to_game_id', '0', '资产到游戏唯一id');
INSERT INTO `ts_sys_config` VALUES ('29', 'nft_storage_read_id', '0', 'nft资产表处理到的hash_id');