package com.scu.niagaramodule.controller;

import com.scu.niagaramodule.pojo.*;
import com.scu.niagaramodule.service.Pm25Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class Pm25Controller {
    @Autowired
    Pm25Service pm25Service;

    @GetMapping("/pm25/lastest")
    public Msg getLastestTemp(){
        SupervisorPm25 supervisorPm25 = pm25Service.getLastestTemp();
        double temp;
        Long timeStamp;
        if (supervisorPm25 != null){
            temp = supervisorPm25.getValue();
            timeStamp = supervisorPm25.getTimestamp();
            return Msg.success("success").addData("pm25",temp).addData("timeStamp",timeStamp);
        } else{
            log.info("数据库中无PM2.5数据");
            return Msg.fail("无PM2.5数据");
        }
    }

    @GetMapping("/pm25/lastSevenDays")
    public Msg getSevenDayAgoTemp(){
        List<DayDataDo> list = pm25Service.getSevenDayAgoTemp();
        Boolean flag =false;  //是否为空的flag
        for(DayDataDo dayDataDo:list){
            if(dayDataDo != null){
                flag = true;
            }
        }
        if (flag){
            return Msg.success("success").addData("pm25List",list);
        } else{
            log.info("数据库中最近七天内无PM2.5数据");
            return Msg.fail("数据库中最近七天内无PM2.5数据");
        }
    }
}
