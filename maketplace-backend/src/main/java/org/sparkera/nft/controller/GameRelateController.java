package org.sparkera.nft.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.sparkera.nft.common.GameConfig;
import org.sparkera.nft.common.GameProcess;
import org.sparkera.nft.common.SignConfig;
import org.sparkera.nft.common.SsoLogin;
import org.sparkera.nft.config.RebateConfig;
import org.sparkera.nft.constant.PageConstant;
import org.sparkera.nft.dao.dto.*;
import org.sparkera.nft.dao.mapper.BankMapper;
import org.sparkera.nft.dao.mapper.InviteMapper;
import org.sparkera.nft.dao.mapper2.FcMapper;
import org.sparkera.nft.dao.mapper2.GameMapper;
import org.sparkera.nft.enums.GameTagEnum;
import org.sparkera.nft.enums.NftTypeEnum;
import org.sparkera.nft.service.IInformationCenterService;
import org.sparkera.nft.utils.BSCUtils;
import org.sparkera.nft.utils.ResultUtil;
import org.sparkera.nft.utils.ResultVo;
import org.sparkera.nft.utils.pageinfoutil.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/gameRelate")
@ApiModel(value="Game relate",description="Game relate method" )
@Slf4j
@Validated
public class GameRelateController {
    @Autowired
    HttpServletRequest req;
    @Autowired
    IInformationCenterService informationCenterService;
    @Autowired
    InviteMapper inviteMapper;
    @Autowired
    FcMapper fcMapper;
    @Autowired
    RebateConfig rebateConfig;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    GameMapper gameMapper;
    @Autowired
    GameProcess gameProcess;
    @Autowired
    BankMapper bankMapper;
    @Autowired
    SignConfig signConfig;

    @GetMapping("/getGameNft")
    @ApiOperation(value = "getGameNft", notes = "get game nft")
    public ResultVo getGameNft(@ApiParam(value = "game nft view"  ) @Validated @ModelAttribute NftGameQueryDto dto, PageVo pageVo, HttpServletRequest request) {
        BigInteger userId = null;
        String authToken = request.getHeader("authToken");
        try {
            Map map = SsoLogin.GetLoginInfo(authToken);
            userId = new BigInteger(map.get("UserId").toString());
        }catch (Exception e){
            return new ResultUtil().setLoginErrorMsg(e.getMessage());
        }

        try {
            if (dto.getGameTag() == GameTagEnum.SPARKERA_v3.getCode()) {
                if (dto.getNftType() ==  NftTypeEnum.SHIP.getCode()) {
                    Page<NftGameShipDto> page = new Page<NftGameShipDto>(pageVo.getPageNumber(), pageVo.getPageSize()==0?
                            PageConstant.PAGE_SIZE:pageVo.getPageSize());
                    List<NftGameShipDto> gameShipInfo = gameMapper.getGameShipInfo(userId);
                    return new ResultUtil().setData(page.setRecords(gameShipInfo));
                }
            }
        }catch (Exception e){
            return new ResultUtil().setErrorMsg(e.getMessage());
        }
        return new ResultUtil().setErrorMsg("do nothing");
    }

    @PostMapping("/takeOutSign")
    @ApiOperation(value = "takeOutSign", notes = "take out sign")
    @Transactional
    public ResultVo takeOutSign(@ApiParam(value = "take out data"  ) @Validated @ModelAttribute NftGTakeOutQueryDto dto, HttpServletRequest request) {
        BigInteger userId = null;
        String authToken = request.getHeader("authToken");
        try {
            Map map = SsoLogin.GetLoginInfo(authToken);
            userId = new BigInteger(map.get("UserId").toString());
        }catch (Exception e){
            return new ResultUtil().setLoginErrorMsg(e.getMessage());
        }

        gameMapper.lockUser(userId);

        UserMailDto userInfo = fcMapper.getUserInfo(userId);
        String walletAddress = userInfo.getWalletAddress();

        List<BigInteger> listErc721 = new ArrayList<>();

        JSONArray logMap = JSONObject.parseArray(dto.getTakeOut());
        for (int i = 0; i < logMap.size(); i++) {
            Map map = (Map)logMap.get(i);
            Integer type = (Integer)map.get("type");
            BigInteger tokenId = new BigInteger(map.get("tokenId").toString());
            BigDecimal cnt = new BigDecimal(map.get("cnt").toString());

            if (dto.getGameTag() == GameTagEnum.SPARKERA_v3.getCode()) {
                if (dto.getNftType() == NftTypeEnum.SHIP.getCode()) {
                    NftGameShipDto gameShipInfoByTokenId = gameMapper.getGameShipInfoByTokenId(tokenId);
                    if (gameShipInfoByTokenId == null) {
                        log.info("tokenId:" + tokenId.toString() + " not found");
                        continue;
                    }
                    if (!gameShipInfoByTokenId.getHashId().equals(BigInteger.valueOf(-1))) {
                        log.info("tokenId:" + tokenId.toString() + " has ever been taking out");
                        continue;
                    }
                }
            }
            listErc721.add(tokenId);
        }

        if (listErc721.size() == 0) {
            return new ResultUtil().setErrorMsg("no nft can take out or you have ever been token out");
        }

        String erc721Address = null;
        if (dto.getNftType() == NftTypeEnum.SHIP.getCode()) {
            erc721Address = BSCUtils.getSHIP_address();
        } else {
            return new ResultUtil().setErrorMsg("nft address can not produce, please check your nft type");
        }

        String hashId = gameProcess.GetNftUniqueId();
        boolean isLockAssets = frozenNftAssets(new BigInteger(hashId), userId, dto.getGameTag(), dto.getNftType(), listErc721, 0);
        if (!isLockAssets) {
            return new ResultUtil().setErrorMsg("lock assets fail");
        }

        byte[] bytes = GameConfig.toNftByte(hashId, walletAddress, erc721Address, dto.getGameTag(), listErc721);
        HashMap<String, Object> mapData = signConfig.SignRSV(bytes, userId);

        log.info("userId:{} hashId:{} takeOut:{}", userId, hashId, dto.getTakeOut());
        return new ResultUtil().setData(mapData);
    }

    @Transactional
    public boolean frozenNftAssets(BigInteger hashId, BigInteger userId, Integer gameTag,
                                Integer nftType, List<BigInteger> listErc721, Integer nFlag) {
        boolean isLockAssets = false;
        if (gameTag == GameTagEnum.SPARKERA_v3.getCode()) {
            if (nftType == NftTypeEnum.SHIP.getCode()) {
                for (BigInteger tokenId : listErc721) {
                    gameMapper.updateShipHashId(tokenId, hashId);
                    isLockAssets = true;
                }
            }
        }

        if (isLockAssets) {
            NftBankDto nftBankDto = new NftBankDto();
            nftBankDto.setHashId(hashId);
            nftBankDto.setUserId(userId);
            nftBankDto.setNftType(nftType);
            nftBankDto.setFlag(nFlag);
            bankMapper.updateNftBankInfo(nftBankDto);
        } else {
            log.error("frozenNftAssets error hashId:"+hashId+" userId:"+userId+" gameTag:"+
                    gameTag+" nType:"+nftType+" listErc721:"+listErc721.toString());
        }
        return isLockAssets;
    }
}
