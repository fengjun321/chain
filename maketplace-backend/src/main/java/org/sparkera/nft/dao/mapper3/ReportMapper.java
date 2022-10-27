package org.sparkera.nft.dao.mapper3;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.sparkera.nft.dao.dto2.IndustryScoreDto;

import java.util.List;
import java.util.Map;

@Mapper
public interface ReportMapper {
    @Select("<script> select * from ${tableName} </script>")
    List<Map<String, Object>> getTableData(@Param("tableName") String tableName);
}
