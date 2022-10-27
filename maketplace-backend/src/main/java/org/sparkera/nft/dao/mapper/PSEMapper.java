package org.sparkera.nft.dao.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.sparkera.nft.dao.dto.NftCardQueryDto;
import org.sparkera.nft.dao.dto.NftHseInfoDto;
import org.sparkera.nft.dao.dto.NftPseInfoDto;
import org.sparkera.nft.dao.entity.BaseEntityInfo;
import org.sparkera.nft.dao.entity.NftPseInfo;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Mapper
public interface PSEMapper extends NFTBaseMapper{
    @Update("update ts_nft_pse_info set views=views+1 where token_id=#{tokenId}")
    int addViewsByTokenId(@Param("tokenId") Integer tokenId);
    @Select("<script>" +
            " select   a.`token_id` as tokenId, a.`owner_address` as ownerAddress,a.`type`,a.`tokenURI`, a.`name`, a.`image`, a.`description`, a.`attr_type` as attrType," +
            " a.`views`, a.`favorites`,(select count(1) from ts_favorite_info f where f.token_id = a.`token_id` and user_address=#{dto.address} and f.nft_type=2 )as isFavorite, " +
            " b.`price` " +
            " from  ts_selling_order b left join ts_nft_pse_info a  on a. `token_id` = b.tokenId and b.nft_type=2 " +
            " where b.nft_type=2 and b.ownerAddress = a.owner_address " +
            "   <if test='dto.searchKey != null and dto.searchKey != \"\" ' > " +
            "   and  (a.`token_id` like concat('%',#{dto.searchKey},'%') or lower(a.`attr_type`) like concat('%',lower(#{dto.searchKey}),'%') )" +
            "   </if>" +
            "   <if test='dto.type != null ' > " +
            "   and  a.`type` = #{dto.type}" +
            "   </if>" +
            "   <choose> " +
            "       <when test='dto.sortFiled ==0'> " +
            "           order by b.`price` "+
            "       </when>"+
            "       <when test='dto.sortFiled ==1'> " +
            "           order by `token_id` "+
            "       </when>"+
            "       <when test='dto.sortFiled ==2'> " +
            "           order by a.`views` "+
            "       </when>"+
            "       <when test='dto.sortFiled ==3'> " +
            "           order by a.`favorites` "+
            "       </when>"+
            "       <when test='dto.sortFiled ==4'> " +
            "           order by b.`create_time` "+
            "       </when> " +
            "       <when test='dto.sortFiled ==5'> " +
            "           order by a.`type` "+
            "       </when> " +
            "       <otherwise> order by b.`price` </otherwise>"+
            "   </choose>" +
            "   <choose> " +
            "       <when test='dto.sortType ==0'> " +
            "            asc "+
            "       </when>"+
            "       <otherwise>  desc </otherwise>"+
            "   </choose>" +
            "</script>" )
    List<Map<String,Object>> tradingMarket(Page<Map<String,Object>> page, @Param("dto") NftCardQueryDto dto);

