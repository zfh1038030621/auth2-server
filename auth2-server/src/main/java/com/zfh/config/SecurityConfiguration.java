package com.zfh.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * spring security config
 * @author zfh
 */
@Configuration
//@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


   @Autowired
    AuthenticationSuccessHandler authenticationSuccessHandler;


    @Autowired
    AuthenticationManager authenticationManager;
    @Override
    public void configure(HttpSecurity http) throws Exception {

        //formLogin() 类型与spring security里xml的form-login元素，目的就是打开一个formlogin配置
        //loginPage("登录页面的url")  自定义登录页url,默认为/login
        // login-processing-url 登录请求拦截的url,也就是form表单提交时指定的action
        //defaultSuccessUrl :成功登录过程后，用户将被重定向到页面 - 默认情况下，该页面是Web应用程序的根目录。
        http.authorizeRequests().antMatchers("/**").authenticated()
        .and().formLogin()
                // 登陆成功之后执行的处理器
            //    .successHandler(authenticationSuccessHandler)


      //  .successHandler(myAuthenticationSuccessHandler())//登录成功之后的handler（自定义handler）

        .and()
        .logout().deleteCookies("JSESSIONID") //登出之后需要删除cookie的名称，多个cookie之间用，隔开
                .invalidateHttpSession(true) //登出之后是否让当前session失效,默认true

        //如果现有的一些配置还是没有满足项目的要求的，可以自定义logoutsuccesshandler
        //.defaultLogoutSuccessHandlerFor() //针对不同的请求地址，登出成功之后给他们自定义的登出handler
        //.addLogoutHandler() //增加登出的一些filter


        //很多网站，比如博客园，在登录页面就有这个选项，勾选“下次自动登录”后，在一定时间段内，
        // 只要不清空浏览器Cookie，就可以自动登录。
     //  .and()
     //   .rememberMe().userDetailsService(new JdbcUserDetailService())
                .and().csrf().disable()
        ;
        /* 增加自定义过滤器   */
        CustomUsernamePasswordAuthenticationFilter filter = new CustomUsernamePasswordAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManager);
        http.addFilter(filter);

    }

   /* @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new MySimpleUrlAuthenticationSuccessHandler();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return  daoAuthenticationProvider;
    }*/

}
