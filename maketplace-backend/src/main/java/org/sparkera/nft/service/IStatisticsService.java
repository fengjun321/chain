package org.sparkera.nft.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.sparkera.nft.dao.dto.NftStatisticsDto;
import org.sparkera.nft.dao.vo.ActivityNftVo;
import org.sparkera.nft.dao.vo.TopNftVO;
import org.sparkera.nft.utils.pageinfoutil.PageVo;

import java.util.Map;

public interface IStatisticsService {
    Page<TopNftVO> topNft(NftStatisticsDto dto, PageVo pageVo);

    Page<ActivityNftVo> activityNft(NftStatisticsDto dto, PageVo pageVo);
}
