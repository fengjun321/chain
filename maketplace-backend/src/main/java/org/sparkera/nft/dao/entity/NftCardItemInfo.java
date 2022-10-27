package org.sparkera.nft.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Data
public class NftCardItemInfo extends BaseEntityInfo<NftCardItemInfo> {
    private static final long serialVersionUID = 1L;
    @TableId(value = "token_id")
    private BigInteger tokenId;
    @ApiModelProperty(value = "所有人")
    @TableField("owner_address")
    private String  ownerAddress;
    @ApiModelProperty(value = "属性值")
    @TableField("itemData")
    private String  itemData;
    @ApiModelProperty(value = "职业")
    @TableField("occupation")
    private Integer occupation;
    @ApiModelProperty(value = "力量")
    @TableField("strength")
    private Integer strength;
    @ApiModelProperty(value = "等级")
    @TableField("level")
    private Integer level;
    @ApiModelProperty(value = "精神")
    @TableField("spirit")
    private Integer spirit;
    @ApiModelProperty(value = "type")
    @TableField("type")
    private Integer type;
    @ApiModelProperty(value = "耐力")
    @TableField("endurance")
    private Integer endurance;
    @ApiModelProperty(value = "靈能")
    @TableField("psionic")
    private Integer psionic;
    @ApiModelProperty(value = "阵营")
    @TableField("camp")
    private Integer camp;
    @ApiModelProperty(value = "护甲")
    @TableField("armor")
    private Integer armor;
    @ApiModelProperty(value = "材质")
    @TableField("material")
    private Integer material;
    @ApiModelProperty(value = "暴擊")
    @TableField("crit")
    private Integer crit;
    @ApiModelProperty(value = "身份")
    @TableField("occupation")
    private Integer identity;
    @ApiModelProperty(value = "角色Id")
    @TableField("nameId")
    private Integer nameId;
    @ApiModelProperty(value = "敏捷")
    @TableField("agility")
    private Integer agility;
    @ApiModelProperty(value = "稀有度")
    @TableField("rarity")
    private Integer rarity;
    @ApiModelProperty(value = "power")
    @TableField("power")
    private Integer power;
    @ApiModelProperty(value = "tokenURI")
    @TableField("tokenURI")
    private String tokenURI;
    @ApiModelProperty(value = "version")
    @TableField("version")
    private Integer version;
    @ApiModelProperty(value = "releaseVersion")
    @TableField("releaseVersion")
    private Integer releaseVersion;
    @ApiModelProperty(value = "name")
    @TableField("name")
    private String name;
    @ApiModelProperty(value = "image")
    @TableField("image")
    private String image;
    @ApiModelProperty(value = "description")
    @TableField("description")
    private String description;
    @ApiModelProperty(value = "attrEdition")
    @TableField("attrEdition")
    private String attrEdition;
    @ApiModelProperty(value = "attrCamp")
    @TableField("attrCamp")
    private String attrCamp;
    @ApiModelProperty(value = "attrRank")
    @TableField("attrRank")
    private String attrRank;
    @ApiModelProperty(value = "attrMaterial")
    @TableField("attrMaterial")
    private String attrMaterial;
    @ApiModelProperty(value = "attrName")
    @TableField("attrName")
    private String attrName;
    @ApiModelProperty(value = "views")
    @TableField("views")
    private Integer views;
    @ApiModelProperty(value = "favorites")
    @TableField("favorites")
    private Integer favorites;
    @ApiModelProperty(value = "lastTradePrice")
    @TableField("last_trade_price")
    private BigDecimal lastTradePrice;
    @ApiModelProperty(value = "lastTradeTime")
    @TableField("last_trade_time")
    private Date lastTradeTime;
    @ApiModelProperty(value = "detailImage")
    @TableField("detail_image")
    private String detailImage;
    @ApiModelProperty(value = "totalAttrs")
    @TableField("total_attrs")
    private int totalAttrs;

}
