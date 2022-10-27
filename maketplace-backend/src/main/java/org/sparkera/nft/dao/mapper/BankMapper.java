package org.sparkera.nft.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.sparkera.nft.dao.dto.BankDto;
import org.sparkera.nft.dao.dto.NftBankDto;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface BankMapper {
    /*银行转账*/
    @Select("select hash_id, user_id, nft_type, flag, create_date from ts_nft_bank where hash_id >= #{hashId} for update")
    List<NftBankDto> getNftBankNotUseList(@Param("hashId") BigInteger hashId);

    @Update("replace into ts_nft_bank (hash_id, user_id, nft_type, flag) values (#{dto.hashId}, #{dto.userId}, #{dto.nftType}," +
            "#{dto.flag})")
    int updateNftBankInfo(@Param("dto") NftBankDto bankDto);
}
