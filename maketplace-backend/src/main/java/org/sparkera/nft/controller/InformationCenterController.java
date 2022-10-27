package org.sparkera.nft.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.encoders.Hex;
import org.sparkera.nft.common.SignConfig;
import org.sparkera.nft.common.SsoLogin;
import org.sparkera.nft.config.RebateConfig;
import org.sparkera.nft.constant.PageConstant;
import org.sparkera.nft.dao.dto.*;
import org.sparkera.nft.dao.mapper.BankMapper;
import org.sparkera.nft.dao.mapper.InviteMapper;
import org.sparkera.nft.dao.mapper.ShipMapper;
import org.sparkera.nft.dao.mapper2.FcMapper;
import org.sparkera.nft.enums.NftTypeEnum;
import org.sparkera.nft.service.IInformationCenterService;
import org.sparkera.nft.utils.BSCUtils;
import org.sparkera.nft.utils.ResultUtil;
import org.sparkera.nft.utils.ResultVo;
import org.sparkera.nft.utils.pageinfoutil.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Sign;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

@RestController
@RequestMapping("/api/v1/informationCenter")
@ApiModel(value="Information Center",description="Information Center" )
@Slf4j
@Validated
public class InformationCenterController {
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
    SignConfig signConfig;

    @GetMapping("/myStorage")
    @ApiOperation(value = "Storage", notes = "get all NFT card by Owner address")
    public ResultVo myStorage(@ApiParam(value = "nft card attr"  ) @ModelAttribute NftCardQueryDto dto, PageVo pageVo){
        Page<Map<String,Object>> myStorageListPage = informationCenterService.myStorage(dto, pageVo);
        return new ResultUtil().setData(myStorageListPage);
    }
    @GetMapping("/selling")
    @ApiOperation(value = "selling", notes = "get User Selling NFT card")
    public ResultVo selling(@ApiParam(value = "nft card attr"  ) @ModelAttribute NftCardQueryDto dto, PageVo pageVo){
        Page<Map<String,Object>> myStorageListPage = informationCenterService.selling(dto, pageVo);
        return new ResultUtil().setData(myStorageListPage);
    }

    @GetMapping("/myFavorite")
    @ApiOperation(value = "myFavorite", notes = "get User favorite NFT card")
    public ResultVo myFavorite(@ApiParam(value = "nft card attr"  ) @ModelAttribute NftCardQueryDto dto, PageVo pageVo){
        Page<Map<String,Object>> myStorageListPage = informationCenterService.myFavorite(dto, pageVo);
        return new ResultUtil().setData(myStorageListPage);
    }
    @PostMapping("/favorite/add")
    @ApiOperation(value = "favorite/add", notes = "favorite token")
    public ResultVo addFavorite(@ApiParam(value = "tokenId 但在材料nftType=8时没有 tokenId，为兼容使用将材料goodId传入"  ) @RequestParam(value = "tokenId", required=true) BigInteger tokenId,@RequestParam(value = "address", required=true) String address,
                                @RequestParam(value = "nftType", required=true) Integer nftType){
        int rs = informationCenterService.addFavorite(tokenId, address,nftType);
        return new ResultUtil().setData(rs);
    }
    @PostMapping("/favorite/remove")
    @ApiOperation(value = "favorite/add", notes = "favorite token")
    public ResultVo removeFavorite(@ApiParam(value = "tokenId"  ) @RequestParam(value = "tokenId", required=true) BigInteger tokenId,@RequestParam(value = "address", required=true) String address,
                                   @RequestParam(value = "nftType", required=true) Integer nftType){
        int rs = informationCenterService.removeFavorite(tokenId, address,nftType);
        return new ResultUtil().setData(rs);
    }
    @GetMapping("/tokenDetailByTokenId")
    @ApiOperation(value = "tokenDetailByTokenId", notes = "get token Detail by token id")
    public ResultVo tokenDetail(@ApiParam(value = "tokenId 但在材料nftType=8时没有 tokenId，为兼容使用将材料id传入") @RequestParam(value = "tokenId", required=true) BigInteger tokenId,@RequestParam(value = "address", required=true) String address,
                                @RequestParam(value = "nftType", required=true) Integer nftType, @RequestParam(value = "ownerAddress", required=false) String ownerAddress, @RequestParam(value = "goodId", required=false) BigInteger goodId){
        Map<String,Object> tokenDetail = informationCenterService.tokenDetail(tokenId,address, ownerAddress, nftType, goodId);
        return new ResultUtil().setData(tokenDetail);
    }

