package com.scu.mapper;

import com.scu.pojo.TabRole;
import com.scu.pojo.TabRoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TabRoleMapper {
    long countByExample(TabRoleExample example);

    int deleteByExample(TabRoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TabRole record);

    int insertSelective(TabRole record);

    List<TabRole> selectByExample(TabRoleExample example);

    TabRole selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TabRole record, @Param("example") TabRoleExample example);

    int updateByExample(@Param("record") TabRole record, @Param("example") TabRoleExample example);

    int updateByPrimaryKeySelective(TabRole record);

    int updateByPrimaryKey(TabRole record);
}