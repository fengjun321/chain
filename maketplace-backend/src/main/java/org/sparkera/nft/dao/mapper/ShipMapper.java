package org.sparkera.nft.dao.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.sparkera.nft.dao.dto.NftCardInfoDto;
import org.sparkera.nft.dao.dto.NftCardQueryDto;
import org.sparkera.nft.dao.dto.NftShipInfoDto;
import org.sparkera.nft.dao.entity.BaseEntityInfo;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Mapper
public interface ShipMapper extends NFTBaseMapper{
    @Update("update ts_nft_ship_info set views=views+1 where token_id=#{tokenId}")
    int addViewsByTokenId(@Param("tokenId") Integer tokenId);

    @Select("<script>" +
                "select `token_id`, `owner_address`,`views`, `favorites`, `itemData`,`type`,`tokenURI`,`version`,`releaseVersion`,`name`, `image`, `model`, `description`," +
                " `ship_name`, `rank`, `level`, `power`, `basic_limit`, `basic_current`,`leader_limit`, `leader_current`," +
                " `scientist_limit`, `scientist_current`, `commander_limit`, `commander_current`," +
                " `start_soldier_limit`, `start_soldier_current`, `common_soldier_limit`, `common_soldier_current`,b.`price`," +
                "  (select count(1) from ts_favorite_info f where f.token_id = a.`token_id` and user_address=#{dto.address} and f.nft_type=4 )as isFavorite," +
                " IFNULL(b.tokenId,0) as isSelling " +
                "from ts_selling_order b left join ts_nft_ship_info a on a. `token_id` = b.tokenId and b.nft_type = 4 " +
                "where b.nft_type = 4 and b.ownerAddress = a.owner_address" +
                "   <if test='dto.searchKey != null and dto.searchKey != \"\" ' > " +
                "   and  (a.`token_id` like concat('%',#{dto.searchKey},'%') or lower(a.`ship_name`) like concat('%',lower(#{dto.searchKey}),'%') )" +
                "   </if>" +
                "   <if test='dto.rarity != null ' > " +
                "   and  a.`rarity` = #{dto.rarity}" +
                "   </if>" +
                "   <if test='dto.level != null ' > " +
                "   and  a.`level` = #{dto.level}" +
                "   </if>" +
                "   <if test='dto.rank != null ' > " +
                "   and  a.`rank` = #{dto.rank}" +
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
                "           order by a.`total_attrs` "+
                "       </when> " +
                "       <otherwise> order by b.`price` </otherwise>"+
                "   </choose>" +
                "   <choose> " +
                "       <when test='dto.sortType ==0'> " +
                "            asc "+
                "       </when>"+
                "       <otherwise>  desc </otherwise>"+
                "   </choose>" +
            " </script> ")
    List<Map<String,Object>> tradingMarket(Page<Map<String,Object>> page, @Param("dto") NftCardQueryDto dto);

    @Select("<script>" +
            "select `token_id`, `owner_address`,`views`, `favorites`, `itemData`,`type`,`tokenURI`,`version`,`releaseVersion`,`name`, `image`, `model`, `description`," +
            " `ship_name`, `rank`, `level`, `power`, `basic_limit`, `basic_current`,`leader_limit`, `leader_current`," +
            " `scientist_limit`, `scientist_current`, `commander_limit`, `commander_current`," +
            " `start_soldier_limit`, `start_soldier_current`, `common_soldier_limit`, `common_soldier_current`, b.`price`," +
            " (select count(1) from ts_favorite_info f where f.token_id = a.`token_id` and user_address=#{dto.address} and f.nft_type=4 )as isFavorite, " +
            " IFNULL(b.tokenId,0) as isSelling " +
            "from ts_nft_ship_info a left join ts_selling_order b on a.`token_id` = b.tokenId and b.nft_type = 4 " +
            "where `owner_address` = #{dto.address}" +
            "   <if test='dto.searchKey != null and dto.searchKey != \"\" ' > " +
            "   and  (a.`token_id` like concat('%',#{dto.searchKey},'%') or lower(a.`ship_name`) like concat('%',lower(#{dto.searchKey}),'%') )" +
            "   </if>" +
            "   <if test='dto.rarity != null ' > " +
            "   and  a.`rarity` = #{dto.rarity}" +
            "   </if>" +
            "   <if test='dto.level != null ' > " +
            "   and  a.`level` = #{dto.level}" +
            "   </if>" +
            "   <if test='dto.rank != null ' > " +
            "   and  a.`rank` = #{dto.rank}" +
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
            "           order by a.`total_attrs` "+
            "       </when> " +
            "       <otherwise> order by b.`price` </otherwise>"+
            "   </choose>" +
            "   <choose> " +
            "       <when test='dto.sortType ==0'> " +
            "            asc "+
            "       </when>"+
            "       <otherwise>  desc </otherwise>"+
            "   </choose>" +
            " </script> ")
    List<Map<String,Object>> myStorage(Page<Map<String,Object>> page, @Param("dto") NftCardQueryDto dto);

