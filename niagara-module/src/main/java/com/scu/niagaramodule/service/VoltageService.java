package com.scu.niagaramodule.service;

import com.scu.niagaramodule.mapper.SupervisorVoltageMapper;
import com.scu.niagaramodule.pojo.DayDataDo;
import com.scu.niagaramodule.pojo.SupervisorVoltage;
import com.scu.niagaramodule.pojo.SupervisorVoltageExample;
import com.scu.niagaramodule.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class VoltageService {

    @Autowired
    SupervisorVoltageMapper mapper;

    /**
     * 获取数据库中最新的温度值
     * @return
     */
    public SupervisorVoltage getLastestTemp(){
       List<SupervisorVoltage> list = mapper.selectNByDesc(1);
       if (list.isEmpty()) {
           log.info("获取数据库中最新的温度值失败");
           return null;
       }
       else
           return list.get(0);
    }

    /**
     * 获取最近一天的温度变化list
     * @return
     */
    public List<SupervisorVoltage> getLastDayTemp(){
        SupervisorVoltageExample example = new SupervisorVoltageExample();
        SupervisorVoltageExample.Criteria criteria = example.createCriteria();
        criteria.andTimestampGreaterThanOrEqualTo(TimeUtils.getTodayZeroPoint());

        List<SupervisorVoltage> list = mapper.selectByExample(example);
        if (list.isEmpty()) {
            log.info("今天温度无数据");
            return null;
        }else {
            return getAverageValueListInAnHour(list);
        }
    }

    /**
     * 获取最近7天的温度变化list
     * @return
     */
    public List<DayDataDo> getSevenDayAgoTemp(){
        SupervisorVoltageExample example = new SupervisorVoltageExample();
        SupervisorVoltageExample.Criteria criteria = example.createCriteria();
        criteria.andTimestampGreaterThanOrEqualTo(TimeUtils.getNDayAgoZeroPoint(7));

        List<SupervisorVoltage> list = mapper.selectByExample(example);
        if (list.isEmpty()) {
            log.info("最近七天的无温度数据");
            return null;
        }else {
            //每小时数据进行平均化
            return getAverageValueListInSevenDays(list);
        }
    }

    /**
     * 对每小时段进行均化
     * @param list
     * @return
     */
    public  List<SupervisorVoltage> getAverageValueListInAnHour(List<SupervisorVoltage> list){
        Long tempPre = TimeUtils.getHourPre(list.get(0).getTimestamp());  //一个小时段的前端点
        Long tempTail = TimeUtils.getHourTail(list.get(0).getTimestamp());  //一个小时段的后端点
        SupervisorVoltage temp;  //临时变量
        SupervisorVoltage result;
        double totalTemp = 0;//每小时段的总数据和
        int count=0;  //每小时段的记录数
        List<SupervisorVoltage> resultList = new ArrayList<SupervisorVoltage>();
        for(int i = 0 ; i < list.size() ; i++){
            temp = list.get(i);
            if(temp.getTimestamp()>=tempPre && temp.getTimestamp()<=tempTail) {
                totalTemp = totalTemp+temp.getValue();
                count++;
            } else {
                //计算上一个小时时间段的平均值
                totalTemp = totalTemp/count;
                result = new SupervisorVoltage();
                result.setValue(totalTemp);
                result.setTimestamp(tempTail);
                resultList.add(result);
                totalTemp = 0;
                count =0;
                //更新小时的前后端点
                tempPre = TimeUtils.getHourPre(temp.getTimestamp());
                tempTail = TimeUtils.getHourTail(temp.getTimestamp());
                totalTemp = totalTemp+temp.getValue();
                count++;
            }
        }
        result = new SupervisorVoltage();
        totalTemp = totalTemp/count;
        result.setValue(totalTemp);
        result.setTimestamp(tempTail);
        resultList.add(result);

        return resultList;
    }

    /**
     * 对七天内每小时段进行均化,每天数据按list区分
     * @param list
     * @return
     */
    public  List<DayDataDo> getAverageValueListInSevenDays(List<SupervisorVoltage> list){

        Long tempPre = TimeUtils.getHourPre(list.get(0).getTimestamp());  //一个小时段的前端点
        Long tempTail = TimeUtils.getHourTail(list.get(0).getTimestamp());  //一个小时段的后端点
        SupervisorVoltage temp;  //临时变量
        double totalTemp = 0;//每小时段的总数据和
        int count=0;  //每小时段的记录数
        List<DayDataDo> resultList = new ArrayList<DayDataDo>();
        DayDataDo oneDayData = DayDataDo.getIntance();
        int n = 6;
        Map<String,String> day;
        Long DayTail = TimeUtils.getNDayAgoZeroPoint(n);  //每天的晚上12点

        for(int i = 0 ; i < list.size();){
            temp = list.get(i);
            if (temp.getTimestamp()<DayTail){  //如果在当天
                if(temp.getTimestamp()>=tempPre && temp.getTimestamp()<=tempTail) {  //如果在当前一小时内
                    totalTemp = totalTemp+temp.getValue();
                    count++;
                } else {
                    //计算上一个小时时间段的平均值
                    totalTemp = totalTemp/count;
                    oneDayData.addData(totalTemp,tempTail);  //
                    totalTemp = 0;
                    count =0;
                    //更新小时的前后端点
                    tempPre = TimeUtils.getHourPre(temp.getTimestamp());   //更换小时段
                    tempTail = TimeUtils.getHourTail(temp.getTimestamp());
                    totalTemp = totalTemp+temp.getValue();
                    count++;
                }
                i++; //下一个数据
            } else {  //如果不在当天内
                resultList.add(oneDayData);
                DayTail = TimeUtils.getNDayAgoZeroPoint(--n); //下一天
                oneDayData = DayDataDo.getIntance();
            }
        }

        totalTemp = totalTemp/count;
        oneDayData.addData(totalTemp,tempTail);  //
        resultList.add(oneDayData);
        --n;
        for(;n>=0;n--){  //如果最近无数据需要创建空
            oneDayData = DayDataDo.getIntance();
            resultList.add(oneDayData);
        }

        return resultList;
    }

    /**
     * 获取最近一个月的温度变化list
     * @return
     */
    public List<SupervisorVoltage> getAMonthAgoTemp(){
        SupervisorVoltageExample example = new SupervisorVoltageExample();
        SupervisorVoltageExample.Criteria criteria = example.createCriteria();
        criteria.andTimestampGreaterThanOrEqualTo(TimeUtils.getAMonthAgoZeroPoint());

        List<SupervisorVoltage> list = mapper.selectByExample(example);
        if (list.isEmpty()) {
            log.info("最近一个月无温度数据");
            return null;
        }else {
            return list;
        }
    }
}