    @PostMapping(value = "/sellToken",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "sellToken", notes = "Sell token ")
    public ResultVo sellToken(@Validated @ApiParam(value = "NftCardTradeDto")   @RequestBody NftCardTradeDto nftCardTradeDto, BindingResult bindingResult){
        if (bindingResult.hasFieldErrors()) {
            log.info("{}", bindingResult.getFieldError().getDefaultMessage());
            return new ResultUtil().setErrorMsg(bindingResult.getFieldError().getDefaultMessage());
        }

        if (nftCardTradeDto.getNftType() == NftTypeEnum.ISE.getCode()) {
            nftCardTradeDto.setTokenId(BigInteger.valueOf(nftCardTradeDto.getId()));
        }

        //check签名
        if(!BSCUtils.validateSignature(nftCardTradeDto.getSignature(),"tokenId="+nftCardTradeDto.getTokenId()+
                "&address="+nftCardTradeDto.getAddress(),nftCardTradeDto.getAddress())){
            return new ResultUtil().setErrorMsg("Signature verification failed");
        }
        int rs=0;
        try {
            rs  = informationCenterService.sellToken(nftCardTradeDto);
        }catch (Exception e){
            return new ResultUtil().setErrorMsg(e.getMessage());
        }

        return new ResultUtil().setData(rs);
    }
    @PostMapping("/cancelSell")
    @ApiOperation(value = "cancelSell", notes = "cancel Sell")
    public ResultVo cancelSell(@ApiParam(value = ""  ) @RequestBody NftCardCancelDto dto, PageVo pageVo){
        int rs = informationCenterService.cancelSell(dto);
        return new ResultUtil().setData(rs);
    }
    @PostMapping(value = "buyToken",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "buyToken", notes = "Sell token ")
    public ResultVo buyToken(@Validated @ApiParam(value = "NftCardTradeDto")   @RequestBody NftCardTradeDto nftCardTradeDto, BindingResult bindingResult){
        if (bindingResult.hasFieldErrors()) {
            log.info("{}", bindingResult.getFieldError().getDefaultMessage());
            return new ResultUtil().setErrorMsg(bindingResult.getFieldError().getDefaultMessage());
        }

        if (nftCardTradeDto.getNftType() == NftTypeEnum.ISE.getCode()) {
            nftCardTradeDto.setTokenId(BigInteger.valueOf(nftCardTradeDto.getId()));
        }

        //check签名
        if(!BSCUtils.validateSignature(nftCardTradeDto.getSignature(),"tokenId="+nftCardTradeDto.getTokenId()+"&address="+nftCardTradeDto.getAddress(),nftCardTradeDto.getAddress())){
            return new ResultUtil().setErrorMsg("Signature verification failed");
        }

        int rs=0;
        try {
            if (nftCardTradeDto.getNftType() == NftTypeEnum.ISE.getCode()) {
                rs = informationCenterService.buyTokenMul(nftCardTradeDto);
            } else {
                rs = informationCenterService.buyToken(nftCardTradeDto);
            }
        }catch (Exception e){
            return new ResultUtil().setErrorMsg(e.getMessage());
        }

        return new ResultUtil().setData(rs);
    }
    @PostMapping(value = "quickSale",produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "quickSale", notes = "quick Sell token ")
    public ResultVo quickSaleToken(@Validated @ApiParam(value = "NftCardTradeDto")   @RequestBody NftCardTradeDto nftCardTradeDto, BindingResult bindingResult){
        if (bindingResult.hasFieldErrors()) {
            log.info("{}", bindingResult.getFieldError().getDefaultMessage());
            return new ResultUtil().setErrorMsg(bindingResult.getFieldError().getDefaultMessage());
        }
        //check签名
        if(!BSCUtils.validateSignature(nftCardTradeDto.getSignature(),"tokenId="+nftCardTradeDto.getTokenId()+"&address="+nftCardTradeDto.getAddress(),nftCardTradeDto.getAddress())){
            return new ResultUtil().setErrorMsg("Signature verification failed");
        }
        int rs=0;
        try {
            rs  = informationCenterService.quickSaleToken(nftCardTradeDto);
        }catch (Exception e){
            return new ResultUtil().setErrorMsg(e.getMessage());
        }

        return new ResultUtil().setData(rs);
    }
    @GetMapping("/getQuickSalePriceByTokenId")
    @ApiOperation(value = "getQuickSalePriceByTokenId", notes = "get token Detail by token id")
    public ResultVo getQuickSalePriceByTokenId(@ApiParam(value = "token id") @RequestParam(value = "tokenId", required=true) BigInteger tokenId,
                                               @RequestParam(value = "nftType", required=true) Integer nftType){
        BigDecimal price = informationCenterService.getQuickSalePriceByTokenId(tokenId,nftType);
        return new ResultUtil().setData(price);
    }

