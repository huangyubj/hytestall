package com.hy.demo.mybatis.mapper;

import com.hy.demo.mybatis.entity.CscTask;

public interface CscTaskMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table csc_task
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long taskId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table csc_task
     *
     * @mbggenerated
     */
    int insert(CscTask record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table csc_task
     *
     * @mbggenerated
     */
    int insertSelective(CscTask record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table csc_task
     *
     * @mbggenerated
     */
    CscTask selectByPrimaryKey(Long taskId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table csc_task
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(CscTask record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table csc_task
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(CscTask record);
}