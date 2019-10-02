package com.ruikun.sys.investment.service.impl;

import java.util.List;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruikun.sys.investment.constants.Constants;
import com.ruikun.sys.investment.utils.CacheUtils;
import com.ruikun.sys.investment.mapper.ContractCarplaceMapper;	
import com.ruikun.sys.investment.entity.ContractCarplace;
import com.ruikun.sys.investment.service.ContractCarplaceService;
 @Service
public class ContractCarplaceServiceImpl implements ContractCarplaceService {
	
	@Autowired
	private ContractCarplaceMapper contractCarplaceMapper;
	
	/**
	 * @Description: 查询合同_车位表信息
	 * @author: xiachunyu
	 * @date: 2019-06-28
	 */
	public List<ContractCarplace> queryContractCarplaceList(ContractCarplace contractCarplace){
		return contractCarplaceMapper.queryContractCarplaceList(contractCarplace);
	}
	
	/**
	 * @Description: 通过主键查询合同_车位表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-28
	 */
	public ContractCarplace queryContractCarplaceDetailByPrimarykey(Long id){
		return contractCarplaceMapper.queryContractCarplaceDetailByPrimarykey(id);
	}
	
	/**
	 * @Description: 查询合同_车位表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-28
	 */
	public ContractCarplace queryContractCarplaceDetail(ContractCarplace contractCarplace){
		return contractCarplaceMapper.queryContractCarplaceDetail(contractCarplace);
	}
	
	/**
	 * @Description: 新增合同_车位表信息
	 * @author: xiachunyu
	 * @date: 2019-06-28
	 */
	public void insertContractCarplace(ContractCarplace contractCarplace) {
		Date date = new Date(); // 当前时间
		Integer userId = CacheUtils.getUser().getUserId();
		contractCarplace.setCreateUserId(userId); // 创建者ID
		contractCarplace.setUpdateUserId(userId); // 更新者ID
		contractCarplace.setCreateTime(date); // 创建时间
		contractCarplace.setUpdateTime(date); // 更新时间
		contractCarplaceMapper.insertContractCarplace(contractCarplace);
	}
	
	/**
	 * @Description: 修改合同_车位表信息
	 * @author: xiachunyu
	 * @date: 2019-06-28
	 */
	public void updateContractCarplace(ContractCarplace contractCarplace) {
		Integer userId = CacheUtils.getUser().getUserId();
		contractCarplace.setUpdateUserId(userId); //更新者ID
		contractCarplace.setUpdateTime(new Date()); //更新时间
		contractCarplaceMapper.updateContractCarplace(contractCarplace);
	}
	
	/**
	 * @Description: 删除合同_车位表信息
	 * @author: xiachunyu
	 * @date: 2019-06-28
	 */
	public void deleteContractCarplace(ContractCarplace contractCarplace) {
		contractCarplaceMapper.deleteContractCarplace(contractCarplace);
	}
	
}
