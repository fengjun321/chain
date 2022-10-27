package org.sparkera.nft.controller;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.sparkera.nft.dao.dto.NftGameQueryDto;
import org.sparkera.nft.dao.dto2.IndustryScoreDto;
import org.sparkera.nft.dao.mapper3.ReportMapper;
import org.sparkera.nft.dao.mapper3.ResearchMapper;
import org.sparkera.nft.utils.ResultVo;
import org.sparkera.nft.utils.pageinfoutil.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/stock")
@ApiModel(value="Stock relate",description="Stock relate method" )
@Slf4j
@Validated
public class StockController {
    @Autowired
    ResearchMapper researchMapper;
    @Autowired
    ReportMapper reportMapper;

    @GetMapping("/ResearchIndustry")
    @ApiOperation(value = "ResearchIndustry", notes = "get industries")
    public ResultVo getResearchIndustry(@ApiParam(value = "date"  ) @RequestParam(value = "curDate" ,required = true) String curDate,
                                        @RequestParam(value = "delDate" ,required = true) Integer delDate, HttpServletRequest request) {
        List<IndustryScoreDto> industryResearchScore = researchMapper.getIndustryResearchScore(curDate, delDate);
        ResultVo resultVo = new ResultVo();
        resultVo.setData(industryResearchScore);
        return resultVo;
    }

    @GetMapping("/getTableData")
    @ApiOperation(value = "getTableData", notes = "get table data")
    public ResultVo getTableData(@ApiParam(value = "tableName"  ) @RequestParam(value = "tableName" ,required = true) String tableName,
                                       HttpServletRequest request) {
        List<Map<String, Object>> tableData = reportMapper.getTableData(tableName);
        ResultVo resultVo = new ResultVo();
        resultVo.setData(tableData);
        return resultVo;
    }
}