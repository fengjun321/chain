package org.sparkera.nft.service;

import org.sparkera.nft.dao.dto.NftShipInfoDto;

public interface ICommonService {
    void fireworkCardLogs() throws Exception;
    void checkRebateBank() throws Exception;
    void checkNftBank() throws Exception;
    void checkStockData() throws Exception;
    NftShipInfoDto getShipTokenURI(NftShipInfoDto nftShipInfoDto) throws Exception;
}
