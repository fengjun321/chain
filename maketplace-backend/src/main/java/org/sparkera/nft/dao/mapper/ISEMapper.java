package org.sparkera.nft.dao.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.*;
import org.sparkera.nft.dao.dto.NftCardQueryDto;
import org.sparkera.nft.dao.dto.NftCardTradeDto;
import org.sparkera.nft.dao.dto.NftIseInfoDto;
import org.sparkera.nft.dao.dto.NftTradeMulDto;
import org.sparkera.nft.dao.entity.BaseEntityInfo;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Mapper
public interface ISEMapper extends NFTBaseMapper{
    @Update("update ts_nft_ise_info set views=views+1 where id=#{id} and owner_address=#{address}")
    int addViewsByTokenIdMul(@Param("id") Integer id, @Param("address") String address);

    @Select("<script>" +
            "select b.good_id, a.id, owner_address, views, favorites, itemData, tokenURI, name, image, detail_image," +
            "   description, version, releaseVersion, last_trade_price, last_trade_time, b.price, data, rank, cnt," +
            "   (select count(1) from ts_favorite_info f where f.token_id = b.good_id and user_address=#{dto.address} and f.nft_type=8 )as isFavorite," +
            "   IFNULL(b.id,0) as isSelling " +
            "from ts_selling_order_mul b left join ts_nft_ise_info a on a.id = b.id and a.owner_address = b.ownerAddress" +
            "    and b.nft_type = 8 " +
            "where b.nft_type = 8 and b.ownerAddress = a.owner_address " +
            "   <if test='dto.searchKey != null and dto.searchKey != \"\" ' > " +
            "   and lower(a.name) like concat('%',lower(#{dto.searchKey}),'%') )" +
            "   </if>" +
            "   <if test='dto.type != null ' > " +
            "   and a.type = #{dto.type}" +
            "   </if>" +
            "   <choose> " +
            "       <when test='dto.sortFiled ==0'> " +
            "           order by b.price "+
            "       </when>"+
            "       <when test='dto.sortFiled ==1'> " +
            "           order by token_id "+
            "       </when>"+
            "       <when test='dto.sortFiled ==2'> " +
            "           order by a.views "+
            "       </when>"+
            "       <when test='dto.sortFiled ==3'> " +
            "           order by a.favorites "+
            "       </when>"+
            "       <when test='dto.sortFiled ==4'> " +
            "           order by b.create_time "+
            "       </when> " +
            "       <when test='dto.sortFiled ==5'> " +
            "           order by a.total_attrs "+
            "       </when> " +
            "       <otherwise> order by b.price </otherwise>"+
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
            "select * from" +
            "   (select * from (select 0 as good_id, a.id, owner_address, views, favorites, itemData, tokenURI, name, image, detail_image, 0 as price," +
            "       description, version, releaseVersion, last_trade_price, last_trade_time, data, rank, (cnt - IFNULL(sum_cnt, 0)) as cnt, 0 as isFavorite," +
            "       0 as isSelling, \"\" as create_time" +
            "    from ts_nft_ise_info a left join (select id, ownerAddress, sum(num) as sum_cnt, sum(price) as price, nft_type from ts_selling_order_mul group by " +
            "       id, ownerAddress, nft_type) g on a.id = g.id and a.owner_address = g.ownerAddress and g.nft_type = 8 " +
            "    where owner_address = #{dto.address}) t where cnt > 0" +
            "   union all " +
            "    select g.good_id, a.id, owner_address, views, favorites, itemData, tokenURI, name, image, detail_image, g.price, " +
            "       description, version, releaseVersion, last_trade_price, last_trade_time, data, rank, g.num as cnt, " +
            "       (select count(1) from ts_favorite_info f where f.token_id = g.good_id and user_address=#{dto.address} and f.nft_type=8) as isFavorite," +
            "       IFNULL(g.id,0) as isSelling, create_time " +
            "    from ts_nft_ise_info a right join (select good_id, id, ownerAddress, price, num, create_time, nft_type from ts_selling_order_mul) g on a.id = g.id and a.owner_address = g.ownerAddress and g.nft_type = 8" +
            "    where owner_address = #{dto.address}" +
            ") e " +
            "   <if test='dto.searchKey != null and dto.searchKey != \"\" ' > " +
            "   and lower(e.name) like concat('%',lower(#{dto.searchKey}),'%') )" +
            "   </if>" +
            "   <if test='dto.type != null ' > " +
            "   and e.type = #{dto.type}" +
            "   </if>" +
            "   <choose> " +
            "       <when test='dto.sortFiled ==0'> " +
            "           order by e.price "+
            "       </when>"+
            "       <when test='dto.sortFiled ==1'> " +
            "           order by e.id "+
            "       </when>"+
            "       <when test='dto.sortFiled ==2'> " +
            "           order by e.views "+
            "       </when>"+
            "       <when test='dto.sortFiled ==3'> " +
            "           order by e.favorites "+
            "       </when>"+
            "       <when test='dto.sortFiled ==4'> " +
            "           order by e.create_time "+
            "       </when> " +
            "       <when test='dto.sortFiled ==5'> " +
            "           order by e.total_attrs "+
            "       </when> " +
            "       <otherwise> order by e.price </otherwise>"+
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
            "select b.good_id, a.id, owner_address, views, favorites, itemData, tokenURI, name, image, detail_image," +
            "   description, version, releaseVersion, last_trade_price, last_trade_time, b.price, data, rank, cnt," +
            "   (select count(1) from ts_favorite_info f where f.token_id = b.good_id and user_address=#{dto.address} and f.nft_type=8  )as isFavorite," +
            "   IFNULL(b.id,0) as isSelling " +
            "from ts_selling_order_mul b left join ts_nft_ise_info a on a.id = b.id and a.owner_address = b.ownerAddress" +
            "    and b.nft_type = 8 " +
            "where owner_address = #{dto.address} and b.nft_type=8 " +
            "   <if test='dto.searchKey != null and dto.searchKey != \"\" ' > " +
            "   and lower(a.name) like concat('%',lower(#{dto.searchKey}),'%') )" +
            "   </if>" +
            "   <if test='dto.type != null ' > " +
            "   and a.type = #{dto.type}" +
            "   </if>" +
            "   <choose> " +
            "       <when test='dto.sortFiled ==0'> " +
            "           order by b.price "+
            "       </when>"+
            "       <when test='dto.sortFiled ==1'> " +
            "           order by b.id "+
            "       </when>"+
            "       <when test='dto.sortFiled ==2'> " +
            "           order by a.views "+
            "       </when>"+
            "       <when test='dto.sortFiled ==3'> " +
            "           order by a.favorites "+
            "       </when>"+
            "       <when test='dto.sortFiled ==4'> " +
            "           order by b.create_time "+
            "       </when> " +
            "       <when test='dto.sortFiled ==5'> " +
            "           order by a.total_attrs "+
            "       </when> " +
            "       <otherwise> order by b.price </otherwise>"+
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
            "select b.good_id, a.id, owner_address, views, favorites, itemData, tokenURI, name, image, detail_image," +
            "   description, version, releaseVersion, last_trade_price, last_trade_time, b.price, b.num, data, rank, cnt," +
            "   IFNULL(b.id,0) as isSelling " +
            "from ts_favorite_info c left join ts_nft_ise_info a on c.ext_id = a.tbId " +
            "   left join ts_selling_order_mul b on c.token_id = b.good_id and c.nft_type = b.nft_type " +
            "where c.user_address = #{dto.address} and c.nft_type = 8" +
            "   <if test='dto.searchKey != null and dto.searchKey != \"\" ' > " +
            "   and lower(a.name) like concat('%',lower(#{dto.searchKey}),'%') )" +
            "   </if>" +
            "   <if test='dto.type != null ' > " +
            "   and a.type = #{dto.type}" +
            "   </if>" +
            "   <choose> " +
            "       <when test='dto.sortFiled ==0'> " +
            "           order by b.price "+
            "       </when>"+
            "       <when test='dto.sortFiled ==1'> " +
            "           order by b.id "+
            "       </when>"+
            "       <when test='dto.sortFiled ==2'> " +
            "           order by a.views "+
            "       </when>"+
            "       <when test='dto.sortFiled ==3'> " +
            "           order by a.favorites "+
            "       </when>"+
            "       <when test='dto.sortFiled ==4'> " +
            "           order by b.create_time "+
            "       </when> " +
            "       <when test='dto.sortFiled ==5'> " +
            "           order by a.total_attrs "+
            "       </when> " +
            "       <otherwise> order by b.price </otherwise>"+
            "   </choose>" +
            "   <choose> " +
            "       <when test='dto.sortType ==0'> " +
            "            asc "+
            "       </when>"+
            "       <otherwise>  desc </otherwise>"+
            "   </choose>" +
            " </script> ")
    List<Map<String, Object>> myFavorite(Page<Map<String, Object>> page, NftCardQueryDto dto);

