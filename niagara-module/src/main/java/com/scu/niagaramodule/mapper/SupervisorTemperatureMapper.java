package com.scu.niagaramodule.mapper;

import com.scu.niagaramodule.pojo.SupervisorTemperature;
import com.scu.niagaramodule.pojo.SupervisorTemperatureExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SupervisorTemperatureMapper {
    long countByExample(SupervisorTemperatureExample example);

    int deleteByExample(SupervisorTemperatureExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SupervisorTemperature record);

    int insertSelective(SupervisorTemperature record);

    List<SupervisorTemperature> selectByExample(SupervisorTemperatureExample example);

    SupervisorTemperature selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SupervisorTemperature record, @Param("example") SupervisorTemperatureExample example);

    int updateByExample(@Param("record") SupervisorTemperature record, @Param("example") SupervisorTemperatureExample example);

    int updateByPrimaryKeySelective(SupervisorTemperature record);

    int updateByPrimaryKey(SupervisorTemperature record);

    List<SupervisorTemperature> selectNByDesc(Integer num);  //查询前num个id大的记录
}