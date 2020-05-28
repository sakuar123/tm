package com.sakura.tm.dao.mapper;

import com.sakura.tm.common.entity.SysBank;
import com.sakura.tm.common.entity.example.SysBankExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysBankMapper {
    long countByExample(SysBankExample example);

    int deleteByExample(SysBankExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysBank record);

    int insertSelective(SysBank record);

    List<SysBank> selectByExample(SysBankExample example);

    SysBank selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysBank record, @Param("example") SysBankExample example);

    int updateByExample(@Param("record") SysBank record, @Param("example") SysBankExample example);

    int updateByPrimaryKeySelective(SysBank record);

    int updateByPrimaryKey(SysBank record);
}