package org.sparkera.nft.dao.dto;

import lombok.Data;
import org.sparkera.nft.dao.entity.NftCardItemInfo;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

@Data
public class NftCardTradeDto implements Serializable {
    private static final long serialVersionUID = 1L;
    @NotNull(message = "tokenId cannot be null")
    @Min(value = 0,message = "tokenId error")
    private BigInteger tokenId;
    private String  ownerAddress;
    @NotNull(message = "price cannot be null")
    @DecimalMin(value = "0.0001",message = "price error")
    private BigDecimal price;
    @NotBlank(message = "Signature cannot be null")
    private String  signature;
    private String  address;
    private String  message;
    @NotNull(message = "NFt type cannot be null")
    @Min(value = 0,message = "nftType error")
    @Max(value = 8,message = "nftType error")
    private Integer nftType;

    //1155资产新增字段
    private BigInteger goodId;
    private Integer id;
    private Integer cnt;
}
