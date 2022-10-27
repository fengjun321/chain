package org.sparkera.nft.common;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.sparkera.nft.utils.BSCUtils;
import org.sparkera.nft.utils.ResultUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class SsoLogin {
    public static Map GetLoginInfo(String authToken) throws Exception {
        if (authToken == null || authToken.length() == 0) {
            throw new Exception("not valid token");
        }
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String url = BSCUtils.getSso_login_url();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("authToken", authToken);
        HttpEntity<String> returnData= restTemplate.exchange(url, HttpMethod.GET,new HttpEntity<>(headers),String.class);

        String body = returnData.getBody();
        Map mintMap = JSONObject.parseObject(body, Map.class);
        Integer code = (Integer) mintMap.get("code");
        if (code == 401) {
            throw new Exception("not valid token");
        }

        Map data = (Map)mintMap.get("data");
        return data;
    }
}
