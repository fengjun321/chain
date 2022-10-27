package org.sparkera.nft.dao.dto2;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import java.util.Date;

@Data
public class ResearchDto {
    @TableField("infoCode")
    private String infoCode;
    @TableField("title")
    private String title;
    @TableField("industryCode")
    private Integer industryCode;
    @TableField("industryName")
    private String industryName;
    @TableField("emRatingName")
    private String emRatingName;
    @TableField("ratingChange")
    private String ratingChange;

    @TableField("publishDate")
    private String publishDate;
}
