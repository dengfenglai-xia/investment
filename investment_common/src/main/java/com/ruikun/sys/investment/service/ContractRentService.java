package com.ruikun.sys.investment.service;

import com.ruikun.sys.investment.entity.ContractRent;

import java.util.List;

/**
 * @Description: 合同_周期费用表相关操作
 * @author: xiachunyu
 * @date: 2019-06-27
 */
public interface ContractRentService {
	 	
	/**
	 * @Description: 查询合同_周期费用表信息列表
	 * @author: xiachunyu
	 * @date: 2019-06-27
	 */
	public List<ContractRent> queryContractRentList(ContractRent contractRent);
	
	/**
	 * @Description: 通过主键查询合同_周期费用表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-27
	 */
	public ContractRent queryContractRentDetailByPrimarykey(String contractCode);
		
	/**
	 * @Description: 查询合同_周期费用表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-27
	 */
	public ContractRent queryContractRentDetail(ContractRent contractRent);
	
	/**
	 * @Description: 新增合同_周期费用表信息
	 * @author: xiachunyu
	 * @date: 2019-06-27
	 */
	public void insertContractRent(ContractRent contractRent) ;
	
	/**
	 * @Description: 修改合同_周期费用表信息
	 * @author: xiachunyu
	 * @date: 2019-06-27
	 */
	public void updateContractRent(ContractRent contractRent) ;
	
	/**
	 * @Description: 删除合同_周期费用表信息
	 * @author: xiachunyu
	 * @date: 2019-06-27
	 */
	public void deleteContractRent(ContractRent contractRent) ;
	
}
