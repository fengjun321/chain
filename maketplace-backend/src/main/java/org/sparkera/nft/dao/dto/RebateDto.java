package org.sparkera.nft.dao.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;


@Data
public class RebateDto implements Serializable {
    @TableField("user_id")
    private BigInteger userId;
    @TableField("erc20_addr")
    private String erc20Addr;
    @TableField("amount")
    private BigDecimal amount;
    @TableField("use_amount")
    private BigDecimal useAmount;
}
