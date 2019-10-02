package com.ruikun.sys.investment.service.impl;

import com.ruikun.sys.investment.entity.ContractIncreaseRate;
import com.ruikun.sys.investment.mapper.ContractIncreaseRateMapper;
import com.ruikun.sys.investment.service.ContractIncreaseRateService;
import com.ruikun.sys.investment.utils.CacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
 @Service
public class ContractIncreaseRateServiceImpl implements ContractIncreaseRateService {
	
	@Autowired
	private ContractIncreaseRateMapper contractIncreaseRateMapper;
	
	/**
	 * @Description: 查询合同递增率表信息
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	public List<ContractIncreaseRate> queryContractIncreaseRateList(ContractIncreaseRate contractIncreaseRate){
		return contractIncreaseRateMapper.queryContractIncreaseRateList(contractIncreaseRate);
	}
	
	/**
	 * @Description: 通过主键查询合同递增率表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	public ContractIncreaseRate queryContractIncreaseRateDetailByPrimarykey(Integer id){
		return contractIncreaseRateMapper.queryContractIncreaseRateDetailByPrimarykey(id);
	}
	
	/**
	 * @Description: 查询合同递增率表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	public ContractIncreaseRate queryContractIncreaseRateDetail(ContractIncreaseRate contractIncreaseRate){
		return contractIncreaseRateMapper.queryContractIncreaseRateDetail(contractIncreaseRate);
	}
	
	/**
	 * @Description: 新增合同递增率表信息
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	public void insertContractIncreaseRate(ContractIncreaseRate contractIncreaseRate) {
		Date date = new Date(); // 当前时间
		Integer userId = CacheUtils.getUser().getUserId();
		contractIncreaseRate.setCreateUserId(userId); // 创建者ID
		contractIncreaseRate.setUpdateUserId(userId); // 更新者ID
		contractIncreaseRate.setCreateTime(date); // 创建时间
		contractIncreaseRate.setUpdateTime(date); // 更新时间
		contractIncreaseRateMapper.insertContractIncreaseRate(contractIncreaseRate);
	}
	
	/**
	 * @Description: 修改合同递增率表信息
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	public void updateContractIncreaseRate(ContractIncreaseRate contractIncreaseRate) {
		Integer userId = CacheUtils.getUser().getUserId();
		contractIncreaseRate.setUpdateUserId(userId); //更新者ID
		contractIncreaseRate.setUpdateTime(new Date()); //更新时间
		contractIncreaseRateMapper.updateContractIncreaseRate(contractIncreaseRate);
	}
	
	/**
	 * @Description: 删除合同递增率表信息
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	public void deleteContractIncreaseRateByPrimarykey(Integer id) {
		contractIncreaseRateMapper.deleteContractIncreaseRateByPrimarykey(id);
	}
	
}