    @Select("<script>" +
            "select `token_id`, `owner_address`,`views`, `favorites`, `itemData`,`type`,`tokenURI`,`version`,`releaseVersion`,`name`, `image`, `model`, `description`," +
            " `ship_name`, `rank`, `level`, `power`, `basic_limit`, `basic_current`,`leader_limit`, `leader_current`," +
            " `scientist_limit`, `scientist_current`, `commander_limit`, `commander_current`," +
            " `start_soldier_limit`, `start_soldier_current`, `common_soldier_limit`, `common_soldier_current`, b.`price`," +
            " (select count(1) from ts_favorite_info f where f.token_id = a.`token_id` and user_address=#{dto.address} and f.nft_type=4 )as isFavorite, " +
            " IFNULL(b.tokenId,0) as isSelling " +
            "from  ts_selling_order b left join ts_nft_ship_info a on a.`token_id` = b.tokenId and b.nft_type = 4 " +
            "where `owner_address` = #{dto.address} and b.nft_type=4 " +
            "   <if test='dto.searchKey != null and dto.searchKey != \"\" ' > " +
            "   and  (a.`token_id` like concat('%',#{dto.searchKey},'%') or lower(a.`ship_name`) like concat('%',lower(#{dto.searchKey}),'%') )" +
            "   </if>" +
            "   <if test='dto.rarity != null ' > " +
            "   and  a.`rarity` = #{dto.rarity}" +
            "   </if>" +
            "   <if test='dto.level != null ' > " +
            "   and  a.`level` = #{dto.level}" +
            "   </if>" +
            "   <if test='dto.rank != null ' > " +
            "   and  a.`rank` = #{dto.rank}" +
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
            "           order by a.`total_attrs` "+
            "       </when> " +
            "       <otherwise> order by b.`price` </otherwise>"+
            "   </choose>" +
            "   <choose> " +
            "       <when test='dto.sortType ==0'> " +
            "            asc "+
            "       </when>"+
            "       <otherwise>  desc </otherwise>"+
            "   </choose>" +
            " </script> ")
    List<Map<String, Object>> selling(Page<Map<String, Object>> page, @Param("dto") NftCardQueryDto dto);

    @Select("<script>" +
            "select a.`token_id`, `owner_address`,`views`, `favorites`,`itemData`,`type`,`tokenURI`, `version`,`releaseVersion`,`name`, `image`, `model`, `description`," +
            " `ship_name`, `rank`, `level`, `power`, `basic_limit`, `basic_current`,`leader_limit`, `leader_current`," +
            " `scientist_limit`, `scientist_current`, `commander_limit`, `commander_current`," +
            " `start_soldier_limit`, `start_soldier_current`, `common_soldier_limit`, `common_soldier_current`, b.`price`," +
            " IFNULL(b.tokenId,0) as isSelling " +
            "from  ts_favorite_info c left join ts_nft_ship_info a on a.`token_id` = c.token_id and c.nft_type = 4 " +
            "  left join ts_selling_order b on a.`token_id` = b.tokenId and c.nft_type=b.nft_type  " +
            "where c.`user_address` = #{dto.address} and b.nft_type=4 " +
            "   <if test='dto.searchKey != null and dto.searchKey != \"\" ' > " +
            "   and  (a.`token_id` like concat('%',#{dto.searchKey},'%') or lower(a.`ship_name`) like concat('%',lower(#{dto.searchKey}),'%') )" +
            "   </if>" +
            "   <if test='dto.rarity != null ' > " +
            "   and  a.`rarity` = #{dto.rarity}" +
            "   </if>" +
            "   <if test='dto.level != null ' > " +
            "   and  a.`level` = #{dto.level}" +
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
            "           order by a.`total_attrs` "+
            "       </when> " +
            "       <otherwise> order by b.`price` </otherwise>"+
            "   </choose>" +
            "   <choose> " +
            "       <when test='dto.sortType ==0'> " +
            "            asc "+
            "       </when>"+
            "       <otherwise>  desc </otherwise>"+
            "   </choose>" +
            " </script> ")
    List<Map<String, Object>> myFavorite(Page<Map<String, Object>> page, NftCardQueryDto dto);

