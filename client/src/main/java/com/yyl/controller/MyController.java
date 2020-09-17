package com.yyl.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.auth0.jwt.interfaces.Claim;
import com.github.scribejava.apis.GitHubApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.yyl.bean.GithubUser;
import com.yyl.bean.MyUser;
import com.yyl.bean.SysUser;
import com.yyl.dao.UserMapper2;
import com.yyl.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * @Description: 第三方登录和token获取
 * @Author: yang.yonglian
 * @CreateDate: 2019/11/19 20:30
 * @Version: 1.0
 */
@Controller
@Slf4j
public class MyController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    protected AuthenticationManager authenticationManager;
    @Autowired
    private UserMapper2 userMapper;
    @Value("${github.appId}")
    private String appId;
    @Value("${github.appSecret}")
    private String appSecret;
    @Value("${github.callbackUrl}")
    private String callbackUrl;
    private static final String PROTECTED_RESOURCE_URL = "https://api.github.com/user";
    /**
     * 请求授权码时的回调地址，这里获取oauth2请求时的授权码，去获取token
     * @param code
     * @return
     */
    @RequestMapping("/redirect")
    @ResponseBody
    public String getToken(String code){
        log.info("get authCode from oauth2 server :{} ",code);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params= new LinkedMultiValueMap<>();
        params.add("grant_type","authorization_code");
        params.add("code",code);
        params.add("client_id","client1");
        params.add("client_secret","client1_secret");
        params.add("redirect_uri","http://127.0.0.1:8085/redirect");
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        ResponseEntity<String> result = restTemplate.postForEntity("http://127.0.0.1:8086/oauth/token",requestEntity,String.class);
        log.info("get token:{}",result.getBody());
        return "success";
    }

    /**
     * 从oauth2-server服务端登录，这是请求oauth2服务端授权码时，oauth2-server端回调的地址
     * @param code
     * @return
     * @throws Exception
     */
    @RequestMapping("/redirectAndLogin")
    public String redirectAndLogin(String code) throws Exception{
        log.info("get authCode from oauth2 server22 :{} ",code);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params= new LinkedMultiValueMap<>();
        params.add("grant_type","authorization_code");
        params.add("code",code);
        params.add("client_id","third_party");
        params.add("client_secret","third_party_secret");
        params.add("redirect_uri","http://127.0.0.1:8085/redirectAndLogin");
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        ResponseEntity<String> result = restTemplate.postForEntity("http://127.0.0.1:8086/oauth/token",requestEntity,String.class);
        log.info("get token from oauth2 server :{} ",result.getBody());
        Map<String,String> tokenMap = JSON.parseObject(result.getBody(),Map.class);
        Map<String, Claim> verifyToken = JwtTokenUtil.verifyToken(tokenMap.get("access_token"));
        Map userinfo = verifyToken.get("userinfo").asMap();
        String username = (String)userinfo.get("username");
        String phone = (String)userinfo.get("phone");
        String id = (String)userinfo.get("id");
        SysUser sysUser = new SysUser();
        sysUser.setId(UUID.randomUUID().toString());
        sysUser.setUsername(username);
        sysUser.setPassword("123");
        sysUser.setPhone(phone);
        sysUser.setId(id);
        userMapper.saveOrUpdate(sysUser);
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        Authentication authentication = new UsernamePasswordAuthenticationToken(username,sysUser.getPassword(),authorities);
        Authentication myUser  = authenticationManager.authenticate(authentication);
        SecurityContextHolder.getContext().setAuthentication(myUser);
        return "oauth2_server_success";
    }

    /**
     * 从github登录，这是请求github获取授权码时，github回调的地址
     * @param code
     * @return
     * @throws Exception
     */
    @RequestMapping("/redirectAndLoginForGithub")
    public String redirectAndLoginForGithub(String code) throws Exception{
        String secretState = "secret" + new Random().nextInt(999_999);
        OAuth20Service service = new ServiceBuilder(appId)
                .apiSecret(appSecret).state(secretState)
                .callback(callbackUrl).build(GitHubApi.instance());
        OAuth2AccessToken accessToken = service.getAccessToken(code);
        final OAuthRequest oAuthRequest = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
        service.signRequest(accessToken, oAuthRequest);
        final Response oAuthresponse = service.execute(oAuthRequest);
        GithubUser githubUser = JSON.parseObject(oAuthresponse.getBody(), new TypeReference<GithubUser>() {});
        SysUser sysUser = new SysUser();
        sysUser.setId(UUID.randomUUID().toString());
        sysUser.setUsername(githubUser.getLogin());
        sysUser.setPassword("123");
        sysUser.setPhone("15255178553");
        userMapper.saveOrUpdate(sysUser);
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER2"));
        Authentication authentication = new UsernamePasswordAuthenticationToken(sysUser.getUsername(), sysUser.getPassword(), authorities);
        Authentication result = authenticationManager.authenticate(authentication);
        SecurityContextHolder.getContext().setAuthentication(result);
        return "github_success";
    }

    /**
     * 访问oauth2服务端受保护的资源
     * @param token
     * @return
     */
    @RequestMapping("/getProtectedResoureFromOauth2Server")
    @ResponseBody
    public String getProtectedResoureFromOauth2Server(String token){
        HttpHeaders headers = new HttpHeaders();
        MultiValueMap<String, String> params= new LinkedMultiValueMap<>();
        params.add("access_token",token);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        ResponseEntity<String> result = restTemplate.postForEntity("http://127.0.0.1:8086/authmenu/getUsers",requestEntity,String.class);
        log.info("get token:{}",result.getBody());
        return "success";
    }
    /**
     * 访问不是oauth2保护资源，可以看到即使带上正确的token也无法正常调用
     * @param token
     * @return
     */
    @RequestMapping("/getUnAuthResoureFromOauth2Server")
    @ResponseBody
    public String getUnAuthResoureFromOauth2Server(String token){
        HttpHeaders headers = new HttpHeaders();
        MultiValueMap<String, String> params= new LinkedMultiValueMap<>();
        params.add("access_token",token);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        ResponseEntity<String> result = restTemplate.postForEntity("http://127.0.0.1:8086/noauthmenu/getUsers",requestEntity,String.class);
        //可以看到这里http返回状态码302，表示该请求已经重定向了
        log.info("http status:{}",result.getStatusCode().value());
        //重定向地址为 http://127.0.0.1:8086/login 说明这个资源不是oauth2资源服务器管理的，所以要走server的认证登录
        log.info("redirect url:{}",result.getHeaders().getLocation());
        log.info("get token:{}",result.getBody());
        return "success";
    }

    /**
     * 测试第三方是否登录成功
     * @return
     */
    @RequestMapping("/getProtectedResoure")
    @ResponseBody
    public String getProtectedResoure(){
        MyUser myUser = (MyUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("get user from login session:{}",myUser);
        return "success";
    }

    @PreAuthorize("hasAuthority('user:add')")
    @RequestMapping("/addUser")
    public String addUser(){
        return "add user success";
    }

}
