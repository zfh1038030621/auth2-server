package com.zfh.config;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 *  @auth zfh
 *  @since  2019/2/27
 *
 *  自定义UsernamePasswordAuthenticationFilter过滤器
 *
 *  自定义的需求：
 *  如果现在在登录页面除了username和password之类还有其它业务属性当成入参，并且要放到token里面
 *  我们这里以"mdid"为例子，这样最后就会在最终获取的Authentication对象中带着这个mdid
 *
 *  如果最后还需要往token加mdid，就直接在我们之前的CustomTokenEnhancer类中从Authentication中获取mdid放进去就可以了
 */
public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private boolean postOnly = true;
    private String mdidParameter = "mdid";
    public CustomUsernamePasswordAuthenticationFilter(){
         super();
     }

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            String username = this.obtainUsername(request);
            String password = this.obtainPassword(request);

            String mdid = this.obtainMdid(request);
            if (username == null) {
                username = "";
            }

            if (password == null) {
                password = "";
            }

            username = username.trim();
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

            // UsernamePasswordAuthenticationToken类中还有一个detail属性，我们想要加的属性可以往这个里面加
            HashMap<String,String> deatil = new HashMap<String, String>();
            deatil.put("mdid",mdid);
            authRequest.setDetails(deatil);

            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }

    protected String obtainMdid(HttpServletRequest request) {
        return request.getParameter(this.mdidParameter);
    }

}
