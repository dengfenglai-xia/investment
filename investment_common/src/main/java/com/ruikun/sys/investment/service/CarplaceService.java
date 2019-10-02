package com.ruikun.sys.investment.service;

import com.ruikun.sys.investment.entity.Carplace;

import java.util.List;

/**
 * @Description: 车位表相关操作
 * @author: xiachunyu
 * @date: 2019-06-24
 */
public interface CarplaceService {
	 	
	/**
	 * @Description: 查询车位表信息列表
	 * @author: xiachunyu
	 * @date: 2019-06-24
	 */
	public List<Carplace> queryCarplaceList(Carplace carplace);
	
	/**
	 * @Description: 通过主键查询车位表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-24
	 */
	public Carplace queryCarplaceDetailByPrimarykey(Integer placeId);
		
	/**
	 * @Description: 查询车位表信息详情
	 * @author: xiachunyu
	 * @date: 2019-06-24
	 */
	public Carplace queryCarplaceDetail(Carplace carplace);

	public int queryCarplaceCount(Carplace carplace);

	/**
	 * @Description: 新增车位表信息
	 * @author: xiachunyu
	 * @date: 2019-06-24
	 */
	public void insertCarplace(Carplace carplace) ;
	
	/**
	 * @Description: 修改车位表信息
	 * @author: xiachunyu
	 * @date: 2019-06-24
	 */
	public void updateCarplace(Carplace carplace) ;
	
	/**
	 * @Description: 删除车位表信息
	 * @author: xiachunyu
	 * @date: 2019-06-24
	 */
	public void deleteCarplaceByPrimarykey(Integer placeId) ;
	
}
