package org.sparkera.nft.dao.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;


@Data
public class UserMailDto implements Serializable {
    @TableField("UserID")
    private BigInteger userId;
    @TableField("UserName")
    private String userName;
    @TableField("RefferId")
    private BigInteger refferId;
    @TableField("WalletAddress")
    private String walletAddress;
}
