package org.sparkera.nft.dao.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

@Data
public class NftFavoriteInfoDto implements Serializable {
    private BigInteger id;
    private BigInteger tokenId;
    private BigInteger extId;
    private String userAddress;
    private Date createTime;
    private Integer nftType;
}
