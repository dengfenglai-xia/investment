package com.ruikun.sys.investment.service.impl;

import java.util.List;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.utils.CacheUtils;
import com.ruikun.sys.investment.mapper.ContractFreeTimeMapper;	
import com.ruikun.sys.investment.entity.ContractFreeTime;
import com.ruikun.sys.investment.service.ContractFreeTimeService;
 @Service
public class ContractFreeTimeServiceImpl implements ContractFreeTimeService {
	
	@Autowired
	private ContractFreeTimeMapper contractFreeTimeMapper;
	
	/**
	 * @Description: 查询合同免租期表信息
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	public List<ContractFreeTime> queryContractFreeTimeList(ContractFreeTime contractFreeTime){
		return contractFreeTimeMapper.queryContractFreeTimeList(contractFreeTime);
	}
	
	/**
	 * @Description: 通过主键查询合同免租期表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	public ContractFreeTime queryContractFreeTimeDetailByPrimarykey(Integer id){
		return contractFreeTimeMapper.queryContractFreeTimeDetailByPrimarykey(id);
	}
	
	/**
	 * @Description: 查询合同免租期表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	public ContractFreeTime queryContractFreeTimeDetail(ContractFreeTime contractFreeTime){
		return contractFreeTimeMapper.queryContractFreeTimeDetail(contractFreeTime);
	}
	
	/**
	 * @Description: 新增合同免租期表信息
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	public void insertContractFreeTime(ContractFreeTime contractFreeTime) {
		Date date = new Date(); // 当前时间
		Integer userId = CacheUtils.getUser().getUserId();
		contractFreeTime.setCreateUserId(userId); // 创建者ID
		contractFreeTime.setUpdateUserId(userId); // 更新者ID
		contractFreeTime.setCreateTime(date); // 创建时间
		contractFreeTime.setUpdateTime(date); // 更新时间
		contractFreeTimeMapper.insertContractFreeTime(contractFreeTime);
	}
	
	/**
	 * @Description: 修改合同免租期表信息
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	public void updateContractFreeTime(ContractFreeTime contractFreeTime) {
		Integer userId = CacheUtils.getUser().getUserId();
		contractFreeTime.setUpdateUserId(userId); //更新者ID
		contractFreeTime.setUpdateTime(new Date()); //更新时间
		contractFreeTimeMapper.updateContractFreeTime(contractFreeTime);
	}
	
	/**
	 * @Description: 删除合同免租期表信息
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	public void deleteContractFreeTimeByPrimarykey(Integer id) {
		contractFreeTimeMapper.deleteContractFreeTimeByPrimarykey(id);
	}
	
}
