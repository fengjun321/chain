package org.sparkera.nft.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.sparkera.nft.common.GameProcess;
import org.sparkera.nft.config.RebateConfig;
import org.sparkera.nft.constant.FireWorkCardConstant;
import org.sparkera.nft.constant.SysConfigConstant;
import org.sparkera.nft.dao.dto.*;
import org.sparkera.nft.dao.dto2.ResearchDto;
import org.sparkera.nft.dao.entity.NftEggItemInfo;
import org.sparkera.nft.dao.entity.NftGameSwapInfo;
import org.sparkera.nft.dao.entity.NftShipItemInfo;
import org.sparkera.nft.dao.mapper.*;
import org.sparkera.nft.dao.mapper2.FcMapper;
import org.sparkera.nft.dao.mapper2.GameMapper;
import org.sparkera.nft.dao.mapper3.ResearchMapper;
import org.sparkera.nft.enums.AssetTypeEnum;
import org.sparkera.nft.enums.GameTagEnum;
import org.sparkera.nft.enums.NftTypeEnum;
import org.sparkera.nft.service.ICommonService;
import org.sparkera.nft.utils.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.spring.web.json.Json;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.*;
import java.util.regex.Pattern;

import static org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.md5;
import static org.sparkera.nft.utils.BSCUtils.getNftTakeToGame_adress;


@Service
@Slf4j
public class CommonServiceImpl implements ICommonService {
    private static final BigInteger MAX_HEIGHT = new BigInteger("2000");
    private static HttpHeaders headers = new HttpHeaders();
    private static boolean isfirst = false;
    private static long dbDelTime = Long.MIN_VALUE;
    private static Queue<GameMsg> queGameMsg = new LinkedList<GameMsg>();

    @Autowired
    private RestTemplate restTemplate;

    static {
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
    }

    @Autowired
    private GameProcess gameProcess;
    @Autowired
    private CommonMapper commonMapper;
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
    private FcMapper fcMapper;
    @Autowired
    private InviteMapper inviteMapper;
    @Autowired
    private BankMapper bankMapper;
    @Autowired
    private GameMapper gameMapper;
    @Autowired
    private RebateConfig rebateConfig;

    @Autowired
    private ResearchMapper researchMapper;

    @Override
    @Transactional
    public void checkStockData() throws Exception {
        System.out.println(" stock data");


        int j = 1, totalPage = 0xFFFFFF;
        while (j <= totalPage) {
            String url = String.format("https://reportapi.eastmoney.com/report/list?industryCode=*&pageSize=50&industry=*&rating=*&ratingChange=*&beginTime=2020-9-12&endTime=2022-10-12&pageNo=%d&fields=&qType=1&orgCode=&rcode=&p=2&pageNum=1&pageNumber=10&_=1665551865034", j);
            String forObject = "";
            while (true) {
                try {
                    forObject = restTemplate.getForObject(url, String.class);
                    break;
                } catch (Exception e) {
                    log.info("visit error:", url);
                    Thread.sleep(2000);
                }
            }

            Map mintMap = JSONObject.parseObject(forObject, Map.class);
            if (totalPage == 0xFFFFFF) {
                totalPage = (Integer) mintMap.get("TotalPage");
            }

            ArrayList<String> arrErr = new ArrayList<>();
            boolean errInsert = false;
            int errCnt = 0;
            JSONArray arr = (JSONArray) mintMap.get("data");
            if (arr != null && arr.size() > 0) {
                for (int i = 0; i < arr.size(); i++) {
                    JSONObject record = (JSONObject) arr.get(i);
                    String infoCode = (String) record.get("infoCode");
                    String title = (String) record.get("title");
                    Integer industryCode = Integer.valueOf((String) record.get("industryCode"));
                    String industryName = (String) record.get("industryName");
                    String emRatingName = (String) record.get("emRatingName");

                    Integer ratingChange = -1;
                    try {
                        ratingChange = (Integer) record.get("ratingChange");
                    } catch (Exception e) {
                        //to do nothing
                    }

                    String ratingChangeStr = "";
                    switch (ratingChange) {
                        case 0:
                            ratingChangeStr = "调高";
                            break;
                        case 1:
                            ratingChangeStr = "调低";
                            break;
                        case 2:
                            ratingChangeStr = "首次";
                            break;
                        case 3:
                            ratingChangeStr = "维持";
                            break;
                        case 4:
                            ratingChangeStr = "无";
                            break;
                        default:
                            break;
                    }

                    String publishDate = (String) record.get("publishDate");

                    ResearchDto researchDto = new ResearchDto();
                    researchDto.setInfoCode(infoCode);
                    researchDto.setTitle(title);
                    researchDto.setIndustryCode(industryCode);
                    researchDto.setIndustryName(industryName);
                    researchDto.setEmRatingName(emRatingName);
                    researchDto.setRatingChange(ratingChangeStr);
                    researchDto.setPublishDate(publishDate);
                    try {
                        researchMapper.collectData(researchDto);
                    } catch (Exception e) {
                        arrErr.add(researchDto.toString());
                        errCnt += 1;
                    }
                }

                if (errCnt == arr.size()) {
                    errInsert = true;
                    for (int k = 0; k < arrErr.size(); k++) {
                        String s = arrErr.get(k);
                        log.info("research table element repeat, stop in page: {}", j);
                        System.out.println(k);
                    }
                }

            }
            j += 1;
            if (errInsert) {
                System.out.println("system break");
                break;
            }
        }
    }

    @Override
    @Transactional
    public void checkRebateBank() throws Exception {
        log.info("bank service");
        if (dbDelTime == Long.MIN_VALUE) {
            Map<String, Object> mTime = commonMapper.getDbTime();
            Long time = (Long)mTime.get("time") * 1000;
            dbDelTime = System.currentTimeMillis() - time.longValue();
            log.info("lgtime time delta: {}", dbDelTime);
        }

        String bank_read_id = commonMapper.getSysConfigByKey(FireWorkCardConstant.BANK_READ_HASH_ID);
        List<BankDto> bankNotUseList = inviteMapper.getBankNotUseList(new BigInteger(bank_read_id));

        BigInteger new_read_id = new BigInteger(bank_read_id);
        log.info("lgtime system time:{}", System.currentTimeMillis());
        //结算一分钟前购买的订单,多减一点，防止误差
        long ltime = System.currentTimeMillis() - dbDelTime - 60000 - 20000;
        int size = bankNotUseList.size();
        for (int i = 0; i < size; i++) {
            BankDto bankDto = bankNotUseList.get(i);
            new_read_id = bankDto.getHashId();
            if (bankDto.getFlag() != 0) {
                continue;
            }

            //spring boot会把数据库时间当成CST时间，若过
            long dbTime = bankDto.getCreateDate().getTime() + 8 * 3600 * 1000;
            log.info("lgtime id:{} dbtime:{} ltime:{}", bankDto.getHashId(), dbTime, ltime);
            if (dbTime < ltime) {
                UserMailDto userInfo = fcMapper.getUserInfo(bankDto.getUserId());
                //查询区块链接口是否成功
                boolean isSuccess = BSCUtils.checkWithDrawDepo(BSCUtils.getCheckRebate_address(), userInfo.getWalletAddress(), bankDto.getHashId());

                bankDto.setFlag(isSuccess == true? 1:2);
                inviteMapper.updateBankInfo(bankDto);

                RebateDto rebateInfo = inviteMapper.getRebateInfo(bankDto.getUserId(), bankDto.getErc20Addr());
                //退回资产
                if (isSuccess == false) {
                    rebateInfo.setAmount(rebateInfo.getAmount().add(bankDto.getAmount()));
                    inviteMapper.upRebateInfo(rebateInfo);
                } else {
                    //成功
                    rebateInfo.setUseAmount(rebateInfo.getUseAmount().add(bankDto.getAmount()));
                    inviteMapper.upRebateInfo(rebateInfo);
                }
            } else {
                break;
            }
        }
        commonMapper.upDateSysConfigByKey(FireWorkCardConstant.BANK_READ_HASH_ID, new_read_id.toString());
    }

