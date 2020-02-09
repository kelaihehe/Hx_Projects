package com.scu.niagaramodule.mapper;

import com.scu.niagaramodule.pojo.SupervisorVoltage;
import com.scu.niagaramodule.pojo.SupervisorVoltageExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SupervisorVoltageMapper {
    long countByExample(SupervisorVoltageExample example);

    int deleteByExample(SupervisorVoltageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SupervisorVoltage record);

    int insertSelective(SupervisorVoltage record);

    List<SupervisorVoltage> selectByExample(SupervisorVoltageExample example);

    SupervisorVoltage selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SupervisorVoltage record, @Param("example") SupervisorVoltageExample example);

    int updateByExample(@Param("record") SupervisorVoltage record, @Param("example") SupervisorVoltageExample example);

    int updateByPrimaryKeySelective(SupervisorVoltage record);

    int updateByPrimaryKey(SupervisorVoltage record);

    List<SupervisorVoltage> selectNByDesc(Integer num);  //查询前num个id大的记录
}