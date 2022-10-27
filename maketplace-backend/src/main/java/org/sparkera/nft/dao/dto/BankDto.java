package org.sparkera.nft.dao.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Data
public class BankDto implements Serializable {
    @TableField("hash_id")
    private BigInteger hashId;
    @TableField("user_id")
    private BigInteger userId;
    @TableField("erc20_addr")
    private String erc20Addr;
    @TableField("amount")
    private BigDecimal amount;
    @TableField("flag")
    private Integer flag;

    @TableField("create_date")
    private Date createDate;
}
