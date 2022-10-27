package org.sparkera.nft.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.sparkera.nft.constant.PageConstant;
import org.sparkera.nft.constant.SysConfigConstant;
import org.sparkera.nft.dao.dto.*;
import org.sparkera.nft.dao.mapper.*;
import org.sparkera.nft.enums.NftTypeEnum;
import org.sparkera.nft.dao.mapper2.FcMapper;
import org.sparkera.nft.service.IInformationCenterService;
import org.sparkera.nft.utils.BSCUtils;
import org.sparkera.nft.utils.exception.CommonException;
import org.sparkera.nft.utils.pageinfoutil.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
@Slf4j
public class InformationCenterServiceImpl implements IInformationCenterService {
/*
    @Autowired
    InformationCenterMapper informationCenterMapper;
*/
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

    @Autowired
    private InviteMapper inviteMapper;
    @Autowired
    private FcMapper fcMapper;

    @Override
    public Page<Map<String,Object>> myStorage(NftCardQueryDto dto, PageVo pageVo) {
        Page<Map<String,Object>> page = new Page<Map<String,Object>>(pageVo.getPageNumber(), pageVo.getPageSize()==0?PageConstant.PAGE_SIZE:pageVo.getPageSize());
        System.out.println("ii value:"+dto.getAddress());
        List<Map<String,Object>> nftCardItemInfoVoList= getNftMapper(dto.getNftType()).myStorage(page,dto);
        return page.setRecords(nftCardItemInfoVoList);
    }

    @Override
    public Page<Map<String, Object>> selling(NftCardQueryDto dto, PageVo pageVo) {
        Page<Map<String,Object>> page = new Page<Map<String,Object>>(pageVo.getPageNumber(), pageVo.getPageSize()==0?PageConstant.PAGE_SIZE:pageVo.getPageSize());
        List<Map<String,Object>> nftCardItemInfoVoList= getNftMapper(dto.getNftType()).selling(page,dto);
        return page.setRecords(nftCardItemInfoVoList);
    }

    @Override
    public Page<Map<String, Object>> myFavorite(NftCardQueryDto dto, PageVo pageVo) {
        Page<Map<String,Object>> page = new Page<Map<String,Object>>(pageVo.getPageNumber(), pageVo.getPageSize()==0?PageConstant.PAGE_SIZE:pageVo.getPageSize());
        List<Map<String,Object>> nftCardItemInfoVoList= getNftMapper(dto.getNftType()).myFavorite(page,dto);
        return page.setRecords(nftCardItemInfoVoList);
    }

    @Override
    public Map<String, Object> tokenDetail(BigInteger tokenId,String address, String ownerAddress, Integer nftType, BigInteger goodId) {
        if (nftType == NftTypeEnum.ISE.getCode()) {
            return iseMapper.tokenDetail2(tokenId, address, ownerAddress, goodId == null? BigInteger.valueOf(0):goodId);
        }
        return getNftMapper(nftType).tokenDetail(tokenId,address);
    }

    @Override
    public int sellToken(NftCardTradeDto nftCardInfo) throws Exception {
        Map<String, Object> tokenInfo = null;
        if (nftCardInfo.getNftType() == NftTypeEnum.ISE.getCode()) {
            tokenInfo = iseMapper.checkToken(nftCardInfo.getId(), nftCardInfo.getAddress());
        } else {
            tokenInfo = getNftMapper(nftCardInfo.getNftType()).checkToken(nftCardInfo.getTokenId());
        }

        if(tokenInfo==null||!tokenInfo.get("ownerAddress").toString().equalsIgnoreCase(nftCardInfo.getAddress())) {
            throw new CommonException("You do not have the authority to operate the token");
        }else if(nftCardInfo.getNftType() != NftTypeEnum.ISE.getCode() && Integer.valueOf(tokenInfo.get("ct").toString()).intValue()>0){
            throw new CommonException("The token is selling");
        }else if(nftCardInfo.getNftType() == NftTypeEnum.ISE.getCode()){
            if (nftCardInfo.getCnt() <= 0) {
                throw new CommonException("ise check: data error");
            }
            if (Integer.valueOf(tokenInfo.get("cnt").toString()).intValue() - nftCardInfo.getCnt() < 0) {
                throw new CommonException("ise check: no enough item in storage to sell");
            }
        }
        return getNftMapper(nftCardInfo.getNftType()).sellToken(nftCardInfo);
    }

