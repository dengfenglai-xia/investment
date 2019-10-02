package com.ruikun.sys.investment.service;

import com.ruikun.sys.investment.entity.ContractReceiptPlan;
import com.ruikun.sys.investment.entity.ContractReceiptPlanSum;

import java.util.List;

/**
 * @Description: 合同收款计划表相关操作
 * @author: xiachunyu
 * @date: 2019-06-10
 */
public interface ContractReceiptPlanService {

	/**
	 * @Description: 查询合同收款计划合计
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	public List<ContractReceiptPlanSum> queryContractReceiptPlanSumList(ContractReceiptPlan contractReceiptPlan);

	/**
	 * @Description: 查询合同收款计划表信息列表
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	public List<ContractReceiptPlan> queryContractReceiptPlanList(ContractReceiptPlan contractReceiptPlan);
	
	/**
	 * @Description: 通过主键查询合同收款计划表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	public ContractReceiptPlan queryContractReceiptPlanDetailByPrimarykey(Long id);
		
	/**
	 * @Description: 查询合同收款计划表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	public ContractReceiptPlan queryContractReceiptPlanDetail(ContractReceiptPlan contractReceiptPlan);
	
	/**
	 * @Description: 新增合同收款计划表信息
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	public void insertContractReceiptPlan(ContractReceiptPlan contractReceiptPlan) ;
	
	/**
	 * @Description: 修改合同收款计划表信息
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	public void updateContractReceiptPlan(ContractReceiptPlan contractReceiptPlan) ;

	/**
	 * @Description: 批量修改合同收款计划表信息
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	public void updateContractReceiptPlanBatch(List<ContractReceiptPlan> planList) throws Exception;

	/**
	 * @Description: 删除合同收款计划表信息
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	public void deleteContractReceiptPlan(String contractCode,Integer version) ;

}
