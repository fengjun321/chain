package org.sparkera.nft.dao.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.sparkera.nft.dao.dto.NftCardQueryDto;
import org.sparkera.nft.dao.dto.NftStatisticsDto;
import org.sparkera.nft.dao.vo.ActivityNftVo;
import org.sparkera.nft.dao.vo.TopNftVO;

import java.util.List;
import java.util.Map;

@Mapper
public interface StatisticsMapper extends BaseMapper {

    @Select("<script>" +
            "select a.tokenId ,a.ownerAddress,price,image,nft_type as nftType from ( " +
            "  select `token_id` AS tokenId, `owner_address` AS ownerAddress,`last_trade_price` as price ," +
            "  `image`,0 as nft_type,camp ,rarity, null as level, null as _rank from ts_nft_card_info where `last_trade_price` &gt; 0" +
            " UNION all" +
            "  select `token_id` AS tokenId, `owner_address` AS ownerAddress,`last_trade_price` as price ," +
            "   `image`,1 as nft_type,null,null,null,null from ts_nft_hse_info  where `last_trade_price` &gt; 0 " +
            " UNION all " +
            "  select `token_id` AS tokenId, `owner_address` AS ownerAddress,`last_trade_price` as price ," +
            "  `image`,2 as nft_type,type,null,null,null  from ts_nft_pse_info where `last_trade_price` &gt; 0 " +
            " UNION all " +
            "  select `token_id` AS tokenId, `owner_address` AS ownerAddress,`last_trade_price` as price ," +
            "  `image`,3 as nft_type,type,null,null,null from ts_nft_egse_info where `last_trade_price` &gt; 0 " +
            " UNION all " +
            "  select `token_id` AS tokenId, `owner_address` AS ownerAddress,`last_trade_price` as price ," +
            "  `image`,4 as nft_type,type,null,level,ts_nft_ship_info.rank from ts_nft_ship_info where `last_trade_price` &gt; 0" +
            " UNION all " +
            "  select `token_id` AS tokenId, `owner_address` AS ownerAddress,`last_trade_price` as price ," +
            "  `image`,5 as nft_type,type,null,null,null from ts_nft_egg_info where `last_trade_price` &gt; 0" +
            " UNION all " +
            "  select `token_id` AS tokenId, `owner_address` AS ownerAddress,`last_trade_price` as price ," +
            "  `image`,6 as nft_type,type,null,null,null from ts_nft_mbse_info where `last_trade_price` &gt; 0" +
            " UNION all " +
            "  select `token_id` AS tokenId, `owner_address` AS ownerAddress,`last_trade_price` as price ," +
            "  `image`,7 as nft_type,null,null,null,null from ts_nft_ase_info where `last_trade_price` &gt; 0" +
            " UNION all " +
            "  select unique_id AS tokenId, `owner_address` AS ownerAddress,`last_trade_price` as price ," +
            "  `image`,8 as nft_type,null,null,null,null from ts_nft_trade_info_mul where `last_trade_price` &gt; 0 and nft_type = 8" +
            ") a where 1=1 "+
            " <if test='dto.nftType != null ' > " +
            "   and  a.nft_type = #{dto.nftType}" +
            " </if>" +
            " <if test='dto.rarity != null ' > " +
            "   and  a.rarity = #{dto.rarity}" +
            " </if>" +
            " <if test='dto.camp != null ' > " +
            "   and  a.camp = #{dto.camp}" +
            " </if>" +
            " <if test='dto.level != null ' > " +
            "   and  a.`level` = #{dto.level}" +
            " </if>" +
            " <if test='dto.rank != null ' > " +
            "   and  a.rank = #{dto.rank}" +
            " </if>" +
            " <if test='dto.type != null ' > " +
            "   and  a.type = #{dto.type}" +
            " </if>" +
            " order by a.price desc " +
            " </script>")
    List<TopNftVO> topNft(Page<TopNftVO> page, NftStatisticsDto dto);
    @Select("<script>" +
            "  select v.* from (select tokenId,fromAddress,toAddress,price,tradeTime,tradeType,l.nft_type as nftType," +
            "   CASE WHEN l.nft_type=0 THEN a.image " +
            "       WHEN l.nft_type=1 THEN  b.image " +
            "       WHEN l.nft_type=2 THEN c.image" +
            "       WHEN l.nft_type=3 THEN d.image " +
            "       WHEN l.nft_type=4 THEN e.image " +
            "       WHEN l.nft_type=5 THEN f.image " +
            "       WHEN l.nft_type=6 THEN g.image " +
            "       WHEN l.nft_type=7 THEN h.image " +
            "       WHEN l.nft_type=8 THEN i.image " +
            "       ELSE null END  as image," +
            "   CASE WHEN l.nft_type=0 THEN a.camp" +
            "       WHEN l.nft_type=1 THEN  null " +
            "       WHEN l.nft_type=2 THEN c.type " +
            "       WHEN l.nft_type=3 THEN d.type " +
            "       ELSE null END  as camp," +
            "   CASE WHEN l.nft_type=0 THEN a.rarity " +
            "       ELSE null END  as rarity," +
            "   CASE WHEN l.nft_type=4 THEN e.level " +
            "       ELSE null END  as level," +
            "   CASE WHEN l.nft_type=4 THEN e.rank " +
            "       WHEN l.nft_type=8 THEN i.rank " +
            "       ELSE null END  as _rank," +
            "   CASE WHEN l.nft_type=5 THEN f.type " +
            "       ELSE null END  as type " +
            "   from ts_nft_activity_logs l " +
            "   left join ts_nft_card_info a on a.token_id=l.tokenId and l.nft_type=0 " +
            "   left join ts_nft_hse_info b on b.token_id=l.tokenId and l.nft_type=1 " +
            "   left join ts_nft_pse_info c on c.token_id=l.tokenId and l.nft_type=2 " +
            "   left join ts_nft_egse_info d on d.token_id=l.tokenId and l.nft_type=3" +
            "   left join ts_nft_ship_info e on e.token_id=l.tokenId and l.nft_type=4" +
            "   left join ts_nft_egg_info f on f.token_id=l.tokenId and l.nft_type=5" +
            "   left join ts_nft_mbse_info g on g.token_id=l.tokenId and l.nft_type=6" +
            "   left join ts_nft_ase_info h on h.token_id=l.tokenId and l.nft_type=7 " +
            "   left join ts_nft_ise_info i on i.tbId=l.tokenId and l.nft_type=8 " +
            " )v where 1=1"+
            " <if test='dto.nftType != null ' > " +
            "   and  v.nftType = #{dto.nftType}" +
            " </if>" +
            " <if test='dto.rarity != null ' > " +
            "   and  v.rarity = #{dto.rarity}" +
            " </if>" +
            " <if test='dto.camp != null ' > " +
            "   and  v.camp = #{dto.camp}" +
            " </if>" +
            "   <if test='dto.level != null ' > " +
            "   and  v.`level` = #{dto.level}" +
            "   </if>" +
            "   <if test='dto.rank != null ' > " +
            "   and  v.`_rank` = #{dto.rank}" +
            "   </if>" +
            "   <if test='dto.type != null ' > " +
            "   and  v.`type` = #{dto.type}" +
            "   </if>" +
            " order by  v.tradeTime desc " +
            "</script>")
    List<ActivityNftVo> activityNft(Page<ActivityNftVo> page, NftStatisticsDto dto);
}
