package org.sparkera.nft.dao.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

@Data
public class NftCardItemDataDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private BigInteger tokenId;
    private String  ownerAddress;
    private String  itemData;
    private Integer occupation;
    private Integer strength;
    private Integer level;
    private Integer spirit;
    private Integer type;
    private Integer endurance;
    private Integer psionic;
    private Integer camp;
    private Integer armor;
    private Integer material;
    private Integer crit;
    private Integer identity;
    private Integer nameId;
    private Integer agility;
    private Integer rarity;
    private String tokenURI;
    private Integer version;
    private Integer releaseVersion;
    private String name;
    private String image;
    private String description;
    private String attrEdition;
    private String attrCamp;
    private String attrRank;
    private String attrMaterial;
    private String attrName;
    private BigInteger views;
    private BigInteger favorites;
    private int totalAttrs;
    private BigDecimal lastTradePrice;
    private String detailImage;
    private Integer power;
}
