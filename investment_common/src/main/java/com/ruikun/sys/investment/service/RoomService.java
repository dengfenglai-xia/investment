package com.ruikun.sys.investment.service;

import com.ruikun.sys.investment.entity.PlaceStatistics;
import com.ruikun.sys.investment.entity.Room;
import com.ruikun.sys.investment.entity.RoomSum;

import java.util.List;

/**
 * @Description: 房间表相关操作
 * @author: xiachunyu
 * @date: 2019-06-04
 */
public interface RoomService {
	 	
	/**
	 * @Description: 查询房间表信息列表
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public List<Room> queryRoomList(Room room);

	/**
	 * @Description: 查询房间表信息列表
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public List<RoomSum> queryRoomSumList(Room room);

	/**
	 * @Description: 查询房间统计信息
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public PlaceStatistics queryRoomSumDetail(Room room);

	/**
	 * @Description: 查询房间基础信息列表
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public List<Room> queryRoomBaseList(Room room);

	/**
	 * @Description: 查询房间数量
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public int queryRoomCount(Room room);

	/**
	 * @Description: 通过主键查询房间表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public Room queryRoomDetailByPrimarykey(Integer roomId);
		
	/**
	 * @Description: 查询房间表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public Room queryRoomDetail(Room room);
	
	/**
	 * @Description: 新增房间表信息
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public void insertRoom(Room room) ;
	
	/**
	 * @Description: 修改房间表信息
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public void updateRoom(Room room) ;

	/**
	 * 更新签约房间状态
	 * @param contractCode
	 * @param version
	 */
	public void updateRoomState(String contractCode, Integer version, String state);

	/**
	 * @Description: 删除房间表信息
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public void deleteRoomByPrimarykey(Integer roomId) ;
	
}
