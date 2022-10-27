package org.sparkera.nft.dao.mapper;

import org.apache.ibatis.annotations.*;
import org.sparkera.nft.dao.dto.NftBuySellLogDto;
import org.sparkera.nft.dao.dto.NftCardItemDataDto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Map;

@Mapper
public interface CommonMapper {

    String getSysConfigByKey(@Param("key")String key);
    int upDateSysConfigByKey(@Param("key")String key,@Param("val")String val);

    int insertBuySellInfo(NftBuySellLogDto buySellLogDto);

    @Insert("insert into ts_nft_activity_logs(tokenId,fromAddress,toAddress,price,tradeTime,tradeType,nft_type)" +
            "values(#{tokenId},#{fromAddress},#{toAddress},#{price},#{tradeTime},#{type},#{nftType})")
    int insertActivityLog(@Param("tokenId")BigInteger tokenId, @Param("fromAddress")String fromAddress, @Param("toAddress")String toAddress,
                         @Param("price")BigDecimal price, @Param("tradeTime")Timestamp tradeTime, @Param("type")int type,@Param("nftType")int nftType);

    @Select("select unix_timestamp() as time")
    Map<String, Object> getDbTime();
}
