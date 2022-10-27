package org.sparkera.nft.dao.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

@Data
public class TopNftVO implements Serializable {
    private BigInteger tokenId;
    private String ownerAddress;
    private BigDecimal price;
    private String image;
    private String imageHead;
    private Integer nftType;
    public String getImageHead() {
        if(getImage()!=null){
            return getImage().replace(".png","_head.png");
        }
        return imageHead;
    }
    public void setImageHead(String imageHead) {
        this.imageHead = imageHead;
    }

}
