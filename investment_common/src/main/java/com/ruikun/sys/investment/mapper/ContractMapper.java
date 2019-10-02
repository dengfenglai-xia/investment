package com.ruikun.sys.investment.mapper;

import com.ruikun.sys.investment.entity.Contract;
import com.ruikun.sys.investment.entity.ContractRoom;
import com.ruikun.sys.investment.entity.ContractSummaryInfo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Description: 合同表相关操作
 * @author: xiachunyu
 * @date: 2019-06-04
 */
public interface ContractMapper {

	/**
	 * @Description: 查询合同基础信息列表
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public List<Contract> queryHistoryContractList(Contract contract);

	public List<ContractRoom> queryContractRoomInfoList(Contract contract);

	/**
	 * @Description: 查询合同房源数量
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public int queryContractRoomCount(Contract contract);

	/**
	 * @Description: 查询合同表信息列表
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public List<Contract> queryContractList(Contract contract);

	/**
	 * @Description: 查询历史合同列表
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public List<Contract> queryContractHistoryList(String sameCode);

	/**
	 * @Description: 通过主键查询合同表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public Contract queryContractDetailByPrimarykey(String contractCode);


	public Contract queryContractDetailInfo(Contract contract);

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
	public void updateContract(Contract contract) ;

	/**
	 * @Description: 修改合同表信息
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public void updateContractState(Contract contract) ;

	public void updateContractDays() ;

	public void updateExpireState(Contract contract) ;

	/**
	 * @Description: 删除合同表信息
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public void deleteContract(Contract contract) ;

	/**
	 * @Description: 查询合同数量
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public int queryContractCount(Contract contract);

	/**
	 * @Description: 查询客户数量
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public int queryCustomerCount(Contract contract);

	/**
	 * @Description: 查询在租房源日租金总额
	 * @author: xiachunyu
	 * @date: 2019-06-27
	 */
	public BigDecimal querySumDayPrice(Contract contract);

	/**
	 * @Description: 查询在租房总平米数
	 * @author: xiachunyu
	 * @date: 2019-06-27
	 */
	public Double querySumArea(Contract contract);

	public List<ContractRoom> queryContractRoomList(Contract contract);

	public List<ContractSummaryInfo> queryContractSummaryInfo(ContractSummaryInfo contractSummaryInfo);
}