    @GetMapping("/getInviteCode")
    @ApiOperation(value = "getInviteCode", notes = "get invite code by tokenId")
    public ResultVo GetInviteCode(HttpServletRequest request) {
        BigInteger userId = null;
        String authToken = request.getHeader("authToken");

        try {
            Map map = SsoLogin.GetLoginInfo(authToken);
            userId = new BigInteger(map.get("UserId").toString());
        }catch (Exception e){
            return new ResultUtil().setLoginErrorMsg(e.getMessage());
        }

        UserMailDto userInfo = fcMapper.getUserInfo(userId);
        if (userInfo == null) {
            return new ResultUtil().setErrorMsg("not valid userId");
        }

        String code = null;
        try {
            code  = informationCenterService.produceInviteCode(userId);
        }catch (Exception e){
            return new ResultUtil().setErrorMsg(e.getMessage());
        }
        return new ResultUtil().setData(code);
    }

    @GetMapping("/getInviteCodeInGame")
    @ApiOperation(value = "getInviteCodeInGame", notes = "get invite code by userId")
    public ResultVo GetInviteCodeInGame(@ApiParam(value = "userId") @RequestParam(value = "userId", required=true) BigInteger userId) {
        UserMailDto userInfo = fcMapper.getUserInfo(userId);
        if (userInfo == null) {
            return new ResultUtil().setErrorMsg("not valid userId");
        }

        String code = null;
        try {
            code  = informationCenterService.produceInviteCode(userId);
        }catch (Exception e){
            return new ResultUtil().setErrorMsg(e.getMessage());
        }
        return new ResultUtil().setData(code);
    }

    @GetMapping("/getRebate")
    @ApiOperation(value = "getRebate", notes = "get rebate by authToken")
    public ResultVo getRebate(@ApiParam(value = "erc20 addr") @RequestParam(value = "erc20Addr", required=false) String erc20Addr, HttpServletRequest request) {
        BigInteger userId = null;
        String authToken = request.getHeader("authToken");
        try {
            Map map = SsoLogin.GetLoginInfo(authToken);
            userId = new BigInteger(map.get("UserId").toString());
        }catch (Exception e){
            return new ResultUtil().setLoginErrorMsg(e.getMessage());
        }

        UserMailDto userInfo = fcMapper.getUserInfo(userId);
        String walletAddress = userInfo.getWalletAddress();
        if (walletAddress == null || walletAddress.equals("")) {
            return new ResultUtil().setErrorMsg("not bind wallet");
        }

        erc20Addr = erc20Addr.toLowerCase();
        List<Map<String, Object>> userRebate = inviteMapper.getUserRebate(userId, erc20Addr);
        if (userRebate.size() == 0) {
            return new ResultUtil().setErrorMsg("no rebate nft");
        }

        boolean isHasMoney = false;
        ArrayList<String> listErc20 = new ArrayList<>();
        ArrayList<BigDecimal> listCnt = new ArrayList<>();
        for (int i = 0; i < userRebate.size(); i++) {
            Map<String, Object> stringObjectMap = userRebate.get(i);
            String erc20_addr = (String)stringObjectMap.get("erc20_addr");
            BigDecimal amount = (BigDecimal)stringObjectMap.get("amount");
            listErc20.add(erc20_addr);
            listCnt.add(amount);

            if (amount.compareTo(new BigDecimal("0")) == 1) {
                isHasMoney = true;
            }
        }

        if (isHasMoney == false) {
            return new ResultUtil().setErrorMsg("there is no money can withdraw");
        }

        String hashId = rebateConfig.GetUniqueId();

        ArrayList<String> listErc20_input = new ArrayList<>();
        ArrayList<BigDecimal> listCnt_input = new ArrayList<>();
        //去除提现0金额地址
        for (int i = 0; i < listCnt.size(); i++) {
            if (listCnt.get(i).compareTo(new BigDecimal(0)) == 1) {
                listErc20_input.add(listErc20.get(i));
                listCnt_input.add(listCnt.get(i));
            }
        }

        byte[] bytes = rebateConfig.toByte(hashId, walletAddress, listErc20_input, listCnt_input);
        HashMap<String, Object> mapData = signConfig.SignRSV(bytes, userId);

        //冻结资产
        for (int i = 0; i < userRebate.size(); i++) {
            Map<String, Object> stringObjectMap = userRebate.get(i);
            String erc20_addr = (String)stringObjectMap.get("erc20_addr");
            BigDecimal amount = (BigDecimal)stringObjectMap.get("amount");
            frozenRebateAssets(new BigInteger(hashId), userId, erc20_addr, amount);
        }

        return new ResultUtil().setData(mapData);
    }

