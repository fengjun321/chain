package org.sparkera.nft.dao.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.*;
import org.sparkera.nft.dao.dto.*;
import org.sparkera.nft.dao.entity.BaseEntityInfo;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Mapper
public interface FHCMapper extends NFTBaseMapper{
    @Update("update ts_nft_card_info set views=views+1 where token_id=#{tokenId}")
    int addViewsByTokenId(@Param("tokenId") Integer tokenId);
    @Select("<script>" +
            " select `token_id`as tokenId, `owner_address` as ownerAddress, `itemData`, `occupation`,`strength`," +
            "  `ts_level` as level, `spirit`, `type`,`endurance`, `psionic`, `camp`, `armor`, `material`," +
            "   `crit`, `identity`, `nameId`, `agility`,`rarity`,`tokenURI`,`version`,`releaseVersion`," +
            "   `name`, `image`, `description`, `attr_edition` as attrEdition, `attr_camp` as attrCamp," +
            "    `attr_rank` as attrRank, `attr_material` as attrMaterial, `attr_name` as attrName,b.`price` ," +
            "     `views`, `favorites` ,`total_attrs` as totalAttrs ,`power`," +
            "   (select count(1) from ts_favorite_info f where f.token_id = a.`token_id` and user_address=#{dto.address} and f.nft_type=0 )as isFavorite " +
            " from  ts_selling_order b left join ts_nft_card_info a  on a. `token_id` = b.tokenId and b.nft_type=0 " +
            " where b.nft_type=0 and b.ownerAddress = a.owner_address " +
            "   <if test='dto.searchKey != null and dto.searchKey != \"\" ' > " +
            "   and  (a.`token_id` like concat('%',#{dto.searchKey},'%') or lower(a.`attr_name`) like concat('%',lower(#{dto.searchKey}),'%') )" +
            "   </if>" +
            "   <if test='dto.camp != null ' > " +
            "   and  a.`camp` = #{dto.camp}" +
            "   </if>" +
            "   <if test='dto.identity != null ' > " +
            "   and  a.`identity` = #{dto.identity}" +
            "   </if>" +
            "   <if test='dto.rarity != null ' > " +
            "   and  a.`rarity` = #{dto.rarity}" +
            "   </if>" +
            "   <if test='dto.material != null ' > " +
            "   and  a.`material` = #{dto.material}" +
            "   </if>" +
            "   <if test='dto.profession != null ' > " +
            "   and  a.`occupation` = #{dto.profession}" +
            "   </if>" +
            "   <if test='dto.level != null ' > " +
            "   and  a.`ts_level` = #{dto.level}" +
            "   </if>" +
            "   <choose> " +
            "       <when test='dto.minEndurance !=null and dto.maxEndurance !=null'> " +
            "           and (a.`endurance` BETWEEN #{dto.minEndurance} and #{dto.maxEndurance} )"+
            "       </when>"+
            "       <when test='dto.minEndurance !=null'> " +
            "           and a.`endurance` &gt;=  #{dto.minEndurance}"+
            "       </when>"+
            "       <when test='dto.maxEndurance !=null'> " +
            "           and a.`endurance` &lt;=  #{dto.maxEndurance}"+
            "       </when>"+
            "   </choose>" +
            "   <choose> " +
            "       <when test='dto.minPower !=null and dto.maxPower !=null'> " +
            "           and (a.`power` BETWEEN #{dto.minPower} and #{dto.maxPower} )"+
            "       </when>"+
            "       <when test='dto.minPower !=null'> " +
            "           and a.`power` &gt;=  #{dto.minPower}"+
            "       </when>"+
            "       <when test='dto.maxPower !=null'> " +
            "           and a.`power` &lt;=  #{dto.maxPower}"+
            "       </when>"+
            "   </choose>" +
            "   <choose> " +
            "       <when test='dto.minStrength !=null and dto.maxStrength !=null'> " +
            "           and (a.`strength` BETWEEN #{dto.minStrength} and #{dto.maxStrength} )"+
            "       </when>"+
            "       <when test='dto.minStrength !=null'> " +
            "           and a.`strength` &gt;=  #{dto.minStrength}"+
            "       </when>"+
            "       <when test='dto.maxStrength !=null'> " +
            "           and a.`strength` &lt;=  #{dto.maxStrength}"+
            "       </when>"+
            "   </choose>" +
            "   <choose> " +
            "       <when test='dto.minAgility !=null and dto.maxAgility !=null'> " +
            "           and (a.`agility` BETWEEN #{dto.minAgility} and #{dto.maxAgility} )"+
            "       </when>"+
            "       <when test='dto.minAgility !=null'> " +
            "           and a.`agility` &gt;=  #{dto.minAgility}"+
            "       </when>"+
            "       <when test='dto.maxStrength !=null'> " +
            "           and a.`agility` &lt;=  #{dto.maxAgility}"+
            "       </when>"+
            "   </choose>" +
            "   <choose> " +
            "       <when test='dto.minArmor !=null and dto.maxArmor !=null'> " +
            "           and (a.`armor` BETWEEN #{dto.minArmor} and #{dto.maxArmor} )"+
            "       </when>"+
            "       <when test='dto.minArmor !=null'> " +
            "           and a.`armor` &gt;=  #{dto.minArmor}"+
            "       </when>"+
            "       <when test='dto.maxArmor !=null'> " +
            "           and a.`armor` &lt;=  #{dto.maxArmor}"+
            "       </when>"+
            "   </choose>" +
            "   <choose> " +
            "       <when test='dto.minCrit !=null and dto.maxCrit !=null'> " +
            "           and (a.`crit` BETWEEN #{dto.minCrit} and #{dto.maxCrit} )"+
            "       </when>"+
            "       <when test='dto.minCrit !=null'> " +
            "           and a.`crit` &gt;=  #{dto.minCrit}"+
            "       </when>"+
            "       <when test='dto.maxCrit !=null'> " +
            "           and a.`crit` &lt;=  #{dto.maxCrit}"+
            "       </when>"+
            "   </choose>" +
            "   <choose> " +
            "       <when test='dto.minPsionic !=null and dto.maxPsionic !=null'> " +
            "           and (a.`psionic` BETWEEN #{dto.minPsionic} and #{dto.maxPsionic} )"+
            "       </when>"+
            "       <when test='dto.minPsionic !=null'> " +
            "           and a.`psionic` &gt;=  #{dto.minPsionic}"+
            "       </when>"+
            "       <when test='dto.maxPsionic !=null'> " +
            "           and a.`psionic` &lt;=  #{dto.maxPsionic}"+
            "       </when>"+
            "   </choose>" +
            "   <choose> " +
            "       <when test='dto.minSpirit !=null and dto.maxSpirit !=null'> " +
            "           and (a.`spirit` BETWEEN #{dto.minSpirit} and #{dto.maxSpirit} )"+
            "       </when>"+
            "       <when test='dto.minSpirit !=null'> " +
            "           and a.`spirit` &gt;=  #{dto.minSpirit}"+
            "       </when>"+
            "       <when test='dto.maxSpirit !=null'> " +
            "           and a.`spirit` &lt;=  #{dto.maxSpirit}"+
            "       </when>"+
            "   </choose>" +
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
            "select `token_id`as tokenId, `owner_address` as ownerAddress, `itemData`, `occupation`,`strength`," +
            "  `ts_level` as level, `spirit`, `type`,`endurance`, `psionic`, `camp`, `armor`, `material`," +
            "   `crit`, `identity`, `nameId`, `agility`,`rarity`,`tokenURI`,`version`,`releaseVersion`," +
            "   `name`, `image`, `description`, `attr_edition` as attrEdition, `attr_camp` as attrCamp," +
            "    `attr_rank` as attrRank, `attr_material` as attrMaterial, `attr_name` as attrName,b.`price` ," +
            "     `views`, `favorites` ,`total_attrs` as totalAttrs,IFNULL(b.tokenId,0) as isSelling,`power`," +
            "   (select count(1) from ts_favorite_info f where f.token_id = a.`token_id` and user_address=#{dto.address} and f.nft_type=0 )as isFavorite " +
            "        from ts_nft_card_info a   left join ts_selling_order b on a. `token_id` = b.tokenId and  b.nft_type=0 " +
            "        where `owner_address` = #{dto.address}" +
            "   <if test='dto.searchKey != null and dto.searchKey != \"\" ' > " +
            "   and  (a.`token_id` like concat('%',#{dto.searchKey},'%') or lower(a.`attr_name`) like concat('%',lower(#{dto.searchKey}),'%') )" +
            "   </if>" +
            "   <if test='dto.camp != null ' > " +
            "   and  a.`camp` = #{dto.camp}" +
            "   </if>" +
            "   <if test='dto.identity != null ' > " +
            "   and  a.`identity` = #{dto.identity}" +
            "   </if>" +
            "   <if test='dto.rarity != null ' > " +
            "   and  a.`rarity` = #{dto.rarity}" +
            "   </if>" +
            "   <if test='dto.material != null ' > " +
            "   and  a.`material` = #{dto.material}" +
            "   </if>" +
            "   <if test='dto.profession != null ' > " +
            "   and  a.`occupation` = #{dto.profession}" +
            "   </if>" +
            "   <if test='dto.level != null ' > " +
            "   and  a.`ts_level` = #{dto.level}" +
            "   </if>" +
            "   <choose> " +
            "       <when test='dto.minEndurance !=null and dto.maxEndurance !=null'> " +
            "           and (a.`endurance` BETWEEN #{dto.minEndurance} and #{dto.maxEndurance} )"+
            "       </when>"+
            "       <when test='dto.minEndurance !=null'> " +
            "           and a.`endurance` &gt;=  #{dto.minEndurance}"+
            "       </when>"+
            "       <when test='dto.maxEndurance !=null'> " +
            "           and a.`endurance` &lt;=  #{dto.maxEndurance}"+
            "       </when>"+
            "   </choose>" +
            "   <choose> " +
            "       <when test='dto.minStrength !=null and dto.maxStrength !=null'> " +
            "           and (a.`strength` BETWEEN #{dto.minStrength} and #{dto.maxStrength} )"+
            "       </when>"+
            "       <when test='dto.minStrength !=null'> " +
            "           and a.`strength` &gt;=  #{dto.minStrength}"+
            "       </when>"+
            "       <when test='dto.maxStrength !=null'> " +
            "           and a.`strength` &lt;=  #{dto.maxStrength}"+
            "       </when>"+
            "   </choose>" +
            "   <choose> " +
            "       <when test='dto.minPower !=null and dto.maxPower !=null'> " +
            "           and (a.`power` BETWEEN #{dto.minPower} and #{dto.maxPower} )"+
            "       </when>"+
            "       <when test='dto.minPower !=null'> " +
            "           and a.`power` &gt;=  #{dto.minPower}"+
            "       </when>"+
            "       <when test='dto.maxPower !=null'> " +
            "           and a.`power` &lt;=  #{dto.maxPower}"+
            "       </when>"+
            "   </choose>" +
            "   <choose> " +
            "       <when test='dto.minAgility !=null and dto.maxAgility !=null'> " +
            "           and (a.`agility` BETWEEN #{dto.minAgility} and #{dto.maxAgility} )"+
            "       </when>"+
            "       <when test='dto.minAgility !=null'> " +
            "           and a.`agility` &gt;=  #{dto.minAgility}"+
            "       </when>"+
            "       <when test='dto.maxStrength !=null'> " +
            "           and a.`agility` &lt;=  #{dto.maxAgility}"+
            "       </when>"+
            "   </choose>" +
            "   <choose> " +
            "       <when test='dto.minArmor !=null and dto.maxArmor !=null'> " +
            "           and (a.`armor` BETWEEN #{dto.minArmor} and #{dto.maxArmor} )"+
            "       </when>"+
            "       <when test='dto.minArmor !=null'> " +
            "           and a.`armor` &gt;=  #{dto.minArmor}"+
            "       </when>"+
            "       <when test='dto.maxArmor !=null'> " +
            "           and a.`armor` &lt;=  #{dto.maxArmor}"+
            "       </when>"+
            "   </choose>" +
            "   <choose> " +
            "       <when test='dto.minCrit !=null and dto.maxCrit !=null'> " +
            "           and (a.`crit` BETWEEN #{dto.minCrit} and #{dto.maxCrit} )"+
            "       </when>"+
            "       <when test='dto.minCrit !=null'> " +
            "           and a.`crit` &gt;=  #{dto.minCrit}"+
            "       </when>"+
            "       <when test='dto.maxCrit !=null'> " +
            "           and a.`crit` &lt;=  #{dto.maxCrit}"+
            "       </when>"+
            "   </choose>" +
            "   <choose> " +
            "       <when test='dto.minPsionic !=null and dto.maxPsionic !=null'> " +
            "           and (a.`psionic` BETWEEN #{dto.minPsionic} and #{dto.maxPsionic} )"+
            "       </when>"+
            "       <when test='dto.minPsionic !=null'> " +
            "           and a.`psionic` &gt;=  #{dto.minPsionic}"+
            "       </when>"+
            "       <when test='dto.maxPsionic !=null'> " +
            "           and a.`psionic` &lt;=  #{dto.maxPsionic}"+
            "       </when>"+
            "   </choose>" +
            "   <choose> " +
            "       <when test='dto.minSpirit !=null and dto.maxSpirit !=null'> " +
            "           and (a.`spirit` BETWEEN #{dto.minSpirit} and #{dto.maxSpirit} )"+
            "       </when>"+
            "       <when test='dto.minSpirit !=null'> " +
            "           and a.`spirit` &gt;=  #{dto.minSpirit}"+
            "       </when>"+
            "       <when test='dto.maxSpirit !=null'> " +
            "           and a.`spirit` &lt;=  #{dto.maxSpirit}"+
            "       </when>"+
            "   </choose>" +
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
            "</script>")
    List<Map<String,Object>> myStorage(Page<Map<String,Object>> page, @Param("dto") NftCardQueryDto dto);
    @Select("<script>" +
            "select `token_id`as tokenId, `owner_address` as ownerAddress, `itemData`, `occupation`,`strength`," +
            "  `ts_level` as level, `spirit`, `type`,`endurance`, `psionic`, `camp`, `armor`, `material`," +
            "   `crit`, `identity`, `nameId`, `agility`,`rarity`,`tokenURI`,`version`,`releaseVersion`," +
            "   `name`, `image`, `description`, `attr_edition` as attrEdition, `attr_camp` as attrCamp," +
            "    `attr_rank` as attrRank, `attr_material` as attrMaterial, `attr_name` as attrName,b.`price` ," +
            "     `views`, `favorites` ,`total_attrs` as totalAttrs,IFNULL(b.tokenId,0) as isSelling ,`power`," +
            "   (select count(1) from ts_favorite_info f where f.token_id = a.`token_id` and user_address=#{dto.address} and f.nft_type=0 )as isFavorite " +
            "        from ts_selling_order b left join ts_nft_card_info a  on a. `token_id` = b.tokenId and b.nft_type=0  " +
            "        where b.`ownerAddress` = #{dto.address} and b.nft_type=0 " +
            "   <if test='dto.searchKey != null and dto.searchKey != \"\" ' > " +
            "   and  (a.`token_id` like concat('%',#{dto.searchKey},'%') or lower(a.`attr_name`) like concat('%',lower(#{dto.searchKey}),'%') )" +
            "   </if>" +
            "   <if test='dto.camp != null ' > " +
            "   and  a.`camp` = #{dto.camp}" +
            "   </if>" +
            "   <if test='dto.identity != null ' > " +
            "   and  a.`identity` = #{dto.identity}" +
            "   </if>" +
            "   <if test='dto.rarity != null ' > " +
            "   and  a.`rarity` = #{dto.rarity}" +
            "   </if>" +
            "   <if test='dto.material != null ' > " +
            "   and  a.`material` = #{dto.material}" +
            "   </if>" +
            "   <if test='dto.profession != null ' > " +
            "   and  a.`occupation` = #{dto.profession}" +
            "   </if>" +
            "   <if test='dto.level != null ' > " +
            "   and  a.`ts_level` = #{dto.level}" +
            "   </if>" +
            "   <choose> " +
            "       <when test='dto.minEndurance !=null and dto.maxEndurance !=null'> " +
            "           and (a.`endurance` BETWEEN #{dto.minEndurance} and #{dto.maxEndurance} )"+
            "       </when>"+
            "       <when test='dto.minEndurance !=null'> " +
            "           and a.`endurance` &gt;=  #{dto.minEndurance}"+
            "       </when>"+
            "       <when test='dto.maxEndurance !=null'> " +
            "           and a.`endurance` &lt;=  #{dto.maxEndurance}"+
            "       </when>"+
            "   </choose>" +
            "   <choose> " +
            "       <when test='dto.minStrength !=null and dto.maxStrength !=null'> " +
            "           and (a.`strength` BETWEEN #{dto.minStrength} and #{dto.maxStrength} )"+
            "       </when>"+
            "       <when test='dto.minStrength !=null'> " +
            "           and a.`strength` &gt;=  #{dto.minStrength}"+
            "       </when>"+
            "       <when test='dto.maxStrength !=null'> " +
            "           and a.`strength` &lt;=  #{dto.maxStrength}"+
            "       </when>"+
            "   </choose>" +
            "   <choose> " +
            "       <when test='dto.minAgility !=null and dto.maxAgility !=null'> " +
            "           and (a.`agility` BETWEEN #{dto.minAgility} and #{dto.maxAgility} )"+
            "       </when>"+
            "       <when test='dto.minAgility !=null'> " +
            "           and a.`agility` &gt;=  #{dto.minAgility}"+
            "       </when>"+
            "       <when test='dto.maxStrength !=null'> " +
            "           and a.`agility` &lt;=  #{dto.maxAgility}"+
            "       </when>"+
            "   </choose>" +
            "   <choose> " +
            "       <when test='dto.minArmor !=null and dto.maxArmor !=null'> " +
            "           and (a.`armor` BETWEEN #{dto.minArmor} and #{dto.maxArmor} )"+
            "       </when>"+
            "       <when test='dto.minArmor !=null'> " +
            "           and a.`armor` &gt;=  #{dto.minArmor}"+
            "       </when>"+
            "       <when test='dto.maxArmor !=null'> " +
            "           and a.`armor` &lt;=  #{dto.maxArmor}"+
            "       </when>"+
            "   </choose>" +
            "   <choose> " +
            "       <when test='dto.minCrit !=null and dto.maxCrit !=null'> " +
            "           and (a.`crit` BETWEEN #{dto.minCrit} and #{dto.maxCrit} )"+
            "       </when>"+
            "       <when test='dto.minCrit !=null'> " +
            "           and a.`crit` &gt;=  #{dto.minCrit}"+
            "       </when>"+
            "       <when test='dto.maxCrit !=null'> " +
            "           and a.`crit` &lt;=  #{dto.maxCrit}"+
            "       </when>"+
            "   </choose>" +
            "   <choose> " +
            "       <when test='dto.minPower !=null and dto.maxPower !=null'> " +
            "           and (a.`power` BETWEEN #{dto.minPower} and #{dto.maxPower} )"+
            "       </when>"+
            "       <when test='dto.minPower !=null'> " +
            "           and a.`power` &gt;=  #{dto.minPower}"+
            "       </when>"+
            "       <when test='dto.maxPower !=null'> " +
            "           and a.`power` &lt;=  #{dto.maxPower}"+
            "       </when>"+
            "   </choose>" +
            "   <choose> " +
            "       <when test='dto.minPsionic !=null and dto.maxPsionic !=null'> " +
            "           and (a.`psionic` BETWEEN #{dto.minPsionic} and #{dto.maxPsionic} )"+
            "       </when>"+
            "       <when test='dto.minPsionic !=null'> " +
            "           and a.`psionic` &gt;=  #{dto.minPsionic}"+
            "       </when>"+
            "       <when test='dto.maxPsionic !=null'> " +
            "           and a.`psionic` &lt;=  #{dto.maxPsionic}"+
            "       </when>"+
            "   </choose>" +
            "   <choose> " +
            "       <when test='dto.minSpirit !=null and dto.maxSpirit !=null'> " +
            "           and (a.`spirit` BETWEEN #{dto.minSpirit} and #{dto.maxSpirit} )"+
            "       </when>"+
            "       <when test='dto.minSpirit !=null'> " +
            "           and a.`spirit` &gt;=  #{dto.minSpirit}"+
            "       </when>"+
            "       <when test='dto.maxSpirit !=null'> " +
            "           and a.`spirit` &lt;=  #{dto.maxSpirit}"+
            "       </when>"+
            "   </choose>" +
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
            "</script>")
    List<Map<String, Object>> selling(Page<Map<String, Object>> page, @Param("dto") NftCardQueryDto dto);

