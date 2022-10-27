package org.sparkera.nft.dao.entity;


import lombok.Data;

import java.math.BigInteger;
import java.util.List;

@Data
public class NftGameSwapInfo {
    String nftAddress;
    List<BigInteger> listTokenId;
}
