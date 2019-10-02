package com.ruikun.sys.investment.service.impl;

import com.ruikun.sys.investment.entity.Contract;
import com.ruikun.sys.investment.entity.ContractRoom;
import com.ruikun.sys.investment.mapper.ContractRoomMapper;
import com.ruikun.sys.investment.service.ContractRoomService;
import com.ruikun.sys.investment.utils.CacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
 @Service
public class ContractRoomServiceImpl implements ContractRoomService {
	
	@Autowired
	private ContractRoomMapper contractRoomMapper;
	
	/**
	 * @Description: 查询合同_房间表信息
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public List<ContractRoom> queryContractRoomList(ContractRoom contractRoom){
		return contractRoomMapper.queryContractRoomList(contractRoom);
	}


	 public List<Contract> queryContractList(Integer roomId){
		 return contractRoomMapper.queryContractList(roomId);
	 }

	/**
	 * @Description: 通过主键查询合同_房间表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public ContractRoom queryContractRoomDetailByPrimarykey(Integer id){
		return contractRoomMapper.queryContractRoomDetailByPrimarykey(id);
	}
	
	/**
	 * @Description: 查询合同_房间表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public ContractRoom queryContractRoomDetail(ContractRoom contractRoom){
		return contractRoomMapper.queryContractRoomDetail(contractRoom);
	}
	
	/**
	 * @Description: 新增合同_房间表信息
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public void insertContractRoom(ContractRoom contractRoom) {
		Date date = new Date(); // 当前时间
		Integer userId = CacheUtils.getUser().getUserId();
		contractRoom.setCreateUserId(userId); // 创建者ID
		contractRoom.setUpdateUserId(userId); // 更新者ID
		contractRoom.setCreateTime(date); // 创建时间
		contractRoom.setUpdateTime(date); // 更新时间
		contractRoomMapper.insertContractRoom(contractRoom);
	}
	
	/**
	 * @Description: 修改合同_房间表信息
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public void updateContractRoom(ContractRoom contractRoom) {
		Integer userId = CacheUtils.getUser().getUserId();
		contractRoom.setUpdateUserId(userId); //更新者ID
		contractRoom.setUpdateTime(new Date()); //更新时间
		contractRoomMapper.updateContractRoom(contractRoom);
	}
	
	/**
	 * @Description: 删除合同_房间表信息
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public void deleteContractRoomByPrimarykey(Integer id) {
		contractRoomMapper.deleteContractRoomByPrimarykey(id);
	}
	
}
