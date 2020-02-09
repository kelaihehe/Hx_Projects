package com.scu.mapper;

import com.scu.pojo.TabConferenceOrganizer;
import com.scu.pojo.TabConferenceOrganizerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TabConferenceOrganizerMapper {
    long countByExample(TabConferenceOrganizerExample example);

    int deleteByExample(TabConferenceOrganizerExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TabConferenceOrganizer record);

    int insertSelective(TabConferenceOrganizer record);

    List<TabConferenceOrganizer> selectByExample(TabConferenceOrganizerExample example);

    TabConferenceOrganizer selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TabConferenceOrganizer record, @Param("example") TabConferenceOrganizerExample example);

    int updateByExample(@Param("record") TabConferenceOrganizer record, @Param("example") TabConferenceOrganizerExample example);

    int updateByPrimaryKeySelective(TabConferenceOrganizer record);

    int updateByPrimaryKey(TabConferenceOrganizer record);
}