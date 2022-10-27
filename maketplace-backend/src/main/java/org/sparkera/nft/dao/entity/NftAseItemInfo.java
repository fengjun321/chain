package org.sparkera.nft.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Data
public class NftAseItemInfo extends BaseEntityInfo<NftAseItemInfo> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "token_id")
    private BigInteger tokenId;
    @ApiModelProperty(value = "所有人")
    @TableField("owner_address")
    private String ownerAddress;
    @TableField("views")
    private Integer views;
    @TableField("favorites")
    private Integer favorites;
    @TableField("itemData")
    private String itemData;
    @TableField("tokenURI")
    private String tokenURI;
    @TableField("version")
    private Integer version = 0;
    @TableField("releaseVersion")
    private Integer releaseVersion = 0;
    @TableField("name")
    private String name;
    @TableField("image")
    private String image;
    @TableField("detail_image")
    private String detailImage;
    @TableField("description")
    private String description;
    @TableField("arms_name")
    private String armsName;
    @TableField("rank")
    private String rank;
    @TableField("power")
    private Integer power;
    @TableField("endurance")
    private Integer endurance;
    @TableField("strength")
    private Integer strength;
    @TableField("agility")
    private Integer agility;
    @TableField("armor")
    private Integer armor;
    @TableField("Crit")
    private Integer crit;
    @TableField("Pisonic")
    private Integer pisonic;
    @TableField("Spirit")
    private Integer spirit;
    @TableField("last_trade_price")
    private BigDecimal lastTradePrice;
    @TableField("last_trade_time")
    private Date lastTradeTime;

    @ApiModelProperty(value = "数据索引")
    @TableField("data")
    private Integer data = 0;
}
