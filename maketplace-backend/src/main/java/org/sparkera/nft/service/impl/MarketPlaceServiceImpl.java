package org.sparkera.nft.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.sparkera.nft.constant.PageConstant;
import org.sparkera.nft.constant.SysConfigConstant;
import org.sparkera.nft.dao.dto.NftCardQueryDto;
import org.sparkera.nft.dao.mapper.*;
import org.sparkera.nft.enums.NftTypeEnum;
import org.sparkera.nft.service.IMarketPlaceService;
import org.sparkera.nft.utils.pageinfoutil.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MarketPlaceServiceImpl implements IMarketPlaceService {
    @Autowired
    MarketPlaceMapper marketPlaceMapper;
    @Autowired
    CommonMapper commonMapper;
    @Autowired
    private FHCMapper fhcMapper;
    @Autowired
    private HSEMapper hseMapper;
    @Autowired
    private PSEMapper pseMapper;
    @Autowired
    private EGSEMapper egseMapper;
    @Autowired
    private ShipMapper shipMapper;
    @Autowired
    private EGGMapper eggMapper;
    @Autowired
    private MBSEMapper mbseMapper;
    @Autowired
    private ASEMapper aseMapper;
    @Autowired
    private ISEMapper iseMapper;

    @Override
    public Page<Map<String, Object>> tradingMarket(NftCardQueryDto dto, PageVo pageVo) {
        Page<Map<String,Object>> page = new Page<Map<String,Object>>(pageVo.getPageNumber(), pageVo.getPageSize()==0?PageConstant.PAGE_SIZE:pageVo.getPageSize());
        List<Map<String,Object>> nftInfoVoList= getNftMapper(dto.getNftType()).tradingMarket(page,dto);
        return page.setRecords(nftInfoVoList);
    }

    @Override
    public int addViewsByTokenId(Integer tokenId, Integer nftType, String ownerAddress) {
        if (nftType == NftTypeEnum.ISE.getCode()) {
            return getNftMapper(nftType).addViewsByTokenIdMul(tokenId, ownerAddress);
        }
        return getNftMapper(nftType).addViewsByTokenId(tokenId);
    }

    @Override
    public String tradeFee() {
        return commonMapper.getSysConfigByKey(SysConfigConstant.CTOC_FEE);
    }

    NFTBaseMapper getNftMapper(Integer nftType){
        NFTBaseMapper rs=null;
        switch (nftType){
            case 0:
                rs = fhcMapper;
                break;
            case 1:
                rs = hseMapper;
                break;
            case 2:
                rs = pseMapper;
                break;
            case 3:
                rs = egseMapper;
                break;
            case 4:
                rs = shipMapper;
                break;
            case 5:
                rs = eggMapper;
                break;
            case 6:
                rs = mbseMapper;
                break;
            case 7:
                rs = aseMapper;
                break;
            case 8:
                rs = iseMapper;
                break;
            default:
                break;

        }
        return rs;
    }
}
