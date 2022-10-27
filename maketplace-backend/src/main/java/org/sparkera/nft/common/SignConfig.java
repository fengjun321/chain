package org.sparkera.nft.common;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.encoders.Hex;
import org.sparkera.nft.constant.FireWorkCardConstant;
import org.sparkera.nft.dao.mapper.CommonMapper;
import org.sparkera.nft.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Sign;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.util.HashMap;

@Slf4j
@Component
public class SignConfig {
    @Autowired
    private CommonMapper commonMapper;
    private String privateSignKey;

    @PostConstruct
    public void postSignConfig() {
        privateSignKey = commonMapper.getSysConfigByKey(FireWorkCardConstant.SIGN_PRIVATE_KEY);
    }

    public HashMap<String, Object> SignRSV(byte[] bytes, BigInteger userId) {
        //私钥
        byte[] privateKey= Hex.decode(privateSignKey);
        ECKeyPair ecKeyPair= ECKeyPair.create(privateKey);

        //签名,第三个参数是是否要hash,这里选要hash，因为链上要求的是256位的信息
        Sign.SignatureData signature = Sign.signPrefixedMessage(bytes, ecKeyPair);
        byte[] r=signature.getR();
        byte[] s= signature.getS();
        byte[] v = signature.getV();

        log.info("==============签名信息================ userAddr:{}", userId);
        System.out.println("r:"+Hex.toHexString(r));
        System.out.println("s:"+Hex.toHexString(s));
        System.out.println("v:"+Hex.toHexString(v));

        HashMap<String, Object> mapData = new HashMap<>();
        mapData.put("claim_content", Hex.toHexString(bytes));
        mapData.put("r", Hex.toHexString(r));
        mapData.put("s", Hex.toHexString(s));
        mapData.put("v", Hex.toHexString(v));

        return mapData;
    }
}