    @Select("select `token_id`, `owner_address`,`views`, `favorites`,`itemData`,`type`,`tokenURI`,`version`,`releaseVersion`, `name`, `image`, `model`, `description`," +
            " `ship_name`, `rank`, `level`, `power`, `basic_limit`, `basic_current`,`leader_limit`, `leader_current`," +
            " `scientist_limit`, `scientist_current`, `commander_limit`, `commander_current`," +
            " `start_soldier_limit`, `start_soldier_current`, `common_soldier_limit`, `common_soldier_current`, b.`price`," +
            " (select count(1) from ts_favorite_info f where f.token_id = a.`token_id` and user_address=#{address} and f.nft_type=4 )as isFavorite, " +
            " IFNULL(b.tokenId,0) as isSelling " +
            "from ts_nft_ship_info a left join ts_selling_order b on a.`token_id` = b.tokenId and b.nft_type = 4 " +
            "where `token_id` = #{tokenId}")
    Map<String, Object> tokenDetail(@Param("tokenId") BigInteger tokenId, @Param("address") String address);

    @Select("<script>" +
            "select `token_id` as tokenId, `owner_address` as ownerAddress, count(b.tokenId) as ct, `views`, `favorites`,`itemData`,`type`,`tokenURI`,`version`,`releaseVersion`, `name`, `image`, `model`, `description`," +
            " `ship_name`, `rank`, `level`, `power`, `basic_limit`,`basic_current`, `leader_limit`, `leader_current`," +
            " `scientist_limit`, `scientist_current`, `commander_limit`, `commander_current`," +
            " `start_soldier_limit`, `start_soldier_current`, `common_soldier_limit`, `common_soldier_current` " +
            "from ts_nft_ship_info a left join ts_selling_order b on a.`token_id` = b.tokenId and b.nft_type = 4 " +
            "where a.`token_id` = #{tokenId}" +
            " </script> ")
    Map<String, Object> checkToken(@Param("tokenId")BigInteger tokenId);

    @Update("update ts_nft_ship_info set favorites =favorites + #{ct} where token_id =#{tokenId} ")
    int updateNftFavorites(@Param("tokenId")BigInteger tokenId, @Param("ct")int ct);

    @Select("<script>" +
            "select `token_id`, `owner_address`,`views`, `favorites`,`itemData`,`type`,`tokenURI`,`version`,`releaseVersion`, `name`, `image`, `model`, `description`," +
            " `ship_name`, `rank`, `level`, `power`, `basic_limit`,`basic_current`, `leader_limit`, `leader_current`," +
            " `scientist_limit`, `scientist_current`, `commander_limit`, `commander_current`," +
            " `start_soldier_limit`, `start_soldier_current`, `common_soldier_limit`, `common_soldier_current`, " +
            " `last_trade_price`, `last_trade_time` " +
            "from ts_nft_ship_info " +
            "where `token_id` = #{tokenId}" +
            " </script> ")
    NftShipInfoDto getNftInfoByTokenId(@Param("tokenId")BigInteger tokenId);

    @Update(" replace into `ts_nft_ship_info`" +
            " (`token_id`, `owner_address`,`views`, `favorites`,`itemData`,`type`,`tokenURI`, `version`,`releaseVersion`,`name`, `image`, `model`, `description`," +
            " `ship_name`, `rank`, `level`, `power`, `basic_limit`, `basic_current`,`leader_limit`, `leader_current`," +
            " `scientist_limit`, `scientist_current`, `commander_limit`, `commander_current`," +
            " `start_soldier_limit`, `start_soldier_current`, `common_soldier_limit`, `common_soldier_current`," +
            " `last_trade_price`, `last_trade_time`) " +
            "VALUES(#{dto.tokenId},#{dto.ownerAddress},#{dto.views},#{dto.favorites},#{dto.itemData},#{dto.type},#{dto.tokenURI}," +
            " #{dto.version},#{dto.releaseVersion},#{dto.name},#{dto.image},#{dto.model},#{dto.description},#{dto.shipName},#{dto.rank}," +
            " #{dto.level},#{dto.power},#{dto.basicLimit},#{dto.basicCurrent},#{dto.leaderLimit},#{dto.leaderCurrent},#{dto.scientistLimit},#{dto.scientistCurrent}," +
            " #{dto.commanderLimit},#{dto.commanderCurrent},#{dto.startSoldierLimit},#{dto.startSoldierCurrent},#{dto.commonSoldierLimit}," +
            " #{dto.commonSoldierCurrent},#{dto.lastTradePrice},#{dto.lastTradeTime}) "
            )
    int upInsetNftInfo(@Param("dto") BaseEntityInfo nftShipnfoDto);

    @Update("<script>" +
            " update ts_nft_ship_info set last_trade_price =#{price}" +
            " <if test='tradeTime!=null'>" +
            " ,last_trade_time=#{tradeTime}" +
            "</if>" +
            " where token_id=#{tokenId}" +
            " </script>")
    int updateNftLastTradePrice(@Param("tokenId")BigInteger tokenId, @Param("price") BigDecimal price, @Param("tradeTime") Timestamp dateTime);
}
