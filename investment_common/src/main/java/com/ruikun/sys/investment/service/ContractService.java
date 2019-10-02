package com.ruikun.sys.investment.service;

import com.ruikun.sys.investment.entity.Contract;
import com.ruikun.sys.investment.entity.ContractRoom;
import com.ruikun.sys.investment.entity.ContractSummaryInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Description: 合同表相关操作
 * @author: xiachunyu
 * @date: 2019-06-04
 */
public interface ContractService {
	 	
	/**
	 * @Description: 查询合同表信息列表
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public List<Contract> queryContractList(Contract contract);

	/**
	 * @Description: 查询合同数量
	 * @author: xiachunyu
	 * @date: 2019-06-04
	*/
	public int queryContractCount(Contract contract);

	/**
	 * @Description: 查询合同房源数量
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public int queryContractRoomCount(Contract contract);

	public List<ContractRoom> queryContractRoomInfoList(Contract contract);

	/**
	 * @Description: 查询合同基础信息列表
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public List<Contract> queryHistoryContractList(Contract contract);

	/**
	 * @Description: 查询合同表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public Contract queryContractDetail(Contract contract);

	/**
	 * @Description: 验证编号是否存在
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public int queryCheckContractCode(String contractCode);

	/**
	 * @Description: 导入合同模板
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public Map importContractInfo(MultipartFile file) throws Exception;

	/**
	 * @Description: 新增合同表信息
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public void insertContract(Contract contract) ;
	
	/**
	 * @Description: 修改合同表信息
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public void updateContract(Contract contract) throws Exception;

	public void updateContractBaseInfo(Contract contract);

	public void updateAgreement(Contract contract);

	/**
	 * @Description: 删除合同表信息
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public void deleteContract(Contract contract) ;

	/**
	 * @Description: 生成合同账单
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public void generateReceipt(Contract contract);

	/**
	 * 客单汇总
	 */
	public List<ContractSummaryInfo> queryContractCustomerBills(ContractSummaryInfo contractSummaryInfo);

	public Map generateContractDetails(Contract contract) throws Exception;

}
