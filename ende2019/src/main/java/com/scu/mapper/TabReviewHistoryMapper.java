package com.scu.mapper;

import com.scu.pojo.TabReviewHistory;
import com.scu.pojo.TabReviewHistoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TabReviewHistoryMapper {
    long countByExample(TabReviewHistoryExample example);

    int deleteByExample(TabReviewHistoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TabReviewHistory record);

    int insertSelective(TabReviewHistory record);

    List<TabReviewHistory> selectByExample(TabReviewHistoryExample example);

    TabReviewHistory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TabReviewHistory record, @Param("example") TabReviewHistoryExample example);

    int updateByExample(@Param("record") TabReviewHistory record, @Param("example") TabReviewHistoryExample example);

    int updateByPrimaryKeySelective(TabReviewHistory record);

    int updateByPrimaryKey(TabReviewHistory record);
}