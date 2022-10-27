package org.sparkera.nft.dao.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

@Data
public class InviteDto implements Serializable {
    @TableField("id")
    private BigInteger id;
    @TableField("user_id")
    private BigInteger userId;
    @TableField("code")
    private String code;
    @TableField("create_date")
    private Date createDate;
}