    @Override
    public int addFavorite(BigInteger tokenId, String address,Integer nftType) {
        BigInteger ext_id = new BigInteger("0");
        Map<String, Object> sellInfo = null;
        if (nftType == NftTypeEnum.ISE.getCode()) {
            //材料的tokenId是商品goodId
            sellInfo = getNftMapper(nftType).getSellMulInfo(tokenId);
            if (sellInfo == null)
                return 0;
            NftIseInfoDto nftInfo = (NftIseInfoDto)getNftMapper(nftType).getNftInfoByTokenIdMul((Integer) sellInfo.get("id"), (String)sellInfo.get("ownerAddress"));
            if (nftInfo == null)
                return 0;
            ext_id = nftInfo.getTbId();
        }

        int isFavorite = getNftMapper(nftType).checkFavorite(tokenId,address,nftType);
        if(isFavorite==0){
            int rs = getNftMapper(nftType).insertFavorite(tokenId,address,nftType,ext_id);
            if(rs==1){
                if (nftType == NftTypeEnum.ISE.getCode()) {
                    getNftMapper(nftType).updateNftFavoritesMul(Integer.valueOf(sellInfo.get("id").toString()),
                            address, 1);
                } else {
                    getNftMapper(nftType).updateNftFavorites(tokenId, 1);
                }
            }
            return rs;
        }
        return 0;
    }

    @Override
    public int removeFavorite(BigInteger tokenId, String address,Integer nftType) {
        NftIseInfoDto nftIseInfoDto = null;
        if (nftType == NftTypeEnum.ISE.getCode()) {
            //材料的tokenId是商品goodId
            NftFavoriteInfoDto favoriteInfo = getNftMapper(nftType).getFavoriteInfo(tokenId, address, nftType);
            if (favoriteInfo == null) {
                return 0;
            }

            nftIseInfoDto = iseMapper.getNftInfoByTbId(favoriteInfo.getExtId());
            if (nftIseInfoDto == null) {
                return 0;
            }
        }

        int isFavorite = getNftMapper(nftType).checkFavorite(tokenId,address,nftType);
        if(isFavorite>0){
            int rs = getNftMapper(nftType).deleteFavorite(tokenId,address,nftType);
            if(rs>0){
                if (nftType == NftTypeEnum.ISE.getCode()) {
                    getNftMapper(nftType).updateNftFavoritesMul(nftIseInfoDto.getId().intValue(),
                            address, -1);
                } else {
                    getNftMapper(nftType).updateNftFavorites(tokenId, -1);
                }
            }
            return 1;
        }
        return 0;
    }

