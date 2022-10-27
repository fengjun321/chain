package org.sparkera.nft.config;

import lombok.Data;
import org.sparkera.nft.constant.FireWorkCardConstant;
import org.sparkera.nft.dao.mapper.CommonMapper;
import org.sparkera.nft.utils.BSCUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.abi.datatypes.Array;
import org.web3j.utils.Numeric;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Data
public class RebateConfig {
    @Autowired
    private CommonMapper commonMapper;

    private List<Integer> listBnbCost = new ArrayList<>();//Arrays.asList(5, 50, 250, 500);
    private List<Integer> listBnbRebate = new ArrayList<>();//Arrays.asList(1,2,3,4);

    private List<Integer> listWBnbCost = new ArrayList<>();//Arrays.asList(5, 50, 250, 500);
    private List<Integer> listWBnbRebate = new ArrayList<>();//Arrays.asList(1,2,3,4);

    private List<Integer> listFireCost = new ArrayList<>();//Arrays.asList(10000, 100000, 500000, 1000000);
    private List<Integer> listFireRebate = new ArrayList<>();//Arrays.asList(1,2,3,4);

    private List<Integer> listQuarkCost = new ArrayList<>();//Arrays.asList(100000, 1000000, 5000000, 10000000);
    private List<Integer> listQuarkRebate = new ArrayList<>();//Arrays.asList(1,2,3,4);

    private List<Integer> listUsdtCost = new ArrayList<>();//Arrays.asList(5, 50, 250, 500);
    private List<Integer> listUsdtRebate = new ArrayList<>();//Arrays.asList(1,2,3,4);

    @PostConstruct
    void postRebateConstruct() {
        String bnbRebate = commonMapper.getSysConfigByKey(FireWorkCardConstant.BNB_REBATE);
        String[] bnbCostMap = bnbRebate.split(",");
        for (String m : bnbCostMap) {
            String[] split = m.split(":");
            listBnbCost.add(Integer.valueOf(split[0]));
            listBnbRebate.add(Integer.valueOf(split[1]));
        }

        String wbnbRebate = commonMapper.getSysConfigByKey(FireWorkCardConstant.WBNB_REBATE);
        String[] wbnbCostMap = wbnbRebate.split(",");
        for (String m : wbnbCostMap) {
            String[] split = m.split(":");
            listWBnbCost.add(Integer.valueOf(split[0]));
            listWBnbRebate.add(Integer.valueOf(split[1]));
        }

        String fireRebate = commonMapper.getSysConfigByKey(FireWorkCardConstant.FIRE_REBATE);
        String[] fireCostMap = fireRebate.split(",");
        for (String m : fireCostMap) {
            String[] split = m.split(":");
            listFireCost.add(Integer.valueOf(split[0]));
            listFireRebate.add(Integer.valueOf(split[1]));
        }

        String quarkRebate = commonMapper.getSysConfigByKey(FireWorkCardConstant.QUARK_REBATE);
        String[] quarkCostMap = quarkRebate.split(",");
        for (String m : quarkCostMap) {
            String[] split = m.split(":");
            listQuarkCost.add(Integer.valueOf(split[0]));
            listQuarkRebate.add(Integer.valueOf(split[1]));
        }

        String usdtRebate = commonMapper.getSysConfigByKey(FireWorkCardConstant.USDT_REBATE);
        String[] usdtCostMap = usdtRebate.split(",");
        for (String m : usdtCostMap) {
            String[] split = m.split(":");
            listUsdtCost.add(Integer.valueOf(split[0]));
            listUsdtRebate.add(Integer.valueOf(split[1]));
        }
    }

    public static String toErc20Type(String addr) {
        String ret = "";
        if (BSCUtils.getBnb_address().equalsIgnoreCase(addr)) {
            ret = "Bnb";
        } else if (BSCUtils.getWBnb_address().equalsIgnoreCase(addr)) {
            ret = "WBnb";
        } else if (BSCUtils.getQuark_address().equalsIgnoreCase(addr)) {
            ret = "Quark";
        } else if (BSCUtils.getFire_address().equalsIgnoreCase(addr)) {
            ret = "Fire";
        } else if (BSCUtils.getUsdt_address().equalsIgnoreCase(addr)) {
            ret = "Usdt";
        }
        return ret;
    }

