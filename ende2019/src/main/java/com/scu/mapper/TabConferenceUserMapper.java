package com.scu.mapper;

import com.scu.pojo.TabConferenceUser;
import com.scu.pojo.TabConferenceUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TabConferenceUserMapper {
    long countByExample(TabConferenceUserExample example);

    int deleteByExample(TabConferenceUserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TabConferenceUser record);

    int insertSelective(TabConferenceUser record);

    List<TabConferenceUser> selectByExample(TabConferenceUserExample example);

    TabConferenceUser selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TabConferenceUser record, @Param("example") TabConferenceUserExample example);

    int updateByExample(@Param("record") TabConferenceUser record, @Param("example") TabConferenceUserExample example);

    int updateByPrimaryKeySelective(TabConferenceUser record);

    int updateByPrimaryKey(TabConferenceUser record);
}