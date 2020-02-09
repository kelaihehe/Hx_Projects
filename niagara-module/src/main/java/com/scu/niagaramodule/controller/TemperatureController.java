package com.scu.niagaramodule.controller;

import com.scu.niagaramodule.pojo.DayDataDo;
import com.scu.niagaramodule.pojo.Msg;
import com.scu.niagaramodule.pojo.SupervisorTemperature;
import com.scu.niagaramodule.service.TemperatureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class TemperatureController {

    @Autowired
    TemperatureService temperatureService;

    @GetMapping("/temperature/lastest")
    public Msg getLastestTemp(){
        SupervisorTemperature supervisorTemperature = temperatureService.getLastestTemp();
        double temp;
        Long timeStamp;
        if (supervisorTemperature != null){
            temp = supervisorTemperature.getValue();
            timeStamp = supervisorTemperature.getTimestamp();
            return Msg.success("success").addData("temperature",temp).addData("timeStamp",timeStamp);
        } else{
            log.info("数据库中无温度数据");
            return Msg.fail("无温度数据");
        }
    }

    @GetMapping("/temperature/lastSevenDays")
    public Msg getSevenDayAgoTemp(){
        List<DayDataDo> list = temperatureService.getSevenDayAgoTemp();
        double temp;
        Long timeStamp;
        Boolean flag =false;  //是否为空的flag
        for(DayDataDo dayDataDo:list){
            if(dayDataDo != null){
                flag = true;
            }
        }
        if (flag){
            return Msg.success("success").addData("temperatureList",list);
        } else{
            log.info("数据库中最近七天内无温度数据");
            return Msg.fail("数据库中最近七天内无温度数据");
        }
    }
}
