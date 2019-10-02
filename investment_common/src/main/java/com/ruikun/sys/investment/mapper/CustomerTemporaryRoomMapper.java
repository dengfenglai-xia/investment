package com.ruikun.sys.investment.mapper;

import com.ruikun.sys.investment.entity.Contract;
import com.ruikun.sys.investment.entity.ContractRoom;
import com.ruikun.sys.investment.entity.CustomerTemporaryRoom;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description: 临时客户房间表相关操作
 */
public interface CustomerTemporaryRoomMapper {
	 	
	/**
	 * @Description: 查询临时客户_房间表信息列表
	 */
	public List<CustomerTemporaryRoom> queryCustomerRoomList(CustomerTemporaryRoom customerTemporaryRoom);

	/**
	 * @Description: 新增临时客户_房间表信息
	 */
	public void insertCustomerTemporaryRoom(CustomerTemporaryRoom customerTemporaryRoom) ;

	/**
	 * @Description: 根据临时客户id删除客户房间信息
	 */
	public void deleteCustomerTemporaryRoomByTemporaryId(@Param("temporaryId") Integer temporaryId) ;
	
}
