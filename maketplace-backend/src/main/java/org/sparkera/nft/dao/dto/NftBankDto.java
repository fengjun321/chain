package org.sparkera.nft.dao.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Data
public class NftBankDto {
    @TableField("hash_id")
    private BigInteger hashId;
    @TableField("user_id")
    private BigInteger userId;
    @TableField("nft_type")
    private Integer nftType;
    @TableField("flag")
    private Integer flag;

    @TableField("create_date")
    private Date createDate;
}
