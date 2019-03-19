package com.zfh.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义 UserDetailsService
 *
 * @author zfh
 * @since 2019/2/5
 */
@Configuration
public class JdbcUserDetailService implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String userName){
        //我这边偷懒没有用dao去查数据库
        //UserDetails ud = super.loadUserByUsername(username);
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        //return  new User("zfh12","$2a$10$k2/8rCh.1FGCoNt1LggQhePMPufVc.LolyZtqGSoGbPlDxLiNFKJq",authorities);
        return  new User("zfh","zfh",authorities);
    }

}
