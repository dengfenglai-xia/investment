package com.ruikun.sys.investment.mapper;

import com.ruikun.sys.investment.entity.ContractIncreaseRate;

import java.util.List;

/**
 * @Description: 合同递增率表相关操作
 * @author: xiachunyu
 * @date: 2019-06-10
 */
public interface ContractIncreaseRateMapper {
	 	
	/**
	 * @Description: 查询合同递增率表信息列表
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	public List<ContractIncreaseRate> queryContractIncreaseRateList(ContractIncreaseRate contractIncreaseRate);
	
	/**
	 * @Description: 通过主键查询合同递增率表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	public ContractIncreaseRate queryContractIncreaseRateDetailByPrimarykey(Integer id);
		
	/**
	 * @Description: 查询合同递增率表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	public ContractIncreaseRate queryContractIncreaseRateDetail(ContractIncreaseRate contractIncreaseRate);
	
	/**
	 * @Description: 新增合同递增率表信息
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	public void insertContractIncreaseRate(ContractIncreaseRate contractIncreaseRate) ;
	
	/**
	 * @Description: 修改合同递增率表信息
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	public void updateContractIncreaseRate(ContractIncreaseRate contractIncreaseRate) ;
	
	/**
	 * @Description: 删除合同递增率表信息
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	public void deleteContractIncreaseRateByPrimarykey(Integer id) ;

	/**
	 * @Description: 删除合同递增率表信息
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	public void deleteContractIncreaseRate(ContractIncreaseRate contractIncreaseRate);

}
