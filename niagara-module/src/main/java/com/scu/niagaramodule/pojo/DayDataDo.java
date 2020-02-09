package com.scu.niagaramodule.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DayDataDo {



    private List<Map<String,String>> dayList = new ArrayList<>();//返回数据


    public List<Map<String, String>> getDayList() {
        return dayList;
    }

    public void setDayList(List<Map<String, String>> dayList) {
        this.dayList = dayList;
    }

    public static DayDataDo getIntance()
    {
        DayDataDo result = new DayDataDo();
        return result;
    }

    public DayDataDo addData(double value,Long timeStamp)
    {
        Map<String,String> map = new HashMap<>();

        map.put("value",String.valueOf(value));
        map.put("timestamp",String.valueOf(timeStamp));
        this.getDayList().add(map);
        return this;
    }
}
