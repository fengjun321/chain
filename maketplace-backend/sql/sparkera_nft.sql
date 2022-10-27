/*
Navicat MySQL Data Transfer

Source Server         : 曼特
Source Server Version : 50735
Source Host           : 18.222.71.127:3306
Source Database       : sparkera_nft

Target Server Type    : MYSQL
Target Server Version : 50735
File Encoding         : 65001

Date: 2022-05-05 20:10:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ts_buy_sell_info
-- ----------------------------
DROP TABLE IF EXISTS `ts_buy_sell_info`;
CREATE TABLE `ts_buy_sell_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tokenId` bigint(20) DEFAULT NULL,
  `sell_address` varchar(60) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '卖出地址',
  `sell_price` decimal(20,6) DEFAULT NULL COMMENT '卖单价格',
  `sell_time` datetime DEFAULT NULL COMMENT '卖出挂单时间',
  `buy_address` varchar(60) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '买入地址',
  `tx_fee` decimal(12,6) DEFAULT NULL COMMENT '交易费',
  `trade_erc_address` varchar(60) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '交易币种合约地址',
  `hashId` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '交易hash',
  `buy_time` datetime DEFAULT NULL COMMENT '买入时间',
  `nft_type` tinyint(1) DEFAULT '0' COMMENT '0:FHC,1:HSE,2:PSE,3:EGSE',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=289 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for ts_favorite_info
-- ----------------------------
DROP TABLE IF EXISTS `ts_favorite_info`;
CREATE TABLE `ts_favorite_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `token_id` bigint(20) DEFAULT NULL,
  `user_address` varchar(60) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户地址',
  `create_time` datetime DEFAULT NULL COMMENT '收藏时间',
  `nft_type` tinyint(1) DEFAULT '0' COMMENT '0:FHC,1:HSE,2:PSE,3:EGSE',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `favorite_tid_addr_index` (`token_id`,`user_address`,`nft_type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for ts_nft_activity_logs
-- ----------------------------
DROP TABLE IF EXISTS `ts_nft_activity_logs`;
CREATE TABLE `ts_nft_activity_logs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tokenId` bigint(20) DEFAULT NULL,
  `fromAddress` varchar(60) COLLATE utf8mb4_bin DEFAULT NULL,
  `toAddress` varchar(60) COLLATE utf8mb4_bin DEFAULT NULL,
  `price` decimal(20,6) DEFAULT NULL COMMENT '价格',
  `tradeTime` timestamp NULL DEFAULT NULL COMMENT '成交时间',
  `tradeType` tinyint(1) DEFAULT NULL COMMENT '交易类型，0:c2c 1:平台回收',
  `nft_type` tinyint(1) DEFAULT '0' COMMENT '0:FHC,1:HSE,2:PSE,3:EGSE',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=275 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for ts_nft_ase_info
-- ----------------------------
DROP TABLE IF EXISTS `ts_nft_ase_info`;
CREATE TABLE `ts_nft_ase_info` (
  `token_id` bigint(20) NOT NULL,
  `owner_address` varchar(42) COLLATE utf8mb4_bin DEFAULT NULL,
  `data` int(11) DEFAULT NULL,
  `views` bigint(20) DEFAULT NULL,
  `favorites` bigint(20) DEFAULT NULL,
  `itemData` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  `tokenURI` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  `name` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  `image` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  `detail_image` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  `description` varchar(3000) COLLATE utf8mb4_bin DEFAULT NULL,
  `version` tinyint(4) DEFAULT NULL,
  `releaseVersion` tinyint(4) DEFAULT NULL,
  `arms_name` varchar(40) COLLATE utf8mb4_bin DEFAULT NULL,
  `rank` varchar(40) COLLATE utf8mb4_bin DEFAULT NULL,
  `power` int(11) DEFAULT NULL,
  `endurance` int(11) DEFAULT NULL,
  `strength` int(11) DEFAULT NULL,
  `agility` int(11) DEFAULT NULL,
  `armor` int(11) DEFAULT NULL,
  `crit` int(11) DEFAULT NULL,
  `pisonic` int(11) DEFAULT NULL,
  `spirit` int(11) DEFAULT NULL,
  `last_trade_price` decimal(20,6) DEFAULT NULL,
  `last_trade_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`token_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for ts_nft_card_info
-- ----------------------------
DROP TABLE IF EXISTS `ts_nft_card_info`;
CREATE TABLE `ts_nft_card_info` (
  `token_id` bigint(20) NOT NULL COMMENT 'nft id',
  `owner_address` varchar(42) COLLATE utf8mb4_bin NOT NULL COMMENT '所有人地址',
  `itemData` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'nft卡片属于原始值',
  `occupation` int(3) DEFAULT NULL COMMENT '职业',
  `strength` tinyint(3) DEFAULT NULL COMMENT '力量',
  `ts_level` int(3) DEFAULT NULL COMMENT '等级',
  `spirit` tinyint(3) DEFAULT NULL COMMENT '精神',
  `type` tinyint(3) DEFAULT NULL,
  `endurance` tinyint(3) DEFAULT NULL COMMENT '耐力',
  `psionic` tinyint(3) DEFAULT NULL COMMENT '靈能',
  `camp` tinyint(3) DEFAULT NULL COMMENT '阵营',
  `armor` tinyint(3) DEFAULT NULL COMMENT '护甲',
  `material` tinyint(3) DEFAULT NULL COMMENT '材质',
  `crit` tinyint(3) DEFAULT NULL COMMENT '暴擊',
  `identity` tinyint(3) DEFAULT NULL COMMENT '身份',
  `nameId` tinyint(3) DEFAULT NULL COMMENT '角色Id',
  `agility` tinyint(3) DEFAULT NULL COMMENT '敏捷',
  `rarity` tinyint(3) DEFAULT NULL COMMENT '稀有度',
  `power` smallint(3) DEFAULT '0' COMMENT '权力',
  `tokenURI` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  `version` tinyint(3) DEFAULT NULL,
  `releaseVersion` tinyint(3) DEFAULT NULL,
  `name` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'nft name',
  `image` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '图片地址',
  `description` varchar(3000) COLLATE utf8mb4_bin DEFAULT NULL,
  `attr_edition` varchar(40) COLLATE utf8mb4_bin DEFAULT NULL,
  `attr_camp` varchar(40) COLLATE utf8mb4_bin DEFAULT NULL,
  `attr_rank` varchar(40) COLLATE utf8mb4_bin DEFAULT NULL,
  `attr_material` varchar(40) COLLATE utf8mb4_bin DEFAULT NULL,
  `attr_name` varchar(40) COLLATE utf8mb4_bin DEFAULT NULL,
  `views` bigint(20) unsigned zerofill DEFAULT '00000000000000000000' COMMENT '查看数',
  `favorites` bigint(20) unsigned zerofill DEFAULT '00000000000000000000' COMMENT '收藏数量',
  `total_attrs` int(4) DEFAULT NULL COMMENT '所有属性值',
  `last_trade_price` decimal(20,6) unsigned zerofill DEFAULT NULL COMMENT '最后一次成交价',
  `last_trade_time` timestamp NULL DEFAULT NULL COMMENT '最近成交时间',
  `detail_image` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`token_id`) USING BTREE,
  UNIQUE KEY `INDEX_NFT_CARD_INFO_TOKEN_ID` (`token_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for ts_nft_egg_info
-- ----------------------------
DROP TABLE IF EXISTS `ts_nft_egg_info`;
CREATE TABLE `ts_nft_egg_info` (
  `token_id` bigint(20) NOT NULL,
  `owner_address` varchar(42) COLLATE utf8mb4_bin NOT NULL,
  `views` bigint(20) unsigned DEFAULT NULL,
  `favorites` bigint(20) DEFAULT NULL,
  `itemData` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  `tokenURI` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  `name` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  `image` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  `detail_image` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  `description` varchar(3000) COLLATE utf8mb4_bin DEFAULT NULL,
  `version` tinyint(4) DEFAULT NULL,
  `releaseVersion` tinyint(4) DEFAULT NULL,
  `type_id` int(11) DEFAULT NULL,
  `type` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL,
  `last_trade_price` decimal(20,6) DEFAULT NULL,
  `last_trade_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`token_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for ts_nft_egse_info
-- ----------------------------
DROP TABLE IF EXISTS `ts_nft_egse_info`;
CREATE TABLE `ts_nft_egse_info` (
  `token_id` bigint(20) NOT NULL COMMENT 'nft id',
  `owner_address` varchar(42) COLLATE utf8mb4_bin NOT NULL COMMENT '所有人地址',
  `itemData` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'nft卡片属于原始值',
  `type` tinyint(3) DEFAULT NULL,
  `tokenURI` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  `name` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'nft name',
  `image` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '图片地址',
  `description` varchar(3000) COLLATE utf8mb4_bin DEFAULT NULL,
  `attr_type` varchar(40) COLLATE utf8mb4_bin DEFAULT NULL,
  `views` bigint(20) unsigned zerofill DEFAULT '00000000000000000000' COMMENT '查看数',
  `favorites` bigint(20) unsigned zerofill DEFAULT '00000000000000000000' COMMENT '收藏数量',
  `last_trade_price` decimal(20,6) unsigned zerofill DEFAULT NULL COMMENT '最后一次成交价',
  `last_trade_time` timestamp NULL DEFAULT NULL COMMENT '最近成交时间',
  `detail_image` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`token_id`) USING BTREE,
  UNIQUE KEY `INDEX_NFT_EGSE_INFO_TOKEN_ID` (`token_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for ts_nft_hse_info
-- ----------------------------
DROP TABLE IF EXISTS `ts_nft_hse_info`;
CREATE TABLE `ts_nft_hse_info` (
  `token_id` bigint(20) NOT NULL COMMENT 'nft id',
  `owner_address` varchar(42) COLLATE utf8mb4_bin NOT NULL COMMENT '所有人地址',
  `tokenURI` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  `name` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'nft name',
  `image` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '图片地址',
  `description` varchar(3000) COLLATE utf8mb4_bin DEFAULT NULL,
  `attr_type` varchar(40) COLLATE utf8mb4_bin DEFAULT NULL,
  `views` bigint(20) unsigned zerofill DEFAULT '00000000000000000000' COMMENT '查看数',
  `favorites` bigint(20) unsigned zerofill DEFAULT '00000000000000000000' COMMENT '收藏数量',
  `last_trade_price` decimal(20,6) unsigned zerofill DEFAULT NULL COMMENT '最后一次成交价',
  `last_trade_time` timestamp NULL DEFAULT NULL COMMENT '最近成交时间',
  `detail_image` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`token_id`) USING BTREE,
  UNIQUE KEY `INDEX_NFT_HSE_INFO_TOKEN_ID` (`token_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for ts_nft_ise_info
-- ----------------------------
DROP TABLE IF EXISTS `ts_nft_ise_info`;
CREATE TABLE `ts_nft_ise_info` (
  `id` bigint(20) NOT NULL COMMENT '物品id',
  `owner_address` varchar(42) COLLATE utf8mb4_bin NOT NULL COMMENT '钱包地址',
  `views` bigint(20) unsigned DEFAULT NULL,
  `favorites` bigint(20) DEFAULT NULL,
  `itemData` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  `tokenURI` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  `name` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  `image` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  `detail_image` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  `description` varchar(3000) COLLATE utf8mb4_bin DEFAULT NULL,
  `data` int(11) DEFAULT NULL,
  `rank` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
  `cnt` int(11) DEFAULT NULL,
  `version` tinyint(4) DEFAULT NULL,
  `releaseVersion` tinyint(4) DEFAULT NULL,
  `last_trade_price` decimal(20,6) DEFAULT NULL,
  `last_trade_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`,`owner_address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for ts_nft_mbse_info
-- ----------------------------
DROP TABLE IF EXISTS `ts_nft_mbse_info`;
CREATE TABLE `ts_nft_mbse_info` (
  `token_id` bigint(20) NOT NULL,
  `owner_address` varchar(42) COLLATE utf8mb4_bin NOT NULL,
  `views` bigint(20) unsigned DEFAULT NULL,
  `favorites` bigint(20) DEFAULT NULL,
  `itemData` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  `tokenURI` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  `name` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  `image` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  `detail_image` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  `description` varchar(3000) COLLATE utf8mb4_bin DEFAULT NULL,
  `version` tinyint(3) DEFAULT NULL,
  `releaseVersion` tinyint(3) DEFAULT NULL,
  `type` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL,
  `last_trade_price` decimal(20,6) DEFAULT NULL,
  `last_trade_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`token_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for ts_nft_pse_info
-- ----------------------------
DROP TABLE IF EXISTS `ts_nft_pse_info`;
CREATE TABLE `ts_nft_pse_info` (
  `token_id` bigint(20) NOT NULL COMMENT 'nft id',
  `owner_address` varchar(42) COLLATE utf8mb4_bin NOT NULL COMMENT '所有人地址',
  `itemData` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'nft卡片属于原始值',
  `type` tinyint(3) DEFAULT NULL,
  `tokenURI` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  `name` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'nft name',
  `image` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '图片地址',
  `description` varchar(3000) COLLATE utf8mb4_bin DEFAULT NULL,
  `attr_type` varchar(40) COLLATE utf8mb4_bin DEFAULT NULL,
  `views` bigint(20) unsigned zerofill DEFAULT '00000000000000000000' COMMENT '查看数',
  `favorites` bigint(20) unsigned zerofill DEFAULT '00000000000000000000' COMMENT '收藏数量',
  `last_trade_price` decimal(20,6) unsigned zerofill DEFAULT NULL COMMENT '最后一次成交价',
  `last_trade_time` timestamp NULL DEFAULT NULL COMMENT '最近成交时间',
  `detail_image` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`token_id`) USING BTREE,
  UNIQUE KEY `INDEX_NFT_PSE_INFO_TOKEN_ID` (`token_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for ts_nft_ship_info
-- ----------------------------
DROP TABLE IF EXISTS `ts_nft_ship_info`;
CREATE TABLE `ts_nft_ship_info` (
  `token_id` bigint(20) NOT NULL COMMENT 'nft ID',
  `owner_address` varchar(42) COLLATE utf8mb4_bin NOT NULL COMMENT '所有人地址',
  `views` bigint(20) unsigned zerofill DEFAULT NULL COMMENT '预览次数',
  `favorites` bigint(20) DEFAULT NULL COMMENT '收藏数量',
  `itemData` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'nft卡片属于原始值',
  `type` tinyint(3) unsigned zerofill DEFAULT NULL,
  `tokenURI` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL,
  `version` tinyint(3) DEFAULT '0' COMMENT '版本',
  `releaseVersion` tinyint(3) DEFAULT '0' COMMENT '发行版本 0 - 标准 1 - 纪念',
  `name` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'nft 名字',
  `image` varchar(200) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'nft 图片',
  `model` varchar(1000) COLLATE utf8mb4_bin DEFAULT NULL,
  `description` varchar(3000) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'nft 卡片简介',
  `ship_name` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'nft 飞船名字',
  `rank` varchar(40) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'nft 稀有度 common - 普通  rarc - 稀有 epic - 史诗 legendary - 传说',
  `level` tinyint(3) DEFAULT NULL COMMENT 'nft 等级',
  `power` int(3) DEFAULT NULL COMMENT 'nft 基础战力',
  `basic_limit` tinyint(3) DEFAULT NULL COMMENT '飞船普通战士限制值',
  `basic_current` tinyint(3) DEFAULT NULL COMMENT '飞船普通战士当前值',
  `leader_limit` tinyint(3) DEFAULT NULL COMMENT '领袖限制值',
  `leader_current` tinyint(3) DEFAULT NULL COMMENT '领袖当前值',
  `scientist_limit` tinyint(3) DEFAULT NULL COMMENT '科学家限制值',
  `scientist_current` tinyint(3) DEFAULT NULL COMMENT '科学家当前值',
  `commander_limit` tinyint(3) DEFAULT NULL COMMENT '指挥官限制值',
  `commander_current` tinyint(3) DEFAULT NULL COMMENT '指挥官当前值',
  `start_soldier_limit` tinyint(3) DEFAULT NULL,
  `start_soldier_current` tinyint(3) DEFAULT NULL,
  `common_soldier_limit` tinyint(3) DEFAULT NULL COMMENT '普通战士限制值',
  `common_soldier_current` tinyint(3) DEFAULT NULL COMMENT '普通战士当前值',
  `last_trade_price` decimal(20,6) DEFAULT NULL COMMENT '上一个交易价格',
  `last_trade_time` timestamp NULL DEFAULT NULL COMMENT '上一个交易时间',
  PRIMARY KEY (`token_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for ts_selling_order
-- ----------------------------
DROP TABLE IF EXISTS `ts_selling_order`;
CREATE TABLE `ts_selling_order` (
  `tokenId` bigint(20) NOT NULL COMMENT 'tokenId',
  `ownerAddress` varchar(60) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户地址',
  `price` decimal(20,6) DEFAULT NULL COMMENT '价格',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `t_version` tinyint(3) DEFAULT NULL COMMENT '更新锁',
  `nft_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0:FHC,1:HSE,2:PSE,3:EGSE',
  PRIMARY KEY (`tokenId`,`nft_type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for ts_selling_order_mul
-- ----------------------------
DROP TABLE IF EXISTS `ts_selling_order_mul`;
CREATE TABLE `ts_selling_order_mul` (
  `good_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '商品id，某些情况可以当tokenId用',
  `id` int(11) NOT NULL COMMENT 'tokenId',
  `ownerAddress` varchar(60) COLLATE utf8mb4_bin NOT NULL COMMENT '用户地址',
  `price` decimal(20,6) DEFAULT NULL COMMENT '价格',
  `num` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `t_version` tinyint(3) DEFAULT NULL COMMENT '更新锁',
  `nft_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0:FHC,1:HSE,2:PSE,3:EGSE',
  PRIMARY KEY (`good_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for ts_sys_config
-- ----------------------------
DROP TABLE IF EXISTS `ts_sys_config`;
CREATE TABLE `ts_sys_config` (
  `id` bigint(255) NOT NULL AUTO_INCREMENT,
  `attr` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT '属性',
  `val` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '值',
  `desc` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `INDEX_SYS_CONFIG_ATTR` (`attr`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC;
-- ----------------------------
-- Records of ts_sys_config
-- ----------------------------
INSERT INTO `ts_sys_config` VALUES ('1', 'mint_last_block', '18607997', '最后扫描高度');
INSERT INTO `ts_sys_config` VALUES ('2', 'trade_erc20_address', '0x0f0Dd5E2c0e0c4A41F8908D73D36B8D142F6745a', '交易代币');
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