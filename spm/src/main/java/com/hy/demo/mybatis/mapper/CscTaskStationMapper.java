package com.hy.demo.mybatis.mapper;

import com.hy.demo.mybatis.entity.CscTaskStation;

public interface CscTaskStationMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table csc_task_station
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long taskStationId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table csc_task_station
     *
     * @mbggenerated
     */
    int insert(CscTaskStation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table csc_task_station
     *
     * @mbggenerated
     */
    int insertSelective(CscTaskStation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table csc_task_station
     *
     * @mbggenerated
     */
    CscTaskStation selectByPrimaryKey(Long taskStationId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table csc_task_station
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(CscTaskStation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table csc_task_station
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(CscTaskStation record);
}