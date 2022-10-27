package org.sparkera.nft.dao.dto;

import lombok.Data;
import org.sparkera.nft.dao.entity.NftCardItemInfo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Data
public class NftSellInfoDto implements Serializable {
    private BigInteger tokenId;
    private String  ownerAddress;
    private BigDecimal price;
    private Date createTime;
    private int version;

    //1155资产新增字段
    private BigInteger goodId;
    private Integer id;
    private Integer num;
}
