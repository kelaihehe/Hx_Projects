package com.scu.niagaramodule.mapper;

import com.scu.niagaramodule.pojo.SupervisorHumidity;
import com.scu.niagaramodule.pojo.SupervisorHumidityExample;
import com.scu.niagaramodule.pojo.SupervisorTemperature;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SupervisorHumidityMapper {
    long countByExample(SupervisorHumidityExample example);

    int deleteByExample(SupervisorHumidityExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SupervisorHumidity record);

    int insertSelective(SupervisorHumidity record);

    List<SupervisorHumidity> selectByExample(SupervisorHumidityExample example);

    SupervisorHumidity selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SupervisorHumidity record, @Param("example") SupervisorHumidityExample example);

    int updateByExample(@Param("record") SupervisorHumidity record, @Param("example") SupervisorHumidityExample example);

    int updateByPrimaryKeySelective(SupervisorHumidity record);

    int updateByPrimaryKey(SupervisorHumidity record);

    List<SupervisorHumidity> selectNByDesc(Integer num);  //查询前num个id大的记录
}