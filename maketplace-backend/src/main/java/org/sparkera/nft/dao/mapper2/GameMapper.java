package org.sparkera.nft.dao.mapper2;

import org.apache.ibatis.annotations.*;
import org.sparkera.nft.dao.dto.NftGameShipDto;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface GameMapper {
    @Select("select * from ship " +
            "where user_id = #{userId} for update")
    List<NftGameShipDto> lockUser(@Param("userId") BigInteger userId);

    @Select("select ship_guid, user_id, ship_id, be_use, token_id, hash_id from ship " +
            "where user_id = #{userId} for update")
    List<NftGameShipDto> getGameShipInfo(@Param("userId") BigInteger userId);

    @Select("select ship_guid, user_id, ship_id, be_use, token_id, hash_id from ship " +
            "where token_id = #{tokenId}")
    NftGameShipDto getGameShipInfoByTokenId(@Param("tokenId") BigInteger tokenId);

    @Update("replace into ship (ship_guid, user_id, ship_id, be_use, token_id, hash_id) values" +
            "(#{dto.shipGuid}, #{dto.userId}, #{dto.shipId}, #{dto.beUse}, #{dto.tokenId}, #{dto.hashId})")
    int upInsetNftInfo(@Param("dto") NftGameShipDto nftInfoDto);

    @Update("update ship set hash_id = #{hashId} where token_id = #{tokenId}")
    int updateShipHashId(@Param("tokenId") BigInteger tokenId, @Param("hashId") BigInteger hashId);

    @Delete("delete from ship where hash_id = #{hashId}")
    int deleteShipByHashId(@Param("hashId") BigInteger hashId);
}
