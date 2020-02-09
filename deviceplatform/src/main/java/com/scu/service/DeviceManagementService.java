package com.scu.service;

import com.iotplatform.client.ClientService;
import com.iotplatform.client.NorthApiClient;
import com.iotplatform.client.NorthApiException;
import com.iotplatform.client.dto.*;
import com.iotplatform.client.invokeapi.DeviceManagement;
import com.iotplatform.utils.AuthUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DeviceManagementService {

    private ClientService clientService = new ClientService();

    /**
     * 注册添加设备
     * 调用后还需调用modifyDeviceInfo与profile的信息进行匹配
     * 设备标识码和验证码一样
     *
     * @param nodeId      设备标识码，唯一指定真实设备
     * @param verifyCode  验证码
     * @param accessToken
     * @param timeout
     * @return
     */
    public RegDirectDeviceOutDTO registerDevice(String nodeId, String verifyCode, String accessToken, int timeout) {
        NorthApiClient northApiClient = AuthUtil.initApiClient();
        DeviceManagement deviceManagement = new DeviceManagement(northApiClient);
        RegDirectDeviceInDTO2 rddid = new RegDirectDeviceInDTO2();
        rddid.setNodeId(nodeId);        //设置设备标识码，用于设备接入
        rddid.setVerifyCode(verifyCode);//设置设备验证码
        rddid.setTimeout(timeout);      //设置超时时间
        RegDirectDeviceOutDTO regDirectDeviceOutDTO = null;
        try {
            regDirectDeviceOutDTO = deviceManagement.regDirectDevice(rddid, null, accessToken);
            System.out.println("register device succeeded");
        } catch (NorthApiException e) {
            e.printStackTrace();
        }
        return regDirectDeviceOutDTO;
    }

    /**
     * 修改deviceId对应的设备信息
     *
     * @param deviceId
     * @param deviceName
     * @param ManufacturerId
     * @param manufacturerName
     * @param deviceType
     * @param model
     * @param protocolType
     * @param location
     * @param accessToken
     */
    public void modifyDeviceInfo(String deviceId,
                                 String deviceName,
                                 String ManufacturerId,
                                 String manufacturerName,
                                 String deviceType,
                                 String model,
                                 String protocolType,
                                 String location,
                                 String imsi,
                                 String accessToken) {
        NorthApiClient northApiClient = AuthUtil.initApiClient();
        DeviceManagement deviceManagement = new DeviceManagement(northApiClient);
        ModifyDeviceInforInDTO mdiInDTO = new ModifyDeviceInforInDTO();
        /*以下是与profile进行匹配必须添入的参数*/
        mdiInDTO.setManufacturerId(ManufacturerId);         //厂商id
        mdiInDTO.setManufacturerName(manufacturerName);     //厂商名称
        mdiInDTO.setDeviceType(deviceType);                 //设备类型
        mdiInDTO.setModel(model);                           //设备型号
        mdiInDTO.setProtocolType(protocolType);             //协议类型
        /***********************************/
        mdiInDTO.setName(deviceName); //设备名称
        mdiInDTO.setLocation(location);  //位置
        mdiInDTO.setEndUser(imsi);  //设置设备的imsi，可以通过它来获取位置
        try {
            deviceManagement.modifyDeviceInfo(mdiInDTO, deviceId, null, accessToken);
            System.out.println("modify device info succeeded");
        } catch (NorthApiException e) {
            System.out.println(e.toString());
        }
    }

    /**
     * 查询设备的状态
     * 字段activated为true表示已激活，false表示未激活
     *
     * @param deviceId
     * @param accessToken
     * @return QueryDeviceStatusOutDTO
     */
    public QueryDeviceStatusOutDTO queryDeviceStatus(String deviceId, String accessToken) {
        NorthApiClient northApiClient = AuthUtil.initApiClient();
        DeviceManagement deviceManagement = new DeviceManagement(northApiClient);
        QueryDeviceStatusOutDTO deviceStatusOutDTO = null;
        try {
            deviceStatusOutDTO = deviceManagement.queryDeviceStatus(deviceId, null, accessToken);
        } catch (NorthApiException e) {
            e.printStackTrace();
        }
        return deviceStatusOutDTO;
    }


    /**
     * 查询设备的实时位置
     *
     * @param deviceId
     * @param accessToken
     * @return QueryDeviceRealtimeLocationOutDTO
     * 字段pd：为位置信息
     * 字段poserr：错误信息
     */
    public QueryDeviceRealtimeLocationOutDTO queryDeviceRealtimeLocation(String deviceId, String accessToken) throws NorthApiException {
        clientService.checkInput(deviceId);
        NorthApiClient northApiClient = AuthUtil.initApiClient();
        DeviceManagement deviceManagement = new DeviceManagement(northApiClient);
        QueryDeviceRealtimeLocationInDTO qdrlInDTO = new QueryDeviceRealtimeLocationInDTO();
        qdrlInDTO.setDeviceId(deviceId);
        qdrlInDTO.setHorAcc(1000);   //水平误差，默认为1000m
        QueryDeviceRealtimeLocationOutDTO qdrlOutDTO = null;
        try {
            qdrlOutDTO = deviceManagement.queryDeviceRealtimeLocation(qdrlInDTO, null, accessToken);
        } catch (NorthApiException e) {
            System.out.println(e.toString());
        }
        return qdrlOutDTO;
    }

    /**
     * 修改设备的影子：影子相当于设备的信息的备份
     *
     * @param deviceId
     * @param services
     * @param accessToken
     */
    private static void modifyDeviceShadow(String deviceId, Map<String, Map<String, Object>> services, String accessToken) {
        NorthApiClient northApiClient = AuthUtil.initApiClient();
        DeviceManagement deviceManagement = new DeviceManagement(northApiClient);
        ModifyDeviceShadowInDTO mdsInDTO = new ModifyDeviceShadowInDTO();
        List<ServiceDesiredDTO> serviceDesireds = new ArrayList<ServiceDesiredDTO>();
        ServiceDesiredDTO sdDTO = new ServiceDesiredDTO();
        //对该设备的需要修改的服务状态进行遍历添加
        //services的map中的每个service包含着服务名和对应的属性和命令（属性和命令以map形式）
        //例如：Smoke:{value:1;temperature:2;};Brigntness:{brigntness:1}
        for (Map.Entry<String, Map<String, Object>> entry : services.entrySet()) {
            sdDTO.setServiceId(entry.getKey());
            sdDTO.setDesired(entry.getValue());
            //将这些需要更改的设备服务信息添加到List<ServiceDesiredDTO>
            serviceDesireds.add(sdDTO);
        }
        mdsInDTO.setServiceDesireds(serviceDesireds);
        try {
            deviceManagement.modifyDeviceShadow(mdsInDTO, deviceId, null, accessToken);
        } catch (NorthApiException e) {
            System.out.println(e.toString());
        }
    }

    /**
     * 查询设备影子信息
     *
     * @param deviceId
     * @param appId
     * @param accessToken
     * @return
     */
    public QueryDeviceShadowOutDTO queryDeviceShadow(String deviceId, String appId, String accessToken) {
        NorthApiClient northApiClient = AuthUtil.initApiClient();
        DeviceManagement deviceManagement = new DeviceManagement(northApiClient);
        QueryDeviceShadowOutDTO queryDeviceShadowOutDTO = null;
        try {
            queryDeviceShadowOutDTO = deviceManagement.queryDeviceShadow(deviceId, appId, accessToken);
        } catch (NorthApiException e) {
            e.printStackTrace();
        }
        return queryDeviceShadowOutDTO;
    }


}
