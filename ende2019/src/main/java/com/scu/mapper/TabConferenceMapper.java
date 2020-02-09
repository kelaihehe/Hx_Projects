package com.scu.mapper;

import com.scu.pojo.TabConference;
import com.scu.pojo.TabConferenceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TabConferenceMapper {
    long countByExample(TabConferenceExample example);

    int deleteByExample(TabConferenceExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TabConference record);

    int insertSelective(TabConference record);

    List<TabConference> selectByExample(TabConferenceExample example);

    TabConference selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TabConference record, @Param("example") TabConferenceExample example);

    int updateByExample(@Param("record") TabConference record, @Param("example") TabConferenceExample example);

    int updateByPrimaryKeySelective(TabConference record);

    int updateByPrimaryKey(TabConference record);
}