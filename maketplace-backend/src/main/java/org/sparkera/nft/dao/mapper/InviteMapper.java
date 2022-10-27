package org.sparkera.nft.dao.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.*;
import org.sparkera.nft.dao.dto.*;
import org.sparkera.nft.dao.entity.BaseEntityInfo;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@Mapper
public interface InviteMapper {
    @Select("select id, user_id, code, create_date from ts_invite where user_id = #{userId}")
    InviteDto getInviteInfo(@Param("userId") BigInteger userId);

    @Insert("insert into ts_invite (user_id, code) values (#{userId}, #{code}) ")
    int insertInviteInfo( @Param("userId")BigInteger userId, @Param("code")String code);

    @Select("select id, user_id, code, create_date from ts_invite where code = #{code}")
    InviteDto getInviteInfoByCode(@Param("code") String code);

    @Select("select user_id, erc20_addr, amount, use_amount from ts_rebate_info where user_id = #{userId} " +
            "and erc20_addr = #{erc20Addr}")
    RebateDto getRebateInfo(@Param("userId") BigInteger userId, @Param("erc20Addr") String erc20Addr);

    @Update("replace into ts_rebate_info (user_id, erc20_addr, amount, use_amount) values (#{dto.userId}," +
            "#{dto.erc20Addr}, #{dto.amount}, #{dto.useAmount})")
    int upRebateInfo(@Param("dto") RebateDto rebateDto);

    @Select("<script> select user_id, erc20_addr, amount, use_amount from ts_rebate_info where user_id = #{userId}" +
            "   <if test='erc20Addr != null ' > " +
            "       and erc20_addr = #{erc20Addr}" +
            "   </if>" +
            "</script>")
    List<Map<String, Object>> getUserRebate(@Param("userId") BigInteger userId, @Param("erc20Addr") String erc20Addr);

    @Select("select ts_invite_rank.UserId as userid, InvitationNum as invitees, IFNULL(b.amount, 0) + IFNULL(b.use_amount, 0) as Fire, " +
            "   IFNULL(c.amount, 0) + IFNULL(c.use_amount, 0) as Bnb, IFNULL(d.amount, 0) + IFNULL(d.use_amount, 0) as Quark," +
            "   IFNULL(e.amount, 0) + IFNULL(e.use_amount, 0) as Usdt from ts_invite_rank " +
            "left join ts_rebate_info b on b.user_id = ts_invite_rank.UserId and lower(b.erc20_addr) = #{dto.fire} " +
            "left join ts_rebate_info c on c.user_id = ts_invite_rank.UserId and lower(c.erc20_addr) = #{dto.bnb} " +
            "left join ts_rebate_info d on d.user_id = ts_invite_rank.UserId and lower(d.erc20_addr) = #{dto.quark} " +
            "left join ts_rebate_info e on e.user_id = ts_invite_rank.UserId and lower(e.erc20_addr) = #{dto.usdt} " +
            "order by invitees desc ")
    List<Map<String, Object>> getInviteRankList(Page<Map<String,Object>> page, @Param("dto") Erc20Dto dto);

    @Select("select ts_invite_rank.UserId as userid, InvitationNum as invitees, IFNULL(b.amount, 0) + IFNULL(b.use_amount, 0) as Fire, " +
            "   IFNULL(c.amount, 0) + IFNULL(c.use_amount, 0) as Bnb, IFNULL(d.amount, 0) + IFNULL(d.use_amount, 0) as Quark," +
            "   IFNULL(e.amount, 0) + IFNULL(e.use_amount, 0) as Usdt from ts_invite_rank " +
            "left join ts_rebate_info b on b.user_id = ts_invite_rank.UserId and lower(b.erc20_addr) = #{dto.fire} " +
            "left join ts_rebate_info c on c.user_id = ts_invite_rank.UserId and lower(c.erc20_addr) = #{dto.bnb} " +
            "left join ts_rebate_info d on d.user_id = ts_invite_rank.UserId and lower(d.erc20_addr) = #{dto.quark} " +
            "left join ts_rebate_info e on e.user_id = ts_invite_rank.UserId and lower(e.erc20_addr) = #{dto.usdt} " +
            "order by invitees desc limit 100")
    List<Map<String, Object>> getOldInviteRankList(@Param("dto") Erc20Dto dto);

    /*银行转账*/
    @Select("select hash_id, user_id, erc20_addr, amount, flag, create_date from ts_bank where hash_id >= #{hashId}")
    List<BankDto> getBankNotUseList(@Param("hashId") BigInteger hashId);

    @Update("replace into ts_bank (hash_id, user_id, erc20_addr, amount, flag) values (#{dto.hashId}, #{dto.userId}, #{dto.erc20Addr}," +
            "#{dto.amount}, #{dto.flag})")
    int updateBankInfo(@Param("dto") BankDto bankDto);
}