    @Select("<script>" +
            "select * from" +
            "   (select 0 as good_id, a.id, owner_address, views, favorites, itemData, tokenURI, name, image, detail_image, 0 as price," +
            "       description, version, releaseVersion, last_trade_price, last_trade_time, data, rank, (cnt - IFNULL(sum_cnt, 0)) as cnt, 0 as isFavorite," +
            "       0 as isSelling, \"\" as create_time" +
            "    from ts_nft_ise_info a left join (select id, ownerAddress, sum(num) as sum_cnt, sum(price) as price, nft_type from ts_selling_order_mul group by " +
            "       id, ownerAddress, nft_type) g on a.id = g.id and a.owner_address = g.ownerAddress and g.nft_type = 8 " +
            "    where owner_address = #{ownerAddress}" +
            "   union all " +
            "    select g.good_id, a.id, owner_address, views, favorites, itemData, tokenURI, name, image, detail_image, g.price, " +
            "       description, version, releaseVersion, last_trade_price, last_trade_time, data, rank, g.num as cnt, " +
            "       (select count(1) from ts_favorite_info f where f.token_id = g.good_id and user_address=#{address} and f.nft_type=8) as isFavorite," +
            "       IFNULL(g.id,0) as isSelling, create_time " +
            "    from ts_nft_ise_info a right join (select good_id, id, ownerAddress, price, num, create_time, nft_type from ts_selling_order_mul) g on a.id = g.id and a.owner_address = g.ownerAddress and g.nft_type = 8" +
            "    where owner_address = #{ownerAddress}" +
            ") e " +
            "where e.good_id = #{goodId} and e.id = #{id}" +
            " </script> ")
    Map<String, Object> tokenDetail2(@Param("id") BigInteger tokenId, @Param("address") String address, @Param("ownerAddress") String ownerAddress, @Param("goodId")BigInteger goodId);

