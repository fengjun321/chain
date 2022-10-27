package org.sparkera.nft.common;

import lombok.Data;
import org.web3j.utils.Numeric;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class GameConfig {
    public static Map<String, Integer> map = new HashMap<>();
    static {
        map.put("Eternal", 201001001);
        map.put("Photon", 201001002);
        map.put("Marauders", 201001003);
        map.put("Aurora", 201001004);
        map.put("Galaxy", 201002001);
        map.put("Enlightenment_Limited", 201002002);
        map.put("Daylight", 201002003);
        map.put("Enlightenment", 201002004);
        map.put("Trailblazer", 201003001);
        map.put("Star", 201003002);
        map.put("Megalith", 201003003);
        map.put("Gemini-Elite", 201003004);
        map.put("Colossus", 201004001);
        map.put("Gemini-Warrior", 201004002);
        map.put("Neptune", 201004003);
        map.put("Gemini-Light", 201004004);
        map.put("Type-Mercenary", 201004005);
        map.put("Type-Silverstar", 201004006);
        map.put("Type-Orion", 201004007);
    }

    //tobyte
    public static byte[] toNftByte(String hashId, String ownerAddress, String erc721Address, Integer fromGame, List<BigInteger> listErc721) {
        BigInteger bigInteger = new BigInteger(hashId);
        String hashId_16 = String.format("%x", bigInteger);
        byte[] uniqueStrBytes = Numeric.hexStringToByteArray(hashId_16);

        long curTime = System.currentTimeMillis() / 1000 + 60;
        String curTime_16 = String.format("%x", curTime);
        System.out.println("cuo:" + System.currentTimeMillis());
        System.out.println("rebate time limit hashId:" + hashId + ", " + curTime_16);
        byte[] curTimeBytes = Numeric.hexStringToByteArray(curTime_16);

        byte[] ownerAddrBytes = Numeric.hexStringToByteArray(ownerAddress.substring(2));

        String fromGame_16 = String.format("%x", fromGame);
        byte[] fromGameBytes = Numeric.hexStringToByteArray(fromGame_16);

        byte[] erc721AddrBytes = Numeric.hexStringToByteArray(erc721Address.substring(2));

        int size1 = listErc721.size();
        Integer erc721Cnt = new Integer(size1);
        String erc721nt_16 = String.format("%x", erc721Cnt);
        byte[] erc721bytes = Numeric.hexStringToByteArray(erc721nt_16);

        int i = 1;
        int nUintSize = 32;

        ByteBuffer wrap = ByteBuffer.wrap(new byte[(6 + size1) * 32]);
        wrap.position(nUintSize * i - uniqueStrBytes.length);
        wrap.put(uniqueStrBytes, 0, uniqueStrBytes.length);
        i += 1;

        wrap.position(nUintSize * i - curTimeBytes.length);
        wrap.put(curTimeBytes, 0, curTimeBytes.length);
        i += 1;

        wrap.position(nUintSize * i - ownerAddrBytes.length);
        wrap.put(ownerAddrBytes, 0, ownerAddrBytes.length);
        i += 1;

        wrap.position(nUintSize * i - fromGameBytes.length);
        wrap.put(fromGameBytes, 0, fromGameBytes.length);
        i += 1;

        wrap.position(nUintSize * i - erc721AddrBytes.length);
        wrap.put(erc721AddrBytes, 0, erc721AddrBytes.length);
        i += 1;

        wrap.position(nUintSize * i - erc721bytes.length);
        wrap.put(erc721bytes, 0, erc721bytes.length);
        i += 1;

        for (int j = 0; j < listErc721.size(); j++) {
            BigInteger tokenId = listErc721.get(j);
            byte[] bytes = Numeric.hexStringToByteArray(tokenId.toString(16));
            wrap.position(nUintSize * i - bytes.length);
            wrap.put(bytes, 0, bytes.length);
            i += 1;
        }

        return wrap.array();
    }
}