    @Select("<script>" +
            "select `token_id`as tokenId, `owner_address` as ownerAddress, `type`,"+
            "   `name`, `image`, `description`, `attr_type` as attrType,b.`price` ," +
            "     `views`, `favorites` ,IFNULL(b.tokenId,0) as isSelling," +
            "   (select count(1) from ts_favorite_info f where f.token_id = a.`token_id` and user_address=#{dto.address} and f.nft_type=2 )as isFavorite " +
            "        from ts_nft_pse_info a   left join ts_selling_order b on a. `token_id` = b.tokenId and b.nft_type=2" +
            "        where `owner_address` = #{dto.address}" +
            "   <if test='dto.searchKey != null and dto.searchKey != \"\" ' > " +
            "   and  (a.`token_id` like concat('%',#{dto.searchKey},'%') or lower(a.`attr_type`) like concat('%',lower(#{dto.searchKey}),'%') )" +
            "   </if>" +
            "   <if test='dto.type != null ' > " +
            "   and  a.`type` = #{dto.type}" +
            "   </if>" +
            "   <choose> " +
            "       <when test='dto.sortFiled ==0'> " +
            "           order by b.`price` "+
            "       </when>"+
            "       <when test='dto.sortFiled ==1'> " +
            "           order by a.`token_id` "+
            "       </when>"+
            "       <when test='dto.sortFiled ==2'> " +
            "           order by a.`views` "+
            "       </when>"+
            "       <when test='dto.sortFiled ==3'> " +
            "           order by a.`favorites` "+
            "       </when>"+
            "       <when test='dto.sortFiled ==4'> " +
            "           order by b.`create_time` "+
            "       </when> " +
            "       <when test='dto.sortFiled ==5'> " +
            "           order by a.`type` "+
            "       </when> " +
            "       <otherwise> order by b.`price` </otherwise>"+
            "   </choose>" +
            "   <choose> " +
            "       <when test='dto.sortType ==0'> " +
            "            asc "+
            "       </when>"+
            "       <otherwise>  desc </otherwise>"+
            "   </choose>" +
            "</script>")
    List<Map<String,Object>> myStorage(Page<Map<String,Object>> page, @Param("dto") NftCardQueryDto dto);
    @Select("<script>" +
            "select `token_id`as tokenId, `owner_address` as ownerAddress, `type`,"+
            "   `name`, `image`, `description`, `attr_type` as attrType,b.`price` ," +
            "     `views`, `favorites` ,IFNULL(b.tokenId,0) as isSelling," +
            "   (select count(1) from ts_favorite_info f where f.token_id = a.`token_id` and user_address=#{dto.address} and f.nft_type=2 )as isFavorite " +
            "        from ts_selling_order b left join ts_nft_pse_info a  on a. `token_id` = b.tokenId \n" +
            "        where b.`ownerAddress` = #{dto.address} and b.nft_type=2" +
            "   <if test='dto.searchKey != null and dto.searchKey != \"\" ' > " +
            "   and  (a.`token_id` like concat('%',#{dto.searchKey},'%') or lower(a.`attr_type`) like concat('%',lower(#{dto.searchKey}),'%') )" +
            "   </if>" +
            "   <if test='dto.type != null ' > " +
            "   and  a.`type` = #{dto.type}" +
            "   </if>" +
            "   <choose> " +
            "       <when test='dto.sortFiled ==0'> " +
            "           order by b.`price` "+
            "       </when>"+
            "       <when test='dto.sortFiled ==1'> " +
            "           order by a.`token_id` "+
            "       </when>"+
            "       <when test='dto.sortFiled ==2'> " +
            "           order by a.`views` "+
            "       </when>"+
            "       <when test='dto.sortFiled ==3'> " +
            "           order by a.`favorites` "+
            "       </when>"+
            "       <when test='dto.sortFiled ==4'> " +
            "           order by b.`create_time` "+
            "       </when> " +
            "       <when test='dto.sortFiled ==5'> " +
            "           order by a.`type` "+
            "       </when> " +
            "       <otherwise> order by b.`price` </otherwise>"+
            "   </choose>" +
            "   <choose> " +
            "       <when test='dto.sortType ==0'> " +
            "            asc "+
            "       </when>"+
            "       <otherwise>  desc </otherwise>"+
            "   </choose>" +
            "</script>")
    List<Map<String, Object>> selling(Page<Map<String, Object>> page, @Param("dto") NftCardQueryDto dto);

