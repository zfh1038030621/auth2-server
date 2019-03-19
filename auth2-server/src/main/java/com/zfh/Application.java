package com.zfh;

import com.zfh.config.CustomTokenEnhancer;
import com.zfh.config.JdbcUserDetailService;
import com.zfh.config.JwtTokenConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

/**
 * @author zfh
 * @since  2019.3.14
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public UserDetailsService jdbcUserDetailService(){
        return new JdbcUserDetailService();
    }

    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new CustomTokenEnhancer();
    }

}