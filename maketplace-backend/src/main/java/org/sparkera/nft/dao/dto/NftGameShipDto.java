package org.sparkera.nft.dao.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;

@Data
public class NftGameShipDto implements Serializable {
    @TableField("ship_guid")
    private BigInteger shipGuid;
    @TableField("user_id")
    private BigInteger userId;
    @TableField("ship_id")
    private Integer shipId;
    @TableField("be_use")
    private Integer beUse;
    @TableField("token_id")
    private BigInteger tokenId;
    @TableField("hash_id")
    private BigInteger hashId;
}
