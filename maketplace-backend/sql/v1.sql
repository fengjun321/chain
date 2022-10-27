SET FOREIGN_KEY_CHECKS=0;
ALTER TABLE `ts_buy_sell_info` ADD COLUMN `ext_id`  bigint(20) NULL DEFAULT 0 COMMENT '额外id字段，可以使用，也可以不用，为差异性扩展新增' AFTER `tokenId`;
ALTER TABLE `ts_favorite_info` ADD COLUMN `ext_id`  bigint(20) NULL DEFAULT 0 COMMENT '扩展信息字段' AFTER `token_id`;
ALTER TABLE `ts_nft_ise_info` DROP PRIMARY KEY;
ALTER TABLE `ts_nft_ise_info` ADD COLUMN `tbId`  bigint(20) NOT NULL AUTO_INCREMENT FIRST , ADD PRIMARY KEY (`tbId`);
CREATE UNIQUE INDEX `attr1` ON `ts_nft_ise_info`(`id`, `owner_address`) USING BTREE ;
CREATE TABLE `ts_nft_trade_info_mul` (
`unique_id`  bigint(20) NULL DEFAULT NULL COMMENT '自定义标识，用于扩展' ,
`ext_id`  bigint(20) NULL DEFAULT NULL COMMENT '自定义标识，用于扩展' ,
`cnt`  int(255) NULL DEFAULT NULL ,
`owner_address`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '归属者地址' ,
`last_trade_price`  decimal(20,6) NULL DEFAULT NULL COMMENT '成交价' ,
`last_trade_time`  timestamp NULL DEFAULT NULL ,
`image`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '图片' ,
`nft_type`  tinyint(255) NULL DEFAULT NULL COMMENT '资产类型' 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_bin
ROW_FORMAT=Dynamic
;
SET FOREIGN_KEY_CHECKS=1;