    @Override
    @Transactional
    public int buyToken(NftCardTradeDto nftCardTradeDto) {
        NftSellInfoDto tokenInfo = getNftMapper(nftCardTradeDto.getNftType()).lockSellingTokenById(nftCardTradeDto.getTokenId(),nftCardTradeDto.getNftType());
        if(tokenInfo!=null){
            BigInteger tokenId=  tokenInfo.getTokenId();
            BigDecimal price = tokenInfo.getPrice();
            String  toAddress = nftCardTradeDto.getAddress();
            String  fromAddress = tokenInfo.getOwnerAddress();
            String tradeErc20Address = commonMapper.getSysConfigByKey(SysConfigConstant.TRADE_ERC20_ADDRESS);
            BigDecimal feeRate = new BigDecimal(commonMapper.getSysConfigByKey(SysConfigConstant.CTOC_FEE));
            BigDecimal txFee = price.multiply(feeRate).setScale(6,BigDecimal.ROUND_HALF_UP);
            BigDecimal newPrice = price.subtract(txFee);
            int tradeErc20Decimals = Integer.valueOf(commonMapper.getSysConfigByKey(SysConfigConstant.TRADE_ERC20_DECIMALS));
            String contractAddress=getNftContractAddress(nftCardTradeDto.getNftType());
            String hashId= BSCUtils.tradeTransferNft(contractAddress,tokenId,fromAddress,commonMapper.getSysConfigByKey(SysConfigConstant.TRADE_PRIVATE_KEY),toAddress,
                    tradeErc20Address,
                    newPrice,
                    txFee,
                    Integer.valueOf(commonMapper.getSysConfigByKey(SysConfigConstant.TRADE_GAS_PRICE)),
                    Integer.valueOf(commonMapper.getSysConfigByKey(SysConfigConstant.TRADE_GAS_LIMIT)),
                    tradeErc20Decimals);
            if(hashId.startsWith("error")){
                throw new CommonException(hashId);
            }else{
                NftBuySellLogDto buySellLogDto = new NftBuySellLogDto();
                buySellLogDto.setTokenId(tokenInfo.getTokenId());
                buySellLogDto.setSellPrice(tokenInfo.getPrice());
                buySellLogDto.setSellCreateTime(tokenInfo.getCreateTime());
                buySellLogDto.setSellAddress(fromAddress);
                buySellLogDto.setBuyAddress(toAddress);
                buySellLogDto.setTxHash(hashId);
                buySellLogDto.setErc20Address(tradeErc20Address);
                buySellLogDto.setTxFee(txFee);
                buySellLogDto.setNftType(nftCardTradeDto.getNftType());
                int rs = getNftMapper(nftCardTradeDto.getNftType()).deleteSellingOrder(tokenId,fromAddress,nftCardTradeDto.getNftType());
                if(rs>0){
                    commonMapper.insertBuySellInfo(buySellLogDto);
                    removeFavorite(tokenId, toAddress, nftCardTradeDto.getNftType());
                }
                return rs;
            }
        }
        return 0;
    }

    @Override
    @Transactional
    public int buyTokenMul(NftCardTradeDto nftCardTradeDto) {
        NftSellInfoDto tokenInfo = getNftMapper(nftCardTradeDto.getNftType()).lockSellingTokenByIdMul(nftCardTradeDto.getGoodId(),nftCardTradeDto.getNftType());
        if(tokenInfo!=null){
            Integer id = tokenInfo.getId();
            Integer num = tokenInfo.getNum();
            BigDecimal price = tokenInfo.getPrice();
            String  toAddress = nftCardTradeDto.getAddress();
            String  fromAddress = tokenInfo.getOwnerAddress();
            String tradeErc20Address = commonMapper.getSysConfigByKey(SysConfigConstant.TRADE_ERC20_ADDRESS);
            BigDecimal feeRate = new BigDecimal(commonMapper.getSysConfigByKey(SysConfigConstant.CTOC_FEE));
            BigDecimal txFee = price.multiply(feeRate).setScale(6,BigDecimal.ROUND_HALF_UP);
            BigDecimal newPrice = price.subtract(txFee);
            int tradeErc20Decimals = Integer.valueOf(commonMapper.getSysConfigByKey(SysConfigConstant.TRADE_ERC20_DECIMALS));
            String contractAddress=getNftContractAddress(nftCardTradeDto.getNftType());
            String hashId= BSCUtils.tradeTransferNftMul(contractAddress,id,num,fromAddress,commonMapper.getSysConfigByKey(SysConfigConstant.TRADE_PRIVATE_KEY),toAddress,
                    tradeErc20Address,
                    newPrice,
                    txFee,
                    Integer.valueOf(commonMapper.getSysConfigByKey(SysConfigConstant.TRADE_GAS_PRICE)),
                    Integer.valueOf(commonMapper.getSysConfigByKey(SysConfigConstant.TRADE_GAS_LIMIT)),
                    tradeErc20Decimals);

            if(hashId.startsWith("error")){
                throw new CommonException(hashId);
            }else{
                NftBuySellLogDto buySellLogDto = new NftBuySellLogDto();
                buySellLogDto.setTokenId(tokenInfo.getTokenId());
                buySellLogDto.setSellPrice(tokenInfo.getPrice());
                buySellLogDto.setSellCreateTime(tokenInfo.getCreateTime());
                buySellLogDto.setSellAddress(fromAddress);
                buySellLogDto.setBuyAddress(toAddress);
                buySellLogDto.setTxHash(hashId);
                buySellLogDto.setErc20Address(tradeErc20Address);
                buySellLogDto.setTxFee(txFee);
                buySellLogDto.setNftType(nftCardTradeDto.getNftType());
                int rs = getNftMapper(nftCardTradeDto.getNftType()).deleteSellingOrderMul(nftCardTradeDto.getGoodId());
                if(rs>0){
                    //若是Ise资产，则使用good_id代替tookenId传入日志接口以兼容老接口
                    if (nftCardTradeDto.getNftType() == NftTypeEnum.ISE.getCode()) {
                        NftIseInfoDto nftInfo = (NftIseInfoDto)getNftMapper(nftCardTradeDto.getNftType()).getNftInfoByTokenIdMul((Integer) tokenInfo.getId(), fromAddress);
                        if (nftInfo == null)
                            return 0;
                        buySellLogDto.setExtId(nftInfo.getTbId());
                        buySellLogDto.setTokenId(nftCardTradeDto.getGoodId());
                    }

                    commonMapper.insertBuySellInfo(buySellLogDto);
                    removeFavorite(nftCardTradeDto.getGoodId(), toAddress, nftCardTradeDto.getNftType());
                }
                return rs;
            }
        }
        return 0;
    }

