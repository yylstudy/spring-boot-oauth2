package com.yyl.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.yyl.bean.MyUser;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.endpoint.AuthorizationEndpoint;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 认证服务器
 * @Author: yang.yonglian
 * @CreateDate: 2019/11/20 19:57
 * @Version: 1.0
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter implements ApplicationContextAware {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthorizationEndpoint authorizationEndpoint;

    /**
     * 刷新缓存时需要配置UserDetailsService，注意这里和DataSource一样，不能使用注入的方式
     * @return
     */
    @Bean
    public MyUserDetailService userDetailsService(){
        return new MyUserDetailService();
    }

    @Bean
    @ConfigurationProperties(prefix = "druid")
    public DataSource dataSource(){
        return new DruidDataSource();
    }

    /**
     * 自定义授权界面
     */
    @PostConstruct
    public void init(){
        authorizationEndpoint.setUserApprovalPage("forward:/oauth/my_approval_page");
    }

    /**
     * 可以看到这个方法是在 oauth2的一些配置方法之后执行的如tokenStore 所以这里不能使用 DataSource注入
     * 因为此时DataSource还未初始化
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("1111111111");
    }

    /**
     * 声明安全约束
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //允许表单认证
        security.allowFormAuthenticationForClients();
        //允许通过token_key的请求
        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");

    }

    /**
     * 客户端信息存储于数据库
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource());
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //配置tokenStore
        endpoints.tokenStore(tokenStore())
                .authenticationManager(authenticationManager)
                //授权码服务
                .authorizationCodeServices(authorizationCodeServices())
                //如果想要刷新token,那么需要配置userDetailsService
                .userDetailsService(userDetailsService())
                //授权确认
                .approvalStore(approvalStore())
                //配置jwtAccessToken转换器
                .accessTokenConverter(jwtAccessTokenConverter())
                .allowedTokenEndpointRequestMethods(HttpMethod.GET,HttpMethod.POST)
                .setClientDetailsService(jdbcClientDetailsService());
    }
    @Bean
    public AuthorizationCodeServices authorizationCodeServices(){
        return new JdbcAuthorizationCodeServices(dataSource());
    }
    /**
     * 声明Token Store
     * @return
     */
    @Bean
    public TokenStore tokenStore(){
        return new JdbcTokenStore(dataSource());
    }

    /**
     * jwt的token格式转化器
     * @return
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter(){
            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
                MyUser myUser = (MyUser)authentication.getUserAuthentication().getPrincipal();
                Map<String,Object> info = new HashMap<>();
                info.put("userinfo",myUser);
                ((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(info);
                return super.enhance(accessToken,authentication);
            }
        };
        converter.setSigningKey("yyl");
        return converter;
    }

    /**
     * 声明确认授权的Store
     * @return
     */
    @Bean
    public ApprovalStore approvalStore(){
        return new JdbcApprovalStore(dataSource());
    }

    @Bean
    public ClientDetailsService jdbcClientDetailsService(){
        return new JdbcClientDetailsService(dataSource());
    }

}
