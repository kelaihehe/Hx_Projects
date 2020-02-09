package com.scu.niagaramodule;

import com.scu.niagaramodule.mapper.SupervisorTemperatureMapper;
import com.scu.niagaramodule.mapper.SupervisorVoltageMapper;
import com.scu.niagaramodule.pojo.SupervisorTemperature;
import com.scu.niagaramodule.pojo.SupervisorTemperatureExample;
import com.scu.niagaramodule.pojo.SupervisorVoltage;
import com.scu.niagaramodule.service.TemperatureService;
import com.scu.niagaramodule.service.VoltageService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class NiagaraModuleApplicationTests {

    @Autowired
    SupervisorTemperatureMapper supervisorTemperatureMapper;

    @Autowired
    SupervisorVoltageMapper supervisorVoltageMapper;

    @Autowired
    TemperatureService temperatureService;

    @Autowired
    VoltageService voltageService;

    @Test
    public void test() {
        SupervisorTemperature supervisorTemperature = supervisorTemperatureMapper.selectByPrimaryKey(1);
        System.out.println(supervisorTemperature);
    }
    @Test
    public void testvolatage() {
        SupervisorVoltage supervisorVoltage = voltageService.getLastestTemp();
        System.out.println(supervisorVoltage.getValue());
    }
    @Test
    public void test1() {
        List<SupervisorTemperature> list = supervisorTemperatureMapper.selectNByDesc(2);
        log.info("success");
    }

    @Test
    public void test2() {
        Long val = 1559652150018L;
        Timestamp timestamp = new Timestamp((val));

        log.info(String.valueOf(timestamp.getTime()));
        Date date = timestamp;
        log.info(String.valueOf(date.getTime()));
        Date date1 = new Date(1559652150018L);
        log.info(String.valueOf(date1.getTime()));

        Date today = new Date();
        SimpleDateFormat toDaysSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String todayString = toDaysSDF.format(today);
        log.info(todayString);

        Long currentTimestamps=System.currentTimeMillis();
        Long oneDayTimestamps= Long.valueOf(60*60*24*1000);
        Long todayZeroPointTimestamp = currentTimestamps-(currentTimestamps+60*60*8*1000)%oneDayTimestamps;
        Date todayZeroPoint = new Date(todayZeroPointTimestamp);
        String todayZeroPointString = toDaysSDF.format(todayZeroPoint);
        log.info(todayZeroPointString);

        Long oneMonthTimestamps = Long.valueOf(30*24*60*60*1000);
        Long theMonthZeroPointTimestamp = currentTimestamps-(currentTimestamps+60*60*8*1000)%oneMonthTimestamps;
        Date theMonthZeroPoint = new Date(theMonthZeroPointTimestamp);
        String theMonthZeroPointString = toDaysSDF.format(theMonthZeroPoint);
        log.info(theMonthZeroPointString);
    }

    @Test
    public void test3() {
        Calendar calendar = Calendar.getInstance();// 获取当前日期
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Long time = calendar.getTimeInMillis();
        SimpleDateFormat toDaysSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String todayString = toDaysSDF.format(time);
        log.info(todayString);
    }

    @Test
    public void test4() {
        Calendar calendar = Calendar.getInstance();// 获取当前日期
        calendar.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Long time = calendar.getTimeInMillis();
        SimpleDateFormat toDaysSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String todayString = toDaysSDF.format(time);
        log.info(todayString);
    }

    @Test
    public void test5() {
        Calendar calendar = Calendar.getInstance();// 获取当前日期
        calendar.add(Calendar.DAY_OF_MONTH,-7);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Long time = calendar.getTimeInMillis();
        SimpleDateFormat toDaysSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String todayString = toDaysSDF.format(time);
        log.info(todayString);
    }

    @Test
    public void test6(){
        List<SupervisorTemperature> list = temperatureService.getAMonthAgoTemp();
        log.info(String.valueOf(list.get(3).getValue()));
        log.info(String.valueOf(list.get(4).getValue()));
    }

    @Test
    public void test7(){
        Calendar calendar = Calendar.getInstance();// 获取当前日期
        Date date =  new Date();
        long dateStamp = date.getTime();
        calendar.setTimeInMillis(Long.valueOf("1563191251027"));
        calendar.add(Calendar.HOUR_OF_DAY, -1);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date1;
        String str1;
        String str2;
        try {
            //方法一
            Long datestamp = 1563260400033L;
            date1 = new Date(datestamp);
            str1 = sdf.format(date1);
            System.out.println(str1);

            //方法二
            str2 = sdf.format(calendar.getTime());
            System.out.println(str2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