    @Select("<script>" +
            "select a.`token_id`as tokenId, `owner_address` as ownerAddress, `type`,"+
            "   `name`, `image`, `description`, `attr_type` as attrType,b.`price` ," +
            "     `views`, `favorites` ,IFNULL(b.tokenId,0) as isSelling " +
            "   from ts_favorite_info c left join ts_nft_pse_info a  on a.`token_id` = c.token_id " +
            "   left join ts_selling_order b on a.`token_id` = b.tokenId and c.nft_type=b.nft_type " +
            "    where c.nft_type = 2 and c.`user_address` = #{dto.address}" +
            "   <if test='dto.searchKey != null and dto.searchKey != \"\" ' > " +
            "   and  (a.`token_id` like concat('%',#{dto.searchKey},'%') or lower(a.`attr_type`) like concat('%',lower(#{dto.searchKey}),'%') )" +
            "   </if>" +
            "   <if test='dto.type != null ' > " +
            "   and  a.`type` = #{dto.type}" +
            "   </if>" +
            "   <choose> " +
            "       <when test='dto.sortFiled ==0'> " +
            "           order by b.`price` "+
            "       </when>"+
            "       <when test='dto.sortFiled ==1'> " +
            "           order by a.`token_id` "+
            "       </when>"+
            "       <when test='dto.sortFiled ==2'> " +
            "           order by a.`views` "+
            "       </when>"+
            "       <when test='dto.sortFiled ==3'> " +
            "           order by a.`favorites` "+
            "       </when>"+
            "       <when test='dto.sortFiled ==4'> " +
            "           order by b.`create_time` "+
            "       </when> " +
            "       <when test='dto.sortFiled ==5'> " +
            "           order by a.`type` "+
            "       </when> " +
            "       <otherwise> order by b.`price` </otherwise>"+
            "   </choose>" +
            "   <choose> " +
            "       <when test='dto.sortType ==0'> " +
            "            asc "+
            "       </when>"+
            "       <otherwise>  desc </otherwise>"+
            "   </choose>" +
            "</script>")
    List<Map<String, Object>> myFavorite(Page<Map<String, Object>> page, NftCardQueryDto dto);
    @Select("select `token_id`as tokenId, `owner_address` as ownerAddress, 2 as nftType,`type`, " +
            "   `name`, `detail_image` as image, `description`, `attr_type` as attrType,b.`price` ," +
            "    IFNULL(b.tokenId,0) as isSelling ,`views`, `favorites`," +
            "  (select count(1) from ts_favorite_info c where c.token_id=a.`token_id` and c.user_address=#{address} and c.nft_type=2) as isFavorite " +
            " from ts_nft_pse_info a left join ts_selling_order b on a. `token_id` = b.tokenId and b.nft_type=2 " +
            "        where `token_id` = #{tokenId}")
    Map<String, Object> tokenDetail(@Param("tokenId")BigInteger tokenId,@Param("address") String address);
    @Select("select a.`token_id`as tokenId, a.`owner_address` as ownerAddress,count(b.tokenId) as ct,type from ts_nft_pse_info a" +
            " left join ts_selling_order b on a. `token_id` = b.tokenId and b.nft_type=2     where a.`token_id` = #{tokenId}")
    Map<String, Object> checkToken(@Param("tokenId")BigInteger tokenId);
    @Update("update ts_nft_pse_info set favorites =favorites + #{ct} where token_id =#{tokenId} ")
    int updateNftFavorites(@Param("tokenId")BigInteger tokenId, @Param("ct")int ct);
    @Select("<script>" +
            " select  `token_id`, `owner_address`, `itemData`, `type`,`tokenURI`, `name`, `image`, `description`, `attr_type`, `views`, `favorites`, " +
            "   `last_trade_price`, `last_trade_time`, `detail_image` from ts_nft_pse_info " +
            " where `token_id` =#{tokenId}" +
            "</script>")
    NftPseInfoDto getNftInfoByTokenId(@Param("tokenId")BigInteger tokenId);
    @Update(" replace into `ts_nft_pse_info`" +
            " (`token_id`, `owner_address`, `itemData`, `type`,`tokenURI`, `name`, `image`, `description`, `attr_type`, " +
            "       `views`, `favorites`, `last_trade_price`, `last_trade_time`, `detail_image`) " +
            "VALUES(#{dto.tokenId},#{dto.ownerAddress},#{dto.itemData},#{dto.type},#{dto.tokenURI},#{dto.name},#{dto.image},#{dto.description},#{dto.attrType}," +
            "   #{dto.views},#{dto.favorites},#{dto.lastTradePrice},#{dto.lastTradeTime},#{dto.detailImage}) ")
    int upInsetNftInfo(@Param("dto")BaseEntityInfo nftPseInfoDto);
    @Update("<script>" +
            " update ts_nft_pse_info set last_trade_price =#{price}" +
            " <if test='tradeTime!=null'>" +
            " ,last_trade_time=#{tradeTime}" +
            "</if>" +
            " where token_id=#{tokenId}" +
            " </script>")
    int updateNftLastTradePrice(@Param("tokenId")BigInteger tokenId, @Param("price") BigDecimal price, @Param("tradeTime") Timestamp dateTime);
}