    @Select("<script>" +
            "select a.id, owner_address as ownerAddress, b.sum_cnt as ct, views, favorites, itemData, tokenURI, name, image, detail_image," +
            "   description, version, releaseVersion, last_trade_price, last_trade_time, b.sum_price, data, rank, cnt," +
            "   IFNULL(b.id,0) as isSelling " +
            "from ts_nft_ise_info a left join (select id, ownerAddress, sum(num) as sum_cnt, sum(price) as sum_price, nft_type " +
            "   from ts_selling_order_mul group by id, ownerAddress, nft_type) b " +
            "on a.id = b.id and a.owner_address = b.ownerAddress and b.nft_type = 8 " +
            "where a.id = #{id} and a.owner_address = #{address} " +
            " </script> ")
    Map<String, Object> checkToken(@Param("id")Integer id, @Param("address") String address);

    @Update("update ts_nft_ise_info set favorites =favorites + #{ct} where id =#{id} and owner_address = #{address} ")
    int updateNftFavoritesMul(@Param("id")Integer id, @Param("address") String address,  @Param("ct")int ct);

    @Select("<script>" +
            "select tbId, id, owner_address, views, favorites, itemData, tokenURI, name, image, detail_image," +
            "   description, version, releaseVersion, last_trade_price, last_trade_time, data, rank, cnt " +
            "from ts_nft_ise_info " +
            "where id = #{id} and owner_address = #{address}" +
            "</script>")
    NftIseInfoDto getNftInfoByTokenIdMul(@Param("id")Integer id, @Param("address") String address);

