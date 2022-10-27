package org.sparkera.nft.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Data
public class NftIseItemInfo extends BaseEntityInfo<NftEggItemInfo>{
    private static final long serialVersionUID = 1L;

    @TableId(value = "tbId")
    private BigInteger tbId;
    @TableId(value = "id")
    private Integer id;
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
    @TableField("name")
    private String name;
    @TableField("image")
    private String image;
    @TableField("detail_image")
    private String detailImage;
    @TableField("description")
    private String description;
    @TableField("data")
    private Integer data;
    @TableField("rank")
    private String rank;
    @TableField("cnt")
    private Integer cnt;
    @TableField("version")
    private Integer version = 0;
    @TableField("releaseVersion")
    private Integer releaseVersion = 0;
    @TableField("last_trade_price")
    private BigDecimal lastTradePrice;
    @TableField("last_trade_time")
    private Date lastTradeTime;
}
