package org.sparkera.nft.dao.mapper;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.*;
import org.sparkera.nft.dao.dto.*;
import org.sparkera.nft.dao.entity.BaseEntityInfo;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface NFTBaseMapper extends Mapper{
    List<Map<String,Object>> tradingMarket(Page<Map<String,Object>> page, NftCardQueryDto dto);
    int addViewsByTokenId(Integer tokenId);
    int addViewsByTokenIdMul(Integer tokenId, String ownerAddress);
    List<Map<String,Object>> myStorage(Page<Map<String,Object>> page, NftCardQueryDto dto);
    List<Map<String, Object>> selling(Page<Map<String, Object>> page, NftCardQueryDto dto);
    List<Map<String, Object>> myFavorite(Page<Map<String, Object>> page, NftCardQueryDto dto);
    Map<String, Object> tokenDetail( BigInteger tokenId, String address);
    Map<String, Object> checkToken(BigInteger tokenId);
    int updateNftFavorites(BigInteger tokenId, int ct);
    int updateNftFavoritesMul(Integer id, String address, int ct);
    Object getNftInfoByTokenId(BigInteger tokenId);
    Object getNftInfoByTokenIdMul(@Param("id")Integer id, @Param("address") String address);
    int updateNftLastTradePrice(@Param("tokenId")BigInteger tokenId, @Param("price") BigDecimal price, @Param("tradeTime") Timestamp dateTime);
    int upInsetNftInfo(BaseEntityInfo baseEntityInfo);

    @Insert("<script> INSERT INTO ts_selling_order(`tokenId`, `ownerAddress`, `price`, `create_time`,`t_version`,`nft_type`) " +
            "        VALUES(#{dto.tokenId},#{dto.ownerAddress},#{dto.price},now(),0,#{dto.nftType}) </script>")
    int sellToken(@Param("dto") NftCardTradeDto nftCardInfo);
    @Select("select count(1) from ts_favorite_info where token_id=#{tokenId} and user_address=#{address} and nft_type=#{nftType}")
    int checkFavorite(@Param("tokenId") BigInteger tokenId, @Param("address") String address,@Param("nftType") Integer nftType);
    @Insert("insert into ts_favorite_info(token_id,user_address,create_time,nft_type, ext_id) " +
            "   values(#{tokenId},#{address},now(),#{nftType}, #{extId}) ")
    int insertFavorite(@Param("tokenId") BigInteger tokenId,@Param("address")String address,@Param("nftType") Integer nftType, @Param("extId")BigInteger extId);
    @Delete("delete from ts_favorite_info where token_id=#{tokenId} and user_address=#{address} and nft_type=#{nftType} ")
    int deleteFavorite(@Param("tokenId") BigInteger tokenId, @Param("address")String address,@Param("nftType") Integer nftType);
    @Select("select id, token_id, ext_id, user_address, create_time, nft_type from ts_favorite_info where token_id=#{tokenId} and user_address=#{address} and nft_type=#{nftType}")
    NftFavoriteInfoDto getFavoriteInfo(@Param("tokenId") BigInteger tokenId, @Param("address")String address,@Param("nftType") Integer nftType);
    @Select("<script>" +
            " select tokenId,ownerAddress,price,create_time as createTime,t_version as version from ts_selling_order " +
            "where  tokenId=#{tokenId} and nft_type=#{nftType} for update " +
            "</script>")
    NftSellInfoDto lockSellingTokenById(@Param("tokenId")BigInteger tokenId,@Param("nftType") Integer nftType);

    @Select("<script>" +
            " select good_id,id,ownerAddress,price,num,create_time as createTime,t_version as version from ts_selling_order_mul " +
            "where good_id=#{goodId} and nft_type=#{nftType} for update " +
            "</script>")
    NftSellInfoDto lockSellingTokenByIdMul(@Param("goodId")BigInteger goodId, @Param("nftType")Integer nftType);
    @Delete(" delete from ts_selling_order where tokenId =#{tokenId} and ownerAddress=#{fromAddress}  and nft_type=#{nftType} ")
    int deleteSellingOrder(@Param("tokenId")BigInteger tokenId, @Param("fromAddress")String fromAddress, @Param("nftType") Integer nftType);
    @Delete("<script> delete from ts_selling_order where tokenId =#{tokenId} and ownerAddress=#{address} and nft_type=#{nftType}</script>")
    int deleteSellingOrderByTokenIdAndAddress(@Param("tokenId")BigInteger tokenId,String address,@Param("nftType") Integer nftType);
    @Delete("<script> delete from ts_selling_order_mul where good_id =#{goodId}</script>")
    int deleteSellingOrderByTokenIdAndAddressMul(@Param("goodId")BigInteger goodId);
    @Delete(" delete from ts_selling_order_mul where good_id =#{goodId}")
    int deleteSellingOrderMul(@Param("goodId")BigInteger goodId);

    @Select("<script>" +
            "select good_id, id, ownerAddress, price, num, create_time, t_version, nft_type from ts_selling_order_mul where good_id = #{goodId}" +
            "</script>")
    Map<String, Object> getSellMulInfo(@Param("goodId")BigInteger goodId);
}
