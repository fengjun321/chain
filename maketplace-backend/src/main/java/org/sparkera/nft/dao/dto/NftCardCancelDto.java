package org.sparkera.nft.dao.dto;

import lombok.Data;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

@Data
public class NftCardCancelDto implements Serializable {
    private static final long serialVersionUID = 1L;
    @NotNull(message = "tokenId cannot be null")
    @Min(value = 0,message = "tokenId error")
    private BigInteger tokenId;
    private String  ownerAddress;
    private String  signature;
    @NotNull(message = "address cannot be null")
    private String  address;
    private String  message;
    @NotNull(message = "NFt type cannot be null")
    @Min(value = 0,message = "nftType error")
    @Max(value = 3,message = "nftType error")
    private Integer nftType;
    private BigInteger goodId;
}
