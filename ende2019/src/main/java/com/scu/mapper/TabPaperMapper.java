package com.scu.mapper;

import com.scu.pojo.TabPaper;
import com.scu.pojo.TabPaperExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TabPaperMapper {
    long countByExample(TabPaperExample example);

    int deleteByExample(TabPaperExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TabPaper record);

    int insertSelective(TabPaper record);

    List<TabPaper> selectByExample(TabPaperExample example);

    TabPaper selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TabPaper record, @Param("example") TabPaperExample example);

    int updateByExample(@Param("record") TabPaper record, @Param("example") TabPaperExample example);

    int updateByPrimaryKeySelective(TabPaper record);

    int updateByPrimaryKey(TabPaper record);
}