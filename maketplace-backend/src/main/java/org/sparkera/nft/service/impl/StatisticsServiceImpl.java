package org.sparkera.nft.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.sparkera.nft.constant.PageConstant;
import org.sparkera.nft.dao.dto.NftStatisticsDto;
import org.sparkera.nft.dao.mapper.StatisticsMapper;
import org.sparkera.nft.dao.vo.ActivityNftVo;
import org.sparkera.nft.dao.vo.TopNftVO;
import org.sparkera.nft.service.IStatisticsService;
import org.sparkera.nft.utils.pageinfoutil.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StatisticsServiceImpl implements IStatisticsService {
    @Autowired
    StatisticsMapper statisticsMapper;

    @Override
    public Page<TopNftVO> topNft(NftStatisticsDto dto, PageVo pageVo) {
        Page<TopNftVO> page = new Page<TopNftVO>(pageVo.getPageNumber(), pageVo.getPageSize()==0?PageConstant.PAGE_SIZE:pageVo.getPageSize());
        List<TopNftVO> nftCardItemInfoVoList= statisticsMapper.topNft(page,dto);
        return page.setRecords(nftCardItemInfoVoList);
    }

    @Override
    public Page<ActivityNftVo> activityNft(NftStatisticsDto dto, PageVo pageVo) {
        Page<ActivityNftVo> page = new Page<ActivityNftVo>(pageVo.getPageNumber(), pageVo.getPageSize()==0?PageConstant.PAGE_SIZE:pageVo.getPageSize());
        List<ActivityNftVo> nftCardItemInfoVoList= statisticsMapper.activityNft(page,dto);
        return page.setRecords(nftCardItemInfoVoList);
    }
}