    @Select("<script>" +
            "select tbId, id, owner_address, views, favorites, itemData, tokenURI, name, image, detail_image," +
            "   description, version, releaseVersion, last_trade_price, last_trade_time, data, rank, cnt " +
            "from ts_nft_ise_info " +
            "where tbId = #{tbId}" +
            "</script>")
    NftIseInfoDto getNftInfoByTbId(@Param("tbId") BigInteger tbId);

    @Update("replace into ts_nft_ise_info" +
            "   (tbId, id, owner_address, views, favorites, itemData, tokenURI, name, image, detail_image, description, version," +
            "   releaseVersion, last_trade_price, last_trade_time, data, rank, cnt)" +
            "values(#{dto.tbId}, #{dto.id}, #{dto.ownerAddress}, #{dto.views}, #{dto.favorites}, #{dto.itemData}, #{dto.tokenURI}," +
            "   #{dto.name}, #{dto.image}, #{dto.detailImage}, #{dto.description}, #{dto.version}, #{dto.releaseVersion}," +
            "   #{dto.lastTradePrice}, #{dto.lastTradeTime}, #{dto.data}, #{dto.rank}, #{dto.cnt})")
    int upInsetNftInfo(@Param("dto") BaseEntityInfo nftIseInfoDto);

    @Update("<script>" +
            " update ts_nft_ise_info set last_trade_price =#{price}" +
            " <if test='tradeTime!=null'>" +
            " ,last_trade_time=#{tradeTime}" +
            "</if>" +
            " where id=#{id} and owner_address=#{address}" +
            " </script>")
    int updateNftLastTradePrice(@Param("id")Integer id, @Param("address") String address, @Param("price") BigDecimal price, @Param("tradeTime") Timestamp dateTime);

    @Insert("insert into ts_nft_trade_info_mul (unique_id, ext_id, cnt, owner_address, last_trade_price, last_trade_time, image, nft_type) values (" +
            "#{dto.unique_id}, #{dto.ext_id}, #{dto.cnt}, #{dto.owner_address}, #{dto.last_trade_price}, #{dto.last_trade_time}, #{dto.image}, #{dto.nft_type})")
    int updateNftLastTradePriceMul(@Param("dto")NftTradeMulDto nftTradeDto);

    @Insert("<script> INSERT INTO ts_selling_order_mul(id, ownerAddress, price, num, create_time, t_version, nft_type) " +
            "        VALUES(#{dto.id}, #{dto.ownerAddress}, #{dto.price}, #{dto.cnt}, now() , 0, #{dto.nftType}) </script>")
    int sellToken(@Param("dto") NftCardTradeDto nftCardInfo);

    @Delete("<script> delete from ts_selling_order_mul where good_id =#{goodId}</script>")
    int deleteSellingOrderByTokenIdAndAddressMul(@Param("goodId")BigInteger goodId);

    @Delete("<script> delete from ts_selling_order_mul where ownerAddress=#{address} and nft_type=#{nftType}</script>")
    int deleteAllIseNftByAddress(@Param("address")String address,@Param("nftType") Integer nftType);
}
