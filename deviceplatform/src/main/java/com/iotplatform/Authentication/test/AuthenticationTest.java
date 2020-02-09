package com.iotplatform.Authentication.test;

import com.iotplatform.client.NorthApiClient;
import com.iotplatform.client.dto.AuthOutDTO;
import com.iotplatform.client.dto.AuthRefreshInDTO;
import com.iotplatform.client.dto.AuthRefreshOutDTO;
import com.iotplatform.client.invokeapi.Authentication;
import com.iotplatform.utils.AuthUtil;
import com.iotplatform.utils.PropertyUtil;

public class AuthenticationTest {
    public static void main(String args[]) throws Exception {
        /**---------------------initialize northApiClient------------------------*/
        //1、从"./src/main/resources/application.properties"中初始化platformIp、platformPort、appId、secret
        NorthApiClient northApiClient = AuthUtil.initApiClient();
        northApiClient.getVersion();

        /**----------------------get access token-------------------------------*/
        System.out.println("======get access token======");
        //2、根据northApiClient生成认证实例authentication
        Authentication authentication = new Authentication(northApiClient);

        // get access token
        //3、获取鉴权OUT：实际上是访问平台的登录地址http://platformIp:platformPort/iocm/app/sec/v1.1.0/login；最后返回的AuthOutDTO实例，其中携带着accessToken和用来刷新accessToken的token
        AuthOutDTO authOutDTO = authentication.getAuthToken();
        System.out.println(authOutDTO.toString());

        /**----------------------refresh token--------------------------------*/
        System.out.println("\n======refresh token======");
        AuthRefreshInDTO authRefreshInDTO = new AuthRefreshInDTO();

        authRefreshInDTO.setAppId(PropertyUtil.getProperty("appId"));
        authRefreshInDTO.setSecret(northApiClient.getClientInfo().getSecret());

        //get refreshToken from the output parameter (i.e. authOutDTO) of Authentication
        String refreshToken = authOutDTO.getRefreshToken();
        authRefreshInDTO.setRefreshToken(refreshToken);

        AuthRefreshOutDTO authRefreshOutDTO = authentication.refreshAuthToken(authRefreshInDTO);
        System.out.println(authRefreshOutDTO.toString());
    }
}
