package org.sparkera.nft.dao.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;

@Data
public class NftStatisticsDto implements Serializable {
    @ApiModelProperty(name ="camp" ,value = "nftType=0(0:Asman Zerg 1:Empire of Amda 2:Confederation of Earth 3:Eternal Protoss)")
    private Integer camp;
    @ApiModelProperty(name ="sortType" ,value = "0:asc 1：desc")
    private int sortType;
    @ApiModelProperty(name ="sortFiled" ,value = "0:Price 1：ownerAddress")
    private Integer sortFiled;
    @ApiModelProperty(name ="nftType" ,value = "0:FHC 1：HSE,2：PSE,3：EGSE,4：SHIP")
    private Integer nftType;
    @ApiModelProperty(name ="rarity")
    private Integer rarity;
    @ApiModelProperty(name ="level")
    private Integer level;
    @ApiModelProperty(name ="rank")
    private String rank;
    @ApiModelProperty(name ="type")
    private String type;
}