    @Override
    public int cancelSell(NftCardCancelDto dto) {
        if (dto.getNftType() == NftTypeEnum.ISE.getCode()) {
            return iseMapper.deleteSellingOrderByTokenIdAndAddressMul(dto.getGoodId());
        }
        return getNftMapper(dto.getNftType()).deleteSellingOrderByTokenIdAndAddress(dto.getTokenId(),dto.getAddress(),dto.getNftType());
    }

    @Override
    @Transactional
    public int quickSaleToken(NftCardTradeDto nftCardInfo) {
        Map<String, Object> tokenInfo = getNftMapper(nftCardInfo.getNftType()).checkToken(nftCardInfo.getTokenId());
        if(tokenInfo==null||!tokenInfo.get("ownerAddress").toString().equalsIgnoreCase(nftCardInfo.getAddress())) {
            throw new CommonException("You do not have the authority to operate the token");
        }else if(Integer.valueOf(tokenInfo.get("ct").toString()).intValue()>0){
            throw new CommonException("The token is selling");
        }
        BigInteger tokenId=  nftCardInfo.getTokenId();

        BigDecimal price =null;
        Integer nftType=nftCardInfo.getNftType();
        if(nftType==0){
            String rarity = tokenInfo.get("rarity").toString();
            price = new BigDecimal(commonMapper.getSysConfigByKey(SysConfigConstant.RECYCLE_RARITY_PREFIX+rarity));
        }else if(nftType==1){
            price = new BigDecimal(commonMapper.getSysConfigByKey(SysConfigConstant.RECYCLE_HSE_PRICE));
        }else if(nftType==2){
            String type = tokenInfo.get("type").toString();
            price = new BigDecimal(commonMapper.getSysConfigByKey(SysConfigConstant.RECYCLE_PSE_TYPE_PREFIX+type));
        }else if(nftType==3){
            String type = tokenInfo.get("type").toString();
            price = new BigDecimal(commonMapper.getSysConfigByKey(SysConfigConstant.RECYCLE_EGSE_TYPE_PREFIX+type));
        }
        String  toAddress = commonMapper.getSysConfigByKey(SysConfigConstant.TRADE_RECYCLE_ADDRESS);
        String  fromAddress = nftCardInfo.getAddress();
        String tradeErc20Address = commonMapper.getSysConfigByKey(SysConfigConstant.TRADE_ERC20_ADDRESS);
        int tradeErc20Decimals = Integer.valueOf(commonMapper.getSysConfigByKey(SysConfigConstant.TRADE_ERC20_DECIMALS));
        String contractAddress=getNftContractAddress(nftCardInfo.getNftType());
        String hashId= BSCUtils.quickSaleNft(contractAddress,tokenId,fromAddress,commonMapper.getSysConfigByKey(SysConfigConstant.TRADE_PRIVATE_KEY),toAddress,
                tradeErc20Address,
                price,
                Integer.valueOf(commonMapper.getSysConfigByKey(SysConfigConstant.TRADE_GAS_PRICE)),
                Integer.valueOf(commonMapper.getSysConfigByKey(SysConfigConstant.TRADE_GAS_LIMIT)),
                tradeErc20Decimals);
        if(hashId.startsWith("error")){
            throw new CommonException(hashId);
        }else{
            NftBuySellLogDto buySellLogDto = new NftBuySellLogDto();
            buySellLogDto.setTokenId(tokenId);
            buySellLogDto.setSellPrice(price);
            buySellLogDto.setSellCreateTime(new Date());
            buySellLogDto.setSellAddress(fromAddress);
            buySellLogDto.setBuyAddress(toAddress);
            buySellLogDto.setTxHash(hashId);
            buySellLogDto.setErc20Address(tradeErc20Address);
            buySellLogDto.setTxFee(BigDecimal.ZERO);
            return commonMapper.insertBuySellInfo(buySellLogDto);
        }
    }

