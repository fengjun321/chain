package org.sparkera.nft.utils.pageinfoutil;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
public class PageVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "pageNumber")
    private int pageNumber;

    @ApiModelProperty(value = "pageSize")
    private int pageSize;

}
