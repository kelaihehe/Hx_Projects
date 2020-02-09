package com.scu.controller;

import com.iotplatform.client.dto.*;
import com.iotplatform.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MessageReceiverController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private final String callbackurl = "/v1.0.0/messageReceiver";
    private final String callbackurl_nbcmd = "/v1.0.0/messageReceiver/cmd";

    @RequestMapping(
            value = {"/v1.0.0/messageReceiver"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    private String receive(@RequestBody String body) throws Exception {
        this.handleBody(body);
        if (body.contains("\"notifyType\":\"deviceAdded\"")) {
            this.handleDeviceAdded((NotifyDeviceAddedDTO) JsonUtil.jsonString2SimpleObj(body, NotifyDeviceAddedDTO.class));
        }

        if (body.contains("\"notifyType\":\"bindDevice\"")) {
            this.handleBindDevice((NotifyBindDeviceDTO) JsonUtil.jsonString2SimpleObj(body, NotifyBindDeviceDTO.class));
        }

        if (body.contains("\"notifyType\":\"deviceInfoChanged\"")) {
            this.handleDeviceInfoChanged((NotifyDeviceInfoChangedDTO) JsonUtil.jsonString2SimpleObj(body, NotifyDeviceInfoChangedDTO.class));
        }

        if (body.contains("\"notifyType\":\"deviceDataChanged\"")) {
            this.handleDeviceDataChanged((NotifyDeviceDataChangedDTO) JsonUtil.jsonString2SimpleObj(body, NotifyDeviceDataChangedDTO.class));
        }

        if (body.contains("\"notifyType\":\"deviceDatasChanged\"")) {
            this.handleDeviceDatasChanged((NotifyDeviceDatasChangedDTO) JsonUtil.jsonString2SimpleObj(body, NotifyDeviceDatasChangedDTO.class));
        }

        if (body.contains("\"notifyType\":\"serviceInfoChanged\"")) {
            this.handleServiceInfoChanged((NotifyServiceInfoChangedDTO) JsonUtil.jsonString2SimpleObj(body, NotifyServiceInfoChangedDTO.class));
        }

        if (body.contains("\"notifyType\":\"deviceDeleted\"")) {
            this.handleDeviceDeleted((NotifyDeviceDeletedDTO) JsonUtil.jsonString2SimpleObj(body, NotifyDeviceDeletedDTO.class));
        }

        if (body.contains("\"notifyType\":\"messageConfirm\"")) {
            this.handleMessageConfirm((NotifyMessageConfirmDTO) JsonUtil.jsonString2SimpleObj(body, NotifyMessageConfirmDTO.class));
        }

        if (body.contains("\"notifyType\":\"commandRsp\"")) {
            this.handleCommandRsp((NotifyCommandRspDTO) JsonUtil.jsonString2SimpleObj(body, NotifyCommandRspDTO.class));
        }

        if (body.contains("\"notifyType\":\"deviceEvent\"")) {
            this.handleDeviceEvent((NotifyDeviceEventDTO) JsonUtil.jsonString2SimpleObj(body, NotifyDeviceEventDTO.class));
        }

        if (body.contains("\"notifyType\":\"deviceModelAdded\"")) {
            this.handleDeviceModelAdded((NotifyDeviceModelAddedDTO) JsonUtil.jsonString2SimpleObj(body, NotifyDeviceModelAddedDTO.class));
        }

        if (body.contains("\"notifyType\":\"deviceModelDeleted\"")) {
            this.handleDeviceModelDeleted((NotifyDeviceModelDeletedDTO) JsonUtil.jsonString2SimpleObj(body, NotifyDeviceModelDeletedDTO.class));
        }

        if (body.contains("\"notifyType\":\"ruleEvent\"")) {
            this.handleRuleEvent((NotifyRuleEventDTO) JsonUtil.jsonString2SimpleObj(body, NotifyRuleEventDTO.class));
        }

        if (body.contains("\"notifyType\":\"deviceDesiredPropertiesModifyStatusChanged\"")) {
            this.handleDeviceDesiredStatusChanged((NotifyDeviceDesiredStatusChangedDTO) JsonUtil.jsonString2SimpleObj(body, NotifyDeviceDesiredStatusChangedDTO.class));
        }

        if (body.contains("\"notifyType\":\"swUpgradeStateChangeNotify\"")) {
            this.handleSwUpgradeStateChanged((NotifySwUpgradeStateChangedDTO) JsonUtil.jsonString2SimpleObj(body, NotifySwUpgradeStateChangedDTO.class));
        }

        if (body.contains("\"notifyType\":\"swUpgradeResultNotify\"")) {
            this.handleSwUpgradeResult((NotifySwUpgradeResultDTO) JsonUtil.jsonString2SimpleObj(body, NotifySwUpgradeResultDTO.class));
        }

        if (body.contains("\"notifyType\":\"fwUpgradeStateChangeNotify\"")) {
            this.handleFwUpgradeStateChanged((NotifyFwUpgradeStateChangedDTO) JsonUtil.jsonString2SimpleObj(body, NotifyFwUpgradeStateChangedDTO.class));
        }

        if (body.contains("\"notifyType\":\"fwUpgradeResultNotify\"")) {
            this.handleFwUpgradeResult((NotifyFwUpgradeResultDTO) JsonUtil.jsonString2SimpleObj(body, NotifyFwUpgradeResultDTO.class));
        }

        return "ok";
    }

    @RequestMapping(
            value = {"/v1.0.0/messageReceiver/cmd"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    private String receiveCmdResult(@RequestBody String body) throws Exception {
        this.handleBody(body);
        this.handleNBCommandStateChanged((NotifyNBCommandStatusChangedDTO) JsonUtil.jsonString2SimpleObj(body, NotifyNBCommandStatusChangedDTO.class));
        return "ok";
    }

    public void forward (Object body){
        Map<String,Object> notifyInfo = new HashMap<String,Object>();
        String posturl = "http://3e6b3310.ngrok.io/local/messageReceiver";
        notifyInfo.put("notifyInfo",body);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(posturl, notifyInfo, String.class);
    }

    public void handleBody(String body) {
    }

    public void handleDeviceAdded(NotifyDeviceAddedDTO body) {
        logger.info("deviceAdded ==> " + body);
        forward(body);
    }

    public void handleBindDevice(NotifyBindDeviceDTO body) {
    }

    public void handleDeviceInfoChanged(NotifyDeviceInfoChangedDTO body) {
    }

    public void handleDeviceDataChanged(NotifyDeviceDataChangedDTO body) {
        logger.info("deviceDataChanged ==> " + body);
        forward(body);
    }

    public void handleDeviceDatasChanged(NotifyDeviceDatasChangedDTO body) {
    }

    public void handleServiceInfoChanged(NotifyServiceInfoChangedDTO body) {
    }

    public void handleDeviceDeleted(NotifyDeviceDeletedDTO body) {
        logger.info("deviceDeleted ==> " + body);
        forward(body);
    }

    public void handleMessageConfirm(NotifyMessageConfirmDTO body) {
    }

    public void handleCommandRsp(NotifyCommandRspDTO body) {
    }

    public void handleDeviceEvent(NotifyDeviceEventDTO body) {
    }

    public void handleDeviceModelAdded(NotifyDeviceModelAddedDTO body) {
    }

    public void handleDeviceModelDeleted(NotifyDeviceModelDeletedDTO body) {
    }

    public void handleRuleEvent(NotifyRuleEventDTO body) {
    }

    public void handleDeviceDesiredStatusChanged(NotifyDeviceDesiredStatusChangedDTO body) {
    }

    public void handleSwUpgradeStateChanged(NotifySwUpgradeStateChangedDTO body) {
    }

    public void handleSwUpgradeResult(NotifySwUpgradeResultDTO body) {
    }

    public void handleFwUpgradeStateChanged(NotifyFwUpgradeStateChangedDTO body) {
    }

    public void handleFwUpgradeResult(NotifyFwUpgradeResultDTO body) {
    }

    public void handleNBCommandStateChanged(NotifyNBCommandStatusChangedDTO body) {
    }
}
