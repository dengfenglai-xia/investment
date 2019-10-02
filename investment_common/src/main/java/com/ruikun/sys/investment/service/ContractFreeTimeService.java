package com.ruikun.sys.investment.service;

import java.util.List;
import com.ruikun.sys.investment.entity.ContractFreeTime;

/**
 * @Description: 合同免租期表相关操作
 * @author: xiachunyu
 * @date: 2019-06-10
 */
public interface ContractFreeTimeService {
	 	
	/**
	 * @Description: 查询合同免租期表信息列表
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	public List<ContractFreeTime> queryContractFreeTimeList(ContractFreeTime contractFreeTime);
	
	/**
	 * @Description: 通过主键查询合同免租期表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	public ContractFreeTime queryContractFreeTimeDetailByPrimarykey(Integer id);
		
	/**
	 * @Description: 查询合同免租期表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	public ContractFreeTime queryContractFreeTimeDetail(ContractFreeTime contractFreeTime);
	
	/**
	 * @Description: 新增合同免租期表信息
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	public void insertContractFreeTime(ContractFreeTime contractFreeTime) ;
	
	/**
	 * @Description: 修改合同免租期表信息
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	public void updateContractFreeTime(ContractFreeTime contractFreeTime) ;
	
	/**
	 * @Description: 删除合同免租期表信息
	 * @author: xiachunyu
	 * @date: 2019-06-10
	 */
	public void deleteContractFreeTimeByPrimarykey(Integer id) ;
	
}
