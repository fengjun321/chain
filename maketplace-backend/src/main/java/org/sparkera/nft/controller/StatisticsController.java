package org.sparkera.nft.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.sparkera.nft.dao.dto.NftCardQueryDto;
import org.sparkera.nft.dao.dto.NftStatisticsDto;
import org.sparkera.nft.dao.vo.ActivityNftVo;
import org.sparkera.nft.dao.vo.TopNftVO;
import org.sparkera.nft.service.IStatisticsService;
import org.sparkera.nft.utils.ResultUtil;
import org.sparkera.nft.utils.ResultVo;
import org.sparkera.nft.utils.pageinfoutil.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/statistics")
@ApiModel(value="statistics",description="statistics api" )
public class StatisticsController {
    @Autowired
    IStatisticsService statisticsService;
    @GetMapping("/topNft")
    @ApiOperation(value = "topNft", notes = "topNft")
    public ResultVo topNft(@ApiParam(value = "nft card attr"  ) @ModelAttribute NftStatisticsDto dto, PageVo pageVo){
        Page<TopNftVO> myStorageListPage = statisticsService.topNft(dto, pageVo);
        return new ResultUtil().setData(myStorageListPage);
    }
    @GetMapping("/activity")
    @ApiOperation(value = "activity", notes = "activity")
    public ResultVo activity(@ApiParam(value = "activity"  ) @ModelAttribute NftStatisticsDto dto, PageVo pageVo){
        Page<ActivityNftVo> myStorageListPage = statisticsService.activityNft(dto, pageVo);
        return new ResultUtil().setData(myStorageListPage);
    }
}
