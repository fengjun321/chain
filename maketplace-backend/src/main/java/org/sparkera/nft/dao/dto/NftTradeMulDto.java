package org.sparkera.nft.dao.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;

@Data
public class NftTradeMulDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private BigInteger unique_id;
    private BigInteger ext_id;
    private Integer cnt;
    private String owner_address;
    private BigDecimal last_trade_price;
    private Timestamp last_trade_time;
    private String image;
    private Integer nft_type;
}
