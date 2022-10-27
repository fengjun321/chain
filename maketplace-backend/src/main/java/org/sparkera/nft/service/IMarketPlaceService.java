package org.sparkera.nft.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.sparkera.nft.dao.dto.NftCardQueryDto;
import org.sparkera.nft.utils.pageinfoutil.PageVo;

import java.util.Map;

public interface IMarketPlaceService {
    Page<Map<String,Object>> tradingMarket(NftCardQueryDto dto, PageVo pageVo);

    int addViewsByTokenId(Integer tokenId, Integer nftType, String ownerAddress);

    String tradeFee();
}
