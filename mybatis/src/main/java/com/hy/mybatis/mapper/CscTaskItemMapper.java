package com.hy.mybatis.mapper;

import com.hy.mybatis.entity.CscTaskItem;

public interface CscTaskItemMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table csc_task_item
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long taskItemId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table csc_task_item
     *
     * @mbggenerated
     */
    int insert(CscTaskItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table csc_task_item
     *
     * @mbggenerated
     */
    int insertSelective(CscTaskItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table csc_task_item
     *
     * @mbggenerated
     */
    CscTaskItem selectByPrimaryKey(Long taskItemId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table csc_task_item
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(CscTaskItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table csc_task_item
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(CscTaskItem record);
}