package org.sparkera.nft.dao.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigInteger;

@Data
public class NftCardQueryDto implements Serializable {
    private BigInteger tokenId;
    @ApiModelProperty(name ="address" ,value = "current user address")
    private String address;
    private String  ownerAddress;
    private String  searchKey;//搜索框
    /**
     * 阵营编号
     * 0:Asman Zerg 1:Empire of Amda 2:Confederation of Earth 3:Eternal Protoss
     */
    @ApiModelProperty(name ="camp" ,value = "0:Asman Zerg 1:Empire of Amda 2:Confederation of Earth 3:Eternal Protoss",
            allowableValues = "0,1,2,3")
    private Integer camp;
    /**
     * 0:Leader
     * 1:Scientist
     * 2:Commander
     * 3:Star Soldier
     * 4:Common Soldier
     */
    @ApiModelProperty(name ="identity" ,allowableValues = "0,1,2,3,4",value = "0:Leader 1:Scientist 2:Commander 3:Star Soldier 4:Common Soldier")
    private Integer identity;
    /**
     * 0:Common
     * 1:Rare
     * 2:Epic
     * 3:Legend
     */
    @ApiModelProperty(name ="rarity" ,allowableValues = "0,1,2,3",value = "0:Common 1:Rare 2:Epic 3:Legend")
    private Integer rarity;
    /**
     * 0:DarkIron
     * 1:Silver
     * 2:Gold
     */
    @ApiModelProperty(name ="material" ,allowableValues = "0,1,2",value = "0:DarkIron 1:Silver 2:Gold")
    private Integer material;
    /**
     * 0:Omni Warrior
     * 1:Psionicist
     * 2:Bounty Hunter
     * 3:Wanderer
     * 4:Liquidator
     */
    @ApiModelProperty(name ="profession",allowableValues = "0,1,2,3,4" ,value = "0:Omni Warrior 1:Psionicist 2:Bounty Hunter 3:Wanderer 4:Liquidator")
    private Integer profession;

    @ApiModelProperty(name ="level",value = " 1-10")
    private Integer level;
    @ApiModelProperty(name ="minEndurance" ,value = "1-100")
    private Integer minEndurance;
    @ApiModelProperty(name ="maxEndurance" ,value = "1-100")
    private Integer maxEndurance;

    @ApiModelProperty(name ="minStrength" ,value = "1-100")
    private Integer minStrength;
    @ApiModelProperty(name ="maxStrength" ,value = "1-100")
    private Integer maxStrength;

    @ApiModelProperty(name ="minAgility" ,value = "1-100")
    private Integer minAgility;
    @ApiModelProperty(name ="maxAgility" ,value = "1-100")
    private Integer maxAgility;

    @ApiModelProperty(name ="minArmor" ,value = "1-100")
    private Integer minArmor;
    @ApiModelProperty(name ="maxArmor" ,value = "1-100")
    private Integer maxArmor;

    @ApiModelProperty(name ="minCrit" ,value = "1-100")
    private Integer minCrit;
    @ApiModelProperty(name ="maxCrit" ,value = "1-100")
    private Integer maxCrit;

    @ApiModelProperty(name ="minPsionic" ,value = "1-100")
    private Integer minPsionic;
    @ApiModelProperty(name ="maxPsionic" ,value = "1-100")
    private Integer maxPsionic;

    @ApiModelProperty(name ="minSpirit" ,value = "1-100")
    private Integer minSpirit;
    @ApiModelProperty(name ="maxSpirit" ,value = "1-100")
    private Integer maxSpirit;
    @ApiModelProperty(name ="sortType" ,value = "0:asc 1：desc")
    private int sortType;
    @ApiModelProperty(name ="sortFiled" ,value = "0:Price 1：recently listed,2：Most viewd,3：Most favorite,4 added time 5 total_attributes")
    private Integer sortFiled;

    @ApiModelProperty(name ="minPower" ,value = "1-400")
    private Integer minPower;
    @ApiModelProperty(name ="maxPower" ,value = "1-400")
    private Integer maxPower;
    @NotNull(message = "nftType cannot be null")
    @Min(value = 0,message = "nftType error")
    @Max(value = 8,message = "nftType error")
    @ApiModelProperty(name ="nftType" ,value = "0:FHC 1：HSE,2：PSE,3：EGSE,4：SHIP,5：EGG,6：MBSE,7：ASE,8:ISE")
    private Integer nftType;
    @ApiModelProperty(name ="type" ,value = "用于PSE,EGSE的条件筛选")
    private Integer type;

    @ApiModelProperty(name ="rank" ,value = "current user rank")
    private String rank;
    @ApiModelProperty(name ="lootBoxType" ,value = "current user lootBoxType")
    private String lootBoxType;
}
