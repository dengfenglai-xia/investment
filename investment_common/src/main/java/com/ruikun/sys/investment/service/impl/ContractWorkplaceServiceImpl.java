package com.ruikun.sys.investment.service.impl;

import java.util.List;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.utils.CacheUtils;
import com.ruikun.sys.investment.mapper.ContractWorkplaceMapper;	
import com.ruikun.sys.investment.entity.ContractWorkplace;
import com.ruikun.sys.investment.service.ContractWorkplaceService;
 @Service
public class ContractWorkplaceServiceImpl implements ContractWorkplaceService {
	
	@Autowired
	private ContractWorkplaceMapper contractWorkplaceMapper;
	
	/**
	 * @Description: 查询合同-工位信息
	 * @author: xiachunyu
	 * @date: 2019-08-04
	 */
	public List<ContractWorkplace> queryContractWorkplaceList(ContractWorkplace contractWorkplace){
		return contractWorkplaceMapper.queryContractWorkplaceList(contractWorkplace);
	}

	 /**
	 * @Description: 通过主键查询合同-工位信息详情
	 * @author: xiachunyu
	 * @date: 2019-08-04
	 */
	public ContractWorkplace queryContractWorkplaceDetailByPrimarykey(Long id){
		return contractWorkplaceMapper.queryContractWorkplaceDetailByPrimarykey(id);
	}
	
	/**
	 * @Description: 查询合同-工位信息详情
	 * @author: xiachunyu
	 * @date: 2019-08-04
	 */
	public ContractWorkplace queryContractWorkplaceDetail(ContractWorkplace contractWorkplace){
		return contractWorkplaceMapper.queryContractWorkplaceDetail(contractWorkplace);
	}
	
	/**
	 * @Description: 新增合同-工位信息
	 * @author: xiachunyu
	 * @date: 2019-08-04
	 */
	public void insertContractWorkplace(ContractWorkplace contractWorkplace) {
		Date date = new Date(); // 当前时间
		Integer userId = CacheUtils.getUser().getUserId();
		contractWorkplace.setCreateUserId(userId); // 创建者ID
		contractWorkplace.setUpdateUserId(userId); // 更新者ID
		contractWorkplace.setCreateTime(date); // 创建时间
		contractWorkplace.setUpdateTime(date); // 更新时间
		contractWorkplaceMapper.insertContractWorkplace(contractWorkplace);
	}
	
	/**
	 * @Description: 修改合同-工位信息
	 * @author: xiachunyu
	 * @date: 2019-08-04
	 */
	public void updateContractWorkplace(ContractWorkplace contractWorkplace) {
		Integer userId = CacheUtils.getUser().getUserId();
		contractWorkplace.setUpdateUserId(userId); //更新者ID
		contractWorkplace.setUpdateTime(new Date()); //更新时间
		contractWorkplaceMapper.updateContractWorkplace(contractWorkplace);
	}
	
	/**
	 * @Description: 删除合同-工位信息
	 * @author: xiachunyu
	 * @date: 2019-08-04
	 */
	public void deleteContractWorkplace(ContractWorkplace contractWorkplace) {
		contractWorkplaceMapper.deleteContractWorkplace(contractWorkplace);
	}
	
}
