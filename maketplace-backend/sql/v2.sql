SET FOREIGN_KEY_CHECKS=0;
CREATE TABLE `ts_bank` (
`hash_id`  bigint(20) NOT NULL ,
`email_addr`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL ,
`erc20_addr`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL ,
`amount`  decimal(20,6) NULL DEFAULT NULL ,
`create_date`  timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
`flag`  int(255) NULL DEFAULT 0 COMMENT '0:在途\r\n1:成功\r\n2:失败' ,
PRIMARY KEY (`hash_id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_bin
ROW_FORMAT=Dynamic
;
ALTER TABLE `ts_buy_sell_info` ADD COLUMN `ext_id`  bigint(20) NULL DEFAULT 0 COMMENT '额外id字段，可以使用，也可以不用，为差异性扩展新增' AFTER `tokenId`;
CREATE TABLE `ts_equip_to_game` (
`id`  bigint(8) NOT NULL AUTO_INCREMENT ,
`nft_type`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL ,
`item_id`  int(8) NULL DEFAULT NULL ,
`owner_address`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL ,
`name`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL ,
`action`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL ,
`changedate`  datetime NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_bin
ROW_FORMAT=Dynamic
;
ALTER TABLE `ts_favorite_info` ADD COLUMN `ext_id`  bigint(20) NULL DEFAULT 0 COMMENT '扩展信息字段' AFTER `token_id`;
CREATE TABLE `ts_invite` (
`id`  bigint(11) NOT NULL AUTO_INCREMENT ,
`email_addr`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL ,
`code`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL ,
`create_date`  timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
PRIMARY KEY (`id`),
UNIQUE INDEX `code` (`code`) USING BTREE ,
UNIQUE INDEX `addr` (`email_addr`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_bin
ROW_FORMAT=Dynamic
;
CREATE TABLE `ts_invite_rank` (
`UserId`  int(11) NULL DEFAULT NULL COMMENT '账户id' ,
`UserName`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '账号名称' ,
`InvitationNum`  int(11) NOT NULL COMMENT '邀请人数' ,
`LastInvitationTime`  datetime(6) NOT NULL COMMENT '上一个邀请人注册时间' ,
PRIMARY KEY (`UserName`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_bin
ROW_FORMAT=Dynamic
;
ALTER TABLE `ts_nft_card_info` MODIFY COLUMN `power`  smallint(3) NULL DEFAULT 0 COMMENT '权力' AFTER `rarity`;
ALTER TABLE `ts_nft_ise_info` DROP PRIMARY KEY;
ALTER TABLE `ts_nft_ise_info` ADD COLUMN `tbId`  bigint(20) NOT NULL AUTO_INCREMENT FIRST , ADD PRIMARY KEY (`tbId`);
CREATE UNIQUE INDEX `attr1` ON `ts_nft_ise_info`(`id`, `owner_address`) USING BTREE ;
CREATE DEFINER=`root`@`localhost` TRIGGER `insert` AFTER INSERT ON `ts_nft_ship_info`
FOR EACH ROW begin
set @itemid = 0;
set @ship_name = new.ship_name;
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
	owner_address = new.owner_address,
	name = @ship_name,
              changedate = now();
end;
CREATE DEFINER=`root`@`localhost` TRIGGER `delete` AFTER DELETE ON `ts_nft_ship_info`
FOR EACH ROW begin
set @itemid = 0;
set @ship_name = old.ship_name;
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
	SET action = 'delete',
              nft_type = "ship",
	item_id = @itemid,
	owner_address = old.owner_address,
	name = @ship_name,
              changedate = now();
end;
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
CREATE TABLE `ts_rebate_info` (
`email_addr`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL ,
`erc20_addr`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL ,
`amount`  decimal(20,6) NULL DEFAULT NULL ,
`create_date`  timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
`use_amount`  decimal(20,6) NULL DEFAULT 0.000000 ,
PRIMARY KEY (`email_addr`, `erc20_addr`),
INDEX `nor1` (`email_addr`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8mb4 COLLATE=utf8mb4_bin
ROW_FORMAT=Dynamic
;
SET FOREIGN_KEY_CHECKS=1;
