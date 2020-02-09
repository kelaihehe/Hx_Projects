package com.scu.service;

import com.iotplatform.client.NorthApiClient;
import com.iotplatform.client.NorthApiException;
import com.iotplatform.client.dto.AuthOutDTO;
import com.iotplatform.client.dto.AuthRefreshInDTO;
import com.iotplatform.client.dto.AuthRefreshOutDTO;
import com.iotplatform.client.invokeapi.Authentication;
import com.iotplatform.utils.AuthUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 通过配置文件验证并获取鉴权OUT实例
     *
     * @return AuthOutDTO
     */
    public AuthOutDTO getAuthOutDTO() {
        //1、从"./src/main/resources/application.properties"中初始化platformIp、platformPort、appId、secret
        NorthApiClient northApiClient = AuthUtil.initApiClient();
        //2、根据northApiClient生成认证实例authentication
        Authentication authentication = new Authentication(northApiClient);
        //3、获取鉴权OUT：实际上是访问平台的登录地址http://platformIp:platformPort/iocm/app/sec/v1.1.0/login；最后返回的AuthOutDTO实例，其中携带着accessToken和用来刷新accessToken的token
        AuthOutDTO authOutDTO = null;
        try {
            authOutDTO = authentication.getAuthToken();
            return authOutDTO;
        } catch (NorthApiException e) {
            e.printStackTrace();
        }
        logger.info("authOutDTO:" + authOutDTO);
        return authOutDTO;
    }

    /**
     * 输入上次的鉴权OUT的refreshToken，用它进行刷新accessToken
     *
     * @param refreshToken
     * @return
     */
    public AuthRefreshOutDTO getAuthRefreshOutDTO(String refreshToken) {
        NorthApiClient northApiClient = AuthUtil.initApiClient();
        Authentication authentication = new Authentication(northApiClient);
        //创建authRefreshInDTO
        AuthRefreshInDTO authRefreshInDTO = new AuthRefreshInDTO();
        authRefreshInDTO.setAppId(northApiClient.getClientInfo().getAppId());
        authRefreshInDTO.setSecret(northApiClient.getClientInfo().getSecret());

        //get refreshToken from the output parameter (i.e. authOutDTO) of Authentication
        authRefreshInDTO.setRefreshToken(refreshToken);
        AuthRefreshOutDTO authRefreshOutDTO = null;
        try {
            authRefreshOutDTO = authentication.refreshAuthToken(authRefreshInDTO);
        } catch (NorthApiException e) {
            e.printStackTrace();
        }
        logger.info("authRefreshOutDTO:" + authRefreshOutDTO);
        return authRefreshOutDTO;
    }

    /**
     * 开启自动刷新accessToken后，在调用其他API接口时，可以不用传入accessToken，可以设置为null
     *
     * @throws NorthApiException
     */
    public void StartAutoRefreshToken() throws NorthApiException {
        NorthApiClient northApiClient = AuthUtil.initApiClient();
        Authentication authentication = new Authentication(northApiClient);
        authentication.startRefreshTokenTimer();
    }

    /**
     * 关闭自动刷新accessToken后，在调用其他API接口时，必须传入accessToken
     *
     * @throws NorthApiException
     */
    public void StopAutoRefreshToken() throws NorthApiException {
        NorthApiClient northApiClient = AuthUtil.initApiClient();
        Authentication authentication = new Authentication(northApiClient);
        authentication.stopRefreshTokenTimer();
    }
}
