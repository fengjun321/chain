package org.sparkera.nft.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

@Data
public class NftShipItemInfo extends BaseEntityInfo<NftShipItemInfo>{
    private static final long serialVersionUID = 1L;

    @TableId(value = "token_id")
    private BigInteger tokenId;
    @ApiModelProperty(value = "所有人")
    @TableField("owner_address")
    private String ownerAddress;

    @TableField("name")
    private String name;
    @TableField("image")
    private String image;
    @TableField("model")
    private String model = "default";
    @TableField("description")
    private String description;
    @TableField("ship_name")
    private String shipName = "default";
    @TableField("rank")
    private String rank = "default";
    @TableField("level")
    private Integer level;
    @TableField("power")
    private Integer power;
    @TableField("basic_limit")
    private Integer basicLimit;
    @TableField("basic_current")
    private Integer basicCurrent;
    @TableField("leader_limit")
    private Integer leaderLimit;
    @TableField("leader_current")
    private Integer leaderCurrent;
    @TableField("scientist_limit")
    private Integer scientistLimit;
    @TableField("scientist_current")
    private Integer scientistCurrent;
    @TableField("commander_limit")
    private Integer commanderLimit;
    @TableField("commander_current")
    private Integer commanderCurrent;
    @TableField("start_soldier_limit")
    private Integer startSoldierLimit;
    @TableField("start_soldier_current")
    private Integer startSoldierCurrent;
    @TableField("common_soldier_limit")
    private Integer commonSoldierLimit;
    @TableField("common_soldier_current")
    private Integer commonSoldierCurrent;

    //必须新增
    @TableField("favorites")
    private Integer favorites;
    @TableField("views")
    private Integer views;
    @TableField("itemData")
    private String  itemData;
    @TableField("type")
    private Integer type;
    @TableField("tokenURI")
    private String tokenURI;
    @TableField("nameId")
    private Integer nameId;
    @TableField("last_trade_price")
    private BigDecimal lastTradePrice;
    @TableField("last_trade_time")
    private Date lastTradeTime;

    @TableField("version")
    private Integer version;
    @TableField("releaseVersion")
    private Integer releaseVersion;
}
