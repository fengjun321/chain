package org.sparkera.nft.controller;

import org.sparkera.nft.utils.BSCUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/test")
public class Test {
    @Autowired
    private RestTemplate restTemplate;
    @RequestMapping(value = "/test/{id}", method = RequestMethod.GET)
    public String getID(@PathVariable("id") Integer id) {
        System.out.println(BSCUtils.getBlockNumber());
        BSCUtils.getAllitemDataByAddress("0x53c5d0177b91101d21e4cbfc53ceB83b32A69DdD");
        test();
        return "Id:"+id;
    }
    void test(){
        String jsonData = "{\"jsonrpc\":\"2.0\",\"method\":\"eth_getLogs\",\"params\":[{\"address\":\"0x02992D9D50B2545e75C2584e6F6DEB6d1a95DFF4\",\"fromBlock\": \"0xd26280\",\"toBlock\":\"0xd26280\",\"topics\":[\"0xddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef\"]}],\"id\":1}";
        HttpHeaders headers = new HttpHeaders();
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> request = new HttpEntity<>(jsonData, headers);
        String url = "https://apis.ankr.com/3d360d6c78c74d7f8ce2248b0a93315e/f2df52af64ea64349a5387f27494e0b8/binance/full/test";
        ResponseEntity<String> postForEntity = restTemplate.postForEntity(url, request, String.class);
        String body = postForEntity.getBody();
        System.out.println(body);



    }
}