    @GetMapping("/showRebateInfo")
    @ApiOperation(value = "showRebateInfo", notes = "get user all rebate info")
    public ResultVo showRebateInfo(HttpServletRequest request) {
        BigInteger userId = null;
        String authToken = request.getHeader("authToken");
        try {
            Map map = SsoLogin.GetLoginInfo(authToken);
            userId = new BigInteger(map.get("UserId").toString());
        }catch (Exception e){
            return new ResultUtil().setLoginErrorMsg(e.getMessage());
        }

        List<Map<String, Object>> userRebate = inviteMapper.getUserRebate(userId, null);
        int userInviteCnt = fcMapper.getUserInviteCnt(userId);
        List<Map<String, Object>> inviteRankList = inviteMapper.getOldInviteRankList(BSCUtils.getAllErc20());

        HashMap<String, Object> mapData = new HashMap<>();
        mapData.put("invite_cnt", userInviteCnt);
        mapData.put("erc", userRebate);
        mapData.put("invite_rank", inviteRankList);

        return new ResultUtil().setData(mapData);
    }

    @GetMapping("/showInviteList")
    @ApiOperation(value = "showInviteList", notes = "get invite list")
    public ResultVo showInviteList(@ApiParam(value = "emial code") @RequestParam(value = "emailAddr", required=true) String emailAddr,
                                   PageVo pageVo) {
        Page<Map<String,Object>> page = new Page<Map<String,Object>>(pageVo.getPageNumber(), pageVo.getPageSize()==0?
                PageConstant.PAGE_SIZE:pageVo.getPageSize());
        List<Map<String, Object>> inviteRankList = inviteMapper.getInviteRankList(page, BSCUtils.getAllErc20());
        return new ResultUtil().setData(page.setRecords(inviteRankList));
    }

    @Transactional
    public void frozenRebateAssets(BigInteger hashId, BigInteger userId, String erc20Addr, BigDecimal amount) {
        RebateDto rebateInfo = inviteMapper.getRebateInfo(userId, erc20Addr);
        rebateInfo.setAmount(new BigDecimal("0"));
        inviteMapper.upRebateInfo(rebateInfo);

        BankDto bankDto = new BankDto();
        bankDto.setHashId(hashId);
        bankDto.setUserId(userId);
        bankDto.setErc20Addr(erc20Addr);
        bankDto.setAmount(amount);
        bankDto.setFlag(0);
        inviteMapper.updateBankInfo(bankDto);
    }

    public static void main(String[] args) {
        String message="tokenId=57&address=0x4e2a59c0e1a30722747e091b28d76af6fd687e16&price=100";
        String signature="0xbcb335dbdeab9fe947ddf4ae03311b654a420de3e535da03d47045c7fe55cbb56384620a00d7da518022d46863d2d72110a70049bd5b3fa9416692d220fad2411b";
        String address ="0x4e2a59c0e1a30722747e091b28d76af6fd687e16";
        System.out.println(BSCUtils.validateSignature(signature,message,address));

    }
}
