package com.scu.niagaramodule.controller;

import com.scu.niagaramodule.pojo.DayDataDo;
import com.scu.niagaramodule.pojo.Msg;
import com.scu.niagaramodule.pojo.SupervisorHumidity;
import com.scu.niagaramodule.pojo.SupervisorTemperature;
import com.scu.niagaramodule.service.HumidityService;
import com.scu.niagaramodule.service.TemperatureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class HumidityController {
    @Autowired
    HumidityService humidityService;

    @GetMapping("/humidity/lastest")
    public Msg getLastestTemp(){
        SupervisorHumidity SupervisorHumidity = humidityService.getLastestTemp();
        double temp;
        Long timeStamp;
        if (SupervisorHumidity != null){
            temp = SupervisorHumidity.getValue();
            timeStamp = SupervisorHumidity.getTimestamp();
            return Msg.success("success").addData("humidity",temp).addData("timeStamp",timeStamp);
        } else{
            log.info("数据库中无湿度数据");
            return Msg.fail("无湿度数据");
        }
    }

    @GetMapping("/humidity/lastSevenDays")
    public Msg getSevenDayAgoTemp(){
        List<DayDataDo> list = humidityService.getSevenDayAgoTemp();
        Boolean flag =false;  //是否为空的flag
        for(DayDataDo dayDataDo:list){
            if(dayDataDo != null){
                flag = true;
            }
        }
        if (flag){
            return Msg.success("success").addData("humidityList",list);
        } else{
            log.info("数据库中最近七天内无湿度数据");
            return Msg.fail("数据库中最近七天内无湿度数据");
        }
    }
}