    @Select("<script>" +
            "select a.`token_id`as tokenId, `owner_address` as ownerAddress, `itemData`, `occupation`,`strength`," +
            "  `ts_level` as level, `spirit`, `type`,`endurance`, `psionic`, `camp`, `armor`, `material`," +
            "   `crit`, `identity`, `nameId`, `agility`,`rarity`,`tokenURI`,`version`,`releaseVersion`," +
            "   `name`, `image`, `description`, `attr_edition` as attrEdition, `attr_camp` as attrCamp," +
            "    `attr_rank` as attrRank, `attr_material` as attrMaterial, `attr_name` as attrName,b.`price` ," +
            "     `views`, `favorites` ,`total_attrs` as totalAttrs,IFNULL(b.tokenId,0) as isSelling ,`power` " +
            "        from ts_favorite_info c left join ts_nft_card_info a  on a.`token_id` = c.token_id and c.nft_type=0  " +
            "       left join ts_selling_order b on a.`token_id` = b.tokenId and c.nft_type=b.nft_type  " +
            "        where c.nft_type = 0 and c.`user_address` = #{dto.address}" +
            "   <if test='dto.searchKey != null and dto.searchKey != \"\" ' > " +
            "   and  (a.`token_id` like concat('%',#{dto.searchKey},'%') or lower(a.`attr_name`) like concat('%',lower(#{dto.searchKey}),'%') )" +
            "   </if>" +
            "   <if test='dto.camp != null ' > " +
            "   and  a.`camp` = #{dto.camp}" +
            "   </if>" +
            "   <if test='dto.identity != null ' > " +
            "   and  a.`identity` = #{dto.identity}" +
            "   </if>" +
            "   <if test='dto.rarity != null ' > " +
            "   and  a.`rarity` = #{dto.rarity}" +
            "   </if>" +
            "   <if test='dto.material != null ' > " +
            "   and  a.`material` = #{dto.material}" +
            "   </if>" +
            "   <if test='dto.profession != null ' > " +
            "   and  a.`occupation` = #{dto.profession}" +
            "   </if>" +
            "   <if test='dto.level != null ' > " +
            "   and  a.`ts_level` = #{dto.level}" +
            "   </if>" +
            "   <choose> " +
            "       <when test='dto.minEndurance !=null and dto.maxEndurance !=null'> " +
            "           and (a.`endurance` BETWEEN #{dto.minEndurance} and #{dto.maxEndurance} )"+
            "       </when>"+
            "       <when test='dto.minEndurance !=null'> " +
            "           and a.`endurance` &gt;=  #{dto.minEndurance}"+
            "       </when>"+
            "       <when test='dto.maxEndurance !=null'> " +
            "           and a.`endurance` &lt;=  #{dto.maxEndurance}"+
            "       </when>"+
            "   </choose>" +
            "   <choose> " +
            "       <when test='dto.minStrength !=null and dto.maxStrength !=null'> " +
            "           and (a.`strength` BETWEEN #{dto.minStrength} and #{dto.maxStrength} )"+
            "       </when>"+
            "       <when test='dto.minStrength !=null'> " +
            "           and a.`strength` &gt;=  #{dto.minStrength}"+
            "       </when>"+
            "       <when test='dto.maxStrength !=null'> " +
            "           and a.`strength` &lt;=  #{dto.maxStrength}"+
            "       </when>"+
            "   </choose>" +
            "   <choose> " +
            "       <when test='dto.minPower !=null and dto.maxPower !=null'> " +
            "           and (a.`power` BETWEEN #{dto.minPower} and #{dto.maxPower} )"+
            "       </when>"+
            "       <when test='dto.minPower !=null'> " +
            "           and a.`power` &gt;=  #{dto.minPower}"+
            "       </when>"+
            "       <when test='dto.maxPower !=null'> " +
            "           and a.`power` &lt;=  #{dto.maxPower}"+
            "       </when>"+
            "   </choose>" +
            "   <choose> " +
            "       <when test='dto.minAgility !=null and dto.maxAgility !=null'> " +
            "           and (a.`agility` BETWEEN #{dto.minAgility} and #{dto.maxAgility} )"+
            "       </when>"+
            "       <when test='dto.minAgility !=null'> " +
            "           and a.`agility` &gt;=  #{dto.minAgility}"+
            "       </when>"+
            "       <when test='dto.maxStrength !=null'> " +
            "           and a.`agility` &lt;=  #{dto.maxAgility}"+
            "       </when>"+
            "   </choose>" +
            "   <choose> " +
            "       <when test='dto.minArmor !=null and dto.maxArmor !=null'> " +
            "           and (a.`armor` BETWEEN #{dto.minArmor} and #{dto.maxArmor} )"+
            "       </when>"+
            "       <when test='dto.minArmor !=null'> " +
            "           and a.`armor` &gt;=  #{dto.minArmor}"+
            "       </when>"+
            "       <when test='dto.maxArmor !=null'> " +
            "           and a.`armor` &lt;=  #{dto.maxArmor}"+
            "       </when>"+
            "   </choose>" +
            "   <choose> " +
            "       <when test='dto.minCrit !=null and dto.maxCrit !=null'> " +
            "           and (a.`crit` BETWEEN #{dto.minCrit} and #{dto.maxCrit} )"+
            "       </when>"+
            "       <when test='dto.minCrit !=null'> " +
            "           and a.`crit` &gt;=  #{dto.minCrit}"+
            "       </when>"+
            "       <when test='dto.maxCrit !=null'> " +
            "           and a.`crit` &lt;=  #{dto.maxCrit}"+
            "       </when>"+
            "   </choose>" +
            "   <choose> " +
            "       <when test='dto.minPsionic !=null and dto.maxPsionic !=null'> " +
            "           and (a.`psionic` BETWEEN #{dto.minPsionic} and #{dto.maxPsionic} )"+
            "       </when>"+
            "       <when test='dto.minPsionic !=null'> " +
            "           and a.`psionic` &gt;=  #{dto.minPsionic}"+
            "       </when>"+
            "       <when test='dto.maxPsionic !=null'> " +
            "           and a.`psionic` &lt;=  #{dto.maxPsionic}"+
            "       </when>"+
            "   </choose>" +
            "   <choose> " +
            "       <when test='dto.minSpirit !=null and dto.maxSpirit !=null'> " +
            "           and (a.`spirit` BETWEEN #{dto.minSpirit} and #{dto.maxSpirit} )"+
            "       </when>"+
            "       <when test='dto.minSpirit !=null'> " +
            "           and a.`spirit` &gt;=  #{dto.minSpirit}"+
            "       </when>"+
            "       <when test='dto.maxSpirit !=null'> " +
            "           and a.`spirit` &lt;=  #{dto.maxSpirit}"+
            "       </when>"+
            "   </choose>" +
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
            "</script>")
    List<Map<String, Object>> myFavorite(Page<Map<String, Object>> page, NftCardQueryDto dto);
    @Select("select `token_id`as tokenId, `owner_address` as ownerAddress, `itemData`, `occupation`,`strength`,0 as nftType," +
            "  `ts_level` as level, `spirit`, `type`,`endurance`, `psionic`, `camp`, `armor`, `material`," +
            "   `crit`, `identity`, `nameId`, `agility`,`rarity`,`tokenURI`,`version`,`releaseVersion`," +
            "   `name`, `detail_image` as image, `description`, `attr_edition` as attrEdition, `attr_camp` as attrCamp," +
            "    `attr_rank` as attrRank, `attr_material` as attrMaterial, `attr_name` as attrName,b.`price` ," +
            "   `total_attrs` as totalAttrs,IFNULL(b.tokenId,0) as isSelling ,`views`, `favorites`,`total_attrs` ,`power`," +
            "  (select count(1) from ts_favorite_info c where c.token_id=a.`token_id` and c.user_address=#{address} and c.nft_type=0) as isFavorite " +
            " from ts_nft_card_info a left join ts_selling_order b on a. `token_id` = b.tokenId and b.nft_type=0 " +
            "        where `token_id` = #{tokenId}")
    Map<String, Object> tokenDetail(@Param("tokenId")BigInteger tokenId,@Param("address") String address);
    @Select("select a.`token_id`as tokenId, a.`owner_address` as ownerAddress,count(b.tokenId) as ct,rarity from ts_nft_card_info a \n" +
            " left join ts_selling_order b on a. `token_id` = b.tokenId and b.nft_type=0     where a.`token_id` = #{tokenId}")
    Map<String, Object> checkToken(@Param("tokenId")BigInteger tokenId);
    @Update("update ts_nft_card_info set favorites =favorites + #{ct} where token_id =#{tokenId} ")
    int updateNftFavorites(@Param("tokenId")BigInteger tokenId, @Param("ct")int ct);
    @Select("<script>" +
            " select `token_id`, `owner_address`, `itemData`, `occupation`, `strength`, `ts_level` as level, `spirit`, `type`, `endurance`, " +
            "`psionic`, `camp`, `armor`, `material`, `crit`, `identity`, `nameId`, `agility`, `rarity`, `power`, `tokenURI`, " +
            "`version`, `releaseVersion`, `name`, `image`, `description`, `attr_edition`, `attr_camp`, `attr_rank`, " +
            "`attr_material`, `attr_name`, `views`, `favorites`, `total_attrs`, `last_trade_price`, " +
            "`last_trade_time`, `detail_image` from ts_nft_card_info " +
            " where `token_id` =#{tokenId}" +
            "</script>")
    NftCardInfoDto getNftInfoByTokenId(@Param("tokenId")BigInteger tokenId);
    @Update(" replace into `ts_nft_card_info`" +
            " (`token_id`, `owner_address`, `itemData`, `occupation`, `strength`, `ts_level`, `spirit`, `type`, `endurance`, " +
            "   `psionic`, `camp`, `armor`, `material`, `crit`, `identity`, `nameId`, `agility`, `rarity`, `power`, `tokenURI`, " +
            "   `version`, `releaseVersion`, `name`, `image`, `description`, `attr_edition`, `attr_camp`, `attr_rank`, `attr_material`, " +
            " `attr_name`, `views`, `favorites`, `total_attrs`, `last_trade_price`, `last_trade_time`, `detail_image`) " +
            " VALUES(#{dto.tokenId},#{dto.ownerAddress}, #{dto.itemData},#{dto.occupation},#{dto.strength},#{dto.level},#{dto.spirit}," +
            "   #{dto.type}, #{dto.endurance},#{dto.psionic},#{dto.camp},#{dto.armor},#{dto.material},#{dto.crit},#{dto.identity}," +
            "   #{dto.nameId},#{dto.agility},#{dto.rarity},#{dto.power},#{dto.tokenURI},#{dto.version}, #{dto.releaseVersion}," +
            "   #{dto.name},#{dto.image},#{dto.description},#{dto.attrEdition},#{dto.attrCamp},#{dto.attrRank}," +
            "   #{dto.attrMaterial},#{dto.attrName},#{dto.views},#{dto.favorites}, #{dto.totalAttrs},#{dto.lastTradePrice},#{dto.lastTradeTime},#{dto.detailImage}) ")
    int upInsetNftInfo(@Param("dto") BaseEntityInfo nftCardInfoDto);
    @Update("<script>" +
            " update ts_nft_card_info set last_trade_price =#{price}" +
            " <if test='tradeTime!=null'>" +
            " ,last_trade_time=#{tradeTime}" +
            "</if>" +
            " where token_id=#{tokenId}" +
            " </script>")
    int updateNftLastTradePrice(@Param("tokenId")BigInteger tokenId, @Param("price")BigDecimal price,@Param("tradeTime") Timestamp dateTime);
}
