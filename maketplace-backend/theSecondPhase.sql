ALTER TABLE `ts_nft_card_info`
    ADD COLUMN `power` smallint NULL DEFAULT 0 COMMENT '算力' AFTER `rarity`;
ALTER TABLE `ts_nft_card_info`
    MODIFY COLUMN `views` bigint(20) UNSIGNED ZEROFILL NULL DEFAULT 0 COMMENT '查看数' AFTER `attr_name`,
    MODIFY COLUMN `favorites` bigint(20) UNSIGNED ZEROFILL NULL DEFAULT 0 COMMENT '收藏数量' AFTER `views`;
ALTER TABLE `ts_buy_sell_info`
    ADD COLUMN `nft_type` tinyint(1) NULL DEFAULT 0 COMMENT '0:FHC,1:HSE,2:PSE,3:EGSE' AFTER `buy_time`;

ALTER TABLE `ts_favorite_info`
    ADD COLUMN `nft_type` tinyint(1) NULL DEFAULT 0 COMMENT '0:FHC,1:HSE,2:PSE,3:EGSE' AFTER `create_time`;
ALTER TABLE `ts_favorite_info`
DROP INDEX `favorite_tid_addr_index`,
ADD UNIQUE INDEX `favorite_tid_addr_index`(`token_id`, `user_address`, `nft_type`) USING BTREE;

ALTER TABLE `ts_nft_activity_logs`
    ADD COLUMN `nft_type` tinyint(1) NULL DEFAULT 0 COMMENT '0:FHC,1:HSE,2:PSE,3:EGSE' AFTER `tradeType`;

ALTER TABLE `ts_selling_order`
    ADD COLUMN `nft_type` tinyint(1) NOT NULL DEFAULT 0 COMMENT '0:FHC,1:HSE,2:PSE,3:EGSE' AFTER `t_version`,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`tokenId`, `nft_type`) USING BTREE;

INSERT INTO `ts_sys_config`(`attr`, `val`, `desc`) VALUES ('recycle_hse_price', '10', '盲盒回收价格');
INSERT INTO `ts_sys_config`(`attr`, `val`, `desc`) VALUES ('recycle_pse_type_1', '11', 'PSE type1回收价格');
INSERT INTO `ts_sys_config`(`attr`, `val`, `desc`) VALUES ('recycle_pse_type_2', '12', 'PSE type2回收价格');
INSERT INTO `ts_sys_config`(`attr`, `val`, `desc`) VALUES ('recycle_pse_type_3', '13', 'PSE type3回收价格');
INSERT INTO `ts_sys_config`(`attr`, `val`, `desc`) VALUES ('recycle_pse_type_4', '14', 'PSE type4回收价格');
INSERT INTO `ts_sys_config`(`attr`, `val`, `desc`) VALUES ('recycle_pse_type_5', '15', 'PSE type5回收价格');
INSERT INTO `ts_sys_config`(`attr`, `val`, `desc`) VALUES ('recycle_pse_type_6', '16', 'PSE type6回收价格');
INSERT INTO `ts_sys_config`(`attr`, `val`, `desc`) VALUES ('recycle_pse_type_7', '17', 'PSE type7回收价格');
INSERT INTO `ts_sys_config`(`attr`, `val`, `desc`) VALUES ('recycle_pse_type_8', '18', 'PSE type8回收价格');
INSERT INTO `ts_sys_config`(`attr`, `val`, `desc`) VALUES ('recycle_egse_type_1', '11', 'EGSE type1回收价格');
INSERT INTO `ts_sys_config`(`attr`, `val`, `desc`) VALUES ('recycle_egse_type_2', '12', 'EGSE type2回收价格');
INSERT INTO `ts_sys_config`(`attr`, `val`, `desc`) VALUES ('recycle_egse_type_3', '13', 'EGSE type3回收价格');

