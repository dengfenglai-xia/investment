package com.ruikun.sys.investment.service.impl;

import com.ruikun.sys.investment.entity.ContractRent;
import com.ruikun.sys.investment.mapper.ContractRentMapper;
import com.ruikun.sys.investment.service.ContractRentService;
import com.ruikun.sys.investment.utils.CacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
 @Service
public class ContractRentServiceImpl implements ContractRentService {
	
	@Autowired
	private ContractRentMapper contractRentMapper;
	
	/**
	 * @Description: 查询合同_周期费用表信息
	 * @author: xiachunyu
	 * @date: 2019-06-27
	 */
	public List<ContractRent> queryContractRentList(ContractRent contractRent){
		return contractRentMapper.queryContractRentList(contractRent);
	}
	
	/**
	 * @Description: 通过主键查询合同_周期费用表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-27
	 */
	public ContractRent queryContractRentDetailByPrimarykey(String contractCode){
		return contractRentMapper.queryContractRentDetailByPrimarykey(contractCode);
	}
	
	/**
	 * @Description: 查询合同_周期费用表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-27
	 */
	public ContractRent queryContractRentDetail(ContractRent contractRent){
		return contractRentMapper.queryContractRentDetail(contractRent);
	}
	
	/**
	 * @Description: 新增合同_周期费用表信息
	 * @author: xiachunyu
	 * @date: 2019-06-27
	 */
	public void insertContractRent(ContractRent contractRent) {
		Date date = new Date(); // 当前时间
		Integer userId = CacheUtils.getUser().getUserId();
		contractRent.setCreateUserId(userId); // 创建者ID
		contractRent.setUpdateUserId(userId); // 更新者ID
		contractRent.setCreateTime(date); // 创建时间
		contractRent.setUpdateTime(date); // 更新时间
		contractRentMapper.insertContractRent(contractRent);
	}
	
	/**
	 * @Description: 修改合同_周期费用表信息
	 * @author: xiachunyu
	 * @date: 2019-06-27
	 */
	public void updateContractRent(ContractRent contractRent) {
		Integer userId = CacheUtils.getUser().getUserId();
		contractRent.setUpdateUserId(userId); //更新者ID
		contractRent.setUpdateTime(new Date()); //更新时间
		contractRentMapper.updateContractRent(contractRent);
	}
	
	/**
	 * @Description: 删除合同_周期费用表信息
	 * @author: xiachunyu
	 * @date: 2019-06-27
	 */
	public void deleteContractRent(ContractRent contractRent) {
		contractRentMapper.deleteContractRent(contractRent);
	}
	
}
