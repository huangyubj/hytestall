package com.hy.dao;

import com.hy.entity.SalesOrder;

public interface SalesOrderMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sales_order
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sales_order
     *
     * @mbggenerated
     */
    int insert(SalesOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sales_order
     *
     * @mbggenerated
     */
    int insertSelective(SalesOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sales_order
     *
     * @mbggenerated
     */
    SalesOrder selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sales_order
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(SalesOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sales_order
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(SalesOrder record);
}