package yyl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;

/**
 * @Description: TODO
 * @Author: yang.yonglian
 * @CreateDate: 2019/11/13 21:08
 * @Version: 1.0
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
@SpringBootApplication
public class SsoClient2Start {
    public static void main(String[] args) {
        SpringApplication.run(SsoClient2Start.class,args);
    }
    @Bean
    public OAuth2RestTemplate oauth2RestTemplate(OAuth2ClientContext oauth2ClientContext,
                                                 OAuth2ProtectedResourceDetails details) {
        return new OAuth2RestTemplate(details, oauth2ClientContext);
    }
}
