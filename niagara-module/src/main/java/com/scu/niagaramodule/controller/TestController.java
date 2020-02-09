package com.scu.niagaramodule.controller;

import com.scu.niagaramodule.mapper.SupervisorTemperatureMapper;
import com.scu.niagaramodule.pojo.SupervisorTemperature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    SupervisorTemperatureMapper supervisorTemperatureMapper;

    @GetMapping("/niagara/Test")
    public String test() {
        return "niagara/Test";
    }

    @GetMapping("/mysql/Test")
    public String mysqlTest(){
        SupervisorTemperature supervisorTemperature = supervisorTemperatureMapper.selectByPrimaryKey(1);
        return supervisorTemperature.getValue().toString();
    }

    @PostMapping("/postTest")
    public String postTest(){
        return "postTest";
    }
}
