package org.sparkera.nft.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.sparkera.nft.dao.dto.NftCardCancelDto;
import org.sparkera.nft.dao.dto.NftCardInfoDto;
import org.sparkera.nft.dao.dto.NftCardQueryDto;
import org.sparkera.nft.dao.dto.NftCardTradeDto;
import org.sparkera.nft.utils.pageinfoutil.PageVo;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

public interface IInformationCenterService {
    Page<Map<String,Object>> myStorage(NftCardQueryDto dto, PageVo pageVo);

    Page<Map<String, Object>> selling(NftCardQueryDto dto, PageVo pageVo);

    Page<Map<String, Object>> myFavorite(NftCardQueryDto dto, PageVo pageVo);

    Map<String, Object> tokenDetail(BigInteger tokenId,String address, String ownerAddress, Integer nftType, BigInteger goodId);

    int sellToken(NftCardTradeDto nftCardInfo)throws Exception;

    int addFavorite(BigInteger tokenId, String address,Integer nftType);

    int removeFavorite(BigInteger tokenId, String address,Integer nftType);

    int buyToken(NftCardTradeDto nftCardTradeDto);
    int buyTokenMul(NftCardTradeDto nftCardTradeDto);

    int cancelSell(NftCardCancelDto dto);

    int quickSaleToken(NftCardTradeDto nftCardTradeDto);

    BigDecimal getQuickSalePriceByTokenId(BigInteger tokenId,Integer nftType);

    String produceInviteCode(BigInteger userId);
}
