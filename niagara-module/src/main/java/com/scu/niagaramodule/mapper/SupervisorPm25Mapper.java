package com.scu.niagaramodule.mapper;

import com.scu.niagaramodule.pojo.SupervisorPm25;
import com.scu.niagaramodule.pojo.SupervisorPm25Example;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SupervisorPm25Mapper {
    long countByExample(SupervisorPm25Example example);

    int deleteByExample(SupervisorPm25Example example);

    int deleteByPrimaryKey(Integer id);

    int insert(SupervisorPm25 record);

    int insertSelective(SupervisorPm25 record);

    List<SupervisorPm25> selectByExample(SupervisorPm25Example example);

    SupervisorPm25 selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SupervisorPm25 record, @Param("example") SupervisorPm25Example example);

    int updateByExample(@Param("record") SupervisorPm25 record, @Param("example") SupervisorPm25Example example);

    int updateByPrimaryKeySelective(SupervisorPm25 record);

    int updateByPrimaryKey(SupervisorPm25 record);

    List<SupervisorPm25> selectNByDesc(Integer num);  //查询前num个id大的记录
}