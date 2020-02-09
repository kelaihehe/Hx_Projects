package com.scu.service;

import com.iotplatform.client.NorthApiClient;
import com.iotplatform.client.NorthApiException;
import com.iotplatform.client.dto.*;
import com.iotplatform.client.invokeapi.DataCollection;
import com.iotplatform.utils.AuthUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueryDataService {

    /**
     * 查询deviceId对应的单个设备信息
     *
     * @param deviceId
     * @param select
     * @param appId
     * @param accessToken
     * @return
     */
    public QuerySingleDeviceInfoOutDTO querySingleDeviceInfo(String deviceId, String select, String appId, String accessToken) {
        NorthApiClient northApiClient = AuthUtil.initApiClient();
        DataCollection dataCollection = new DataCollection(northApiClient);
        QuerySingleDeviceInfoOutDTO qsdiOutDTO = null;
        try {
            qsdiOutDTO = dataCollection.querySingleDeviceInfo(deviceId, select, appId, accessToken);
        } catch (NorthApiException e) {
            e.printStackTrace();
        }
        return qsdiOutDTO;
    }

    /**
     * 根据QueryBatchDevicesInfoInDTO实例进行查询多个设备信息
     *
     * @param qbdiInDTO
     * @param accessToken
     * @return
     */
    public List<QuerySingleDeviceInfoOutDTO> queryBatchDevicesInfo(QueryBatchDevicesInfoInDTO qbdiInDTO, String accessToken) {
        NorthApiClient northApiClient = AuthUtil.initApiClient();
        DataCollection dataCollection = new DataCollection(northApiClient);
        QueryBatchDevicesInfoOutDTO qbdiOutDTO = null;
        try {
            qbdiOutDTO = dataCollection.queryBatchDevicesInfo(qbdiInDTO, accessToken);
        } catch (NorthApiException e) {
            e.printStackTrace();
        }
        return qbdiOutDTO != null ? qbdiOutDTO.getDevices() : null;
    }

    /**
     * 查询对应的deviceId的数据上传历史信息
     * QueryDeviceDataHistoryInDTO实例中属性deviceId和属性gatewayId不可少
     * 对于没有网关的直连设备时，deviceId=gatewayId
     *
     * @param dataHistoryInDTO
     * @param accessToken
     * @return 历史数据列表
     */
    public List<DeviceDataHistoryDTO> queryDeviceDataHistory(QueryDeviceDataHistoryInDTO dataHistoryInDTO, String deviceId, String gatewayId, String accessToken) {
        NorthApiClient northApiClient = AuthUtil.initApiClient();
        DataCollection dataCollection = new DataCollection(northApiClient);
        dataHistoryInDTO.setDeviceId(deviceId);
        dataHistoryInDTO.setGatewayId(gatewayId);
        QueryDeviceDataHistoryOutDTO DeviceDataHistory = null;
        try {
            DeviceDataHistory = dataCollection.queryDeviceDataHistory(dataHistoryInDTO, accessToken);
        } catch (NorthApiException e) {
            e.printStackTrace();
        }
        return DeviceDataHistory != null ? DeviceDataHistory.getDeviceDataHistoryDTOs() : null;
    }

    /**
     * 查询serviceId对应的设备服务能力List，即服务的属性及命令
     *
     * @param qdcInDTO
     * @param deviceId
     * @param accessToken
     * @return
     */
    public List<DeviceCapabilityDTO> queryDeviceCapabilities(QueryDeviceCapabilitiesInDTO qdcInDTO, String deviceId, String accessToken) {
        NorthApiClient northApiClient = AuthUtil.initApiClient();
        DataCollection dataCollection = new DataCollection(northApiClient);
        qdcInDTO.setDeviceId(deviceId);
        QueryDeviceCapabilitiesOutDTO DeviceCapabilities = null;
        try {
            DeviceCapabilities = dataCollection.queryDeviceCapabilities(qdcInDTO, accessToken);
        } catch (NorthApiException e) {
            e.printStackTrace();
        }
        return DeviceCapabilities != null ? DeviceCapabilities.getDeviceCapabilities() : null;
    }

}
