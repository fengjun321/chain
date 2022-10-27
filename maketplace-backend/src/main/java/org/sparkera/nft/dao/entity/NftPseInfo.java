package org.sparkera.nft.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Data
public class NftPseInfo extends BaseEntityInfo<NftPseInfo> {
    private static final long serialVersionUID = 1L;
    @TableId(value = "token_id")
    private BigInteger tokenId;
    @ApiModelProperty(value = "所有人")
    @TableField("owner_address")
    private String  ownerAddress;
    @TableField("itemData")
    private String  itemData;
    @ApiModelProperty(value = "type")
    @TableField("type")
    private Integer type;
    @ApiModelProperty(value = "tokenURI")
    @TableField("tokenURI")
    private String tokenURI;
    @ApiModelProperty(value = "name")
    @TableField("name")
    private String name;
    @ApiModelProperty(value = "image")
    @TableField("image")
    private String image;
    @ApiModelProperty(value = "description")
    @TableField("description")
    private String description;
    @ApiModelProperty(value = "attrType")
    @TableField("attrType")
    private String attrType;
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

}