    //获取返佣金额
    public BigDecimal getRebate(String type, BigDecimal cost) {
        List<Integer> listCost = null;
        List<Integer> listRebate = null;
        Integer eleRebate = -1;
        switch (type)
        {
            case "Bnb":
                listCost = listBnbCost;
                listRebate = listBnbRebate;
                eleRebate = 5;
                break;
            case "WBnb":
                listCost = listWBnbCost;
                listRebate = listWBnbRebate;
                eleRebate = 5;
                break;
            case "Fire":
                listCost = listFireCost;
                listRebate = listFireRebate;
                eleRebate = 5;
                break;
            case "Quark":
                listCost = listQuarkCost;
                listRebate = listQuarkRebate;
                eleRebate = 5;
                break;
            case "Usdt":
                listCost = listUsdtCost;
                listRebate = listUsdtRebate;
                eleRebate = 5;
            default:
                break;
        }

        if (listCost != null) {
            Integer targetRate = eleRebate;
            for (int i = 0; i < listCost.size(); i++) {
                if (cost.compareTo(new BigDecimal(listCost.get(i))) != 1){
                    targetRate = listRebate.get(i);
                    break;
                }
            }

            BigDecimal rate = new BigDecimal(targetRate).divide(new BigDecimal(100), 6, RoundingMode.HALF_DOWN);
            return cost.multiply(rate);
        }
        return new BigDecimal(0);
    }

    @Transactional
    public String GetUniqueId(){
        String unique_str = commonMapper.getSysConfigByKey(FireWorkCardConstant.UNIQUE_ID);
        BigInteger bigInteger = new BigInteger(unique_str);
        commonMapper.upDateSysConfigByKey(FireWorkCardConstant.UNIQUE_ID, String.valueOf(bigInteger.intValue()+1));
        return unique_str;
    }


    //tobyte
    public byte[] toByte(String hashId, String ownerAddress, ArrayList<String> listErc20, ArrayList<BigDecimal> listCnt) {
        BigInteger bigInteger = new BigInteger(hashId);
        String hashId_16 = String.format("%x", bigInteger);
        byte[] uniqueStrBytes = Numeric.hexStringToByteArray(hashId_16);

        long curTime = System.currentTimeMillis() / 1000 + 60;
        String curTime_16 = String.format("%x", curTime);
        System.out.println("cuo:" + System.currentTimeMillis());
        System.out.println("rebate time limit hashId:" + hashId + ", " + curTime_16);
        byte[] curTimeBytes = Numeric.hexStringToByteArray(curTime_16);

        byte[] ownerAddrBytes = Numeric.hexStringToByteArray(ownerAddress.substring(2));

        int size1 = listErc20.size();
        Integer erc20Cnt = new Integer(size1);
        String erc20Cnt_16 = String.format("%x", erc20Cnt);
        byte[] erc20bytes = Numeric.hexStringToByteArray(erc20Cnt_16);

        int size2 = listCnt.size();
        Integer iCnt = new Integer(size2);
        String iCnt_16 = String.format("%x", iCnt);
        byte[] cntBytes = Numeric.hexStringToByteArray(iCnt_16);

        int i = 1;
        int nUintSize = 32;

        ByteBuffer wrap = ByteBuffer.wrap(new byte[(3 + 1 + size1 + size2) * 32]);
        wrap.position(nUintSize * i - uniqueStrBytes.length);
        wrap.put(uniqueStrBytes, 0, uniqueStrBytes.length);
        i += 1;

        wrap.position(nUintSize * i - curTimeBytes.length);
        wrap.put(curTimeBytes, 0, curTimeBytes.length);
        i += 1;

        wrap.position(nUintSize * i - ownerAddrBytes.length);
        wrap.put(ownerAddrBytes, 0, ownerAddrBytes.length);
        i += 1;

        wrap.position(nUintSize * i - erc20bytes.length);
        wrap.put(erc20bytes, 0, erc20bytes.length);
        i += 1;

        for (int j = 0; j < listErc20.size(); j++) {
            String substring = listErc20.get(j);
            if (listErc20.get(j).length() >= 2) {
                substring = substring.substring(2); //去除0x
            }
            byte[] bytes = Numeric.hexStringToByteArray(substring);
            wrap.position(nUintSize * i - bytes.length);
            wrap.put(bytes, 0, bytes.length);
            i += 1;

            BigDecimal aMount = listCnt.get(j);
            BigDecimal multiply = aMount.multiply(new BigDecimal(Math.pow(10, 18)));
            byte[] bytes2 = Numeric.hexStringToByteArray(multiply.toBigInteger().toString(16));
            wrap.position(nUintSize * i - bytes2.length);
            wrap.put(bytes2, 0, bytes2.length);
            i++;
        }

        return wrap.array();
    }
}
