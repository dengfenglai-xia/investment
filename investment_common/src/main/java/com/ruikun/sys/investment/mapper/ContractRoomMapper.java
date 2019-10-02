package com.ruikun.sys.investment.mapper;

import com.ruikun.sys.investment.entity.Contract;
import com.ruikun.sys.investment.entity.ContractRoom;

import java.util.List;

/**
 * @Description: 合同_房间表相关操作
 * @author: xiachunyu
 * @date: 2019-06-04
 */
public interface ContractRoomMapper {
	 	
	/**
	 * @Description: 查询合同_房间表信息列表
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public List<ContractRoom> queryContractRoomList(ContractRoom contractRoom);

	public List<Contract> queryContractList(Integer roomId);

	/**
	 * @Description: 通过主键查询合同_房间表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public ContractRoom queryContractRoomDetailByPrimarykey(Integer id);
		
	/**
	 * @Description: 查询合同_房间表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public ContractRoom queryContractRoomDetail(ContractRoom contractRoom);
	
	/**
	 * @Description: 新增合同_房间表信息
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public void insertContractRoom(ContractRoom contractRoom) ;
	
	/**
	 * @Description: 修改合同_房间表信息
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public void updateContractRoom(ContractRoom contractRoom) ;
	
	/**
	 * @Description: 删除合同_房间表信息
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public void deleteContractRoomByPrimarykey(Integer id) ;

	/**
	 * @Description: 根据合同编号删除合同_房间表信息
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public void deleteContractRoom(ContractRoom contractRoom) ;

}
