package com.zfh.config;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义token 兼容原生token
 * @author zfh
 * @since 2019/2/5
 */
public class CustomTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        DefaultOAuth2AccessToken token = null;
        if (oAuth2AccessToken instanceof DefaultOAuth2AccessToken) {
            token = ((DefaultOAuth2AccessToken) oAuth2AccessToken);
            //token.setValue(getNewToken());  //修改token的vakue值

            Map<String, Object> additionalInformation = new HashMap<String, Object>();
            additionalInformation.put("zfh12", "1234");
            token.setAdditionalInformation(additionalInformation);
            System.out.println(token.getValue());

            String type = oAuth2AccessToken.getTokenType();
            if ("authorization_code".equals(type)) {
               /* //如果授权方式是code的方式,在兼容原先的基础上更改，增加一个过期时间
                Map<String, Object> additionalInformation = new HashMap<String, Object>();
                additionalInformation.put("refresh_exp", token.getExpiration());
                token.setAdditionalInformation(additionalInformation);*/
            }
        }
        return token;
    }
}