    @Override
    @Transactional
    public void checkNftBank() throws Exception {
        log.info("bank service");
        if (dbDelTime == Long.MIN_VALUE) {
            Map<String, Object> mTime = commonMapper.getDbTime();
            Long time = (Long)mTime.get("time") * 1000;
            dbDelTime = System.currentTimeMillis() - time.longValue();
            log.info("lgtime time delta: {}", dbDelTime);
        }

        String bank_read_id = commonMapper.getSysConfigByKey(FireWorkCardConstant.NFT_BANK_READ_ID);
        List<NftBankDto> nftBankNotUseList = bankMapper.getNftBankNotUseList(new BigInteger(bank_read_id));

        ArrayList<String> walletList = new ArrayList<>();
        BigInteger new_read_id = new BigInteger(bank_read_id);
        log.info("lgtime system time:{}", System.currentTimeMillis());
        //结算一分钟前购买的订单,多减一点，防止误差
        long ltime = System.currentTimeMillis() - dbDelTime - 60000 - 20000;
        int size = nftBankNotUseList.size();
        for (int i = 0; i < size; i++) {
            NftBankDto bankDto = nftBankNotUseList.get(i);
            new_read_id = bankDto.getHashId();
            if (bankDto.getFlag() != 0) {
                continue;
            }

            //spring boot会把数据库时间当成CST时间，若过
            long dbTime = bankDto.getCreateDate().getTime() + 8 * 3600 * 1000;
            log.info("lgtime id:{} dbtime:{} ltime:{}", bankDto.getHashId(), dbTime, ltime);
            if (dbTime < ltime) {
                UserMailDto userInfo = fcMapper.getUserInfo(bankDto.getUserId());
                //查询区块链接口是否成功
                boolean isSuccess = BSCUtils.checkWithDrawDepo(getNftTakeToGame_adress(), userInfo.getWalletAddress(), bankDto.getHashId());
                bankDto.setFlag(isSuccess == true? 1:2);
                bankMapper.updateNftBankInfo(bankDto);

                //飞船银行业务
                if (bankDto.getNftType() == NftTypeEnum.SHIP.getCode()) {
                    List<NftGameShipDto> gameShipInfo = gameMapper.getGameShipInfo(bankDto.getUserId());
                    if (isSuccess == false) {
                        for (NftGameShipDto d : gameShipInfo) {
                            if (d.getHashId().equals(bankDto.getHashId())) {
                                gameMapper.updateShipHashId(d.getTokenId(), BigInteger.valueOf(-1));
                            }
                        }
                    } else {
                        NftGameShipDto newUseShip = null;
                        boolean isNeedNewUseShip = false;
                        for (NftGameShipDto t : gameShipInfo) {
                            if (t.getHashId().equals(bankDto.getHashId())) {
                                if (t.getBeUse() == 1) {
                                    isNeedNewUseShip = true;
                                }
                            } else {
                                newUseShip = t;
                            }
                        }

                        gameMapper.deleteShipByHashId(bankDto.getHashId());
                        if (isNeedNewUseShip && newUseShip != null) {
                            newUseShip.setBeUse(1);
                            gameMapper.upInsetNftInfo(newUseShip);
                        }
                    }
                    walletList.add(userInfo.getWalletAddress());
                }
            } else {
                break;
            }
        }
        commonMapper.upDateSysConfigByKey(FireWorkCardConstant.NFT_BANK_READ_ID, new_read_id.toString());
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void afterCommit() {
                    for (int i = 0; i < walletList.size(); i++) {
                        NotifyGameTransaction("ship", walletList.get(i), "");
                    }
                    log.info("Transaction end");
                }
            });
        }
    }

    @Override
    @Transactional
    public void fireworkCardLogs() throws Exception {
        DealGameTransaction();
        BigInteger curBlockNumber = BSCUtils.getBlockNumber();
        if (curBlockNumber.compareTo(BigInteger.ZERO) == 0) {
            return;
        }
        BigInteger newMintLastBlock = BigInteger.ZERO;
        String mintLastBlockStr = commonMapper.getSysConfigByKey(FireWorkCardConstant.MINT_LAST_BLOCK);

        BigInteger mintLastBlock = new BigInteger(mintLastBlockStr);
        if (mintLastBlock.add(MAX_HEIGHT).compareTo(curBlockNumber) >= 0) {
            newMintLastBlock = curBlockNumber;
        } else {
            newMintLastBlock = mintLastBlock.add(MAX_HEIGHT);
        }

        if (isfirst) {
            mintLastBlock = BigInteger.valueOf(20321726);
            newMintLastBlock = BigInteger.valueOf(20321727);
            isfirst = false;
        }

        String fromBlock = mintLastBlock.add(BigInteger.ONE).toString(16);
        String toBlock = newMintLastBlock.toString(16);

        System.out.println("util fromBlock:" + Integer.valueOf(fromBlock, 16) + " toblock:" + Integer.valueOf(toBlock, 16));
        //FHC
        String mintLogs = requestGetLog(BSCUtils.getFireworkCardAddress(), fromBlock, toBlock, BSCUtils.getFireworkCardMint_topic(), 0);
        handleMintLogs(mintLogs);
        String transferLogs = requestGetLog(BSCUtils.getFireworkCardAddress(), fromBlock, toBlock, BSCUtils.getTransfer_topic(), 0);
        handleTransferLogs(transferLogs);
        String mutatedLogs = requestGetLog(BSCUtils.getFireworkCardAddress(), fromBlock, toBlock, BSCUtils.getFireworkCardMutated_topic(), 0);
        handleMutatedLogs(mutatedLogs);

        //HSE
        String hseTransferLogs = requestGetLog(BSCUtils.getHSE_address(), fromBlock, toBlock, BSCUtils.getTransfer_topic(), 0);
        handleHseTransferLogs(hseTransferLogs);

        //PSE
        String pseMintLogs = requestGetLog(BSCUtils.getPSE_address(), fromBlock, toBlock, BSCUtils.getPSEMint_topic(), 0);
        handlePseMintLogs(pseMintLogs);
        String pseTransferLogs = requestGetLog(BSCUtils.getPSE_address(), fromBlock, toBlock, BSCUtils.getTransfer_topic(), 0);
        handlePseTransferLogs(pseTransferLogs);
        String pseSwapLogs = requestGetLog(BSCUtils.getPSE_swap_address(), fromBlock, toBlock, BSCUtils.getPSESwap_topic(), 0);
        handlePseSwapLogs(pseSwapLogs);

        //EGSE
        //String egseMintLogs = requestGetLog(BSCUtils.getEGSE_address(), fromBlock, toBlock, BSCUtils.getEGSEMint_topic(), 0);
        //handleEgseMintLogs(egseMintLogs);
        //String egseTransferLogs = requestGetLog(BSCUtils.getEGSE_address(), fromBlock, toBlock, BSCUtils.getTransfer_topic(), 0);
        //handleEgseTransferLogs(egseTransferLogs);

        //SHIP
        String shipMintLogs = requestGetLog(BSCUtils.getSHIP_address(), fromBlock, toBlock, BSCUtils.getSHIPMint_topic(), 0);
        handleShipMintLogs(shipMintLogs);
        String shipTransferLogs = requestGetLog(BSCUtils.getSHIP_address(), fromBlock, toBlock, BSCUtils.getTransfer_topic(), 0);
        handleSHIPTransferLogs(shipTransferLogs);

        //EGG
        String eggMintLogs = requestGetLog(BSCUtils.getEGG_address(), fromBlock, toBlock, BSCUtils.getEGGMint_topic(), 0);
        handleEggMintLogs(eggMintLogs);
        String eggTransferLogs = requestGetLog(BSCUtils.getEGG_address(), fromBlock, toBlock, BSCUtils.getTransfer_topic(), 0);
        handleEggTransferLogs(eggTransferLogs);

        //MBSE
        String mbseMintAndTransferLogs = requestGetLog(BSCUtils.getMBSE_address(), fromBlock, toBlock, BSCUtils.getTransfer_topic(), 0);
        handleMbseTransferLogs(mbseMintAndTransferLogs);

        //MBSE Rebate
        String mbseRebateLogs = requestGetLog(BSCUtils.getMBSERebate_address(), fromBlock, toBlock, BSCUtils.getMBSERebate_topic(), 0);
        handleMbseRebateLogs(mbseRebateLogs);

        //ASE
        String aseMintLogs = requestGetLog(BSCUtils.getASE_address(), fromBlock, toBlock, BSCUtils.getASEMint_topic(), 0);
        handleAseMintLogs(aseMintLogs);
        String aseTransferLogs = requestGetLog(BSCUtils.getASE_address(), fromBlock, toBlock, BSCUtils.getTransfer_topic(), 0);
        handleAseTransferLogs(aseTransferLogs);

        //ISE 1155
        String iseMintAndTransferLogs = requestGetLog(BSCUtils.getISE_address(), fromBlock, toBlock, BSCUtils.getISE_topic(), 0);
        handleIseTransferLogs(iseMintAndTransferLogs);

        //TAKE IN
        String takeInLogs = requestGetLog(BSCUtils.getNftTakeToGame_adress(), fromBlock, toBlock, BSCUtils.getNftTakeToGame_topic(), 0);
        handleTakeInLogs(takeInLogs);

        // trade logs
        String buyNftLogs = requestGetLog(BSCUtils.getNFTTransferAddress(), fromBlock, toBlock, BSCUtils.getNFTtransfer_topic(), 0);
        handleBuyNftLogs(buyNftLogs);
        String quickSoldLogs = requestGetLog(BSCUtils.getNFTTransferAddress(), fromBlock, toBlock, BSCUtils.getNFTQuickSale_topic(), 0);
        handleQuickSoldLogs(quickSoldLogs);
        String buyNft1155Logs = requestGetLog(BSCUtils.getNFTTransferAddress(), fromBlock, toBlock, BSCUtils.getTransfer1155_topic(), 0);
        handleBuyNft1155Logs(buyNft1155Logs);

        commonMapper.upDateSysConfigByKey(FireWorkCardConstant.MINT_LAST_BLOCK, newMintLastBlock.toString());

        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void afterCommit() {
                    log.info("Transaction end");
                    //DealGameTransaction();
                }
            });
        }
    }


    private String requestGetLog(String contractAddress, String fromBlockNumber, String toBlockNumber, String topic, int type) throws Exception {
        String jsonData = "{\"jsonrpc\":\"2.0\",\"method\":\"eth_getLogs\",\"params\":[{\"address\":\"" + contractAddress + "\",\"fromBlock\": \"0x" + fromBlockNumber + "\",\"toBlock\":\"0x" + toBlockNumber + "\",\"topics\":[\"" + topic + "\"]}],\"id\":1}";
        HttpEntity<String> request = new HttpEntity<>(jsonData, headers);
        try {
            ResponseEntity<String> postForEntity = restTemplate.postForEntity(BSCUtils.getNode_url(), request, String.class);
            String body = postForEntity.getBody();
            boolean error = body.contains("error");
            if (error) {
                System.out.println(body);
                throw new Exception("requestGetLog 获取数据异常");
            }
            return body;
        } catch (Exception e) {
            throw e;
        }
    }


    private String getBlockByHash(String blockHash) {
        String jsonData = "{\"jsonrpc\":\"2.0\",\"method\":\"eth_getBlockByHash\",\"params\":[\"" + blockHash + "\",false],\"id\":1}";
        HttpEntity<String> request = new HttpEntity<>(jsonData, headers);
        try {
            ResponseEntity<String> postForEntity = restTemplate.postForEntity(BSCUtils.getNode_url(), request, String.class);
            return postForEntity.getBody();
        } catch (Exception e) {
            return "";
        }
    }

    public void DealGameTransaction() {
        while (queGameMsg.size() > 0) {
            GameMsg poll = queGameMsg.poll();
            String func = poll.getFunc();
            String firstAdrr = poll.getFirstAdrr();
            String secondAddr = poll.getSecondAddr();

            String url = BSCUtils.getWorld_url();
            HttpHeaders formHeader = new HttpHeaders();
            formHeader.setContentType(MediaType.MULTIPART_FORM_DATA);
            formHeader.set("Accept-Encoding", "gzip, deflate, br");
            formHeader.set("Accept", "*/*");

            String data = "";
            if (func == "ship") {
                data += "ship";
            } else if (func == "pse") {
                data += "pse";
            } else {
                data += "default";
            }

            try {
                if (firstAdrr != "") {
                    UserMailDto userInfo = fcMapper.getUserInfoByOwnerAddr(firstAdrr);
                    if (userInfo != null) {
                        data += ",";
                        BigInteger userId = userInfo.getUserId();
                        data += userId.toString();
                    }
                }

                if (secondAddr != "") {
                    UserMailDto userInfo = fcMapper.getUserInfoByOwnerAddr(secondAddr);
                    if (userInfo != null) {
                        BigInteger userId = userInfo.getUserId();
                        data += ",";
                        data += userId.toString();
                    }
                }
            } catch (Exception e) {
                log.info("maybe game db can not visit");
            }

            MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
            map.add("op_code", "580");
            map.add("t_data", data);

            log.info("send to game:" + data);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, formHeader);
            try {
                restTemplate.postForEntity(url, request, String.class);
                log.info("notify game msg send success");
            } catch (Exception e) {
                log.info("notify game msg miss");
                log.info(e.toString());
            }
        }
    }

    private void NotifyGameTransaction(String func, String firstAdrr, String secondAddr) {
        GameMsg msg = new GameMsg();
        msg.setFunc(func);
        msg.setFirstAdrr(firstAdrr);
        msg.setSecondAddr(secondAddr);
        queGameMsg.offer(msg);
    }

    /**
     * EGSE mint日志
     *
     * @param egseMintLogs
     * @throws Exception
     */
    private void handleEgseMintLogs(String egseMintLogs) throws Exception {
        Map mintMap = JSONObject.parseObject(egseMintLogs, Map.class);
        JSONArray mintResult = (JSONArray) mintMap.get("result");
        if (mintResult != null && mintResult.size() > 0) {
            for (int i = 0; i < mintResult.size(); i++) {
                JSONObject record = (JSONObject) mintResult.get(i);
                String data = record.getString("data");
                String ownerAddress = "0x" + data.substring(26, 66);
                String cardIdStr = data.substring(67, 130).replaceAll("^(0+)", "");
                String itemDataStr = data.substring(131).replaceAll("^(0+)", "");
                BigInteger tokenId = new BigInteger(StringUtils.isEmpty(cardIdStr) ? "0" : cardIdStr, 16);
                BigInteger type = new BigInteger(StringUtils.isEmpty(itemDataStr) ? "0" : itemDataStr, 16);
                NftEgseInfoDto nftEgseInfoDto = egseMapper.getNftInfoByTokenId(tokenId);
                if (nftEgseInfoDto == null) {
                    nftEgseInfoDto = new NftEgseInfoDto();
                    nftEgseInfoDto.setViews(0);
                    nftEgseInfoDto.setFavorites(0);
                }
                nftEgseInfoDto.setTokenId(tokenId);
                nftEgseInfoDto.setOwnerAddress(ownerAddress);
                nftEgseInfoDto.setItemData(itemDataStr);
                nftEgseInfoDto.setType(type.intValue());
                nftEgseInfoDto.setTokenURI(BSCUtils.getTokenURI(BSCUtils.getEGSE_address(), tokenId));
                nftEgseInfoDto = getEgseTokenURI(nftEgseInfoDto);
                egseMapper.upInsetNftInfo(nftEgseInfoDto);
            }

        }
    }

    /**
     * SHIP mint日志
     *
     * @param shipMintLogs
     * @throws Exception
     */
    private void handleShipMintLogs(String shipMintLogs) throws Exception {
        Map logMap = JSONObject.parseObject(shipMintLogs, Map.class);
        JSONArray mintResult = (JSONArray) logMap.get("result");
        System.out.println(mintResult);
        if (mintResult != null && mintResult.size() > 0) {
            for (int i = 0; i < mintResult.size(); i++) {
                JSONObject record = (JSONObject) mintResult.get(i);
                String data = record.getString("data");
                String ownerAddress = "0x" + data.substring(26, 66);
                String cardIdStr = data.substring(67, 130).replaceAll("^(0+)", "");
                String itemDataStr = data.substring(131).replaceAll("^(0+)", "");
                getNftShipItemData(ownerAddress, cardIdStr, itemDataStr);
            }
        }
    }

    /**
     * EGG mint日志
     *
     * @param eggMintLogs
     * @throws Exception
     */
    private void handleEggMintLogs(String eggMintLogs) throws Exception {
        Map logMap = JSONObject.parseObject(eggMintLogs, Map.class);
        JSONArray mintResult = (JSONArray) logMap.get("result");
        if (mintResult != null && mintResult.size() > 0) {
            for (int i = 0; i < mintResult.size(); i++) {
                JSONObject record = (JSONObject) mintResult.get(i);
                String data = record.getString("data");
                String ownerAddress = "0x" + data.substring(26, 66);
                String cardIdStr = data.substring(67, 130).replaceAll("^(0+)", "");
                String itemDataStr = data.substring(131).replaceAll("^(0+)", "");
                getNftEggItemData(ownerAddress, cardIdStr, itemDataStr);
            }
        }
    }

    /**
     * MBSE 转账日志
     *
     * @param mbseMintLogs
     * @throws Exception
     */
    private void handleMbseTransferLogs(String mbseMintLogs) throws Exception {
        Map logMap = JSONObject.parseObject(mbseMintLogs, Map.class);
        JSONArray mintResult = (JSONArray) logMap.get("result");
        if (mintResult != null && mintResult.size() > 0) {
            for (int i = 0; i < mintResult.size(); i++) {
                JSONObject record = (JSONObject) mintResult.get(i);
                JSONArray topics = (JSONArray)record.get("topics");

                String from = "0x" + topics.get(1).toString().substring(26, 66);
                String to = "0x" + topics.get(2).toString().substring(26, 66);
                String data = (String)topics.get(3);

                String cardIdStr = data.substring(2).replaceAll("^(0+)", "");
                BigInteger tokenId = new BigInteger(StringUtils.isEmpty(cardIdStr) ? "0" : cardIdStr, 16);

                NftMbseInfoDto nftMbseInfoDto = mbseMapper.getNftInfoByTokenId(tokenId);

                //from 0地址 to 非0地址，生产
                //from 非0地址 to 0地址， 转账
                //from 非0地址 to 非0地址，转账
                if (!Pattern.matches("0x0+", from)) {
                    if (nftMbseInfoDto == null) {
                        log.info("没有查询到tokenId:{}", tokenId);
                    } else if (!nftMbseInfoDto.getOwnerAddress().equalsIgnoreCase(to)) {
                        log.info("mbse transfer tokenId>>>{},owner 发生变化，执行更新操作 ownerAddress:{} toAddress:{}",
                                tokenId, nftMbseInfoDto.getOwnerAddress(), to);
                        nftMbseInfoDto.setOwnerAddress(to);
                        mbseMapper.deleteSellingOrderByTokenIdAndAddress(tokenId, from, NftTypeEnum.MBSE.getCode());
                        mbseMapper.upInsetNftInfo(nftMbseInfoDto);
                    }
                } else {
                    //盲盒被打开后将会获取不到URI
                    try {
                        BSCUtils.getTokenURI(BSCUtils.getMBSE_address(), tokenId);
                    } catch (Exception e) {
                        log.error("mbse获取不到URI，可能是mbse已使用:" + BSCUtils.getMBSE_address() + " tokenId:"+tokenId);
                        continue;
                    }

                    if (nftMbseInfoDto == null) {
                        nftMbseInfoDto = new NftMbseInfoDto();
                        nftMbseInfoDto.setViews(0);
                        nftMbseInfoDto.setFavorites(0);
                    }
                    log.info("mbse mint tokenId>>>{},owner 发生变化，执行更新操作 ownerAddress:{} toAddress:{}",
                            tokenId, from, to);
                    nftMbseInfoDto.setTokenId(tokenId);
                    nftMbseInfoDto.setOwnerAddress(to);
                    nftMbseInfoDto.setItemData("");
                    nftMbseInfoDto = getMbseTokenURI(nftMbseInfoDto);
                    mbseMapper.upInsetNftInfo(nftMbseInfoDto);
                }
            }
        }
    }

    /**
     * MBSE 返佣日志
     *
     * @param mbseRebateLogs
     * @throws Exception
     */
    private void handleMbseRebateLogs(String mbseRebateLogs) throws Exception {
        Map logMap = JSONObject.parseObject(mbseRebateLogs, Map.class);
        JSONArray mintResult = (JSONArray) logMap.get("result");
        if (mintResult != null && mintResult.size() > 0) {
            for (int i = 0; i < mintResult.size(); i++) {
                JSONObject record = (JSONObject) mintResult.get(i);
                String data = record.getString("data");
                String ownerAddress = "0x" + data.substring(26, 66);
                String erc20Address = "0x" + data.substring(67, 130).replaceAll("^(0+)", "");
                String aMount = data.substring(131).replaceAll("^(0+)", "");

                if (erc20Address.equals("0x")) {
                    erc20Address = "0";
                }
                BigDecimal price = new BigDecimal(new BigInteger(aMount, 16)).divide(new BigDecimal(Math.pow(10, 18)),
                        6, RoundingMode.HALF_DOWN);
                dealRebateInfo(ownerAddress, price, erc20Address);
                System.out.println("value");
            }
        }
    }

    /**
     * TAKEIN 游戏带入
     *
     * @param takeInRebateLogs
     * @throws Exception
     */
    private void handleTakeInLogs(String takeInRebateLogs) throws Exception {
        Map logMap = JSONObject.parseObject(takeInRebateLogs, Map.class);
        JSONArray mintResult = (JSONArray) logMap.get("result");
        if (mintResult != null && mintResult.size() > 0) {
            for (int i = 0; i < mintResult.size(); i++) {
                JSONObject record = (JSONObject) mintResult.get(i);
                String data = record.getString("data");
                String ownerAddress = "0x" + data.substring(26, 66);
                String toGame = data.substring(67, 130).replaceAll("^(0+)", "");
                String assetType = data.substring(131, 194).replaceAll("^(0+)", "");
                String swapTransId = data.substring(195, 258).replaceAll("^(0+)", "");

                log.info("handleTakeInLogs data: {}", data);
                log.info("handleTakeInLogs ownerAddress: {}", ownerAddress);
                log.info("handleTakeInLogs toGame: {}", toGame);
                log.info("handleTakeInLogs assetType: {}", assetType);
                log.info("handleTakeInLogs swapTransId: {}", new BigInteger(swapTransId, 16));
                NftGameSwapInfo nftGameSwapInfo = BSCUtils.queryNftSwap(ownerAddress, Integer.valueOf(toGame, 16), new BigInteger(swapTransId, 16));

                if (Integer.valueOf(assetType, 16) == AssetTypeEnum.ERC721.getCode()) {
                    //飞船资产业务逻辑
                    if (Integer.valueOf(toGame, 16) == GameTagEnum.SPARKERA_v3.getCode()) {
                        if (nftGameSwapInfo.getNftAddress().equalsIgnoreCase(BSCUtils.getSHIP_address())) {
                            try {
                                gameProcess.SynShipNft(ownerAddress, nftGameSwapInfo.getListTokenId());
                                NotifyGameTransaction("ship", ownerAddress, "");
                                log.info("handleTakeInLogs success");
                            } catch(Exception e) {
                                log.info("handleTakeInLogs not success");
                            }
                        } else {
                            log.error("handleTakeInLogs nftAddress:{}", nftGameSwapInfo.getNftAddress());
                            log.error("handleTakeInLogs shipAddress:{}", BSCUtils.getSHIP_address());
                        }
                    }
                }
                System.out.println("handleTakeInLogs");
            }
        }
    }

    /**
     * ISE 转账日志
     *
     * @param iseMintLogs
     * @throws Exception
     */
    private void handleIseTransferLogs(String iseMintLogs) throws Exception {
        Map logMap = JSONObject.parseObject(iseMintLogs, Map.class);
        JSONArray mintResult = (JSONArray) logMap.get("result");
        if (mintResult != null && mintResult.size() > 0) {
            for (int i = 0; i < mintResult.size(); i++) {
                JSONObject record = (JSONObject) mintResult.get(i);
                JSONArray topics = (JSONArray)record.get("topics");
                String data = record.get("data").toString().substring(130);

                String from = "0x" + topics.get(2).toString().substring(26, 66);
                String to = "0x" + topics.get(3).toString().substring(26, 66);

                ArrayList<Integer> ids = new ArrayList<>();
                ArrayList<Integer> nums = new ArrayList<>();
                //解析物品id数组
                Integer ids_cnt = Integer.valueOf(data.substring(0, 64), 16);
                data = data.substring(64);
                for (int j = 0; j < ids_cnt; j++) {
                    Integer id = Integer.valueOf(data.substring(0, 64), 16);
                    ids.add(id);
                    data = data.substring(64);
                }

                //解析物品数量数组
                Integer nums_cnt = Integer.valueOf(data.substring(0, 64), 16);
                data = data.substring(64);
                for (int j = 0; j < nums_cnt; j++) {
                    Integer num = Integer.valueOf(data.substring(0, 64), 16);
                    nums.add(num);
                    data = data.substring(64);
                }

                //from 0地址 to 非0地址，生产
                //from 非0地址 to 0地址， 转账
                //from 非0地址 to 非0地址，转账
                for (int j = 0; j < nums_cnt; j++) {
                    if (!Pattern.matches("0x0+", from)) {
                        NftIseInfoDto nftIseInfoDto = iseMapper.getNftInfoByTokenIdMul(ids.get(j), from);
                        if (nftIseInfoDto == null) {
                            throw new Exception("ise from address not found:" + from);
                        } else if (!nftIseInfoDto.getOwnerAddress().equalsIgnoreCase(to)) {
                            //from地址用户减少资产
                            int from_left_cnt = nftIseInfoDto.getCnt() - nums.get(j);
                            if (from_left_cnt >= 0) {
                                ChangeAddressIseNft(from, ids.get(j), -nums.get(j));
                                //iseMapper.deleteAllIseNftByAddress(from, 8);
                            } else {
                                throw new Exception("ise from address is not enough:" + from);
                            }

                            log.info("ise transfer id:{} num:{},owner 发生变化，执行更新操作  ownerAddress:{} toAddress:{}",
                                    ids.get(j), nums.get(j), from, to);
                            //to地址用户增加资产
                            ChangeAddressIseNft(to, ids.get(j), nums.get(j));
                        }
                    } else {
                        log.info("ise mint id:{} num:{},owner 发生变化，执行更新操作  ownerAddress:{} toAddress:{}",
                                ids.get(j), nums.get(j), from, to);
                        ChangeAddressIseNft(to, ids.get(j), nums.get(j));
                    }
                }
            }
        }
    }

    /**
     * 修改ise用户资产
     */
    private void ChangeAddressIseNft(String address, Integer id, Integer ct) throws Exception {
        NftIseInfoDto nftIseInfoDto = iseMapper.getNftInfoByTokenIdMul(id, address);
        //没有用户则创建用户
        if (nftIseInfoDto == null) {
            nftIseInfoDto = new NftIseInfoDto();
            nftIseInfoDto.setViews(0);
            nftIseInfoDto.setFavorites(0);

            nftIseInfoDto.setId(id);
            nftIseInfoDto.setCnt(0);
            nftIseInfoDto.setOwnerAddress(address);
            nftIseInfoDto.setItemData("");
            nftIseInfoDto = getIseItemURI(nftIseInfoDto);
        }
        nftIseInfoDto.setCnt(nftIseInfoDto.getCnt() + ct);
        iseMapper.upInsetNftInfo(nftIseInfoDto);
    }

    /**
     * ASE mint日志
     *
     * @param aseMintLogs
     * @throws Exception
     */
    private void handleAseMintLogs(String aseMintLogs) throws Exception {
        Map logMap = JSONObject.parseObject(aseMintLogs, Map.class);
        JSONArray mintResult = (JSONArray) logMap.get("result");
        if (mintResult != null && mintResult.size() > 0) {
            for (int i = 0; i < mintResult.size(); i++) {
                JSONObject record = (JSONObject) mintResult.get(i);
                JSONArray topics = (JSONArray)record.get("topics");
                String data1 = (String)topics.get(2);
                String data2 = (String)topics.get(3);
                String ownerAddress = "0x" + data1.substring(26, 66);
                String cardIdStr = data2.substring(3).replaceAll("^(0+)", "");
                String itemDataStr = "";

                BigInteger tokenId = new BigInteger(StringUtils.isEmpty(cardIdStr) ? "0" : cardIdStr, 16);
                NftAseInfoDto nftAseInfoDto = aseMapper.getNftInfoByTokenId(tokenId);
                if (nftAseInfoDto == null) {
                    nftAseInfoDto = new NftAseInfoDto();
                    nftAseInfoDto.setViews(0);
                    nftAseInfoDto.setFavorites(0);
                }
                log.info("ase mint tokenId>>>{},owner 发生变化，执行更新操作 ownerAddress:{}",
                        tokenId, ownerAddress);
                nftAseInfoDto.setTokenId(tokenId);
                nftAseInfoDto.setOwnerAddress(ownerAddress);
                nftAseInfoDto.setItemData(itemDataStr);
                nftAseInfoDto = getAseTokenURI(nftAseInfoDto);
                aseMapper.upInsetNftInfo(nftAseInfoDto);
            }
        }
    }

    /**
     * PSE mint 日志
     *
     * @param pseMintLogs
     * @throws Exception
     */
    private void handlePseMintLogs(String pseMintLogs) throws Exception {
        Map mintMap = JSONObject.parseObject(pseMintLogs, Map.class);
        JSONArray mintResult = (JSONArray) mintMap.get("result");
        if (mintResult != null && mintResult.size() > 0) {
            for (int i = 0; i < mintResult.size(); i++) {
                JSONObject record = (JSONObject) mintResult.get(i);
                String data = record.getString("data");
                String ownerAddress = "0x" + data.substring(26, 66);
                String cardIdStr = data.substring(67, 130).replaceAll("^(0+)", "");
                String itemDataStr = data.substring(131).replaceAll("^(0+)", "");
                BigInteger tokenId = new BigInteger(StringUtils.isEmpty(cardIdStr) ? "0" : cardIdStr, 16);
                BigInteger type = new BigInteger(StringUtils.isEmpty(itemDataStr) ? "0" : itemDataStr, 16);

                try {
                    BSCUtils.getTokenURI(BSCUtils.getPSE_address(), tokenId);
                } catch (Exception e) {
                    log.error("pse获取不到URI，可能是pse已使用:" + BSCUtils.getPSE_address() + " tokenId:"+tokenId);
                    continue;
                }

                NftPseInfoDto nftPseInfoDto = pseMapper.getNftInfoByTokenId(tokenId);
                if (nftPseInfoDto == null) {
                    nftPseInfoDto = new NftPseInfoDto();
                    nftPseInfoDto.setViews(0);
                    nftPseInfoDto.setFavorites(0);
                }

                nftPseInfoDto.setTokenId(tokenId);
                nftPseInfoDto.setOwnerAddress(ownerAddress);
                nftPseInfoDto.setItemData(itemDataStr);
                nftPseInfoDto.setType(type.intValue());
                nftPseInfoDto.setTokenURI(BSCUtils.getTokenURI(BSCUtils.getPSE_address(), tokenId));
                nftPseInfoDto = getPseTokenURI(nftPseInfoDto);
                pseMapper.upInsetNftInfo(nftPseInfoDto);
                NotifyGameTransaction("pse", ownerAddress, "");
            }

        }
    }

    /**
     * PSE swap 日志 (此接口用于更新pse卡牌数据)
     *
     * @param pseSwapLogs
     * @throws Exception
     */
    private void handlePseSwapLogs(String pseSwapLogs) throws Exception {
        Map mintMap = JSONObject.parseObject(pseSwapLogs, Map.class);
        JSONArray mintResult = (JSONArray) mintMap.get("result");
        if (mintResult != null && mintResult.size() > 0) {
            for (int i = 0; i < mintResult.size(); i++) {
                JSONObject record = (JSONObject) mintResult.get(i);
                String data = record.getString("data");
                String cardIdStr = data.substring(67, 130).replaceAll("^(0+)", "");

                BigInteger tokenId = new BigInteger(StringUtils.isEmpty(cardIdStr) ? "0" : cardIdStr, 16);
                String tokenURI = null;
                try {
                    tokenURI = BSCUtils.getTokenURI(BSCUtils.getPSE_address(), tokenId);
                } catch (Exception e) {
                    log.error("pse获取不到URI，可能是pse已使用:" + BSCUtils.getPSE_address() + " tokenId:"+tokenId);
                    continue;
                }

                NftPseInfoDto nftPseInfoDto = pseMapper.getNftInfoByTokenId(tokenId);
                if (nftPseInfoDto == null) {
                    log.error("pse找不到数据，但是却收到更新消息 tokenId:{}", tokenId);
                    continue;
                }

                nftPseInfoDto.setTokenURI(tokenURI);
                nftPseInfoDto = getPseTokenURI(nftPseInfoDto);
                pseMapper.upInsetNftInfo(nftPseInfoDto);
                NotifyGameTransaction("pse", nftPseInfoDto.getOwnerAddress(), "");
            }

        }
    }

    /**
     * EGSE 转账日志
     *
     * @param egseTransferLogs
     * @throws Exception
     */
    private void handleEgseTransferLogs(String egseTransferLogs) throws Exception {
        Map transferMap = JSONObject.parseObject(egseTransferLogs, Map.class);
        JSONArray transferResult = (JSONArray) transferMap.get("result");
        if (transferResult != null && transferResult.size() > 0) {
            for (int i = 0; i < transferResult.size(); i++) {
                JSONObject record = (JSONObject) transferResult.get(i);
                String toAddress = "0x" + record.getJSONArray("topics").get(2).toString().substring(26, 66);
                String cardIdStr = record.getJSONArray("topics").get(3).toString().toString().substring(26, 66).replaceAll("^(0+)", "");
                BigInteger tokenId = new BigInteger(StringUtils.isEmpty(cardIdStr) ? "0" : cardIdStr, 16);
                NftEgseInfoDto nftEgseInfoDto = egseMapper.getNftInfoByTokenId(tokenId);
                if (nftEgseInfoDto == null) {
                    nftEgseInfoDto = new NftEgseInfoDto();
                    nftEgseInfoDto.setOwnerAddress(toAddress);
                    nftEgseInfoDto.setTokenId(tokenId);
                    nftEgseInfoDto.setFavorites(0);
                    nftEgseInfoDto.setViews(0);
                    nftEgseInfoDto.setTokenURI(BSCUtils.getTokenURI(BSCUtils.getEGSE_address(), tokenId));
                    nftEgseInfoDto = getEgseTokenURI(nftEgseInfoDto);
                    egseMapper.upInsetNftInfo(nftEgseInfoDto);
                } else if (!nftEgseInfoDto.getOwnerAddress().equalsIgnoreCase(toAddress)) {
                    nftEgseInfoDto.setOwnerAddress(toAddress);
                    egseMapper.deleteSellingOrderByTokenIdAndAddress(tokenId, toAddress, NftTypeEnum.EGSE.getCode());
                    egseMapper.upInsetNftInfo(nftEgseInfoDto);
                }

            }
        }
    }

    /**
     * SHIP 转账日志
     *
     * @param shipTransferLogs
     * @throws Exception
     */
    private void handleSHIPTransferLogs(String shipTransferLogs) throws Exception {
        Map transferMap = JSONObject.parseObject(shipTransferLogs, Map.class);
        JSONArray transferResult = (JSONArray) transferMap.get("result");
        if (transferResult != null && transferResult.size() > 0) {
            for (int i = 0; i < transferResult.size(); i++) {
                JSONObject record = (JSONObject) transferResult.get(i);
                String toAddress = "0x" + record.getJSONArray("topics").get(2).toString().substring(26, 66);
                String cardIdStr = record.getJSONArray("topics").get(3).toString().toString().substring(26, 66).replaceAll("^(0+)", "");
                BigInteger tokenId = new BigInteger(StringUtils.isEmpty(cardIdStr) ? "0" : cardIdStr, 16);
                NftShipInfoDto nfthipInfoDto = shipMapper.getNftInfoByTokenId(tokenId);
                if (nfthipInfoDto == null) {
                    log.info("没有查询到tokenId:{}", tokenId);
                    continue;
                } else if (!nfthipInfoDto.getOwnerAddress().equalsIgnoreCase(toAddress)) {
                    log.info("ship tokenId>>>{},owner 发生变化，执行更新操作 ownerAddress:{} toAddress:{}",
                            tokenId, nfthipInfoDto.getOwnerAddress(), toAddress);
                    String ownerAddress = nfthipInfoDto.getOwnerAddress();
                    nfthipInfoDto.setOwnerAddress(toAddress);
                    shipMapper.deleteSellingOrderByTokenIdAndAddress(tokenId, toAddress, NftTypeEnum.SHIP.getCode());
                    shipMapper.upInsetNftInfo(nfthipInfoDto);
                }
            }
        }
    }

    /**
     * Egg 转账日志
     *
     * @param eggTransferLogs
     * @throws Exception
     */
    private void handleEggTransferLogs(String eggTransferLogs) throws Exception {
        Map transferMap = JSONObject.parseObject(eggTransferLogs, Map.class);
        JSONArray transferResult = (JSONArray) transferMap.get("result");
        if (transferResult != null && transferResult.size() > 0) {
            for (int i = 0; i < transferResult.size(); i++) {
                JSONObject record = (JSONObject) transferResult.get(i);
                String toAddress = "0x" + record.getJSONArray("topics").get(2).toString().substring(26, 66);
                String cardIdStr = record.getJSONArray("topics").get(3).toString().substring(26, 66).replaceAll("^(0+)", "");
                BigInteger tokenId = new BigInteger(StringUtils.isEmpty(cardIdStr) ? "0" : cardIdStr, 16);
                NftEggInfoDto nftEggInfoDto = eggMapper.getNftInfoByTokenId(tokenId);
                if (nftEggInfoDto == null) {
                    log.info("没有查询到tokenId:{}", tokenId);
                    continue;
                } else if (!nftEggInfoDto.getOwnerAddress().equalsIgnoreCase(toAddress)) {
                    log.info("egg tokenId>>>{},owner 发生变化，执行更新操作 ownerAddress:{} toAddress:{}",
                            tokenId, nftEggInfoDto.getOwnerAddress(), toAddress);
                    nftEggInfoDto.setOwnerAddress(toAddress);
                    eggMapper.deleteSellingOrderByTokenIdAndAddress(tokenId, toAddress, NftTypeEnum.EGG.getCode());
                    eggMapper.upInsetNftInfo(nftEggInfoDto);
                }
            }
        }
    }

    /**
     * Ase 转账日志
     *
     * @param aseTransferLogs
     * @throws Exception
     */
    private void handleAseTransferLogs(String aseTransferLogs) throws Exception {
        Map transferMap = JSONObject.parseObject(aseTransferLogs, Map.class);
        JSONArray transferResult = (JSONArray) transferMap.get("result");
        if (transferResult != null && transferResult.size() > 0) {
            for (int i = 0; i < transferResult.size(); i++) {
                JSONObject record = (JSONObject) transferResult.get(i);
                String toAddress = "0x" + record.getJSONArray("topics").get(2).toString().substring(26, 66);
                String cardIdStr = record.getJSONArray("topics").get(3).toString().substring(26, 66).replaceAll("^(0+)", "");
                BigInteger tokenId = new BigInteger(StringUtils.isEmpty(cardIdStr) ? "0" : cardIdStr, 16);
                NftAseInfoDto nftAseInfoDto = aseMapper.getNftInfoByTokenId(tokenId);
                if (nftAseInfoDto == null) {
                    log.info("没有查询到tokenId:{}", tokenId);
                    continue;
                } else if (!nftAseInfoDto.getOwnerAddress().equalsIgnoreCase(toAddress)) {
                    log.info("ase mint tokenId>>>{},owner 发生变化，执行更新操作 ownerAddress:{} toAddress:{}",
                            tokenId, nftAseInfoDto.getOwnerAddress(), toAddress);
                    nftAseInfoDto.setOwnerAddress(toAddress);
                    aseMapper.deleteSellingOrderByTokenIdAndAddress(tokenId, toAddress, NftTypeEnum.ASE.getCode());
                    aseMapper.upInsetNftInfo(nftAseInfoDto);
                }
            }
        }
    }

    private void handlePseTransferLogs(String pseTransferLogs) throws Exception {
        Map transferMap = JSONObject.parseObject(pseTransferLogs, Map.class);
        JSONArray transferResult = (JSONArray) transferMap.get("result");
        if (transferResult != null && transferResult.size() > 0) {
            for (int i = 0; i < transferResult.size(); i++) {
                JSONObject record = (JSONObject) transferResult.get(i);
                String toAddress = "0x" + record.getJSONArray("topics").get(2).toString().substring(26, 66);
                String cardIdStr = record.getJSONArray("topics").get(3).toString().toString().substring(26, 66).replaceAll("^(0+)", "");
                BigInteger tokenId = new BigInteger(StringUtils.isEmpty(cardIdStr) ? "0" : cardIdStr, 16);
                NftPseInfoDto nftpseInfoDto = pseMapper.getNftInfoByTokenId(tokenId);
                if (nftpseInfoDto == null) {
                    try {
                        BSCUtils.getTokenURI(BSCUtils.getPSE_address(), tokenId);
                    } catch (Exception e) {
                        log.error("pse transfer获取不到URI，可能是pse已使用:" + BSCUtils.getPSE_address() + " tokenId:"+tokenId);
                        continue;
                    }
                    nftpseInfoDto = new NftPseInfoDto();
                    nftpseInfoDto.setOwnerAddress(toAddress);
                    nftpseInfoDto.setTokenId(tokenId);
                    nftpseInfoDto.setFavorites(0);
                    nftpseInfoDto.setViews(0);
                    nftpseInfoDto.setTokenURI(BSCUtils.getTokenURI(BSCUtils.getPSE_address(), tokenId));
                    nftpseInfoDto = getPseTokenURI(nftpseInfoDto);
                    pseMapper.upInsetNftInfo(nftpseInfoDto);
                    NotifyGameTransaction("pse", toAddress, "");
                } else if (!nftpseInfoDto.getOwnerAddress().equalsIgnoreCase(toAddress)) {
                    String ownerAddress = nftpseInfoDto.getOwnerAddress();
                    nftpseInfoDto.setOwnerAddress(toAddress);
                    pseMapper.upInsetNftInfo(nftpseInfoDto);
                    pseMapper.deleteSellingOrderByTokenIdAndAddress(tokenId, toAddress, NftTypeEnum.PSE.getCode());
                    NotifyGameTransaction("pse", ownerAddress, toAddress);
                }

            }
        }
    }


    private void handleHseTransferLogs(String hseTransferLogs) throws Exception {
        Map transferMap = JSONObject.parseObject(hseTransferLogs, Map.class);
        JSONArray transferResult = (JSONArray) transferMap.get("result");
        if (transferResult != null && transferResult.size() > 0) {
            for (int i = 0; i < transferResult.size(); i++) {
                JSONObject record = (JSONObject) transferResult.get(i);
                String toAddress = "0x" + record.getJSONArray("topics").get(2).toString().substring(26, 66);
                String cardIdStr = record.getJSONArray("topics").get(3).toString().toString().substring(26, 66).replaceAll("^(0+)", "");
                BigInteger tokenId = new BigInteger(StringUtils.isEmpty(cardIdStr) ? "0" : cardIdStr, 16);
                NftHseInfoDto nftHseInfoDto = hseMapper.getNftInfoByTokenId(tokenId);
                if (nftHseInfoDto == null) {
                    nftHseInfoDto = new NftHseInfoDto();
                    nftHseInfoDto.setOwnerAddress(toAddress);
                    nftHseInfoDto.setTokenId(tokenId);
                    nftHseInfoDto.setFavorites(0);
                    nftHseInfoDto.setViews(0);
                    nftHseInfoDto.setTokenURI(BSCUtils.getTokenURI(BSCUtils.getHSE_address(), tokenId));
                    nftHseInfoDto = getHseTokenURI(nftHseInfoDto);
                } else if (!nftHseInfoDto.getOwnerAddress().equalsIgnoreCase(toAddress)) {
                    nftHseInfoDto.setOwnerAddress(toAddress);
                    hseMapper.deleteSellingOrderByTokenIdAndAddress(tokenId, toAddress, NftTypeEnum.HSE.getCode());
                }
                int aa = hseMapper.upInsetNftInfo(nftHseInfoDto);
                System.out.println(aa);
            }
        }
    }

    private NftEgseInfoDto getEgseTokenURI(NftEgseInfoDto nftEgseInfoDto) throws Exception {
        if (!StringUtils.isEmpty(nftEgseInfoDto.getTokenURI())) {
            String res = HttpClientUtil.get(nftEgseInfoDto.getTokenURI());
            System.out.println(res);
            JSONObject obj = JSON.parseObject(res);
            nftEgseInfoDto.setName(obj.getString("name"));
            nftEgseInfoDto.setImage(obj.getString("image"));
            nftEgseInfoDto.setDescription(obj.getString("description"));
            nftEgseInfoDto.setDetailImage(obj.getString("detailimage"));
            JSONArray attrArray = obj.getJSONArray("attributes");
            for (int i = 0; i < attrArray.size(); i++) {
                JSONObject attr = (JSONObject) attrArray.get(i);
                switch (attr.getString("trait_type")) {
                    case "type":
                        nftEgseInfoDto.setAttrType(attr.getString("value"));
                        break;
                    default:
                        break;
                }
            }
            return nftEgseInfoDto;
        } else {
            throw new Exception("获取EGSE---URI异常");
        }
    }

    private NftPseInfoDto getPseTokenURI(NftPseInfoDto nftPseInfoDto) throws Exception {
        if (!StringUtils.isEmpty(nftPseInfoDto.getTokenURI())) {
            String res = HttpClientUtil.get(nftPseInfoDto.getTokenURI());
            System.out.println(res);
            JSONObject obj = JSON.parseObject(res);
            nftPseInfoDto.setName(obj.getString("name"));
            nftPseInfoDto.setImage(obj.getString("image"));
            nftPseInfoDto.setDescription(obj.getString("description"));
            nftPseInfoDto.setDetailImage(obj.getString("detailimage"));
            JSONArray attrArray = obj.getJSONArray("attributes");
            for (int i = 0; i < attrArray.size(); i++) {
                JSONObject attr = (JSONObject) attrArray.get(i);
                switch (attr.getString("trait_type")) {
                    case "type":
                        nftPseInfoDto.setAttrType(attr.getString("value"));
                        break;
                    default:
                        break;
                }
            }
            return nftPseInfoDto;
        } else {
            throw new Exception("获取PSE---URI异常");
        }
    }

    private NftHseInfoDto getHseTokenURI(NftHseInfoDto nftHseInfoDto) throws Exception {
        if (!StringUtils.isEmpty(nftHseInfoDto.getTokenURI())) {
            String res = HttpClientUtil.get(nftHseInfoDto.getTokenURI());
            System.out.println(res);
            JSONObject obj = JSON.parseObject(res);
            nftHseInfoDto.setName(obj.getString("name"));
            nftHseInfoDto.setImage(obj.getString("image"));
            nftHseInfoDto.setDescription(obj.getString("description"));
            nftHseInfoDto.setDetailImage(obj.getString("detailimage"));
            JSONArray attrArray = obj.getJSONArray("attributes");
            for (int i = 0; i < attrArray.size(); i++) {
                JSONObject attr = (JSONObject) attrArray.get(i);
                switch (attr.getString("trait_type")) {
                    case "type":
                        nftHseInfoDto.setAttrType(attr.getString("value"));
                        break;
                    default:
                        break;
                }
            }
            return nftHseInfoDto;
        } else {
            throw new Exception("获取HSE---URI异常");
        }
    }

    /**
     * 处理NFT交易日志
     *
     * @param buyNftLogs
     */
    private void handleBuyNftLogs(String buyNftLogs) {
        Map logMap = JSONObject.parseObject(buyNftLogs, Map.class);
        JSONArray buyNftResult = (JSONArray) logMap.get("result");
        if (buyNftResult != null && buyNftResult.size() > 0) {
            for (int i = 0; i < buyNftResult.size(); i++) {
                JSONObject record = (JSONObject) buyNftResult.get(i);
                String data = record.getString("data");
                String blockHash = record.getString("blockHash");
                String nftAddress = "0x" + data.substring(26, 66);
                String cardIdStr = data.substring(67, 130).replaceAll("^(0+)", "");
                BigInteger tokenId = new BigInteger(StringUtils.isEmpty(cardIdStr) ? "0" : cardIdStr, 16);
                String fromAddress = "0x" + data.substring(131, 194).replaceAll("^(0+)", "");
                String toAddress = "0x" + data.substring(195, 258).replaceAll("^(0+)", "");
                String priceStr = data.substring(323, 386).replaceAll("^(0+)", "");
                String txfeeStr = data.substring(387).replaceAll("^(0+)", "");
                BigDecimal price = new BigDecimal(new BigInteger(priceStr, 16)).divide(new BigDecimal(Math.pow(10, 18)), 6, RoundingMode.HALF_DOWN);
                BigDecimal txfee = new BigDecimal(new BigInteger(txfeeStr, 16)).divide(new BigDecimal(Math.pow(10, 18)), 6, RoundingMode.HALF_DOWN);
                price = price.add(txfee);
                String blockInfo = getBlockByHash(blockHash);
                Timestamp timestamp = null;
                if (!StringUtils.isEmpty(blockInfo)) {
                    Map blockMap = JSONObject.parseObject(blockInfo, Map.class);
                    JSONObject blockMapObject = (JSONObject) blockMap.get("result");
                    if (blockMapObject != null) {
                        String timestampStr = blockMapObject.getString("timestamp");
                        timestampStr = timestampStr.substring(2);
                        Long tradeTime = new BigInteger(timestampStr, 16).longValue();
                        timestamp = new Timestamp(tradeTime * 1000);
                    }
                }
                updateLastTradeInfo(nftAddress, tokenId, fromAddress, toAddress, price, timestamp, 0, txfee);

            }
        }
    }

    /**
     * 处理NFT 1155交易日志
     *
     * @param buyNft1155Logs
     */
    private void handleBuyNft1155Logs(String buyNft1155Logs) {
        Map logMap = JSONObject.parseObject(buyNft1155Logs, Map.class);
        JSONArray buyNftResult = (JSONArray) logMap.get("result");
        if (buyNftResult != null && buyNftResult.size() > 0) {
            for (int i = 0; i < buyNftResult.size(); i++) {
                JSONObject record = (JSONObject) buyNftResult.get(i);
                String data = record.getString("data").substring(2);
                String blockHash = record.getString("blockHash");

                String from = "0x" + data.substring(24, 64);
                String to = "0x" +data.substring(88, 128);
                String priceStr = data.substring(256, 320).replaceAll("^(0+)", "");
                String txfeeStr = data.substring(320, 384).replaceAll("^(0+)", "");
                BigDecimal price = new BigDecimal(new BigInteger(priceStr, 16)).divide(new BigDecimal(Math.pow(10, 18)), 6, RoundingMode.HALF_DOWN);
                BigDecimal txfee = new BigDecimal(new BigInteger(txfeeStr, 16)).divide(new BigDecimal(Math.pow(10, 18)), 6, RoundingMode.HALF_DOWN);
                price = price.add(txfee);

                data = data.substring(384);
                ArrayList<Integer> ids = new ArrayList<>();
                ArrayList<Integer> nums = new ArrayList<>();
                //解析物品id数组
                Integer ids_cnt = Integer.valueOf(data.substring(0, 64), 16);
                data = data.substring(64);
                for (int j = 0; j < ids_cnt; j++) {
                    Integer id = Integer.valueOf(data.substring(0, 64), 16);
                    ids.add(id);
                    data = data.substring(64);
                }

                //解析物品数量数组
                Integer nums_cnt = Integer.valueOf(data.substring(0, 64), 16);
                data = data.substring(64);
                for (int j = 0; j < nums_cnt; j++) {
                    Integer num = Integer.valueOf(data.substring(0, 64), 16);
                    nums.add(num);
                    data = data.substring(64);
                }

                String blockInfo = getBlockByHash(blockHash);
                Timestamp timestamp = null;
                if (!StringUtils.isEmpty(blockInfo)) {
                    Map blockMap = JSONObject.parseObject(blockInfo, Map.class);
                    JSONObject blockMapObject = (JSONObject) blockMap.get("result");
                    if (blockMapObject != null) {
                        String timestampStr = blockMapObject.getString("timestamp");
                        timestampStr = timestampStr.substring(2);
                        Long tradeTime = new BigInteger(timestampStr, 16).longValue();
                        timestamp = new Timestamp(tradeTime * 1000);
                    }
                }
                updateLastTradeInfo1155(from, to, price, timestamp, 0, ids, nums, txfee);
            }
        }
    }

    /**
     * 处理快捷售卖日志
     *
     * @param quickSoldLogs
     */
    private void handleQuickSoldLogs(String quickSoldLogs) {
        Map logMap = JSONObject.parseObject(quickSoldLogs, Map.class);
        JSONArray quickSoldResult = (JSONArray) logMap.get("result");
        if (quickSoldResult != null && quickSoldResult.size() > 0) {
            for (int i = 0; i < quickSoldResult.size(); i++) {
                JSONObject record = (JSONObject) quickSoldResult.get(i);
                String data = record.getString("data");
                String cardIdStr = data.substring(67, 130).replaceAll("^(0+)", "");
                BigInteger tokenId = new BigInteger(StringUtils.isEmpty(cardIdStr) ? "0" : cardIdStr, 16);
                String priceStr = data.substring(323).replaceAll("^(0+)", "");
                BigDecimal price = new BigDecimal(new BigInteger(priceStr, 16)).divide(new BigDecimal(Math.pow(10, 18)), 6, RoundingMode.HALF_DOWN);
                String fromAddress = "0x" + data.substring(131, 194).replaceAll("^(0+)", "");
                String toAddress = "0x" + data.substring(195, 258).replaceAll("^(0+)", "");
                String blockHash = record.getString("blockHash");
                String blockInfo = getBlockByHash(blockHash);
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                if (!StringUtils.isEmpty(blockInfo)) {
                    Map blockMap = JSONObject.parseObject(blockInfo, Map.class);
                    JSONObject blockMapObject = (JSONObject) blockMap.get("result");
                    if (blockMapObject != null) {
                        String timestampStr = blockMapObject.getString("timestamp");
                        timestampStr = timestampStr.substring(2);
                        Long tradeTime = new BigInteger(timestampStr, 16).longValue();
                        timestamp = new Timestamp(tradeTime * 1000);
                    }
                }
                String nftAddress = "0x" + data.substring(26, 66);
                updateLastTradeInfo(nftAddress, tokenId, fromAddress, toAddress, price, timestamp, 1, new BigDecimal(0));
            }
        }
    }

    /**
     * 更新最后成交价格与成交日志
     *
     * @param nftAddress
     * @param tokenId
     * @param fromAddress
     * @param toAddress
     * @param price
     * @param timestamp
     * @param type
     */
    void updateLastTradeInfo(String nftAddress, BigInteger tokenId, String fromAddress, String toAddress, BigDecimal price, Timestamp timestamp, int type, BigDecimal txfee) {
        System.out.println("nftAddress>>>>>>>>>>>>" + nftAddress);
        int nftType = 0;
        if (nftAddress.equalsIgnoreCase(BSCUtils.getFireworkCardAddress())) {
            nftType = NftTypeEnum.FHC.getCode();
            fhcMapper.updateNftLastTradePrice(tokenId, price, timestamp);
        } else if (nftAddress.equalsIgnoreCase(BSCUtils.getHSE_address())) {
            nftType = NftTypeEnum.HSE.getCode();
            hseMapper.updateNftLastTradePrice(tokenId, price, timestamp);
        } else if (nftAddress.equalsIgnoreCase(BSCUtils.getPSE_address())) {
            nftType = NftTypeEnum.PSE.getCode();
            pseMapper.updateNftLastTradePrice(tokenId, price, timestamp);
        } else if (nftAddress.equalsIgnoreCase(BSCUtils.getEGSE_address())) {
            nftType = NftTypeEnum.EGSE.getCode();
            egseMapper.updateNftLastTradePrice(tokenId, price, timestamp);
        } else if (nftAddress.equalsIgnoreCase(BSCUtils.getSHIP_address())) {
            nftType = NftTypeEnum.SHIP.getCode();
            shipMapper.updateNftLastTradePrice(tokenId, price, timestamp);
        } else if (nftAddress.equalsIgnoreCase(BSCUtils.getEGG_address())) {
            nftType = NftTypeEnum.EGG.getCode();
            eggMapper.updateNftLastTradePrice(tokenId, price, timestamp);
        } else if (nftAddress.equalsIgnoreCase(BSCUtils.getMBSE_address())) {
            nftType = NftTypeEnum.MBSE.getCode();
            mbseMapper.updateNftLastTradePrice(tokenId, price, timestamp);
        } else if (nftAddress.equalsIgnoreCase(BSCUtils.getASE_address())) {
            nftType = NftTypeEnum.ASE.getCode();
            aseMapper.updateNftLastTradePrice(tokenId, price, timestamp);
        } else if (nftAddress.equalsIgnoreCase(BSCUtils.getISE_address())) {
            log.info("ise updateLastTradeInfo tokenId:{} fromAddress:{}", tokenId.intValue(), fromAddress);
            nftType = NftTypeEnum.ISE.getCode();
            iseMapper.updateNftLastTradePrice(tokenId.intValue(), fromAddress, price, timestamp);

            NftIseInfoDto nftInfoByTokenIdMul = iseMapper.getNftInfoByTokenIdMul(tokenId.intValue(), fromAddress);
            NftTradeMulDto nftTradeMulDto = new NftTradeMulDto();
            nftTradeMulDto.setUnique_id(nftInfoByTokenIdMul.getTbId());
            nftTradeMulDto.setExt_id(BigInteger.valueOf(nftInfoByTokenIdMul.getId()));
            nftTradeMulDto.setOwner_address(fromAddress);
            nftTradeMulDto.setNft_type(nftType);
            nftTradeMulDto.setLast_trade_price(price);
            nftTradeMulDto.setLast_trade_time(timestamp);
            nftTradeMulDto.setImage(nftInfoByTokenIdMul.getImage());
            iseMapper.updateNftLastTradePriceMul(nftTradeMulDto);

            tokenId = nftInfoByTokenIdMul.getTbId();
        }
        commonMapper.insertActivityLog(tokenId, fromAddress, toAddress, price, timestamp, type, nftType);
        dealRebateInfo(toAddress, txfee,"");
    }

    void updateLastTradeInfo1155(String fromAddress, String toAddress,
                                 BigDecimal price, Timestamp timestamp, int type, ArrayList<Integer> ids, ArrayList<Integer> nums, BigDecimal txfee) {
        log.info("updateLastTradeInfo1155>>>>>>>>>>>> from:{} to:{}", fromAddress, toAddress);
        int nftType = 8;
        if (ids.size() <= 0 || nums.size() <= 0) {
            log.error("updateLastTradeInfo1155 value exception: ids:{} nums:{} 放弃更新", ids.toString(), nums.toString());
            return;
        }

        iseMapper.updateNftLastTradePrice(ids.get(0), fromAddress, price, timestamp);

        NftIseInfoDto nftInfoByTokenIdMul = iseMapper.getNftInfoByTokenIdMul(ids.get(0), fromAddress);
        NftTradeMulDto nftTradeMulDto = new NftTradeMulDto();
        nftTradeMulDto.setUnique_id(nftInfoByTokenIdMul.getTbId());
        nftTradeMulDto.setExt_id(BigInteger.valueOf(nftInfoByTokenIdMul.getId()));
        nftTradeMulDto.setCnt(nums.get(0));
        nftTradeMulDto.setOwner_address(fromAddress);
        nftTradeMulDto.setNft_type(nftType);
        nftTradeMulDto.setLast_trade_price(price);
        nftTradeMulDto.setLast_trade_time(timestamp);
        nftTradeMulDto.setImage(nftInfoByTokenIdMul.getImage());
        iseMapper.updateNftLastTradePriceMul(nftTradeMulDto);

        commonMapper.insertActivityLog(nftInfoByTokenIdMul.getTbId(), fromAddress, toAddress, price, timestamp, type, nftType);
        dealRebateInfo(toAddress, txfee, "");
    }
    /**
     * 处理FHC铸币日志
     *
     * @param mintLogs
     * @throws Exception
     */
    private void handleMintLogs(String mintLogs) throws Exception {
        Map logMap = JSONObject.parseObject(mintLogs, Map.class);
        JSONArray mintResult = (JSONArray) logMap.get("result");
        if (mintResult != null && mintResult.size() > 0) {
            for (int i = 0; i < mintResult.size(); i++) {
                JSONObject record = (JSONObject) mintResult.get(i);
                String data = record.getString("data");
                String ownerAddress = "0x" + data.substring(26, 66);
                String cardIdStr = data.substring(67, 130).replaceAll("^(0+)", "");
                String itemDataStr = data.substring(131).replaceAll("^(0+)", "");
                getNftCardItemData(ownerAddress, cardIdStr, itemDataStr);
            }
        }
    }

    /**
     * 处理FHC转账日志
     *
     * @param transferLogs
     */
    private void handleTransferLogs(String transferLogs) {
        Map transferMap = JSONObject.parseObject(transferLogs, Map.class);
        JSONArray transferResult = (JSONArray) transferMap.get("result");
        if (transferResult != null && transferResult.size() > 0) {
            for (int i = 0; i < transferResult.size(); i++) {
                JSONObject record = (JSONObject) transferResult.get(i);
                String toAddress = "0x" + record.getJSONArray("topics").get(2).toString().substring(26, 66);
                String cardIdStr = record.getJSONArray("topics").get(3).toString().toString().substring(26, 66).replaceAll("^(0+)", "");
                BigInteger tokenId = new BigInteger(StringUtils.isEmpty(cardIdStr) ? "0" : cardIdStr, 16);
                NftCardInfoDto nftCardInfoDto = fhcMapper.getNftInfoByTokenId(tokenId);
                if (nftCardInfoDto == null) {
                    log.info("没有查询到tokenId:{}", tokenId);
                    continue;
                }
                if (!nftCardInfoDto.getOwnerAddress().equalsIgnoreCase(toAddress)) {
                    log.info("tokenId>>>{},owner 发生变化，执行更新操作 toAddress>>>{}", tokenId, toAddress);
                    nftCardInfoDto.setOwnerAddress(toAddress);
                    fhcMapper.upInsetNftInfo(nftCardInfoDto);
                    fhcMapper.deleteSellingOrderByTokenIdAndAddress(tokenId, toAddress, NftTypeEnum.FHC.getCode());
                }
            }
        }
    }

    /**
     * FHC升级日志
     *
     * @param mutatedLogs
     * @throws Exception
     */
    private void handleMutatedLogs(String mutatedLogs) throws Exception {
        System.out.println(mutatedLogs);
        Map logMap = JSONObject.parseObject(mutatedLogs, Map.class);
        JSONArray mutatedResult = (JSONArray) logMap.get("result");
        if (mutatedResult != null && mutatedResult.size() > 0) {
            for (int i = 0; i < mutatedResult.size(); i++) {
                try {
                    JSONObject record = (JSONObject) mutatedResult.get(i);
                    String data = record.getString("data");
                    String ownerAddress = "0x" + data.substring(26, 66);
                    String cardIdStr = data.substring(67, 130).replaceAll("^(0+)", "");
                    String newItemDataStr = data.substring(195).replaceAll("^(0+)", "");
                    getNftCardItemData(ownerAddress, cardIdStr, newItemDataStr);
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
            }
        }
    }

    public static void main(String[] args) {
        BigInteger b = new BigInteger("0000000000000000000000000000000000000002d28231e19140f188700071a8", 16);
        System.out.println(b);
        //BigInteger.valueOf(2236055730045027497099636289680);
        Long ff = b.longValue();
        BigInteger a = new BigInteger("98a7d9b8314c0000", 16);
        System.out.println(a);

    }

    private void getNftCardItemData(String ownerAddress, String cardIdStr, String newItemDataStr) throws Exception {
        BigInteger itemDataBigInteger = new BigInteger(StringUtils.isEmpty(newItemDataStr) ? "0" : newItemDataStr, 16);
        Card card = CardUtil.fromBigIntVersion0(itemDataBigInteger);
        if (card != null) {
            NftCardInfoDto nftCardInfoDto = new NftCardInfoDto();
            BeanUtils.copyProperties(card, nftCardInfoDto);
            nftCardInfoDto.setItemData(itemDataBigInteger.toString());
            BigInteger tokenId = new BigInteger(StringUtils.isEmpty(cardIdStr) ? "0" : cardIdStr, 16);
            nftCardInfoDto.setTokenId(tokenId);
            nftCardInfoDto.setOwnerAddress(ownerAddress);
            nftCardInfoDto = getTokenURI(nftCardInfoDto);
            System.out.println(nftCardInfoDto.getLevel());
            nftCardInfoDto.setViews(0);
            nftCardInfoDto.setFavorites(0);
            nftCardInfoDto.setLastTradePrice(BigDecimal.ZERO);
            NftCardInfoDto oldNftCardInfoDto = fhcMapper.getNftInfoByTokenId(nftCardInfoDto.getTokenId());
            if (oldNftCardInfoDto != null) {
                nftCardInfoDto.setViews(oldNftCardInfoDto.getViews());
                nftCardInfoDto.setFavorites(oldNftCardInfoDto.getFavorites());
                nftCardInfoDto.setLastTradePrice(oldNftCardInfoDto.getLastTradePrice());
                nftCardInfoDto.setLastTradeTime(oldNftCardInfoDto.getLastTradeTime());
            }
            nftCardInfoDto.setTotalAttrs(nftCardInfoDto.getEndurance() + nftCardInfoDto.getStrength()
                    + nftCardInfoDto.getAgility() + nftCardInfoDto.getArmor() + nftCardInfoDto.getCrit()
                    + nftCardInfoDto.getPsionic() + nftCardInfoDto.getSpirit());
            fhcMapper.upInsetNftInfo(nftCardInfoDto);
        } else {
            throw new Exception("获取卡片属性失败");
        }
    }

    private NftCardInfoDto getTokenURI(NftCardInfoDto nftCardInfoDto) throws Exception {
        String result = BSCUtils.getTokenURI(BSCUtils.getFireworkCardAddress(), nftCardInfoDto.getTokenId());
        if (!StringUtils.isEmpty(result)) {
            nftCardInfoDto.setTokenURI(result);
            System.out.println(result);
            String res = HttpClientUtil.get(result);
            System.out.println(res);
            JSONObject obj = JSON.parseObject(res);
            nftCardInfoDto.setName(obj.getString("name"));
            nftCardInfoDto.setImage(obj.getString("image"));
            nftCardInfoDto.setDescription(obj.getString("description"));
            nftCardInfoDto.setDetailImage(obj.getString("detailimage"));
            JSONArray attrArray = obj.getJSONArray("attributes");
            for (int i = 0; i < attrArray.size(); i++) {
                JSONObject attr = (JSONObject) attrArray.get(i);
                switch (attr.getString("trait_type")) {
                    case "edition":
                        nftCardInfoDto.setAttrEdition(attr.getString("value"));
                        break;
                    case "camp":
                        nftCardInfoDto.setAttrCamp(attr.getString("value"));
                        break;
                    case "rank":
                        nftCardInfoDto.setAttrRank(attr.getString("value"));
                        break;
                    case "material":
                        nftCardInfoDto.setAttrMaterial(attr.getString("value"));
                        break;
                    case "name":
                        nftCardInfoDto.setAttrName(attr.getString("value"));
                        break;
                    case "Power":
                        nftCardInfoDto.setPower(attr.getInteger("value"));
                        break;
                    default:
                        break;
                }
            }
            return nftCardInfoDto;
        } else {
            throw new Exception("获取卡片URI异常");
        }
    }

    private NftEggInfoDto getEggTokenURI(NftEggInfoDto nftEggInfoDto) throws Exception {
        String result = BSCUtils.getTokenURI(BSCUtils.getEGG_address(), nftEggInfoDto.getTokenId());
        if (!StringUtils.isEmpty(result)) {
            nftEggInfoDto.setTokenURI(result);
            System.out.println(result);
            String res = HttpClientUtil.get(result);
            System.out.println(res);
            JSONObject obj = JSON.parseObject(res);
            System.out.println("ship get obj:" + obj.toString());
            System.out.println("ship name:" + obj.getString("name"));
            nftEggInfoDto.setName(obj.getString("name"));
            nftEggInfoDto.setImage(obj.getString("image"));
            nftEggInfoDto.setDetailImage(obj.getString("detailimage"));
            nftEggInfoDto.setDescription(obj.getString("description"));

            JSONArray attrArray = obj.getJSONArray("attributes");

            NftEggItemInfo tools = new NftEggItemInfo();
            for (int i = 0; i < attrArray.size(); i++) {
                JSONObject attr = (JSONObject) attrArray.get(i);
                switch (attr.getString("trait_type")) {
                    case "type":
                        nftEggInfoDto.setType(attr.getString("value"));
                        break;
                    default:
                        break;
                }
            }
            return nftEggInfoDto;
        } else {
            throw new Exception("获取卡片URI异常");
        }
    }

    private NftMbseInfoDto getMbseTokenURI(NftMbseInfoDto nftMbseInfoDto) throws Exception {
        String result = BSCUtils.getTokenURI(BSCUtils.getMBSE_address(), nftMbseInfoDto.getTokenId());
        if (!StringUtils.isEmpty(result)) {
            nftMbseInfoDto.setTokenURI(result);
            System.out.println(result);
            String res = HttpClientUtil.get(result);
            System.out.println(res);
            JSONObject obj = JSON.parseObject(res);
            System.out.println("mbse get obj:" + obj.toString());
            System.out.println("mbse name:" + obj.getString("name"));
            nftMbseInfoDto.setName(obj.getString("name"));
            nftMbseInfoDto.setImage(obj.getString("image"));
            nftMbseInfoDto.setDetailImage(obj.getString("detailimage"));
            nftMbseInfoDto.setDescription(obj.getString("description"));

            JSONArray attrArray = obj.getJSONArray("attributes");
            for (int i = 0; i < attrArray.size(); i++) {
                JSONObject attr = (JSONObject) attrArray.get(i);
                switch (attr.getString("trait_type")) {
                    case "type":
                        nftMbseInfoDto.setType(attr.getString("value"));
                        break;
                    default:
                        break;
                }
            }
            return nftMbseInfoDto;
        } else {
            throw new Exception("获取卡片URI异常");
        }
    }

    private NftIseInfoDto getIseItemURI(NftIseInfoDto nftIseInfoDto) throws Exception {
        String result = BSCUtils.getGameItemURI(nftIseInfoDto.getId());
        if (!StringUtils.isEmpty(result)) {
            nftIseInfoDto.setTokenURI(result);
            System.out.println(result);
            String res = HttpClientUtil.get(result);
            System.out.println(res);
            JSONObject obj = JSON.parseObject(res);
            System.out.println("ise get obj:" + obj.toString());
            System.out.println("ise name:" + obj.getString("name"));
            nftIseInfoDto.setName(obj.getString("name"));
            nftIseInfoDto.setImage(obj.getString("image"));
            nftIseInfoDto.setDetailImage(obj.getString("detailimage"));
            nftIseInfoDto.setDescription(obj.getString("description"));

            JSONArray attrArray = obj.getJSONArray("attributes");
            for (int i = 0; i < attrArray.size(); i++) {
                JSONObject attr = (JSONObject) attrArray.get(i);
                switch (attr.getString("trait_type")) {
                    case "data":
                        nftIseInfoDto.setData(attr.getInteger("value"));
                        break;
                    case "rank":
                        nftIseInfoDto.setRank(attr.getString("value"));
                        break;
                    default:
                        break;
                }
            }
            return nftIseInfoDto;
        } else {
            throw new Exception("获取卡片URI异常");
        }
    }

    private NftAseInfoDto getAseTokenURI(NftAseInfoDto nftAseInfoDto) throws Exception {
        String result = BSCUtils.getTokenURI(BSCUtils.getASE_address(), nftAseInfoDto.getTokenId());
        if (!StringUtils.isEmpty(result)) {
            nftAseInfoDto.setTokenURI(result);
            System.out.println(result);
            String res = HttpClientUtil.get(result);
            System.out.println(res);
            JSONObject obj = JSON.parseObject(res);
            System.out.println("ship get obj:" + obj.toString());
            System.out.println("ship name:" + obj.getString("name"));
            nftAseInfoDto.setName(obj.getString("name"));
            nftAseInfoDto.setImage(obj.getString("image"));
            nftAseInfoDto.setDetailImage(obj.getString("detailimage"));
            nftAseInfoDto.setDescription(obj.getString("description"));

            JSONArray attrArray = obj.getJSONArray("attributes");

            NftEggItemInfo tools = new NftEggItemInfo();
            for (int i = 0; i < attrArray.size(); i++) {
                JSONObject attr = (JSONObject) attrArray.get(i);
                switch (attr.getString("trait_type")) {
                    case "Data":
                        nftAseInfoDto.setData(attr.getInteger("value"));
                        break;
                    case "Arms Name":
                        nftAseInfoDto.setArmsName(attr.getString("value"));
                        break;
                    case "Rank":
                        nftAseInfoDto.setRank(attr.getString("value"));
                        break;
                    case "Power":
                        nftAseInfoDto.setPower(attr.getInteger("value"));
                        break;
                    case "Endurance":
                        nftAseInfoDto.setEndurance(attr.getInteger("value"));
                        break;
                    case "Strength":
                        nftAseInfoDto.setStrength(attr.getInteger("value"));
                        break;
                    case "Agility":
                        nftAseInfoDto.setAgility(attr.getInteger("value"));
                        break;
                    case "Armor":
                        nftAseInfoDto.setArmor(attr.getInteger("value"));
                        break;
                    case "Crit":
                        nftAseInfoDto.setCrit(attr.getInteger("value"));
                        break;
                    case "Pisonic":
                        nftAseInfoDto.setPisonic(attr.getInteger("value"));
                        break;
                    case "Spirit":
                        nftAseInfoDto.setSpirit(attr.getInteger("value"));
                        break;
                    default:
                        break;
                }
            }
            return nftAseInfoDto;
        } else {
            throw new Exception("获取卡片URI异常");
        }
    }

    public NftShipInfoDto getShipTokenURI(NftShipInfoDto nftShipInfoDto) throws Exception {
        String result = BSCUtils.getTokenURI(BSCUtils.getSHIP_address(), nftShipInfoDto.getTokenId());
        if (!StringUtils.isEmpty(result)) {
            nftShipInfoDto.setTokenURI(result);
            System.out.println(result);
            String res = HttpClientUtil.get(result);
            System.out.println(res);
            JSONObject obj = JSON.parseObject(res);
            System.out.println("ship get obj:" + obj.toString());
            System.out.println("ship name:" + obj.getString("name"));
            nftShipInfoDto.setName(obj.getString("name"));
            nftShipInfoDto.setImage(obj.getString("image"));
            nftShipInfoDto.setModel(obj.getString("model"));
            nftShipInfoDto.setDescription(obj.getString("description"));
            JSONArray attrArray = obj.getJSONArray("attributes");

            NftShipItemInfo tools = new NftShipItemInfo();
            for (int i = 0; i < attrArray.size(); i++) {
                JSONObject attr = (JSONObject) attrArray.get(i);
                switch (attr.getString("trait_type")) {
                    case "Shipname":
                        nftShipInfoDto.setShipName(attr.getString("value"));
                        break;
                    case "Level":
                        nftShipInfoDto.setLevel(attr.getInteger("value"));
                        break;
                    case "Rank":
                        nftShipInfoDto.setRank(attr.getString("value"));
                        break;
                    case "Power":
                        nftShipInfoDto.setPower(attr.getInteger("value"));
                        break;
                    case "BasicLimit":
                        nftShipInfoDto.setBasicLimit(attr.getInteger("value"));
                        break;
                    case "BasicCurrent":
                        nftShipInfoDto.setBasicCurrent(attr.getInteger("value"));
                        break;
                    case "LeaderLimit":
                        nftShipInfoDto.setLeaderLimit(attr.getInteger("value"));
                        break;
                    case "LeaderCurrent":
                        nftShipInfoDto.setLeaderCurrent(attr.getInteger("value"));
                        break;
                    case "ScientistLimit":
                        nftShipInfoDto.setScientistLimit(attr.getInteger("value"));
                        break;
                    case "ScientistCurrent":
                        nftShipInfoDto.setScientistCurrent(attr.getInteger("value"));
                        break;
                    case "CommanderLimit":
                        nftShipInfoDto.setCommanderLimit(attr.getInteger("value"));
                        break;
                    case "CommanderCurrent":
                        nftShipInfoDto.setCommanderCurrent(attr.getInteger("value"));
                        break;
                    case "StartSoldierLimit":
                        nftShipInfoDto.setStartSoldierLimit(attr.getInteger("value"));
                        break;
                    case "StartSoldierCurrent":
                        nftShipInfoDto.setStartSoldierCurrent(attr.getInteger("value"));
                        break;
                    case "CommonSoldierLimit":
                        nftShipInfoDto.setCommonSoldierLimit(attr.getInteger("value"));
                        break;
                    case "CommonSoldierCurrent":
                        nftShipInfoDto.setCommonSoldierCurrent(attr.getInteger("value"));
                        break;
                    default:
                        break;
                }
            }
            return nftShipInfoDto;
        } else {
            throw new Exception("获取卡片URI异常");
        }
    }

    private void getNftShipItemData(String ownerAddress, String cardIdStr, String newItemDataStr) throws Exception {
        BigInteger itemDataBigInteger = new BigInteger(StringUtils.isEmpty(newItemDataStr) ? "0" : newItemDataStr, 16);
        Ship card = ShipUtil.fromBigIntVersion0(itemDataBigInteger);
        if (card != null) {
            NftShipInfoDto nftShipInfoDto = new NftShipInfoDto();
            BeanUtils.copyProperties(card, nftShipInfoDto);
            nftShipInfoDto.setItemData(itemDataBigInteger.toString());
            BigInteger tokenId = new BigInteger(StringUtils.isEmpty(cardIdStr) ? "0" : cardIdStr, 16);
            nftShipInfoDto.setTokenId(tokenId);
            nftShipInfoDto.setOwnerAddress(ownerAddress);
            nftShipInfoDto = getShipTokenURI(nftShipInfoDto);
            System.out.println(nftShipInfoDto.getLevel());
            nftShipInfoDto.setViews(0);
            nftShipInfoDto.setFavorites(0);
            nftShipInfoDto.setLastTradePrice(BigDecimal.ZERO);
            NftShipInfoDto oldNftCardInfoDto = shipMapper.getNftInfoByTokenId(nftShipInfoDto.getTokenId());
            if (oldNftCardInfoDto != null) {
                nftShipInfoDto.setViews(oldNftCardInfoDto.getViews());
                nftShipInfoDto.setFavorites(oldNftCardInfoDto.getFavorites());
                nftShipInfoDto.setLastTradePrice(oldNftCardInfoDto.getLastTradePrice());
                nftShipInfoDto.setLastTradeTime(oldNftCardInfoDto.getLastTradeTime());
            }
            log.info("getNftShipItemData ownerAddress:{} 获得资产", ownerAddress);
            shipMapper.upInsetNftInfo(nftShipInfoDto);
        } else {
            throw new Exception("获取卡片属性失败");
        }
    }

    private void getNftEggItemData(String ownerAddress, String cardIdStr, String newItemDataStr) throws Exception {
        BigInteger itemDataBigInteger = new BigInteger(StringUtils.isEmpty(newItemDataStr) ? "0" : newItemDataStr, 16);
        Egg card = EggUtil.fromBigIntVersion0(itemDataBigInteger);
        if (card != null) {
            NftEggInfoDto nftEggInfoDto = new NftEggInfoDto();
            BeanUtils.copyProperties(card, nftEggInfoDto);
            nftEggInfoDto.setItemData(itemDataBigInteger.toString());
            BigInteger tokenId = new BigInteger(StringUtils.isEmpty(cardIdStr) ? "0" : cardIdStr, 16);
            nftEggInfoDto.setTokenId(tokenId);
            nftEggInfoDto.setOwnerAddress(ownerAddress);
            nftEggInfoDto = getEggTokenURI(nftEggInfoDto);
            nftEggInfoDto.setViews(0);
            nftEggInfoDto.setFavorites(0);
            nftEggInfoDto.setLastTradePrice(BigDecimal.ZERO);
            NftEggInfoDto oldNftCardInfoDto = eggMapper.getNftInfoByTokenId(nftEggInfoDto.getTokenId());
            if (oldNftCardInfoDto != null) {
                nftEggInfoDto.setViews(oldNftCardInfoDto.getViews());
                nftEggInfoDto.setFavorites(oldNftCardInfoDto.getFavorites());
                nftEggInfoDto.setLastTradePrice(oldNftCardInfoDto.getLastTradePrice());
                nftEggInfoDto.setLastTradeTime(oldNftCardInfoDto.getLastTradeTime());
            }
            log.info("getNftEggItemData ownerAddress:{} 获得资产", ownerAddress);
            eggMapper.upInsetNftInfo(nftEggInfoDto);
        } else {
            throw new Exception("获取卡片属性失败");
        }
    }

    private void dealRebateInfo(String ownerAddress, BigDecimal cost, String tradeErc20Address) {
        UserMailDto userInfo = fcMapper.getUserInfoByOwnerAddr(ownerAddress);
        if (userInfo == null) {
            log.warn("other service error ownerAddr:{} 未找到 cost:{}", ownerAddress, cost);
            return;
        }

        BigInteger refferId = userInfo.getRefferId();
        if (refferId == null) {
            return;
        }

        if (tradeErc20Address.equals("")) {
            tradeErc20Address = commonMapper.getSysConfigByKey(SysConfigConstant.TRADE_ERC20_ADDRESS);
        }

        RebateDto rebateInfo = inviteMapper.getRebateInfo(refferId, tradeErc20Address);
        if (rebateInfo == null) {
            rebateInfo = new RebateDto();
            rebateInfo.setUserId(refferId);
            rebateInfo.setErc20Addr(tradeErc20Address);
            rebateInfo.setAmount(new BigDecimal(0));
            rebateInfo.setUseAmount(new BigDecimal(0));
        }

        BigDecimal rebate = rebateConfig.getRebate(RebateConfig.toErc20Type(tradeErc20Address), cost);
        rebateInfo.setAmount(rebateInfo.getAmount().add(rebate));
        inviteMapper.upRebateInfo(rebateInfo);
        log.info("ownerAddr:{} cost:{} => 用户Id:{}获得返佣，请及时领取", ownerAddress, cost, refferId);
    }
}
