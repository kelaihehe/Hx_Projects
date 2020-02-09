package com.scu.mapper;

import com.scu.pojo.TabPaymentHistory;
import com.scu.pojo.TabPaymentHistoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TabPaymentHistoryMapper {
    long countByExample(TabPaymentHistoryExample example);

    int deleteByExample(TabPaymentHistoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TabPaymentHistory record);

    int insertSelective(TabPaymentHistory record);

    List<TabPaymentHistory> selectByExample(TabPaymentHistoryExample example);

    TabPaymentHistory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TabPaymentHistory record, @Param("example") TabPaymentHistoryExample example);

    int updateByExample(@Param("record") TabPaymentHistory record, @Param("example") TabPaymentHistoryExample example);

    int updateByPrimaryKeySelective(TabPaymentHistory record);

    int updateByPrimaryKey(TabPaymentHistory record);
}