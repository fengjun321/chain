package org.sparkera.nft.dao.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.sparkera.nft.dao.dto.NftCardInfoDto;
import org.sparkera.nft.dao.dto.NftCardQueryDto;
import org.sparkera.nft.dao.vo.NftCardItemInfoVo;

import java.util.List;
import java.util.Map;

@Mapper
public interface MarketPlaceMapper extends BaseMapper {

    @Update("update ts_nft_card_info set views=views+1 where token_id=#{tokenId}")
    int addViewsByTokenId(@Param("tokenId") Integer tokenId);
}
