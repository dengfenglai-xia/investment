package com.ruikun.sys.investment.service;

import com.ruikun.sys.investment.entity.Customer;

import java.util.List;

/**
 * @Description: 客户表相关操作
 * @author: xiachunyu
 * @date: 2019-06-26
 */
public interface CustomerService {
	 	
	/**
	 * @Description: 查询客户表信息列表
	 * @author: xiachunyu
	 * @date: 2019-06-26
	 */
	public List<Customer> queryCustomerList(Customer customer);

	/**
	 * @Description: 查询客户表基础信息
	 * @author: xiachunyu
	 * @date: 2019-06-26
	 */
	public List<Customer> queryCustomerBaseList(Customer customer);

	/**
	 * @Description: 通过主键查询客户表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-26
	 */
	public Customer queryCustomerDetailByPrimarykey(Integer customerId);
		
	/**
	 * @Description: 查询客户表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-26
	 */
	public Customer queryCustomerDetail(Customer customer);
	
	/**
	 * @Description: 新增客户表信息
	 * @author: xiachunyu
	 * @date: 2019-06-26
	 */
	public void insertCustomer(Customer customer) ;
	
	/**
	 * @Description: 修改客户表信息
	 * @author: xiachunyu
	 * @date: 2019-06-26
	 */
	public void updateCustomer(Customer customer) ;
	
	/**
	 * @Description: 删除客户表信息
	 * @author: xiachunyu
	 * @date: 2019-06-26
	 */
	public void deleteCustomerByPrimarykey(Integer customerId) ;
	
}