ALTER TABLE `ts_nft_card_info`
    MODIFY COLUMN `description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL AFTER `image`;

DROP TABLE IF EXISTS `ts_nft_egse_info`;
CREATE TABLE `ts_nft_egse_info`  (
                                     `token_id` bigint(20) NOT NULL COMMENT 'nft id',
                                     `owner_address` varchar(42) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '所有人地址',
                                     `itemData` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'nft卡片属于原始值',
                                     `type` tinyint(3) NULL DEFAULT NULL,
                                     `tokenURI` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
                                     `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'nft name',
                                     `image` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '图片地址',
                                     `description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
                                     `attr_type` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
                                     `views` bigint(20) UNSIGNED ZEROFILL NULL DEFAULT 00000000000000000000 COMMENT '查看数',
                                     `favorites` bigint(20) UNSIGNED ZEROFILL NULL DEFAULT 00000000000000000000 COMMENT '收藏数量',
                                     `last_trade_price` decimal(20, 6) UNSIGNED ZEROFILL NULL DEFAULT NULL COMMENT '最后一次成交价',
                                     `last_trade_time` timestamp(0) NULL DEFAULT NULL COMMENT '最近成交时间',
                                     `detail_image` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
                                     PRIMARY KEY (`token_id`) USING BTREE,
                                     UNIQUE INDEX `INDEX_NFT_EGSE_INFO_TOKEN_ID`(`token_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ts_nft_hse_info
-- ----------------------------
DROP TABLE IF EXISTS `ts_nft_hse_info`;
CREATE TABLE `ts_nft_hse_info`  (
                                    `token_id` bigint(20) NOT NULL COMMENT 'nft id',
                                    `owner_address` varchar(42) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '所有人地址',
                                    `tokenURI` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
                                    `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'nft name',
                                    `image` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '图片地址',
                                    `description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
                                    `attr_type` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
                                    `views` bigint(20) UNSIGNED ZEROFILL NULL DEFAULT 00000000000000000000 COMMENT '查看数',
                                    `favorites` bigint(20) UNSIGNED ZEROFILL NULL DEFAULT 00000000000000000000 COMMENT '收藏数量',
                                    `last_trade_price` decimal(20, 6) UNSIGNED ZEROFILL NULL DEFAULT NULL COMMENT '最后一次成交价',
                                    `last_trade_time` timestamp(0) NULL DEFAULT NULL COMMENT '最近成交时间',
                                    `detail_image` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
                                    PRIMARY KEY (`token_id`) USING BTREE,
                                    UNIQUE INDEX `INDEX_NFT_HSE_INFO_TOKEN_ID`(`token_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ts_nft_pse_info
-- ----------------------------
DROP TABLE IF EXISTS `ts_nft_pse_info`;
CREATE TABLE `ts_nft_pse_info`  (
                                    `token_id` bigint(20) NOT NULL COMMENT 'nft id',
                                    `owner_address` varchar(42) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '所有人地址',
                                    `itemData` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'nft卡片属于原始值',
                                    `type` tinyint(3) NULL DEFAULT NULL,
                                    `tokenURI` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
                                    `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'nft name',
                                    `image` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '图片地址',
                                    `description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
                                    `attr_type` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
                                    `views` bigint(20) UNSIGNED ZEROFILL NULL DEFAULT 00000000000000000000 COMMENT '查看数',
                                    `favorites` bigint(20) UNSIGNED ZEROFILL NULL DEFAULT 00000000000000000000 COMMENT '收藏数量',
                                    `last_trade_price` decimal(20, 6) UNSIGNED ZEROFILL NULL DEFAULT NULL COMMENT '最后一次成交价',
                                    `last_trade_time` timestamp(0) NULL DEFAULT NULL COMMENT '最近成交时间',
                                    `detail_image` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
                                    PRIMARY KEY (`token_id`) USING BTREE,
                                    UNIQUE INDEX `INDEX_NFT_PSE_INFO_TOKEN_ID`(`token_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;