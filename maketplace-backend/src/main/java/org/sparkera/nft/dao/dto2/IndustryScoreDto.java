package org.sparkera.nft.dao.dto2;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class IndustryScoreDto {
    @TableField("industryName")
    private String industryName;
    @TableField("industryCode")
    private Integer industryCode;
    @TableField("score")
    private Integer score;
}
