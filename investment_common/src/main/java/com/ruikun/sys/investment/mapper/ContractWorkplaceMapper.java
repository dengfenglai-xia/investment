package com.ruikun.sys.investment.mapper;

import java.util.List;

import com.ruikun.sys.investment.entity.Contract;
import com.ruikun.sys.investment.entity.ContractWorkplace;

/**
 * @Description: 合同-工位相关操作
 * @author: xiachunyu
 * @date: 2019-08-04
 */
public interface ContractWorkplaceMapper {
	 	
	/**
	 * @Description: 查询合同-工位信息列表
	 * @author: xiachunyu
	 * @date: 2019-08-04
	 */
	public List<ContractWorkplace> queryContractWorkplaceList(ContractWorkplace contractWorkplace);

	public int queryContractWorkplaceCount(Contract contract);

	/**
	 * @Description: 通过主键查询合同-工位信息详情
	 * @author: xiachunyu
	 * @date: 2019-08-04
	 */
	public ContractWorkplace queryContractWorkplaceDetailByPrimarykey(Long id);
		
	/**
	 * @Description: 查询合同-工位信息详情
	 * @author: xiachunyu
	 * @date: 2019-08-04
	 */
	public ContractWorkplace queryContractWorkplaceDetail(ContractWorkplace contractWorkplace);
	
	/**
	 * @Description: 新增合同-工位信息
	 * @author: xiachunyu
	 * @date: 2019-08-04
	 */
	public void insertContractWorkplace(ContractWorkplace contractWorkplace) ;
	
	/**
	 * @Description: 修改合同-工位信息
	 * @author: xiachunyu
	 * @date: 2019-08-04
	 */
	public void updateContractWorkplace(ContractWorkplace contractWorkplace) ;
	
	/**
	 * @Description: 删除合同-工位信息
	 * @author: xiachunyu
	 * @date: 2019-08-04
	 */
	public void deleteContractWorkplace(ContractWorkplace contractWorkplace) ;
	
}
