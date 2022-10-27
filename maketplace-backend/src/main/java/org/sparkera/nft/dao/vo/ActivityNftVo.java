package org.sparkera.nft.dao.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;

@Data
public class ActivityNftVo implements Serializable {
    private BigInteger tokenId;
    private String ownerAddress;
    private BigDecimal price;
    private String image;
    private String fromAddress;
    private String toAddress;
    private Timestamp tradeTime;
    private Integer tradeType;
    private String imageHead;
    private Long tradeTimestamp;
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
    public Long getTradeTimestamp() {
        if(getTradeTime()!=null){
            return getTradeTime().getTime();
        }
        return tradeTimestamp;
    }

    public void setTradeTimestamp(Long tradeTimestamp) {
        this.tradeTimestamp = tradeTimestamp;
    }



}
