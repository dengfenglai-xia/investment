package com.ruikun.sys.investment.mapper;

import com.ruikun.sys.investment.entity.CustomerTemporary;

import java.util.List;

/**
 * 临时客户
 **/
public interface CustomerTemporaryMapper {

    /**
     * @Description: 查询客户表基础信息
     * @author: xiachunyu
     * @date: 2019-06-26
     */
    public List<CustomerTemporary> queryCustomerBaseList(CustomerTemporary customerTemporary);

    /**
     * 添加临时客户
     **/
    void insertCustomer(CustomerTemporary customerTemporary);

    /**
     * 查询客户详情
     **/
    CustomerTemporary queryCustomerDetail(Integer temporaryId);

    /**
     * 查询临时客户组数
     **/
    int queryCustomerTemporaryNum(CustomerTemporary customerTemporary);

    /**
     * 修改临时客户
     **/
    void updateCustomerTemporary(CustomerTemporary customerTemporary);

    /**
     * 删除临时客户
     **/
    void deleteCustomerTemporary(Integer temporaryId);
}
