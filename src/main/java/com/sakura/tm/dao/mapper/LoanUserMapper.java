package com.sakura.tm.dao.mapper;

import com.sakura.tm.common.entity.LoanUser;
import com.sakura.tm.common.entity.LoanUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LoanUserMapper {
    long countByExample(LoanUserExample example);

    int deleteByExample(LoanUserExample example);

    int deleteByPrimaryKey(Integer userId);

    int insert(LoanUser record);

    int insertSelective(LoanUser record);

    List<LoanUser> selectByExample(LoanUserExample example);

    LoanUser selectByPrimaryKey(Integer userId);

    int updateByExampleSelective(@Param("record") LoanUser record, @Param("example") LoanUserExample example);

    int updateByExample(@Param("record") LoanUser record, @Param("example") LoanUserExample example);

    int updateByPrimaryKeySelective(LoanUser record);

    int updateByPrimaryKey(LoanUser record);
}