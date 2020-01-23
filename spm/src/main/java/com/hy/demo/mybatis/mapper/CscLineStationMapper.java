package com.hy.demo.mybatis.mapper;

import com.hy.demo.mybatis.entity.CscLineStation;

import java.util.List;

public interface CscLineStationMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table csc_line_station
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long lineStationId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table csc_line_station
     *
     * @mbggenerated
     */
    int insert(CscLineStation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table csc_line_station
     *
     * @mbggenerated
     */
    int insertSelective(CscLineStation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table csc_line_station
     *
     * @mbggenerated
     */
    CscLineStation selectByPrimaryKey(Long lineStationId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table csc_line_station
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(CscLineStation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table csc_line_station
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(CscLineStation record);
    List<CscLineStation> selectByLineId(Long taskId);
}