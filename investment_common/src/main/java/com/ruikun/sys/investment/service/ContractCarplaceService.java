package com.ruikun.sys.investment.service;

import java.util.List;
import com.ruikun.sys.investment.entity.ContractCarplace;

/**
 * @Description: 合同_车位表相关操作
 * @author: xiachunyu
 * @date: 2019-06-28
 */
public interface ContractCarplaceService {
	 	
	/**
	 * @Description: 查询合同_车位表信息列表
	 * @author: xiachunyu
	 * @date: 2019-06-28
	 */
	public List<ContractCarplace> queryContractCarplaceList(ContractCarplace contractCarplace);
	
	/**
	 * @Description: 通过主键查询合同_车位表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-28
	 */
	public ContractCarplace queryContractCarplaceDetailByPrimarykey(Long id);
		
	/**
	 * @Description: 查询合同_车位表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-28
	 */
	public ContractCarplace queryContractCarplaceDetail(ContractCarplace contractCarplace);
	
	/**
	 * @Description: 新增合同_车位表信息
	 * @author: xiachunyu
	 * @date: 2019-06-28
	 */
	public void insertContractCarplace(ContractCarplace contractCarplace) ;
	
	/**
	 * @Description: 修改合同_车位表信息
	 * @author: xiachunyu
	 * @date: 2019-06-28
	 */
	public void updateContractCarplace(ContractCarplace contractCarplace) ;
	
	/**
	 * @Description: 删除合同_车位表信息
	 * @author: xiachunyu
	 * @date: 2019-06-28
	 */
	public void deleteContractCarplace(ContractCarplace contractCarplace) ;
	
}
