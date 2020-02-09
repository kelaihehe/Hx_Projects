package com.scu.service;

import com.iotplatform.client.NorthApiClient;
import com.iotplatform.client.NorthApiException;
import com.iotplatform.client.dto.CommandDTO2;
import com.iotplatform.client.dto.CommandNA2CloudHeader;
import com.iotplatform.client.dto.InvokeDeviceServiceOutDTO;
import com.iotplatform.client.invokeapi.DeviceServiceInvocation;
import com.iotplatform.utils.AuthUtil;
import org.springframework.stereotype.Service;

import java.util.Map;


@Deprecated //目前该接口只用于安装了IoT Agent/AgentLite的网关设备。
@Service
public class DeviceServiceCommandService {

    public InvokeDeviceServiceOutDTO postDeviceCommand(String deviceId, String serviceId, String command, Map<String, Object> commandParameter, String accessToken) {
        NorthApiClient northApiClient = AuthUtil.initApiClient();
        DeviceServiceInvocation deviceServiceInvocation = new DeviceServiceInvocation(northApiClient);
        CommandDTO2 cmdDTO = new CommandDTO2();
        CommandNA2CloudHeader cmdHeader = new CommandNA2CloudHeader();
        cmdHeader.setMode("NOACK");      //set mode to NOACK or ACK according to the business quest
        cmdHeader.setMethod(command);   //设置命令名称
        cmdDTO.setHeader(cmdHeader);
        cmdDTO.setBody(commandParameter);  //添加命令参数的键值对

        InvokeDeviceServiceOutDTO idsOutDTO = null;
        try {
            idsOutDTO = deviceServiceInvocation.invokeDeviceService(deviceId, serviceId, cmdDTO, null, accessToken);
        } catch (NorthApiException e) {
            if ("100428".equals(e.getError_code())) {
                System.out.println("please make sure the device is online");
            }
        }
        return idsOutDTO;
    }
}
