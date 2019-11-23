package com.yyl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Description: TODO
 * @Author: yang.yonglian
 * @CreateDate: 2019/11/19 20:30
 * @Version: 1.0
 */
@RestController
@Slf4j
public class MyController {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 请求授权码时的回调地址，这里获取oauth2请求时的授权码，去获取token
     * @param code
     * @return
     */
    @RequestMapping("/redirect")
    public String getToken(String code){
        log.info("get authCode from oauth2 server :{} ",code);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params= new LinkedMultiValueMap<>();
        params.add("grant_type","authorization_code");
        params.add("code",code);
        params.add("client_id","client1");
        params.add("client_secret","client1_secret");
        params.add("redirect_uri","http://localhost:8085/redirect");
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        ResponseEntity<String> result = restTemplate.postForEntity("http://localhost:8086/oauth/token",requestEntity,String.class);
        log.info("get token:{}",result.getBody());
        return "success";
    }

    /**
     * 访问oauth2服务端受保护的资源
     * @param token
     * @return
     */
    @RequestMapping("/getProtectedResoure")
    public String getProtectedResoure(String token){
        HttpHeaders headers = new HttpHeaders();
        MultiValueMap<String, String> params= new LinkedMultiValueMap<>();
        params.add("access_token",token);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        ResponseEntity<String> result = restTemplate.postForEntity("http://localhost:8086/authmenu/getUsers",requestEntity,String.class);
        log.info("get token:{}",result.getBody());
        return "success";
    }

}
