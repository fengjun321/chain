package org.sparkera.nft.dao.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Data
public class NftBuySellLogDto implements Serializable {
    private BigInteger tokenId;
    private String sellAddress;
    private Date sellCreateTime;
    private BigDecimal sellPrice;
    private BigDecimal txFee;
    private String buyAddress;
    private String txHash;
    private String erc20Address;
    private Integer nftType;

    private BigInteger extId = BigInteger.valueOf(0);
}
