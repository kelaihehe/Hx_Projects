package com.iotplatform.DeviceUpgrade.test;

import com.iotplatform.client.NorthApiClient;
import com.iotplatform.client.NorthApiException;
import com.iotplatform.client.dto.*;
import com.iotplatform.client.invokeapi.Authentication;
import com.iotplatform.client.invokeapi.DeviceUpgrade;
import com.iotplatform.utils.AuthUtil;

import java.util.ArrayList;
import java.util.List;

public class DeviceUpgradeTest {

    public static void main(String args[]) throws Exception {
        /**---------------------initialize northApiClient------------------------*/
        NorthApiClient northApiClient = AuthUtil.initApiClient();
        DeviceUpgrade deviceUpgrade = new DeviceUpgrade(northApiClient);

        /**---------------------get accessToken at first------------------------*/
        Authentication authentication = new Authentication(northApiClient);
        AuthOutDTO authOutDTO = authentication.getAuthToken();
        String accessToken = authOutDTO.getAccessToken();

        String deviceId = "09691354-872d-49a1-9c1c-a18512f10ae2";//this is a test device

        /**---------------------query upgrade package list------------------------*/
        System.out.println("======query upgrade package list (firmware and software)======");
        QueryUpgradePackageListInDTO quplInDTO = new QueryUpgradePackageListInDTO();
        quplInDTO.setPageNo(0);
        quplInDTO.setPageSize(10);
        quplInDTO.setFileType("firmwarePackage");//query firmware package list
        QueryUpgradePackageListOutDTO quplOutDTO_firmware = deviceUpgrade.queryUpgradePackageList(quplInDTO, accessToken);
        System.out.println(quplOutDTO_firmware.toString());

        if (quplOutDTO_firmware.getTotalCount() == 0) {
            System.out.println("please upload a firmware package");
        }

        quplInDTO.setFileType("softwarePackage");//query software package list
        QueryUpgradePackageListOutDTO quplOutDTO_software = deviceUpgrade.queryUpgradePackageList(quplInDTO, accessToken);
        System.out.println(quplOutDTO_software.toString());

        if (quplOutDTO_software.getTotalCount() == 0) {
            System.out.println("please upload a software package");
        }

        List<QueryUpgradePackageOutDTO> packageList_firmware = quplOutDTO_firmware.getData();

        if (packageList_firmware != null && packageList_firmware.size() > 0) {

            for (QueryUpgradePackageOutDTO queryUpgradePackageOutDTO : packageList_firmware) {
                /**---------------------query a specified upgrade package------------------------*/
                System.out.println("\n======query a specified upgrade package======");
                QueryUpgradePackageOutDTO qupOutDTO = deviceUpgrade.queryUpgradePackage(queryUpgradePackageOutDTO.getFileId(), accessToken);
                System.out.println(qupOutDTO.toString());
            }

            /**---------------------create a firmware upgrade task------------------------*/
            System.out.println("\n======create a firmware upgrade task======");
            //find a test package from the list
            QueryUpgradePackageOutDTO package0 = packageList_firmware.get(0);

            CreateUpgradeTaskOutDTO cutOutDTO_firmware = createFirmwareUpgradeTask(deviceUpgrade, package0.getFileId(), deviceId, accessToken);

            if (cutOutDTO_firmware != null) {
                System.out.println(cutOutDTO_firmware.toString());

                /**---------------------query the upgrade task------------------------*/
                System.out.println("\n======query the upgrade task======");
                QueryUpgradeTaskOutDTO qutOutDTO = deviceUpgrade.queryUpgradeTask(cutOutDTO_firmware.getOperationId(), accessToken);
                System.out.println(qutOutDTO.toString());

                /**---------------------query the upgrade task detail------------------------*/
                System.out.println("\n======query the upgrade task detail======");
                QueryUpgradeSubTaskInDTO qustInDTO = new QueryUpgradeSubTaskInDTO();
                qustInDTO.setPageNo(0);
                qustInDTO.setPageSize(10);
                QueryUpgradeSubTaskOutDTO qustOutDTO = deviceUpgrade.queryUpgradeSubTask(qustInDTO, cutOutDTO_firmware.getOperationId(), accessToken);
                System.out.println(qustOutDTO.toString());
            }

            //delete the second firmware package
            if (packageList_firmware.size() > 1) {
                QueryUpgradePackageOutDTO package1 = packageList_firmware.get(1);
                /**---------------------delete a specified upgrade package------------------------*/
                System.out.println("\n======delete a specified upgrade package======");
                deviceUpgrade.deleteUpgradePackage(package1.getFileId(), accessToken);
                System.out.println("delete a specified upgrade package succeeded");
            }
        }

        /**---------------------query upgrade task list------------------------*/
        System.out.println("\n======query upgrade task list======");
        QueryUpgradeTaskListInDTO qutlInDTO = new QueryUpgradeTaskListInDTO();
        qutlInDTO.setPageNo(0);
        qutlInDTO.setPageSize(10);
        QueryUpgradeTaskListOutDTO qutlOutDTO = deviceUpgrade.queryUpgradeTaskList(qutlInDTO, accessToken);
        System.out.println(qutlOutDTO.toString());


        List<QueryUpgradePackageOutDTO> packageList_software = quplOutDTO_software.getData();
        if (packageList_software != null && packageList_software.size() > 0) {
            /**---------------------create a software upgrade task------------------------*/
            System.out.println("\n======create a software upgrade task======");
            //find a test package from the list
            QueryUpgradePackageOutDTO package0 = packageList_software.get(0);
            CreateUpgradeTaskInDTO cutInDTO = new CreateUpgradeTaskInDTO();
            cutInDTO.setFileId(package0.getFileId());
            //set target devices
            OperateDevices targets = new OperateDevices();
            List<String> devices = new ArrayList<String>();
            devices.add(deviceId);
            targets.setDevices(devices);
            cutInDTO.setTargets(targets);

            CreateUpgradeTaskOutDTO cutOutDTO_software = deviceUpgrade.createSoftwareUpgradeTask(cutInDTO, accessToken);
            System.out.println(cutOutDTO_software.toString());
        }
    }

    private static CreateUpgradeTaskOutDTO createFirmwareUpgradeTask(DeviceUpgrade deviceUpgrade, String FileId,
                                                                     String deviceId, String accessToken) {
        CreateUpgradeTaskInDTO cutInDTO = new CreateUpgradeTaskInDTO();
        cutInDTO.setFileId(FileId);
        //set target devices
        OperateDevices targets = new OperateDevices();
        List<String> devices = new ArrayList<String>();
        devices.add(deviceId);
        targets.setDevices(devices);
        cutInDTO.setTargets(targets);

        try {
            CreateUpgradeTaskOutDTO cutOutDTO_firmware = deviceUpgrade.createFirmwareUpgradeTask(cutInDTO, accessToken);
            return cutOutDTO_firmware;
        } catch (NorthApiException e) {
            System.out.println(e.toString());
        }
        return null;
    }
}