    @Override
    public BigDecimal getQuickSalePriceByTokenId(BigInteger tokenId,Integer nftType) {
        Map<String, Object> tokenInfo = getNftMapper(nftType).checkToken(tokenId);
        if(tokenInfo==null){
            throw new CommonException("token error");
        }
        BigDecimal price =null;
        if(nftType==0){
            String rarity = tokenInfo.get("rarity").toString();
            price = new BigDecimal(commonMapper.getSysConfigByKey(SysConfigConstant.RECYCLE_RARITY_PREFIX+rarity));
        }else if(nftType==1){
            price = new BigDecimal(commonMapper.getSysConfigByKey(SysConfigConstant.RECYCLE_HSE_PRICE));
        }else if(nftType==2){
            String type = tokenInfo.get("type").toString();
            price = new BigDecimal(commonMapper.getSysConfigByKey(SysConfigConstant.RECYCLE_PSE_TYPE_PREFIX+type));
        }else if(nftType==3){
            String type = tokenInfo.get("type").toString();
            price = new BigDecimal(commonMapper.getSysConfigByKey(SysConfigConstant.RECYCLE_EGSE_TYPE_PREFIX+type));
        }
        return price;
    }

    public static String generateCode() {
        String charList = "ABCDEFGHIJKLMNPQRSTUVWXY";
        String numList = "123456789";
        String rev = "";
        int maxNumCount = 4;
        int length = 6;
        Random f = new Random();
        for (int i = 0; i < length; i++) {
            if (f.nextBoolean() && maxNumCount > 0) {
                maxNumCount--;
                rev += numList.charAt(f.nextInt(numList.length()));
            } else {
                rev += charList.charAt(f.nextInt(charList.length()));
            }
        }
        return rev;
    }

    @Override
    @Transactional
    public String produceInviteCode(BigInteger userId) {
        UserMailDto userInfo = fcMapper.getUserInfo(userId);

        String code = null;
        InviteDto inviteInfo = inviteMapper.getInviteInfo(userId);
        if (inviteInfo != null) {
            return inviteInfo.getCode();
        } else {
            int max_produce = 1000;
            while (max_produce > 0){
                code = generateCode();
                InviteDto inviteInfoByCode = inviteMapper.getInviteInfoByCode(code);
                if (inviteInfoByCode == null) {
                    inviteMapper.insertInviteInfo(userId, code);
                    break;
                }
                code = null;
            }

            if (code == null) {
                log.error("无法产生有效的邀请码");
            }
        }

        return code == null? "": code;
    }

    private NFTBaseMapper getNftMapper(Integer nftType){
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
    public String getNftContractAddress(Integer nftType){
        String rs =null;
        switch (nftType){
            case 0:
                rs = BSCUtils.getFireworkCardAddress();
                break;
            case 1:
                rs = BSCUtils.getHSE_address();
                break;
            case 2:
                rs = BSCUtils.getPSE_address();
                break;
            case 3:
                rs = BSCUtils.getEGSE_address();
                break;
            case 4:
                rs = BSCUtils.getSHIP_address();
                break;
            case 5:
                rs = BSCUtils.getEGG_address();
                break;
            case 6:
                rs = BSCUtils.getMBSE_address();
                break;
            case 7:
                rs = BSCUtils.getASE_address();
                break;
            case 8:
                rs = BSCUtils.getISE_address();
                break;
            default:
                break;

        }
        return rs;
    }
}
