package org.sparkera.nft.dao.mapper2;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.sparkera.nft.dao.dto.UserMailDto;

import java.math.BigInteger;

@Mapper
public interface FcMapper {
    @Select("select UserID, UserName, RefferId, WalletAddress from user_info " +
            "where UserID = #{userId}")
    UserMailDto getUserInfo(@Param("userId") BigInteger userId);

    @Select("select UserID, UserName, RefferId, WalletAddress from user_info " +
            "where WalletAddress = #{ownerAddr}")
    UserMailDto getUserInfoByOwnerAddr(@Param("ownerAddr") String ownerAddr);

    @Select("select count(1) as cnt from user_info where RefferId = #{userId}")
    int getUserInviteCnt(@Param("userId") BigInteger emailAddr);
}
