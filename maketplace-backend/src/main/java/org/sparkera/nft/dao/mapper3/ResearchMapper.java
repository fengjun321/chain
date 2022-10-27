package org.sparkera.nft.dao.mapper3;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.sparkera.nft.dao.dto2.IndustryScoreDto;
import org.sparkera.nft.dao.dto2.ResearchDto;

import java.util.List;

@Mapper
public interface ResearchMapper {
    @Insert("insert into research(infoCode, title, industryCode, industryName, emRatingName, ratingChange, publishDate) values" +
            "(#{dto.infoCode}, #{dto.title}, #{dto.industryCode}, #{dto.industryName}, #{dto.emRatingName}, #{dto.ratingChange}," +
            "#{dto.publishDate})")
    int collectData(@Param("dto") ResearchDto nftCardInfo);

    @Select("Call get_industry2(#{curDate}, #{delDay})")
    List<IndustryScoreDto> getIndustryResearchScore(@Param("curDate") String curDate, @Param("delDay") Integer delDay);

}
