/*
Navicat MySQL Data Transfer

Source Server         : fjwin
Source Server Version : 50737
Source Host           : localhost:3306
Source Database       : sparkera_nft

Target Server Type    : MYSQL
Target Server Version : 50737
File Encoding         : 65001

Date: 2022-11-24 16:42:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ts_bank
-- ----------------------------
DROP TABLE IF EXISTS `ts_bank`;
CREATE TABLE `ts_bank` (
  `hash_id` bigint(20) NOT NULL,
  `erc20_addr` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `amount` decimal(20,6) DEFAULT NULL,
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `flag` int(255) DEFAULT '0' COMMENT '0:在途\r\n1:成功\r\n2:失败',
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`hash_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of ts_bank
-- ----------------------------

-- ----------------------------
-- Table structure for ts_buy_sell_info
-- ----------------------------
DROP TABLE IF EXISTS `ts_buy_sell_info`;
CREATE TABLE `ts_buy_sell_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tokenId` bigint(20) DEFAULT NULL,
  `ext_id` bigint(20) DEFAULT '0' COMMENT '额外id字段，可以使用，也可以不用，为差异性扩展新增',
  `sell_address` varchar(60) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '卖出地址',
  `sell_price` decimal(20,6) DEFAULT NULL COMMENT '卖单价格',
  `sell_time` datetime DEFAULT NULL COMMENT '卖出挂单时间',
  `buy_address` varchar(60) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '买入地址',
  `tx_fee` decimal(12,6) DEFAULT NULL COMMENT '交易费',
  `trade_erc_address` varchar(60) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '交易币种合约地址',
  `hashId` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '交易hash',
  `buy_time` datetime DEFAULT NULL COMMENT '买入时间',
  `nft_type` tinyint(1) DEFAULT '0' COMMENT '0:FHC,1:HSE,2:PSE,3:EGSE',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of ts_buy_sell_info
-- ----------------------------

-- ----------------------------
-- Table structure for ts_equip_to_game
-- ----------------------------
DROP TABLE IF EXISTS `ts_equip_to_game`;
CREATE TABLE `ts_equip_to_game` (
  `id` bigint(8) NOT NULL AUTO_INCREMENT,
  `nft_type` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `item_id` int(8) DEFAULT NULL,
  `owner_address` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `action` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `changedate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of ts_equip_to_game
-- ----------------------------

-- ----------------------------
-- Table structure for ts_favorite_info
-- ----------------------------
DROP TABLE IF EXISTS `ts_favorite_info`;
CREATE TABLE `ts_favorite_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `token_id` bigint(20) DEFAULT NULL,
  `ext_id` bigint(20) DEFAULT '0' COMMENT '扩展信息字段',
  `user_address` varchar(60) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户地址',
  `create_time` datetime DEFAULT NULL COMMENT '收藏时间',
  `nft_type` tinyint(1) DEFAULT '0' COMMENT '0:FHC,1:HSE,2:PSE,3:EGSE',
  PRIMARY KEY (`id`),
  UNIQUE KEY `favorite_tid_addr_index` (`token_id`,`user_address`,`nft_type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of ts_favorite_info
-- ----------------------------

-- ----------------------------
-- Table structure for ts_invite
-- ----------------------------
DROP TABLE IF EXISTS `ts_invite`;
CREATE TABLE `ts_invite` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of ts_invite
-- ----------------------------

-- ----------------------------
-- Table structure for ts_invite_rank
-- ----------------------------
DROP TABLE IF EXISTS `ts_invite_rank`;
CREATE TABLE `ts_invite_rank` (
  `UserId` int(11) NOT NULL COMMENT '账户id',
  `UserName` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT '账号名称',
  `InvitationNum` int(11) NOT NULL COMMENT '邀请人数',
  `LastInvitationTime` datetime(6) NOT NULL COMMENT '上一个邀请人注册时间',
  PRIMARY KEY (`UserId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of ts_invite_rank
-- ----------------------------

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of ts_nft_activity_logs
-- ----------------------------

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
-- Records of ts_nft_ase_info
-- ----------------------------

-- ----------------------------
-- Table structure for ts_nft_bank
-- ----------------------------
DROP TABLE IF EXISTS `ts_nft_bank`;
CREATE TABLE `ts_nft_bank` (
  `hash_id` bigint(20) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `nft_type` int(255) DEFAULT NULL,
  `flag` int(255) DEFAULT '0' COMMENT '0:在途1:成功2:失败',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`hash_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of ts_nft_bank
-- ----------------------------

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
  PRIMARY KEY (`token_id`),
  UNIQUE KEY `INDEX_NFT_CARD_INFO_TOKEN_ID` (`token_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of ts_nft_card_info
-- ----------------------------

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
-- Records of ts_nft_egg_info
-- ----------------------------

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
  PRIMARY KEY (`token_id`),
  UNIQUE KEY `INDEX_NFT_EGSE_INFO_TOKEN_ID` (`token_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of ts_nft_egse_info
-- ----------------------------

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
  PRIMARY KEY (`token_id`),
  UNIQUE KEY `INDEX_NFT_HSE_INFO_TOKEN_ID` (`token_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of ts_nft_hse_info
-- ----------------------------

-- ----------------------------
-- Table structure for ts_nft_ise_info
-- ----------------------------
DROP TABLE IF EXISTS `ts_nft_ise_info`;
CREATE TABLE `ts_nft_ise_info` (
  `tbId` bigint(20) NOT NULL AUTO_INCREMENT,
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
  PRIMARY KEY (`tbId`),
  UNIQUE KEY `attr1` (`id`,`owner_address`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of ts_nft_ise_info
-- ----------------------------

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
-- Records of ts_nft_mbse_info
-- ----------------------------

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
  PRIMARY KEY (`token_id`),
  UNIQUE KEY `INDEX_NFT_PSE_INFO_TOKEN_ID` (`token_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of ts_nft_pse_info
-- ----------------------------

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
-- Records of ts_nft_ship_info
-- ----------------------------

-- ----------------------------
-- Table structure for ts_nft_trade_info_mul
-- ----------------------------
DROP TABLE IF EXISTS `ts_nft_trade_info_mul`;
CREATE TABLE `ts_nft_trade_info_mul` (
  `unique_id` bigint(20) DEFAULT NULL COMMENT '自定义标识，用于扩展',
  `ext_id` bigint(20) DEFAULT NULL COMMENT '自定义标识，用于扩展',
  `cnt` int(255) DEFAULT NULL,
  `owner_address` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '归属者地址',
  `last_trade_price` decimal(20,6) DEFAULT NULL COMMENT '成交价',
  `last_trade_time` timestamp NULL DEFAULT NULL,
  `image` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '图片',
  `nft_type` tinyint(255) DEFAULT NULL COMMENT '资产类型'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of ts_nft_trade_info_mul
-- ----------------------------

-- ----------------------------
-- Table structure for ts_rebate_info
-- ----------------------------
DROP TABLE IF EXISTS `ts_rebate_info`;
CREATE TABLE `ts_rebate_info` (
  `erc20_addr` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `amount` decimal(20,6) DEFAULT NULL,
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `use_amount` decimal(20,6) DEFAULT '0.000000',
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`erc20_addr`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of ts_rebate_info
-- ----------------------------

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
  PRIMARY KEY (`tokenId`,`nft_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of ts_selling_order
-- ----------------------------

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of ts_selling_order_mul
-- ----------------------------

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
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of ts_sys_config
-- ----------------------------
INSERT INTO `ts_sys_config` VALUES ('1', 'mint_last_block', '4000', '最后扫描高度');
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
INSERT INTO `ts_sys_config` VALUES ('26', 'unique_id', '0', '唯一id');
INSERT INTO `ts_sys_config` VALUES ('27', 'bank_read_hash_id', '0', 'bank表已经处理到的hash_id');
INSERT INTO `ts_sys_config` VALUES ('28', 'nft_to_game_id', '0', '资产到游戏唯一id');
INSERT INTO `ts_sys_config` VALUES ('29', 'nft_storage_read_id', '0', 'nft资产表处理到的hash_id');

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `UserID` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `UserName` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `PasssWord` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `BanFlag` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `BanTime` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `BanReason` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `RegisterTime` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `Gm` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `LastLogoutTime` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `WalletAddress` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `WalletAddressBindTime` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `RefferAddr` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `InvitationNum` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of user_info
-- ----------------------------

-- ----------------------------
-- Procedure structure for repair_invite_table_and_data
-- ----------------------------
DROP PROCEDURE IF EXISTS `repair_invite_table_and_data`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `repair_invite_table_and_data`()
BEGIN
	#Routine body goes here...
	#修复ts_rebate_info表
	IF NOT EXISTS(SELECT 1 FROM information_schema.columns
		WHERE table_schema='sparkera_nft' AND table_name='ts_rebate_info'
		AND column_name='user_id' ) THEN
		alter table sparkera_nft.ts_rebate_info drop primary key;
		delete from sparkera_nft.ts_rebate_info where email_addr = "";
		alter table sparkera_nft.ts_rebate_info add user_id bigint not Null;
		update sparkera_nft.ts_rebate_info a, game.user_info b set a.user_id = b.UserID where a.email_addr = b.UserName;
		alter table sparkera_nft.ts_rebate_info drop email_addr;
		alter table sparkera_nft.ts_rebate_info add primary key(user_id, erc20_addr);
	END IF;
	
	#修复game user_info表
	IF NOT EXISTS(SELECT 1 FROM information_schema.columns
		WHERE table_schema='game' AND table_name='user_info'
		AND column_name='RefferId' ) THEN
		alter table game.user_info add RefferId bigint;
		update game.user_info a, game.user_info b set a.RefferId = b.UserID where a.RefferAddr = b.UserName and b.UserName != "";
		alter table game.user_info drop RefferAddr;
	END IF;

	#修复bank表
	IF NOT EXISTS(SELECT 1 FROM information_schema.columns
		WHERE table_schema='sparkera_nft' AND table_name='ts_bank'
		AND column_name='user_id' ) THEN
		alter table sparkera_nft.ts_bank add user_id bigint not Null;
		update sparkera_nft.ts_bank a, game.user_info b set a.user_id = b.UserID where a.email_addr = b.UserName;
		alter table sparkera_nft.ts_bank drop email_addr;
	END IF;

	#修复邀请码表
	IF NOT EXISTS(SELECT 1 FROM information_schema.columns
		WHERE table_schema='sparkera_nft' AND table_name='ts_invite'
		AND column_name='user_id' ) THEN
		alter table sparkera_nft.ts_invite add user_id bigint not Null;
		update sparkera_nft.ts_invite a, game.user_info b set a.user_id = b.UserID where a.email_addr = b.UserName;
		delete from ts_invite where user_id = 0;
	END IF;
	alter table sparkera_nft.ts_invite drop email_addr;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for repair_invite_table_and_data2
-- ----------------------------
DROP PROCEDURE IF EXISTS `repair_invite_table_and_data2`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `repair_invite_table_and_data2`()
BEGIN
	#Routine body goes here...
	#修复ts_rebate_info表
	IF NOT EXISTS(SELECT 1 FROM information_schema.columns
		WHERE table_schema='testdb_sparkera_nft' AND table_name='ts_rebate_info'
		AND column_name='user_id' ) THEN
		alter table testdb_sparkera_nft.ts_rebate_info drop primary key;
		delete from testdb_sparkera_nft.ts_rebate_info where email_addr = "";
		alter table testdb_sparkera_nft.ts_rebate_info add user_id bigint not Null;
		update testdb_sparkera_nft.ts_rebate_info a, game.user_info b set a.user_id = b.UserID where a.email_addr = b.UserName;
		alter table testdb_sparkera_nft.ts_rebate_info drop email_addr;
		alter table testdb_sparkera_nft.ts_rebate_info add primary key(user_id, erc20_addr);
	END IF;
	
	#修复game user_info表
	IF NOT EXISTS(SELECT 1 FROM information_schema.columns
		WHERE table_schema='game' AND table_name='user_info'
		AND column_name='RefferId' ) THEN
		alter table game.user_info add RefferId bigint;
		update game.user_info a, game.user_info b set a.RefferId = b.UserID where a.RefferAddr = b.UserName and b.UserName != "";
		alter table game.user_info drop RefferAddr;
	END IF;

	#修复bank表
	IF NOT EXISTS(SELECT 1 FROM information_schema.columns
		WHERE table_schema='testdb_sparkera_nft' AND table_name='ts_bank'
		AND column_name='user_id' ) THEN
		alter table testdb_sparkera_nft.ts_bank add user_id bigint not Null;
		update testdb_sparkera_nft.ts_bank a, game.user_info b set a.user_id = b.UserID where a.email_addr = b.UserName;
		alter table testdb_sparkera_nft.ts_bank drop email_addr;
	END IF;

	#修复邀请码表
	IF NOT EXISTS(SELECT 1 FROM information_schema.columns
		WHERE table_schema='testdb_sparkera_nft' AND table_name='ts_invite'
		AND column_name='user_id' ) THEN
		alter table testdb_sparkera_nft.ts_invite add user_id bigint not Null;
		update testdb_sparkera_nft.ts_invite a, game.user_info b set a.user_id = b.UserID where a.email_addr = b.UserName;
		delete from ts_invite where user_id = 0;
	END IF;
	alter table testdb_sparkera_nft.ts_invite drop email_addr;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for repair_old_ship_data
-- ----------------------------
DROP PROCEDURE IF EXISTS `repair_old_ship_data`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `repair_old_ship_data`()
BEGIN
	#Routine body goes here...
	declare done int default -1;
	declare tmp_owner_address varchar(100) default "";
	declare tmp_ship_name varchar(100) default "";
	declare myCursor cursor for select owner_address, ship_name from ts_nft_ship_info;
	declare continue handler for not found set done = 1;

	delete from ts_equip_to_game;
	open myCursor;
	myLoop: LOOP
		/* 移动游标并赋值 */
		fetch myCursor into tmp_owner_address, tmp_ship_name;
		if done = 1 then
			leave myLoop;
		end if;
		
		set @itemid = 0;
		set @ship_name = tmp_ship_name;
		if @ship_name = "Eternal" then
			set @itemid = 201001001;
		elseif @ship_name = "Photon" then
		 set @itemid = 201001002;
		elseif @ship_name = "Marauders" then
		 set @itemid = 201001003;
		elseif @ship_name = "Aurora" then
		 set @itemid = 201001004;
		elseif @ship_name = "Galaxy" then
		 set @itemid = 201002001;
		elseif @ship_name = "Enlightenment_Limited" then
		 set @itemid = 201002002;
		elseif @ship_name = "Daylight" then
		 set @itemid = 201002003;
		elseif @ship_name = "Enlightenment" then
		 set @itemid = 201002004;
		elseif @ship_name = "Trailblazer" then
		 set @itemid = 201003001;
		elseif @ship_name = "Star" then
		 set @itemid = 201003002;
		elseif @ship_name = "Megalith" then
		 set @itemid = 201003003;
		elseif @ship_name = "Gemini-Elite" then
		 set @itemid = 201003004;
		elseif @ship_name = "Colossus" then
		 set @itemid = 201004001;
		elseif @ship_name = "Gemini-Warrior" then
		 set @itemid = 201004002;
		elseif @ship_name = "Neptune" then
		 set @itemid = 201004003;
		elseif @ship_name = "Gemini-Light" then
		 set @itemid = 201004004;
		elseif @ship_name = "Type-Mercenary" then
		 set @itemid = 201004005;
		elseif @ship_name = "Type-Silverstar" then
		 set @itemid = 201004006;
		elseif @ship_name = "Type-Orion" then
		 set @itemid = 201004007;
		end if;
		
		INSERT INTO ts_equip_to_game
		SET action = 'insert',
								nft_type = "ship",
		item_id = @itemid,
		owner_address = tmp_owner_address,
		name = @ship_name,
								changedate = now();

	end loop myLoop;
	close myCursor;
END
;;
DELIMITER ;
