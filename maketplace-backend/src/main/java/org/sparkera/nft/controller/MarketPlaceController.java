package org.sparkera.nft.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.sparkera.nft.dao.dto.NftCardInfoDto;
import org.sparkera.nft.dao.dto.NftCardQueryDto;
import org.sparkera.nft.service.IMarketPlaceService;
import org.sparkera.nft.utils.ResultUtil;
import org.sparkera.nft.utils.ResultVo;
import org.sparkera.nft.utils.pageinfoutil.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/marketplace")
@ApiModel(value="marketplace",description="marketplace api" )
public class MarketPlaceController {
    @Autowired
    IMarketPlaceService marketPlaceService;
    @GetMapping("/tradingMarket")
    @ApiOperation(value = "tradingMarket", notes = "trading market")
    public ResultVo tradingMarket(@ApiParam(value = "nft card attr"  ) @ModelAttribute NftCardQueryDto dto, PageVo pageVo){
        Page<Map<String,Object>> myStorageListPage = marketPlaceService.tradingMarket(dto, pageVo);
        return new ResultUtil().setData(myStorageListPage);
    }
    @PostMapping("/token/views/add")
    @ApiOperation(value = "views", notes = "add nft views")
    public ResultVo addViewsByTokenId(@ApiParam(value = "add Nft views"  ) @RequestParam(value = "tokenId" ,required = true) Integer tokenId,@RequestParam(value = "nftType" ,required = true) Integer nftType,
                                      @RequestParam(value = "ownerAddress" ,required = false) String ownerAddress){
        int rs = marketPlaceService.addViewsByTokenId(tokenId,nftType,ownerAddress);
        return new ResultUtil().setData(rs);
    }

    @GetMapping("/tradeFee")
    @ApiOperation(value = "tradeFee", notes = "tradeFee")
    public ResultVo tradeFee(){
        String tradeFee = marketPlaceService.tradeFee();
        return new ResultUtil().setData(tradeFee);
    }
}
