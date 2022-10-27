package org.sparkera.nft.timer;

import lombok.extern.slf4j.Slf4j;
import org.sparkera.nft.service.ICommonService;
import org.sparkera.nft.utils.BSCUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.EnableAsync;
@Component
@Slf4j
@EnableAsync
public class NftTimer {
    @Autowired
    ICommonService commonService;
    @Scheduled(cron = "*/10 * * * * ? ")
    public void fireworkCardMintTimer() throws Exception {
        if(BSCUtils.getSCAN()){
            commonService.fireworkCardLogs();
        }

    }
    @Scheduled(cron = "*/10 * * * * ? ")
    public void bankOrderTimer() throws Exception {
        if(BSCUtils.getSCAN()) {
            commonService.checkRebateBank();
            commonService.checkNftBank();
        }
    }

//    @Scheduled(cron = "*/10 * * * * ? ")
//    public void StockService() throws Exception {
//        if(BSCUtils.getSCAN()) {
//            commonService.checkStockData();
//        }
//    }
}
