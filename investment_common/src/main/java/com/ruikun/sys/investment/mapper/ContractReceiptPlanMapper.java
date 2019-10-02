package com.ruikun.sys.investment.mapper;

import com.ruikun.sys.investment.entity.ContractReceiptPlan;
import com.ruikun.sys.investment.entity.ContractReceiptPlanSum;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description: 合同收款计划表相关操作
 * @author: xiachunyu
 * @date: 2019-06-10
 */
public interface ContractReceiptPlanMapper {

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
	 * @Description: 查询合同收款计划表信息列表
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	public List<ContractReceiptPlan> queryReceiptPlanInfoList(ContractReceiptPlan contractReceiptPlan);

	/**
	 * @Description: 查询需要生成账单的收款计划列表
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	public List<ContractReceiptPlan> queryExpectantReceiptPlanList(ContractReceiptPlan contractReceiptPlan);

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
	 * @Description: 批量新增合同收款计划
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	public void insertContractReceiptPlanBatch(@Param("list") List<ContractReceiptPlan> planList);

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
	public void updateContractReceiptPlanBatch(@Param("list") List<ContractReceiptPlan> planList) ;

	/**
	 * @Description: 根据合同编号删除合同收款计划
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	public void deleteContractReceiptPlan(ContractReceiptPlan contractReceiptPlan) ;


}
