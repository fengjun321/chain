/*
Navicat MySQL Data Transfer

Source Server         : fjwin
Source Server Version : 50737
Source Host           : localhost:3306
Source Database       : testdb_sparkera_nft

Target Server Type    : MYSQL
Target Server Version : 50737
File Encoding         : 65001

Date: 2022-08-12 12:24:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ts_sys_config
-- ----------------------------
DROP TABLE IF EXISTS `ts_sys_config`;
CREATE TABLE `ts_sys_config` (
  `id` bigint(255) NOT NULL AUTO_INCREMENT,
  `attr` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '属性',
  `val` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '值',
  `desc` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `INDEX_SYS_CONFIG_ATTR` (`attr`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of ts_sys_config
-- ----------------------------
INSERT INTO `ts_sys_config` VALUES ('1', 'mint_last_block', '21817136', '最后扫描高度');
INSERT INTO `ts_sys_config` VALUES ('2', 'trade_erc20_address', '0x337610d27c682E347C9cD60BD4b3b107C9d34dDd', '交易代币');
INSERT INTO `ts_sys_config` VALUES ('3', 'trade_erc20_decimals', '18', '交易代币小数位');
INSERT INTO `ts_sys_config` VALUES ('4', 'ctoc_fee', '0.03', 'c2c交易手续费率');
INSERT INTO `ts_sys_config` VALUES ('5', 'ctob_fee', '0.03', 'c2b交易手续费率(暂时没用)');
INSERT INTO `ts_sys_config` VALUES ('6', 'trade_private_key', '83ae70c1c865c1af940690dde1f9b15222141669ab13a433f8b727203fbd47e8', '交易合约owner私钥');
INSERT INTO `ts_sys_config` VALUES ('7', 'trade_gas_price', '10', 'gas_price');
INSERT INTO `ts_sys_config` VALUES ('8', 'trade_gas_limit', '1080420', 'gas_limit');
INSERT INTO `ts_sys_config` VALUES ('9', 'trade_recycle_address', '0x0d6315faf2c812f4607af2933dd3fe4bd743e9dd', 'nft平台回收地址');
INSERT INTO `ts_sys_config` VALUES ('10', 'recycle_rarity_0', '10', 'NFT稀有度Common回收价');
INSERT INTO `ts_sys_config` VALUES ('11', 'recycle_rarity_1', '11', 'NFT稀有度Rare回收价');
INSERT INTO `ts_sys_config` VALUES ('12', 'recycle_rarity_2', '12', 'NFT稀有度Epic回收价');
INSERT INTO `ts_sys_config` VALUES ('13', 'recycle_rarity_3', '13', 'NFT稀有度Legend回收价');
INSERT INTO `ts_sys_config` VALUES ('14', 'recycle_hse_price', '10', '盲盒回收价格');
INSERT INTO `ts_sys_config` VALUES ('15', 'recycle_pse_type_1', '11', 'PSE type1回收价格');
INSERT INTO `ts_sys_config` VALUES ('16', 'recycle_pse_type_2', '12', 'PSE type2回收价格');
INSERT INTO `ts_sys_config` VALUES ('17', 'recycle_pse_type_3', '13', 'PSE type3回收价格');
INSERT INTO `ts_sys_config` VALUES ('18', 'recycle_pse_type_4', '14', 'PSE type4回收价格');
INSERT INTO `ts_sys_config` VALUES ('19', 'recycle_pse_type_5', '15', 'PSE type5回收价格');
INSERT INTO `ts_sys_config` VALUES ('20', 'recycle_pse_type_6', '16', 'PSE type6回收价格');
INSERT INTO `ts_sys_config` VALUES ('21', 'recycle_pse_type_7', '17', 'PSE type7回收价格');
INSERT INTO `ts_sys_config` VALUES ('22', 'recycle_pse_type_8', '18', 'PSE type8回收价格');
INSERT INTO `ts_sys_config` VALUES ('23', 'recycle_egse_type_1', '11', 'EGSE type1回收价格');
INSERT INTO `ts_sys_config` VALUES ('24', 'recycle_egse_type_2', '12', 'EGSE type2回收价格');
INSERT INTO `ts_sys_config` VALUES ('25', 'recycle_egse_type_3', '13', 'EGSE type3回收价格');
INSERT INTO `ts_sys_config` VALUES ('26', 'unique_id', '133', '唯一id');
INSERT INTO `ts_sys_config` VALUES ('27', 'bank_read_hash_id', '132', 'bank表已经处理到的hash_id');
INSERT INTO `ts_sys_config` VALUES ('28', 'nft_to_game_id', '25', '资产到游戏唯一id');
INSERT INTO `ts_sys_config` VALUES ('29', 'nft_storage_read_id', '24', 'nft资产表处理到的hash_id');
INSERT INTO `ts_sys_config` VALUES ('30', 'bnb_rebate', '5:1,50:2,250:3,500:4', 'bnb返佣比例');
INSERT INTO `ts_sys_config` VALUES ('31', 'wbnb_rebate', '5:1,50:2,250:3,500:4', 'wbnb返佣比例');
INSERT INTO `ts_sys_config` VALUES ('32', 'fire_rebate', '10000:1,100000:2,500000:3,1000000:4', 'fire返佣比例');
INSERT INTO `ts_sys_config` VALUES ('33', 'quark_rebate', '100000:1,1000000:2,5000000:3,10000000:4', 'quark返佣比例');
INSERT INTO `ts_sys_config` VALUES ('34', 'usdt_rebate', '5:1,50:2,250:3,500:4', 'usdt返佣比例');
INSERT INTO `ts_sys_config` VALUES ('35', 'sign_private_key', '1402a46a3c6c4fb7a33058713a12c4af5897788deeb242295a3954235e8d83fa', '签名私钥');
