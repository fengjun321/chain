package org.sparkera.nft.common;

import lombok.extern.slf4j.Slf4j;
import org.sparkera.nft.constant.FireWorkCardConstant;
import org.sparkera.nft.dao.dto.NftGameShipDto;
import org.sparkera.nft.dao.dto.NftShipInfoDto;
import org.sparkera.nft.dao.dto.UserMailDto;
import org.sparkera.nft.dao.mapper.CommonMapper;
import org.sparkera.nft.dao.mapper2.FcMapper;
import org.sparkera.nft.dao.mapper2.GameMapper;
import org.sparkera.nft.service.impl.CommonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

@Slf4j
@Component
public class GameProcess {
    @Autowired
    CommonMapper commonMapper;
    @Autowired
    GameMapper gameMapper;
    @Autowired
    FcMapper fcMapper;

    public void SynShipNft(String ownerAddress, List<BigInteger> listTokenId) throws Exception {
        CommonServiceImpl commonService = new CommonServiceImpl();
        NftShipInfoDto nftShipInfoDto = new NftShipInfoDto();

        UserMailDto userInfoByOwnerAddr = fcMapper.getUserInfoByOwnerAddr(ownerAddress);
        if (userInfoByOwnerAddr == null) {
            throw new Exception("can not find user ownerAddress:" + ownerAddress);
        }

        boolean isHasUse = false;
        List<NftGameShipDto> gameShipInfo = gameMapper.getGameShipInfo(userInfoByOwnerAddr.getUserId());
        //fj todo use this data to do something in game
        for (BigInteger tokenId : listTokenId) {
            nftShipInfoDto.setTokenId(tokenId);
            nftShipInfoDto.setOwnerAddress(ownerAddress);
            nftShipInfoDto = commonService.getShipTokenURI(nftShipInfoDto);

            String shipName = nftShipInfoDto.getShipName();
            log.info("SynShipNft ownerAddress:{} shipName:{}", ownerAddress, shipName);

            Integer shipId = GameConfig.map.get(shipName);
            if (shipId == null) {
                throw new Exception("can not find shipid ownerAddress:" + ownerAddress);
            }

            NftGameShipDto gameData = null;
            for (NftGameShipDto gameDto: gameShipInfo) {
                if (gameDto.getTokenId() == null) {
                    log.error("[to do]fj must delete this game ship table, otherwise, damage will happen");
                    return;
                }
                if (gameDto.getTokenId().equals(tokenId)) {
                    gameData = gameDto;
                }

                if (isHasUse == false && gameDto.getBeUse() == 1) {
                    isHasUse = true;
                }
            }

            if (gameData == null) {
                gameData = new NftGameShipDto();
                gameData.setUserId(userInfoByOwnerAddr.getUserId());
                gameData.setShipId(shipId);
                gameData.setHashId(BigInteger.valueOf(-1));
                if (isHasUse == false) {
                    gameData.setBeUse(1);
                    isHasUse = true;
                } else {
                    gameData.setBeUse(0);
                }
                gameData.setTokenId(tokenId);
            } else {
                gameData.setHashId(BigInteger.valueOf(-1));
            }

            gameMapper.upInsetNftInfo(gameData);
        }

    }

    @Transactional
    public String GetNftUniqueId(){
        String unique_str = commonMapper.getSysConfigByKey(FireWorkCardConstant.NFT_TO_GAME_ID);
        BigInteger bigInteger = new BigInteger(unique_str);
        commonMapper.upDateSysConfigByKey(FireWorkCardConstant.NFT_TO_GAME_ID, String.valueOf(bigInteger.intValue()+1));
        return unique_str;
    }
}
