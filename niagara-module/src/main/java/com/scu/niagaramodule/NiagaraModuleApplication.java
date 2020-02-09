package com.scu.niagaramodule;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@MapperScan("com.scu.niagaramodule.mapper")
@EnableEurekaClient
@SpringBootApplication
public class NiagaraModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(NiagaraModuleApplication.class, args);
    }

}
