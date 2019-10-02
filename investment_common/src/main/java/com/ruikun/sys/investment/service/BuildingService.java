package com.ruikun.sys.investment.service;

import com.ruikun.sys.investment.entity.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Description: 楼宇表相关操作
 * @author: xiachunyu
 * @date: 2019-06-04
 */
public interface BuildingService {
	 	
	/**
	 * @Description: 查询楼宇表信息列表
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public List<Building> queryBuildingList(Building building,String flag) throws Exception;

	public PlaceStatistics queryRoomStatistics(Room room) ;

	public PlaceStatistics queryWorkplaceStatistics(Workplace workPlace);

	public PlaceStatistics queryCarplaceStatistics(Carplace carplace);

	/**
	 * @Description: 查询楼宇基础信息列表
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public List<Building> queryBuildingBaseList(Building building);

	/**
	 * @Description: 查询楼宇数量
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public int queryBuildingCount(Building building);

	/**
	 * @Description: 通过主键查询楼宇表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public Building queryBuildingDetailByPrimarykey(Integer buildingId);
		
	/**
	 * @Description: 查询楼宇表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public Building queryBuildingDetail(Building building);
	
	/**
	 * @Description: 新增楼宇表信息
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public void insertBuilding(Building building) ;
	
	/**
	 * @Description: 修改楼宇表信息
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public void updateBuilding(Building building) ;
	
	/**
	 * @Description: 删除楼宇表信息
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public void deleteBuildingByPrimarykey(Integer buildingId) ;

	/**
	 * @Description: 导入数据
	 * @author: xiachunyu
	 * @date: 2019-06-04
	 */
	public Map importBuildingInfo(Building building,MultipartFile file) throws IOException;

}
