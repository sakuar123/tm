package com.sakura.tm.dao.mapper;

import com.sakura.tm.common.entity.SysBank;
import com.sakura.tm.common.entity.SysBankExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysBankMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_bank
     *
     * @mbggenerated Wed May 27 11:20:51 GMT+08:00 2020
     */
    int countByExample(SysBankExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_bank
     *
     * @mbggenerated Wed May 27 11:20:51 GMT+08:00 2020
     */
    int deleteByExample(SysBankExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_bank
     *
     * @mbggenerated Wed May 27 11:20:51 GMT+08:00 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_bank
     *
     * @mbggenerated Wed May 27 11:20:51 GMT+08:00 2020
     */
    int insert(SysBank record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_bank
     *
     * @mbggenerated Wed May 27 11:20:51 GMT+08:00 2020
     */
    int insertSelective(SysBank record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_bank
     *
     * @mbggenerated Wed May 27 11:20:51 GMT+08:00 2020
     */
    List<SysBank> selectByExample(SysBankExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_bank
     *
     * @mbggenerated Wed May 27 11:20:51 GMT+08:00 2020
     */
    SysBank selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_bank
     *
     * @mbggenerated Wed May 27 11:20:51 GMT+08:00 2020
     */
    int updateByExampleSelective(@Param("record") SysBank record, @Param("example") SysBankExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_bank
     *
     * @mbggenerated Wed May 27 11:20:51 GMT+08:00 2020
     */
    int updateByExample(@Param("record") SysBank record, @Param("example") SysBankExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_bank
     *
     * @mbggenerated Wed May 27 11:20:51 GMT+08:00 2020
     */
    int updateByPrimaryKeySelective(SysBank record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_bank
     *
     * @mbggenerated Wed May 27 11:20:51 GMT+08:00 2020
     */
    int updateByPrimaryKey(SysBank record);
}