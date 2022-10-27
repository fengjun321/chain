package org.sparkera.nft.dao.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class NftGTakeOutQueryDto implements Serializable {
    @NotNull(message = "gameTag cannot be null")
    @ApiModelProperty(name ="gameTag" ,value = "0:WALLET 1：SPARKERA_v3")
    private Integer gameTag;
    @NotNull(message = "nftType cannot be null")
    @ApiModelProperty(name ="nftType" ,value = "0:FHC 1：HSE,2：PSE,3：EGSE,4：SHIP")
    private Integer nftType;
    @NotNull(message = "takeOut cannot be null")
    @TableField("take_out")
    private String takeOut;
}
